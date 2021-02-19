package Exceptions;

public class WrongParamsQuantityException extends WorkflowException {
    @Override
    public String getMessage() {
        return "Wrong quantity of params!";
    }
}
