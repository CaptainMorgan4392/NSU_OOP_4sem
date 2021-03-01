package Exceptions;

public class UnrecognizedIdException extends Exception {
    public UnrecognizedIdException() {
        super();
    }

    public UnrecognizedIdException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
