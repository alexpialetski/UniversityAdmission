package by.epam.pialetskialiaksei.sql.connection;

import by.epam.pialetskialiaksei.exception.ConnectionPoolException;
import by.epam.pialetskialiaksei.sql.connection.api.ConnectionPool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPoolManager {
    private final static ConnectionPoolManager INSTANCE = new ConnectionPoolManager();
    private static ConnectionPool connectionPool;

    private static String url;
    private static String user;
    private static String password;
    private static int maxPoolCapacity;
    private static int initialPoolCapacity;

    public static ConnectionPoolManager getInstance() {
        return INSTANCE;
    }

    private ConnectionPoolManager() {
        try {
            init();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        }
    }

    private void init() throws ConnectionPoolException {
        try {
            InputStream inputStream = ConnectionPool.class.getResourceAsStream("/db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            maxPoolCapacity = Integer.parseInt(properties.getProperty("maxPoolCapacity"));
            initialPoolCapacity = Integer.parseInt(properties.getProperty("initialPoolCapacity"));
            connectionPool = BasicConnectionPool.create(url, user, password, initialPoolCapacity, maxPoolCapacity);
        } catch (SQLException e) {
            throw new ConnectionPoolException("Driver manager failed to connect to DataBase", e);
        } catch (IOException e) {
            throw new ConnectionPoolException("can't load property file", e);
        }
    }

    public static Connection getConnection() {
        return connectionPool.getConnection();
    }

    public static void releaseConnection(Connection connection) {
        connectionPool.releaseConnection(connection);
    }

    public static void shutDown() {
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
