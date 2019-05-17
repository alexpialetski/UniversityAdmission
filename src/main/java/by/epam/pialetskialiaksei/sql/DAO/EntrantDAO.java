package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Entrant;
import by.epam.pialetskialiaksei.entity.Faculty;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.EntrantBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.core.util.Closer.close;

public class EntrantDAO extends SqlDAO {
    private EntrantBuilder entrantBuilder = new EntrantBuilder();

    private static final String FIND_ALL_ENTRANTS = "SELECT id, city, district, school, User_idUser, diploma_mark, isBlocked\n" +
                                                    "FROM university_admission.entrant;";
    private static final String FIND_ENTRANT = "SELECT id, city, district, school, User_idUser, diploma_mark, isBlocked\n" +
                                                "FROM university_admission.entrant\n" +
                                                "WHERE entrant.id = ?\n" +
                                                "LIMIT 1;";
    private static final String FIND_ENTRANT_BY_USER_ID = "SELECT id, city, district, school, User_idUser, diploma_mark, isBlocked\n" +
                                                            "FROM university_admission.entrant\n" +
                                                            "WHERE entrant.User_idUser = ?\n" +
                                                            "LIMIT 1;";
    private static final String INSERT_ENTRANT = "INSERT INTO university_admission.entrant(city, district, school, User_idUser,\n" +
            "                                         isBlocked, diploma_mark)\n" +
                                                        "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ENTRANT = "UPDATE entrant\n" +
                                                    "SET city=?,\n" +
                                                    "    district=?,\n" +
                                                    "    school=?,\n" +
                                                    "    User_idUser=?,\n" +
                                                    "    diploma_mark=?\n" +
//                                                    "    isBlocked=?\n" +
                                                    "WHERE id = ?\n" +
                                                    "LIMIT 1;";
    private static final String DELETE_ENTRANT = "DELETE FROM university_admission.entrant WHERE entrant.id=? LIMIT 1;";
    private static final String FIND_ALL_FACULTY_ENTRANTS = "SELECT entrant.id, entrant.city, entrant.district, entrant.school, entrant.User_idUser, entrant.diploma_mark entrant.isBlocked\n" +
                                                            "FROM university_admission.entrant\n" +
                                                            "       INNER JOIN university_admission.faculty_entrants\n" +
                                                            "                  ON university_admission.faculty_entrants.Entrant_idEntrant = university_admission.entrant.id\n" +
                                                            "WHERE faculty_entrants.Faculty_idFaculty = ?;";

    private final static Logger LOG = LogManager
            .getLogger(EntrantDAO.class);

    public void create(Entrant entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_ENTRANT,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setString(counter++, entity.getCity());
            pstmt.setString(counter++, entity.getDistrict());
            pstmt.setString(counter++, entity.getSchool());
            pstmt.setInt(counter++, entity.getUserId());
            pstmt.setInt(counter++, 0);
            pstmt.setBoolean(counter, entity.getBlockedStatus());
            pstmt.execute();
//            connection.commit();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
    }

    public void update(Entrant entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_ENTRANT);
            int counter = 1;
            pstmt.setString(counter++, entity.getCity());
            pstmt.setString(counter++, entity.getDistrict());
            pstmt.setString(counter++, entity.getSchool());
            pstmt.setInt(counter++, entity.getUserId());
            pstmt.setInt(counter++, entity.getDiplomaMark());
//            pstmt.setBoolean(counter++, entity.getBlockedStatus());

            pstmt.setInt(counter, entity.getId());

            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(Entrant entity) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_ENTRANT);
            pstmt.setInt(1, entity.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete an entrant", e);
        } finally {
            close(connection);
            close(pstmt);
        }
    }

    public Entrant find(int entityPK) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Entrant entrant = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ENTRANT);
            pstmt.setInt(1, entityPK);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                entrant = null;
            } else {
//                entrant = unmarshal(rs);
                entrant = entrantBuilder.build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return entrant;
    }

    public Entrant find(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Entrant entrant = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ENTRANT_BY_USER_ID);
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                entrant = null;
            } else {
//                entrant = unmarshal(rs);
                entrant = entrantBuilder.build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find an entrant", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return entrant;
    }

    public List<Entrant> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Entrant> users = new ArrayList<Entrant>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_ENTRANTS);
            rs = pstmt.executeQuery();
//            connection.commit();
            while (rs.next()) {
//                users.add(unmarshal(rs));
                users.add(entrantBuilder.build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all entrants", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return users;
    }

    public List<Entrant> findAllFacultyEntrants(Faculty faculty) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Entrant> facultyEntrants = new ArrayList<Entrant>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_FACULTY_ENTRANTS);
            pstmt.setInt(1, faculty.getId());
            rs = pstmt.executeQuery();
//            connection.commit();
            while (rs.next()) {
//                facultyEntrants.add(unmarshal(rs));
                facultyEntrants.add(entrantBuilder.build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find faculty entrants", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return facultyEntrants;
    }

    /**
     * Unmarshals Entrant record in database to Java Entrant instance.
     *
     * @param rs - ResultSet record
     * @return Entrant instance of this record
     */

}

