package by.epam.pialetskialiaksei.exception;

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

    @Override
    public String getMessage() {
        return "CommandException: " + super.getMessage();
    }
}
