package model.common;

public class Field {
    private static class Cell {
        private Sign state;

        private Cell(Sign state) {
            this.state = state;
        }

        private Sign getState() {
            return state;
        }

        private void setState(Sign state) {
            this.state = state;
        }
    }

    private final int PER_DIMENSION = 3;
    private final Cell[][] gameField;

    public Field() {
        this.gameField = new Cell[PER_DIMENSION][PER_DIMENSION];
        for (int i = 0; i < PER_DIMENSION; ++i) {
            for (int j = 0; j < PER_DIMENSION; ++j) {
                this.gameField[i][j] = new Cell(Sign.EMPTY);
            }
        }
    }

    public Sign get(int i, int j) {
        return gameField[i][j].getState();
    }

    public void set(int i, int j, Sign sign) {
        gameField[i][j].setState(sign);
    }

    public int getCellsPerDim() {
        return PER_DIMENSION;
    }
}
