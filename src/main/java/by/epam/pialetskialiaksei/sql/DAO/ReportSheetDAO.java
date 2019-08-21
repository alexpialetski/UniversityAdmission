package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.*;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.model.EntrantResultModel;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FormOfEducationBuilder;
import by.epam.pialetskialiaksei.sql.builder.MarksSumBuilder;
import by.epam.pialetskialiaksei.sql.builder.ReportSheetBuilder;
import by.epam.pialetskialiaksei.sql.builder.UserBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportSheetDAO extends SqlDAO {
    private final static Logger LOG = LogManager
            .getLogger(ReportSheetDAO.class);

    private static final String GET_REPORT_SHEET = "SELECT facultyId, User_idUser, first_name, last_name,\n" +
                                                    "       email, preliminary_sum, diplomaMark, total_sum\n" +
                                                    "FROM faculties_report_sheet\n" +
                                                    "WHERE facultyId=? ORDER BY total_sum DESC ;";
    private static final String GET_SCORE = "CALL getScore(?, ?);";
    private static final String GET_BUDGET_ENTRANTS = "CALL getBudgetEntrants(?, ?);";
    private static final String GET_NOT_BUDGET_ENTRANTS = "CALL getNotBudgetEntrants(?, ?, ?);";
    private static final String GET_FORM_OF_EDUCATION = "SELECT formofeducation.id, formofeducation.formRu, formofeducation.formEng\n" +
                                                            "from result\n" +
                                                            "       inner join formofeducation on result.form_id = formofeducation.id\n" +
                                                            "where User_idUser = ?;";
    private static final String RESULTS_EXISTS = "SELECT EXISTS(SELECT 1 FROM result) as exist;";
    private static final String DELETE_RESULTS = "DELETE FROM result;";
    private static final String FIND_ENTRANT_RESULT= "SELECT result.User_idUser, user.first_name, user.last_name, user.email,\n" +
                                                        "       f.diplomaMark, f.preliminary_sum, f.total_sum,\n" +
                                                        "       fe.id as form_id, fe.formEng, fe.formRu\n" +
                                                        "FROM result\n" +
                                                        "       INNER JOIN user on result.User_idUser = user.id\n" +
                                                        "       INNER JOIN formofeducation fe on result.form_id = fe.id\n" +
                                                        "INNER JOIN faculties_report_sheet f on result.User_idUser = f.User_idUser\n" +
                                                        "WHERE faculty_id = ?;";

    public List<EntrantReportSheet> getReport(int facultyId) throws DaoException {
        List<EntrantReportSheet> entrantsResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(GET_REPORT_SHEET);
            pstmt.setInt(1, facultyId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                entrantsResults.add((EntrantReportSheet) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return entrantsResults;
    }

    public List<EntrantResultModel> getResultReport(int facultyId) throws DaoException {
        List<EntrantResultModel> entrantsResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ENTRANT_RESULT);
            pstmt.setInt(1, facultyId);
            rs = pstmt.executeQuery();
            UserBuilder userBuilder = new UserBuilder();
            FormOfEducationBuilder formOfEducationBuilder = new FormOfEducationBuilder();
            MarksSumBuilder marksSumBuilder = new MarksSumBuilder();

            while (rs.next()) {
                User user = userBuilder.buildShort(rs);
                FormOfEducation formOfEducation = formOfEducationBuilder.buildForeign(rs);
                MarksSum marksSum = marksSumBuilder.build(rs);
                entrantsResults.add(new EntrantResultModel(user, formOfEducation, marksSum));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return entrantsResults;
    }

    public int getScore(int facultyId, int numberOfSeats) throws DaoException {
        Connection connection = null;
        CallableStatement pstmt = null;
        int score = 0;
        try {
            connection = getConnection();
//            pstmt = connection.prepareStatement(GET_SCORE);
            pstmt = connection.prepareCall(GET_SCORE);
            pstmt.setInt(1, facultyId);
            pstmt.setInt(2, numberOfSeats);
            pstmt.execute();
            try(ResultSet resultSet = pstmt.getResultSet()){
                if (resultSet!=null && resultSet.next()) {
                    return resultSet.getInt(Fields.REPORT_SHEET_ENTRANT_TOTAL_SUM);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
        return score;
    }

    public void makeResult(List<Faculty> faculties) throws DaoException {
        List<EntrantReportSheet> entrantsResults = new ArrayList<>();
        Connection connection = null;
        CallableStatement pstmtForBudget = null;
        CallableStatement pstmtForNotBudget = null;

        try {
            connection = getConnection();
            for (Faculty faculty : faculties) {
                pstmtForBudget = connection.prepareCall(GET_BUDGET_ENTRANTS);
                pstmtForBudget.setInt(1, faculty.getId());
                pstmtForBudget.setInt(2, faculty.getBudgetSeats());

                pstmtForNotBudget = connection.prepareCall(GET_NOT_BUDGET_ENTRANTS);
                pstmtForNotBudget.setInt(1, faculty.getId());
                pstmtForNotBudget.setInt(2, faculty.getTotalSeats());
                pstmtForNotBudget.setInt(3, faculty.getBudgetSeats());

                pstmtForBudget.executeQuery();
                pstmtForNotBudget.executeQuery();
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmtForBudget);
            close(pstmtForNotBudget);
        }
    }

    public boolean areResultExists() throws DaoException {
        boolean exists = false;
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(RESULTS_EXISTS);
            rs = pstmt.executeQuery();
            rs.next();
            exists = (rs.getInt("exist") == 1);
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return exists;
    }

    public void deleteResults() throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_RESULTS);
            pstmt.execute();
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public FormOfEducation getFormOfEducation(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FormOfEducation formOfEducation = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(GET_FORM_OF_EDUCATION);
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                FormOfEducationBuilder formOfEducationBuilder = new FormOfEducationBuilder();
                formOfEducation =  formOfEducationBuilder.build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return formOfEducation;
    }



    @Override
    protected SetBuilder createBuilder() {
        return new ReportSheetBuilder();
    }
}
