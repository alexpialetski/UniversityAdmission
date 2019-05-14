package by.epam.pialetskialiaksei.sql.connection;

import by.epam.pialetskialiaksei.sql.connection.api.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolManager {
    private final static ConnectionPoolManager INSTANCE = new ConnectionPoolManager();
    private static ConnectionPool connectionPool;
    private static final String URL = "jdbc:mysql://localhost:3306/university_admission?autoReconnect=true&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ConnectionPoolManager getInstance() {
        return INSTANCE;
    }

    private ConnectionPoolManager() {
        init();
    }

    private void init() {
        try {
           connectionPool = BasicConnectionPool.create(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connectionPool.getConnection();
    }

    public static void releaseConnection(Connection connection){
        connectionPool.releaseConnection(connection);
    }

    public static void shutDown(){
        try {
            connectionPool.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
