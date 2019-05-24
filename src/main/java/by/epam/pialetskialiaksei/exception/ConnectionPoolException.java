package by.epam.pialetskialiaksei.exception;

import java.io.IOException;
import java.sql.SQLException;

public class ConnectionPoolException extends Throwable {
    public ConnectionPoolException(String s, SQLException e) {
    }

    public ConnectionPoolException(String s, IOException e) {

    }
}
