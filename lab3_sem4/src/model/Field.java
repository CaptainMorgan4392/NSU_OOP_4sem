package model;

public class Field {
    Integer[][] twoDimensionalArray;

    public Field() {
        twoDimensionalArray = new Integer[BehaviourConstants.DIMENSION][BehaviourConstants.DIMENSION];
    }

    public Field init() {
        Field newField = new Field();

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                newField.setByIndices(i, j, BehaviourConstants.EMPTY);
            }
        }

        FieldModifier.insertInRandomEmptyCell(newField.getTwoDimensionalArray());
        FieldModifier.insertInRandomEmptyCell(newField.getTwoDimensionalArray());

        return newField;
    }

    public Integer[][] getTwoDimensionalArray() {
        return twoDimensionalArray;
    }

    public Integer getByIndices(int i, int j) {
        return twoDimensionalArray[i][j];
    }

    public void setByIndices(int i, int j, int val) {
        twoDimensionalArray[i][j] = val;
    }
}
