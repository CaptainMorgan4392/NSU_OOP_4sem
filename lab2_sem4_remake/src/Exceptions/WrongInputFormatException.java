package Exceptions;

public class WrongInputFormatException extends Exception {
    public WrongInputFormatException() {
        super();
    }

    public WrongInputFormatException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
