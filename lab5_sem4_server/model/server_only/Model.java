package model.server_only;

import exceptions.IllegalActionException;
import model.common.Field;
import model.common.Sign;
import protocol.response.ErrorResponse;
import protocol.response.MoveResponse;
import protocol.response.Response;
import server.Server;

public class Model {
    public enum ModelState {
        WAITING_FOR_PLAYERS,

        MOVE_OF_X,
        MOVE_OF_O,

        MARKS_WIN,
        ZEROS_WIN,
        DRAW
    }

    private final Field field;
    private ModelState state;

    public Model() {
        this.field = new Field();
        this.state = ModelState.WAITING_FOR_PLAYERS;
    }

    public Response<?> doMove(int i, int j, Sign sign) {
        final boolean checkRow = (0 <= i && i < field.getCellsPerDim());
        final boolean checkCol = (0 <= j && j < field.getCellsPerDim());
        if (!checkRow || !checkCol) {
            return new ErrorResponse(new IllegalActionException());
        }

        final boolean wrongMoveOfX = (state == ModelState.MOVE_OF_O && sign != Sign.ZERO);
        final boolean wrongMoveOfO = (state == ModelState.MOVE_OF_X && sign != Sign.MARK) ;
        if (wrongMoveOfO || wrongMoveOfX) {
            return new ErrorResponse(new IllegalActionException());
        }

        field.set(i, j, sign);

        ModelState checkIfGameEnd = checkForWin();
        if (checkIfGameEnd != null) {
            setState(checkIfGameEnd);
        }

        return new MoveResponse(field);
    }

    private ModelState checkForWin() {
        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            if (field.get(i, 0) == field.get(i, 1)
                    && field.get(i, 1) == field.get(i, 2)
                        && field.get(i, 0) != Sign.EMPTY)
            {
                switch (field.get(i, 0)) {
                    case MARK -> {
                        return ModelState.MARKS_WIN;
                    }
                    case ZERO -> {
                        return ModelState.ZEROS_WIN;
                    }
                }
            }
        }

        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            if (field.get(0, i) == field.get(1, i)
                    && field.get(1, i) == field.get(2, i)
                    && field.get(0, i) != Sign.EMPTY)
            {
                switch (field.get(0, i)) {
                    case MARK -> {
                        return ModelState.MARKS_WIN;
                    }
                    case ZERO -> {
                        return ModelState.ZEROS_WIN;
                    }
                }
            }
        }

        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            if (field.get(0, 0) == field.get(1, 1)
                    && field.get(1, 1) == field.get(2, 2)
                    && field.get(0, 0) != Sign.EMPTY)
            {
                switch (field.get(0, 0)) {
                    case MARK -> {
                        return ModelState.MARKS_WIN;
                    }
                    case ZERO -> {
                        return ModelState.ZEROS_WIN;
                    }
                }
            }
        }

        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            if (field.get(2, 0) == field.get(1, 1)
                    && field.get(1, 1) == field.get(0, 2)
                    && field.get(1, 1) != Sign.EMPTY)
            {
                switch (field.get(1, 1)) {
                    case MARK -> {
                        return ModelState.MARKS_WIN;
                    }
                    case ZERO -> {
                        return ModelState.ZEROS_WIN;
                    }
                }
            }
        }

        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            for (int j = 0; j < field.getCellsPerDim(); ++j) {
                if (field.get(i, j) == Sign.EMPTY) {
                    return null;
                }
            }
        }

        return ModelState.DRAW;
    }

    public void setState(ModelState state) {
        this.state = state;
    }

    public ModelState getState() {
        return state;
    }
}
