package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FacultyBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class FacultyDAO extends SqlDAO {
//    private FacultyBuilder facultyBuilder = new FacultyBuilder();

    private static final String FIND_ALL_FACULTIES = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu, passingScore FROM university_admission.faculty;";
    private static final String FIND_FACULTY_BY_ID = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu, passingScore FROM university_admission.faculty WHERE faculty.id = ? LIMIT 1;";
    private static final String FIND_FACULTY_BY_NAME = "SELECT id, name_ru, name_eng, total_seats, budget_seats, infoEng, infoRu, passingScore FROM university_admission.faculty WHERE faculty.name_ru = ? OR faculty.name_eng = ? LIMIT 1;";
//    private static final String INSERT_FACULTY = "INSERT INTO university_admission.faculty(faculty.name_ru, faculty.name_eng, faculty.total_seats,faculty.budget_seats, faculty.infoEng, faculty.infoRu) VALUES (?,?,?,?,?,?);";
    private static final String INSERT_FACULTY = "INSERT INTO university_admission.faculty(faculty.name_ru, faculty.name_eng, faculty.total_seats,faculty.budget_seats, faculty.passingScore) VALUES (?,?,?,?,?);";
    private static final String UPDATE_FACULTY = "UPDATE faculty SET faculty.name_ru=?, faculty.name_eng=?, faculty.total_seats=?, faculty.budget_seats=?,  faculty.infoRu = ?, faculty.infoEng = ? WHERE faculty.id=? LIMIT 1;";
    private static final String DELETE_FACULTY = "DELETE FROM university_admission.faculty WHERE faculty.id=? LIMIT 1;";

    private final static Logger LOG = LogManager
            .getLogger(FacultyDAO.class);


    public void create(Faculty entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_FACULTY,
                    Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setString(counter++, entity.getNameRu());
            pstmt.setString(counter++, entity.getNameEng());
            pstmt.setByte(counter++, entity.getTotalSeats());
            pstmt.setByte(counter++, entity.getBudgetSeats());
//            pstmt.setString(counter++, entity.getInfoRu());
//            pstmt.setString(counter++, entity.getInfoEng());

            pstmt.execute();
            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create a faculty", e);
        } finally {
            close(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void update(Faculty entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_FACULTY);
            int counter = 1;
            pstmt.setString(counter++, entity.getNameRu());
            pstmt.setString(counter++, entity.getNameEng());
            pstmt.setByte(counter++, entity.getTotalSeats());
            pstmt.setByte(counter++, entity.getBudgetSeats());
            pstmt.setString(counter++, entity.getInfoRu());
            pstmt.setString(counter++, entity.getInfoEng());

            pstmt.setInt(counter, entity.getId());

            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update a faculty", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public void delete(Faculty entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_FACULTY);
            pstmt.setInt(1, entity.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete a faculty", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public Faculty find(int entityPK) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_BY_ID);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (rs.next()) {
                faculty = null;
//                faculty = unmarshal(rs);
//                faculty = facultyBuilder.build(rs);
                faculty = (Faculty) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a faculty", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculty;
    }

    public Faculty find(String facultyName) {
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
//                faculty = unmarshal(rs);
//                faculty = facultyBuilder.build(rs);
                faculty = (Faculty)createBuilder().build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a faculty", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculty;
    }

    public List<Faculty> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Faculty> faculties = new ArrayList<Faculty>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTIES);
            rs = pstmt.executeQuery();
            connection.commit();
            while (rs.next()) {
//                faculties.add(unmarshal(rs));
//                faculties.add(facultyBuilder.build(rs));
                faculties.add((Faculty) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all faculties", e);
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

    /**
     * Unmarshals database Faculty record to java Faculty instance.
     *
     * @param rs - ResultSet record
     * @return Faculty instance of this record
     */
}
