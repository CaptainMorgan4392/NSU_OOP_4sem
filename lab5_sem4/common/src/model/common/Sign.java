package model.common;

public enum Sign {
    MARK,
    ZERO,
    EMPTY;

    @Override
    public String toString() {
        return switch (this) {
            case MARK -> "X";
            case ZERO -> "O";
            case EMPTY -> " ";
        };
    }
}
