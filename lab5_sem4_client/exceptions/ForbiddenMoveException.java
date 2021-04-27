package exceptions;

public class ForbiddenMoveException extends GameException {
    @Override
    public String getMessage() {
        return "Forbidden move!";
    }
}
