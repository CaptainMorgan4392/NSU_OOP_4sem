package Exceptions;

public class UnknownWorkflowOperationException extends WorkflowException {
    @Override
    public String getMessage() {
        return "Unknown operation for workflow environment!";
    }
}
