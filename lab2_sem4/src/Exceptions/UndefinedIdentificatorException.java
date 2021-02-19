package Exceptions;

public class UndefinedIdentificatorException extends WorkflowException {
    @Override
    public String getMessage() {
        return "Unknown identificator!";
    }
}
