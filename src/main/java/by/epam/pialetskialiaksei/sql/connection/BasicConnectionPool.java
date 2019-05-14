package by.epam.pialetskialiaksei.sql.connection;

import by.epam.pialetskialiaksei.sql.connection.api.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicConnectionPool implements ConnectionPool {

    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 5;
    private static final Semaphore SEMAPHORE = new Semaphore(MAX_POOL_SIZE);
    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = LogManager.getLogger(BasicConnectionPool.class);

    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }

    static BasicConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
    }

    @Override
    public Connection getConnection() {

        try {
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOCK.lock();

        if (connectionPool.isEmpty() && usedConnections.size() < MAX_POOL_SIZE) {
            try {
                connectionPool.add(createConnection(url, user, password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection connection = connectionPool
                .remove(connectionPool.size() - 1);
        usedConnections.add(connection);

        LOCK.unlock();

        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        LOCK.lock();
        connectionPool.add(connection);
        SEMAPHORE.release();
        LOCK.unlock();
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void shutdown() throws SQLException {
        for (Connection c : usedConnections) {
            c.close();
            LOGGER.info("closed connection");
        }
        usedConnections.clear();
        for (Connection c : connectionPool) {
            c.close();
            LOGGER.info("closed connection");
        }
        connectionPool.clear();
    }

    public static void main(String[] args) {
        ConnectionPool connectionPool = null;
        try {
            connectionPool = BasicConnectionPool.create("jdbc:mysql://localhost:3306/university_admission?autoReconnect=true&useSSL=false",
                    "root",
                    "root");
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from entrant");
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    System.out.println(rsmd.getColumnName(i + 1) + " --- " + rs.getString(i + 1));
                }
                System.out.println("\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connectionPool.shutdown();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
