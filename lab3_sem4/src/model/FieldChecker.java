package model;

public class FieldChecker {
    public static boolean checkWin(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                if (field[i][j] == BehaviourConstants.WIN_VALUE) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkLose(Integer[][] field) {
        if (!fieldIsFull(field)) {
            return false;
        }

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                if (i != 0 && field[i][j].equals(field[i - 1][j])) {
                    return false;
                }

                if (i != BehaviourConstants.DIMENSION - 1 && field[i][j].equals(field[i + 1][j])) {
                    return false;
                }

                if (j != 0 && field[i][j].equals(field[i][j - 1])) {
                    return false;
                }

                if (j != BehaviourConstants.DIMENSION - 1 && field[i][j].equals(field[i][j + 1])) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean fieldIsFull(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                if (field[i][j] == BehaviourConstants.EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }
}
