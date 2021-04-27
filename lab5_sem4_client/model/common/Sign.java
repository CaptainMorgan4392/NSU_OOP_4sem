package model.common;

import java.io.Serializable;

public enum Sign implements Serializable {
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
