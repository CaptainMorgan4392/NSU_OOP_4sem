package model;

import java.util.ArrayList;

public class FieldModifier {
    private static void insertInRandomEmptyCell(Integer[][] field) {
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

    public static Integer[][] init() {
        Integer[][] newField = new Integer[BehaviourConstants.DIMENSION][BehaviourConstants.DIMENSION];

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                newField[i][j] = BehaviourConstants.EMPTY;
            }
        }

        insertInRandomEmptyCell(newField);
        insertInRandomEmptyCell(newField);

        return newField;
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

    public static void moveUp(Integer[][] field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[BehaviourConstants.DIMENSION - j - 1][i]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field[BehaviourConstants.DIMENSION - j - 1][i].equals(numberLine.get(j))) {
                    field[BehaviourConstants.DIMENSION - j - 1][i] = numberLine.get(j);
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field[BehaviourConstants.DIMENSION - j - 1][i].equals(BehaviourConstants.EMPTY)) {
                    field[BehaviourConstants.DIMENSION - j - 1][i] = BehaviourConstants.EMPTY;
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field);
        }
    }

    public static void moveDown(Integer[][] field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[j][i]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field[j][i].equals(numberLine.get(j))) {
                    field[j][i] = numberLine.get(j);
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field[j][i].equals(BehaviourConstants.EMPTY)) {
                    field[j][i] = BehaviourConstants.EMPTY;
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field);
        }
    }

    public static void moveLeft(Integer[][] field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[i][j]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field[i][j].equals(numberLine.get(j))) {
                    field[i][j] = numberLine.get(j);
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field[i][j].equals(BehaviourConstants.EMPTY)) {
                    field[i][j] = BehaviourConstants.EMPTY;
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field);
        }
    }

    public static void moveRight(Integer[][] field) {
        boolean modified = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[i][BehaviourConstants.DIMENSION - j - 1]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                if (!field[i][BehaviourConstants.DIMENSION - j - 1].equals(numberLine.get(j))) {
                    field[i][BehaviourConstants.DIMENSION - j - 1] = numberLine.get(j);
                    modified = true;
                }
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                if (!field[i][BehaviourConstants.DIMENSION - j - 1].equals(BehaviourConstants.EMPTY)) {
                    field[i][BehaviourConstants.DIMENSION - j - 1] = BehaviourConstants.EMPTY;
                    modified = true;
                }
            }
        }

        if (modified) {
            insertInRandomEmptyCell(field);
        }
    }
}
