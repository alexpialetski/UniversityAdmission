package by.epam.pialetskialiaksei.sql.connection;

import by.epam.pialetskialiaksei.sql.connection.api.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BasicConnectionPool implements ConnectionPool {

    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private int maxPoolSize;
    private Semaphore SEMAPHORE;
    private static final Lock LOCK = new ReentrantLock();
    private static final Logger LOGGER = LogManager.getLogger(BasicConnectionPool.class);

    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool, int maxPoolSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxPoolSize = maxPoolSize;
        this.connectionPool = connectionPool;
        SEMAPHORE = new Semaphore(maxPoolSize);
    }

    static BasicConnectionPool create(String url, String user, String password, int initialPoolSize, int maxPoolSize) throws SQLException {
        List<Connection> pool = new ArrayList<>(initialPoolSize);
        for (int i = 0; i < initialPoolSize; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool, maxPoolSize);
    }

    @Override
    public Connection getConnection() {

        try {
            SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOCK.lock();

        if (connectionPool.isEmpty() && usedConnections.size() < maxPoolSize) {
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
}
