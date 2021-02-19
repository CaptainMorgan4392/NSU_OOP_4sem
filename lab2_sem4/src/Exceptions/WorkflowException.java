package Exceptions;

public class WorkflowException extends Exception {
    String msg = "An error has occured with workflow environment";

    @Override
    public String getMessage() {
        return msg;
    }
}
