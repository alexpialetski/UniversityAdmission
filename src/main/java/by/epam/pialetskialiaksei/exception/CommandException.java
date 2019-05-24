package by.epam.pialetskialiaksei.exception;

import java.io.IOException;
import java.sql.SQLException;

public class CommandException extends Throwable {
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
