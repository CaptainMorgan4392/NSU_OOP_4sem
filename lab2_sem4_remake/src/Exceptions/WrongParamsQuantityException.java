package Exceptions;

public class WrongParamsQuantityException extends Exception {
    public WrongParamsQuantityException() {
        super();
    }

    public WrongParamsQuantityException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
