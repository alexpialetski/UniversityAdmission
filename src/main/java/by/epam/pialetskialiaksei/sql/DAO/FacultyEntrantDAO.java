package by.epam.pialetskialiaksei.sql.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.FacultyEntrant;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.exception.DaoException;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.FacultyBuilder;
import by.epam.pialetskialiaksei.sql.builder.FacultyEntrantBuilder;
import by.epam.pialetskialiaksei.sql.builder.UserBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FacultyEntrantDAO extends SqlDAO {
//    private FacultyEntrantBuilder facultyEntrantBuilder = new FacultyEntrantBuilder();

    private static final String FIND_ALL_FACULTY_ENTRANTS = "SELECT * FROM university_admission.faculty_entrants;";
    private static final String FIND_FACULTY_ENTRANT_BY_ID = "SELECT * FROM university_admission.faculty_entrants WHERE faculty_entrants.id = ? LIMIT 1;";
    private static final String FIND_FACULTY_ENTRANT_BY_FOREIGN_KEYS = "SELECT * FROM university_admission.faculty_entrants WHERE faculty_entrants.Faculty_idFaculty = ? AND faculty_entrants.Entrant_idEntrant = ? LIMIT 1;";
    private static final String FIND_FACULTY_ENTRANT_BY_ENTRANT_ID = "SELECT id, Entrant_idEntrant, Faculty_idFaculty FROM university_admission.faculty_entrants WHERE faculty_entrants.Entrant_idEntrant = ? LIMIT 1;";
    private static final String FIND_FACULTY_BY_ENTRANT_ID = "SELECT faculty.id, faculty.name_ru, faculty.name_eng, faculty.total_seats, faculty.budget_seats, faculty.infoEng, faculty.infoRu, faculty.passingScore\n" +
            "FROM faculty_entrants\n" +
            "       INNER JOIN faculty ON faculty_entrants.Faculty_idFaculty = faculty.id\n" +
            "WHERE Entrant_idEntrant = ?;";
    private static final String FIND_FACULTY_USER_BY_FACULTY_ID = "SELECT user.id as User_idUser,user.first_name,user.last_name,user.email,user.password, role.role_name\n" +
            "FROM university_admission.faculty_entrants\n" +
            "       inner join university_admission.entrant e\n" +
            "                  on faculty_entrants.Entrant_idEntrant = e.id\n" +
            "       inner join user on e.User_idUser = user.id\n" +
            "       inner join role on user.role_id = role.id\n" +
            "WHERE faculty_entrants.Faculty_idFaculty = ?;";
    private static final String INSERT_FACULTY_ENTRANT = "INSERT INTO university_admission.faculty_entrants(faculty_entrants.Faculty_idFaculty,faculty_entrants.Entrant_idEntrant) VALUES (?,?);";
    private static final String DELETE_FACULTY_ENTRANT = "DELETE FROM university_admission.faculty_entrants WHERE faculty_entrants.id=? LIMIT 1;";
    private static final String DELETE_FACULTY_ENTRANT_BY_FOREIGN_KEYS = "DELETE FROM university_admission.faculty_entrants WHERE faculty_entrants.Faculty_idFaculty=? and faculty_entrants.Entrant_idEntrant=? LIMIT 1;";

    private final static Logger LOG = LogManager
            .getLogger(FacultyEntrantDAO.class);

    public void create(FacultyEntrant entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_FACULTY_ENTRANT,
                    Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setInt(counter++, entity.getFacultyId());
            pstmt.setInt(counter, entity.getEntrantId());

            pstmt.execute();
//            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not create a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not create a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void delete(FacultyEntrant entity) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_FACULTY_ENTRANT_BY_FOREIGN_KEYS);
            pstmt.setInt(1, entity.getFacultyId());
            pstmt.setInt(2, entity.getEntrantId());
            pstmt.execute();
//            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can not delete a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not delete a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public List<User> findUsers(int entityPK) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacultyEntrant facultyEntrant = null;
        List<User> users = new ArrayList<>();
        UserBuilder userBuilder = new UserBuilder();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_USER_BY_FACULTY_ID);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(userBuilder.buildForeign(rs));
            }
//            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not find a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return users;
    }

    public FacultyEntrant find(Entrant entrant) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacultyEntrant facultyEntrant = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_ENTRANT_BY_ENTRANT_ID);
            pstmt.setInt(1, entrant.getId());
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                facultyEntrant = null;
            } else {
//                facultyEntrant = unmarshal(rs);
//                facultyEntrant = facultyEntrantBuilder.build(rs);
                facultyEntrant = (FacultyEntrant) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not find a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultyEntrant;
    }

    public Faculty find(int entrantId) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacultyEntrant facultyEntrant = null;
        Faculty faculty = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_FACULTY_BY_ENTRANT_ID);
            pstmt.setInt(1, entrantId);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (rs.next()) {
                FacultyBuilder facultyBuilder= new FacultyBuilder();
                faculty = facultyBuilder.build(rs);
            }
            if (!rs.next()) {
                facultyEntrant = null;
            } else {
//                facultyEntrant = unmarshal(rs);
//                facultyEntrant = facultyEntrantBuilder.build(rs);
                facultyEntrant = (FacultyEntrant) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not find a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return faculty;
    }

    public FacultyEntrant find(FacultyEntrant facultyEntrants) throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacultyEntrant facultyEntrant = null;
        try {
            connection = getConnection();
            pstmt = connection
                    .prepareStatement(FIND_FACULTY_ENTRANT_BY_FOREIGN_KEYS);
            pstmt.setInt(1, facultyEntrants.getFacultyId());
            pstmt.setInt(2, facultyEntrants.getEntrantId());

            rs = pstmt.executeQuery();
            connection.commit();
            if (!rs.next()) {
                facultyEntrant = null;
            } else {
//                facultyEntrant = unmarshal(rs);
//                facultyEntrant = facultyEntrantBuilder.build(rs);
                facultyEntrant = (FacultyEntrant) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find a faculty entrant", e);
//            rollback(connection);
//            LOG.error("Can not find a faculty entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultyEntrant;
    }

    public List<FacultyEntrant> findAll() throws DaoException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<FacultyEntrant> facultyEntrants = new ArrayList<FacultyEntrant>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTY_ENTRANTS);
            rs = pstmt.executeQuery();
            connection.commit();
            while (rs.next()) {
//                facultyEntrants.add(unmarshal(rs));
//                facultyEntrants.add(facultyEntrantBuilder.build(rs));
                facultyEntrants.add((FacultyEntrant) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            throw new DaoException("Can not find all faculty entrants", e);
//            rollback(connection);
//            LOG.error("Can not find all faculty entrants", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultyEntrants;
    }

    @Override
    protected SetBuilder createBuilder() {
        return new FacultyEntrantBuilder();
    }
}
