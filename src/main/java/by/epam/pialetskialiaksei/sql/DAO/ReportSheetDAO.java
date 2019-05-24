package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.EntrantReportSheet;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.FormOfEducation;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FormOfEducationBuilder;
import by.epam.pialetskialiaksei.sql.builder.ReporstSheetBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportSheetDAO extends SqlDAO {
    private final static Logger LOG = LogManager
            .getLogger(ReportSheetDAO.class);

    private static final String GET_REPORT_SHEET = "SELECT * FROM faculties_report_sheet WHERE facultyId=?;";
    private static final String GET_SCORE = "CALL getScore(?, ?);";
    private static final String GET_BUDGET_ENTRANTS = "CALL getBudgetEntrants(?, ?);";
    private static final String GET_NOT_BUDGET_ENTRANTS = "CALL getNotBudgetEntrants(?, ?, ?);";
    private static final String GET_FORM_OF_EDUCATION = "SELECT formofeducation.id, formofeducation.form_ru, formofeducation.form_eng\n" +
                                                            "from result\n" +
                                                            "       inner join formofeducation on result.form_id = formofeducation.id\n" +
                                                            "where email = ?;";
    private static final String RESULTS_EXISTS = "SELECT EXISTS(SELECT 1 FROM result) as exist;";
    private static final String DELETE_RESULTS = "DELETE FROM result;";

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
//            connection.commit();
            while (rs.next()) {
                entrantsResults.add((EntrantReportSheet) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return entrantsResults;
    }

    public int getScore(int facultyId, int numberOfSeats) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(GET_SCORE);
            pstmt.setInt(1, facultyId);
            pstmt.setInt(2, numberOfSeats);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (rs.next()) {
                return rs.getInt(Fields.REPORT_SHEET_ENTRANT_TOTAL_SUM);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return 0;
    }

    public void makeResult(List<Faculty> faculties) throws DaoException {
        List<EntrantReportSheet> entrantsResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmtForBudget = null;
        PreparedStatement pstmtForNotBudget = null;

        try {
            connection = getConnection();
            for (Faculty faculty : faculties) {
                pstmtForBudget = connection.prepareStatement(GET_BUDGET_ENTRANTS);
                pstmtForBudget.setInt(1, faculty.getId());
                pstmtForBudget.setInt(2, faculty.getBudgetSeats());

                pstmtForNotBudget = connection.prepareStatement(GET_NOT_BUDGET_ENTRANTS);
                pstmtForNotBudget.setInt(1, faculty.getId());
                pstmtForNotBudget.setInt(2, faculty.getTotalSeats());
                pstmtForNotBudget.setInt(3, faculty.getBudgetSeats());

                pstmtForBudget.executeQuery();
                pstmtForNotBudget.executeQuery();
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
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
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
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
//            pstmt.executeQuery();
            pstmt.execute();
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
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
            pstmt.setString(1, user.getEmail());
            rs = pstmt.executeQuery();
//            connection.commit();
            if (rs.next()) {
                FormOfEducationBuilder formOfEducationBuilder = new FormOfEducationBuilder();
                formOfEducation =  formOfEducationBuilder.build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not get report sheet", e);
//            rollback(connection);
//            LOG.error("Can not get report sheet", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return formOfEducation;
    }


    @Override
    protected SetBuilder createBuilder() {
        return new ReporstSheetBuilder();
    }
}
