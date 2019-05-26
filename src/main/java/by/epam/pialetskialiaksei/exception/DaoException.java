package by.epam.pialetskialiaksei.exception;

public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "DaoException: " + super.getMessage();
    }
}
