package Exceptions;

public class RedefinitionException extends WorkflowException {
    @Override
    public String getMessage() {
        return "This id already used in config file!";
    }
}
