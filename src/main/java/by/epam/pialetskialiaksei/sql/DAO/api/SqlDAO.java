package by.epam.pialetskialiaksei.sql.DAO.api;

import by.epam.pialetskialiaksei.sql.builder.api.SetBuilder;
import by.epam.pialetskialiaksei.sql.connection.ConnectionPoolManager;

import java.sql.*;

public abstract class SqlDAO {
    protected Connection getConnection(){
        return ConnectionPoolManager.getInstance().getConnection();
    }
    protected void releaseConnection(Connection connection){
        ConnectionPoolManager.getInstance().releaseConnection(connection);
    }
    protected abstract SetBuilder createBuilder();
    protected void rollback(Connection connection){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void close(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void close(ResultSet resultSet){
        try {
            if(resultSet!=null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    protected void close(Statement statement){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
