package Exceptions;

public class WrongTestResultException extends WorkflowException {
    @Override
    public String getMessage() {
        return "Wrong test result! :(";
    }
}
