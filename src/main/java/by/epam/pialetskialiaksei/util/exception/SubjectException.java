package by.epam.pialetskialiaksei.util.exception;

public class SubjectException extends Exception {
    public SubjectException() {
        super();
    }

    public SubjectException(String message) {
        super(message);
    }

    public SubjectException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return "SubjectException: " + super.getMessage();
    }
}
