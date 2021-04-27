package exceptions;

public class IllegalActionException extends GameException {
    @Override
    public String getMessage() {
        return "Wrong action!";
    }
}
