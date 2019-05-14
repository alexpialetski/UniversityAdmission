package by.epam.pialetskialiaksei.sql.connection.api;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection();
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
    void shutdown() throws SQLException;
}
