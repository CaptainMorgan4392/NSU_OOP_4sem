package model;

import java.util.ArrayList;

public class FieldModifier {
    static void insertInRandomEmptyCell(Integer[][] field) {
        ArrayList <Integer> indices = new ArrayList<>();

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                int curIndex = 4 * i + j;

                if (field[i][j] == BehaviourConstants.EMPTY) {
                    indices.add(curIndex);
                }
            }
        }

        int index = (int)(Math.random() * indices.size());

        field[indices.get(index) / 4][indices.get(index) % 4] = BehaviourConstants.DEFAULT_VALUE;
    }

    private static ArrayList<Integer> collapse(ArrayList <Integer> numbersLine) {
        ArrayList <Integer> newLine = new ArrayList<>();

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            int currentInLine = numbersLine.get(i);

            if (currentInLine != BehaviourConstants.EMPTY) {
                newLine.add(currentInLine);
                if (newLine.size() > 1 && newLine.get(newLine.size() - 1).equals(newLine.get(newLine.size() - 2))) {
                    newLine.set(newLine.size() - 2, 2 * newLine.get(newLine.size() - 2));
                    newLine.remove(newLine.size() - 1);
                }
            }
        }

        return newLine;
    }

    public static void moveUp(Field field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field.getByIndices(BehaviourConstants.DIMENSION - j - 1, i));
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field.getByIndices(BehaviourConstants.DIMENSION - j - 1, i).equals(numberLine.get(j))) {
                    field.setByIndices(BehaviourConstants.DIMENSION - j - 1, i, numberLine.get(j));
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field.getByIndices(BehaviourConstants.DIMENSION - j - 1, i).equals(BehaviourConstants.EMPTY)) {
                    field.setByIndices(BehaviourConstants.DIMENSION - j - 1, i, BehaviourConstants.EMPTY);
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field.getTwoDimensionalArray());
        }
    }

    public static void moveDown(Field field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field.getByIndices(j, i));
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field.getByIndices(j, i).equals(numberLine.get(j))) {
                    field.setByIndices(j, i, numberLine.get(j));
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field.getByIndices(j, i).equals(BehaviourConstants.EMPTY)) {
                    field.setByIndices(j, i, BehaviourConstants.EMPTY);
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field.getTwoDimensionalArray());
        }
    }

    public static void moveLeft(Field field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field.getByIndices(i, j));
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field.getByIndices(i, j).equals(numberLine.get(j))) {
                    field.setByIndices(i, j, numberLine.get(j));
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field.getByIndices(i, j).equals(BehaviourConstants.EMPTY)) {
                    field.setByIndices(i, j, BehaviourConstants.EMPTY);
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field.getTwoDimensionalArray());
        }
    }

    public static void moveRight(Field field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field.getByIndices(i, BehaviourConstants.DIMENSION - j - 1));
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field.getByIndices(i, BehaviourConstants.DIMENSION - j - 1).equals(numberLine.get(j))) {
                    field.setByIndices(i, BehaviourConstants.DIMENSION - j - 1, numberLine.get(j));
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field.getByIndices(i,BehaviourConstants.DIMENSION - j - 1).equals(BehaviourConstants.EMPTY)) {
                    field.setByIndices(i, BehaviourConstants.DIMENSION - j - 1, BehaviourConstants.EMPTY);
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field.getTwoDimensionalArray());
        }
    }
}
