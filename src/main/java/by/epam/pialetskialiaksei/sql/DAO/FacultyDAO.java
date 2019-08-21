package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FacultyBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacultyDAO extends SqlDAO {
    private static final String FIND_ALL_FACULTIES = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu FROM university_admission.faculty;";
    private static final String FIND_FACULTY_BY_ID = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu FROM university_admission.faculty WHERE faculty.id = ? LIMIT 1;";
    private static final String FIND_FACULTY_BY_NAME = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu FROM university_admission.faculty WHERE faculty.name_ru = ? OR faculty.name_eng = ? LIMIT 1;";
    private static final String INSERT_FACULTY = "INSERT INTO university_admission.faculty(faculty.name_ru, faculty.name_eng, faculty.total_seats,faculty.budget_seats) VALUES (?,?,?,?);";
    private static final String UPDATE_FACULTY = "UPDATE faculty SET faculty.name_ru=?, faculty.name_eng=?, faculty.total_seats=?, faculty.budget_seats=?,  faculty.infoRu = ?, faculty.infoEng = ? WHERE faculty.id=? LIMIT 1;";
    private static final String DELETE_FACULTY = "DELETE FROM university_admission.faculty WHERE faculty.id=? LIMIT 1;";

    private final static Logger LOG = LogManager
            .getLogger(FacultyDAO.class);



    public void update(Faculty entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_FACULTY);
            int counter = 1;
            pstmt.setString(counter++, entity.getNameRu());
            pstmt.setString(counter++, entity.getNameEng());
            pstmt.setInt(counter++, entity.getTotalSeats());
            pstmt.setInt(counter++, entity.getBudgetSeats());
            pstmt.setString(counter++, entity.getInfoRu());
            pstmt.setString(counter++, entity.getInfoEng());

            pstmt.setInt(counter, entity.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can not update a faculty", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public Faculty find(int entityPK) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_BY_ID);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                faculty = (Faculty) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculty;
    }

    public Faculty find(String facultyName) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_BY_NAME);
            pstmt.setString(1, facultyName);
            pstmt.setString(2, facultyName);
            rs = pstmt.executeQuery();
            connection.commit();
            if (!rs.next()) {
                faculty = null;
            } else {
                faculty = (Faculty)createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculty;
    }

    public List<Faculty> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faculty> faculties = new ArrayList<Faculty>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTIES);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                faculties.add((Faculty) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all faculties", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculties;
    }

    @Override
    protected SetBuilder createBuilder() {
        return new FacultyBuilder();
    }
}
