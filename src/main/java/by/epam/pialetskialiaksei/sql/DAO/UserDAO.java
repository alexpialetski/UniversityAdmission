package by.epam.pialetskialiaksei.sql.DAO;

import by.epam.pialetskialiaksei.Fields;
import by.epam.pialetskialiaksei.entity.Role;
import by.epam.pialetskialiaksei.entity.User;
import by.epam.pialetskialiaksei.sql.DAO.api.SqlDAO;
import by.epam.pialetskialiaksei.sql.builder.UserBuilder;
import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends SqlDAO {
//    private UserBuilder userBuilder = new UserBuilder();

    private static final String FIND_ALL_USERS = "SELECT user.id, user.first_name, user.last_name, user.email, user.password, role.role_name, user.lang\n" +
                                                    "FROM university_admission.user\n" +
                                                    "       inner join role on user.role_id = role.id;";
    private static final String FIND_USER = "SELECT user.id, user.first_name, user.last_name, user.email, user.password, role.role_name, user.lang\n" +
                                                "FROM university_admission.user\n" +
                                                "       inner join role on user.role_id = role.id\n" +
                                                "WHERE user.id = ?\n" +
                                                "LIMIT 1;\n";
    private static final String FIND_USER_BY_EMAIL_AND_PASS = "SELECT user.id, user.first_name, user.last_name, user.email, user.password, role.role_name, user.lang\n" +
                                                                "FROM university_admission.user\n" +
                                                                "       inner join role on user.role_id = role.id\n" +
                                                                "WHERE user.email = ?\n" +
                                                                "  AND user.password = ?\n" +
                                                                "LIMIT 1;";
    private static final String FIND_USER_BY_EMAIL = "SELECT user.id, user.first_name, user.last_name, user.email, user.password, role.role_name, user.lang\n" +
                                                        "FROM university_admission.user\n" +
                                                        "            inner join role on user.role_id = role.id\n" +
                                                        "WHERE user.email = ?\n" +
                                                        "LIMIT 1;";
    private static final String INSERT_USER = "INSERT INTO university_admission.user(user.first_name, user.last_name, user.email, user.password, user.role_id, user.lang)\n" +
                                                "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_USER = "UPDATE user SET first_name=?,last_name=?,email=?,password=?,role_id=?, lang=? WHERE user.id= ? LIMIT 1;";
    private static final String UPDATE_USER_FIRST_LAST_NAME = "UPDATE user SET first_name=?,last_name=? WHERE user.id= ? LIMIT 1;";
    private static final String DELETE_USER = "DELETE FROM university_admission.user WHERE user.id=? LIMIT 1;";

    private final static Logger LOG = LogManager
            .getLogger(UserDAO.class);

    public void create(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        try {

            connection = getConnection();
            pstmt = connection.prepareStatement(INSERT_USER,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            int counter = 1;
            pstmt.setString(counter++, user.getFirstName());
            pstmt.setString(counter++, user.getLastName());
            pstmt.setString(counter++, user.getEmail());
            pstmt.setString(counter++, user.getPassword());
            pstmt.setInt(counter++, Role.valueOf(user.getRole().toUpperCase()).getVal());
            pstmt.setString(counter++, user.getLang());
//            pstmt.setBoolean(counter, user.getActiveStatus());

            pstmt.execute();
//            connection.commit();
            generatedKeys = pstmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(Fields.GENERATED_KEY));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not create a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(generatedKeys);
        }
    }

    public void update(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_USER);
            int counter = 1;
            pstmt.setString(counter++, user.getFirstName());
            pstmt.setString(counter++, user.getLastName());
            pstmt.setString(counter++, user.getEmail());
            pstmt.setString(counter++, user.getPassword());
//            pstmt.setString(counter++, user.getRole());
            pstmt.setInt(counter++, Role.valueOf(user.getRole().toUpperCase()).getVal());
            pstmt.setString(counter++, user.getLang());
//            pstmt.setBoolean(counter++, user.getActiveStatus());
            pstmt.setInt(counter++, user.getId());

            pstmt.executeUpdate();
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void updateName(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(UPDATE_USER_FIRST_LAST_NAME);
            int counter = 1;
            pstmt.setString(counter++, user.getFirstName());
            pstmt.setString(counter++, user.getLastName());

            pstmt.setInt(counter, user.getId());

            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not update a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    public void delete(User user) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(DELETE_USER);
            pstmt.setInt(1, user.getId());

            pstmt.execute();
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not delete a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see ua.nure.norkin.SummaryTask4.repository.Repository#find(int)
     */
    public User find(int userId) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_USER);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            connection.commit();
            if (!rs.next()) {
                user = null;
            } else {
//                user = unmarshal(rs);
//                user = userBuilder.build(rs);
                user = (User) createBuilder().build(rs);
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return user;
    }

    public User find(String email, String password) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASS);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                user = null;
            } else {
//                user = unmarshal(rs);
//                user = userBuilder.build(rs);
                user = (User) createBuilder().build(rs);
            }
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return user;
    }

    public User find(String email) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_USER_BY_EMAIL);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
//            connection.commit();
            if (!rs.next()) {
                user = null;
            } else {
//                user = unmarshal(rs);
//                user = userBuilder.build(rs);
                user = (User) createBuilder().build(rs);
            }
//            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find a user", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return user;
    }

    public List<User> findAll() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(FIND_ALL_USERS);
            rs = pstmt.executeQuery();
            connection.commit();
            while (rs.next()) {
//                users.add(unmarshal(rs));
//                users.add(userBuilder.build(rs));
                users.add((User) createBuilder().build(rs));
            }
        } catch (SQLException e) {
            rollback(connection);
            LOG.error("Can not find all users", e);
        } finally {
            releaseConnection(connection);
            close(pstmt);
            close(rs);
        }
        return users;
    }

    @Override
    protected SetBuilder createBuilder() {
        return new UserBuilder();
    }

    /**
     * Unmarshals User record in database to Java instance.
     *
     * @param rs - record from result set
     * @return User instance of database record.
     */
}

