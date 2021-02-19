package Exceptions;

public class WrongConfigInformationException extends WorkflowException {
    @Override
    public String getMessage() {
        return "Wrong config information!";
    }
}
