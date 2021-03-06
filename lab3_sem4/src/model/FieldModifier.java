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

        int row = index / 4;
        int col = index % 4;
        field[row][col] = BehaviourConstants.DEFAULT_VALUE;
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
        boolean prevCollapsed = false;

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            int currentInLine = numbersLine.get(i);
            if (currentInLine != BehaviourConstants.EMPTY) {
                if (!newLine.isEmpty() && newLine.get(newLine.size() - 1) == currentInLine && !prevCollapsed) {
                    newLine.set(newLine.size() - 1, 2 * newLine.get(newLine.size() - 1));
                    prevCollapsed = true;
                } else {
                    newLine.add(currentInLine);
                    prevCollapsed = false;
                }
            }
        }

        return newLine;
    }

    public static void moveUp(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[BehaviourConstants.DIMENSION - j - 1][i]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                field[BehaviourConstants.DIMENSION - j - 1][i] = numberLine.get(j);
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                field[BehaviourConstants.DIMENSION - j - 1][i] = BehaviourConstants.EMPTY;
            }
        }

        insertInRandomEmptyCell(field);
    }

    public static void moveDown(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[j][i]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                field[j][i] = numberLine.get(j);
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                field[j][i] = BehaviourConstants.EMPTY;
            }
        }

        insertInRandomEmptyCell(field);
    }

    public static void moveLeft(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[i][j]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                field[i][j] = numberLine.get(j);
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                field[i][j] = BehaviourConstants.EMPTY;
            }
        }

        insertInRandomEmptyCell(field);
    }

    public static void moveRight(Integer[][] field) {
        for (int i = 0; i < BehaviourConstants.DIMENSION; i++) {
            ArrayList <Integer> numberLine = new ArrayList<>();

            for (int j = 0; j < BehaviourConstants.DIMENSION; j++) {
                numberLine.add(field[i][BehaviourConstants.DIMENSION - j - 1]);
            }

            numberLine = collapse(numberLine);

            for (int j = 0; j < numberLine.size(); j++) {
                field[i][BehaviourConstants.DIMENSION - j - 1] = numberLine.get(j);
            }
            for (int j = numberLine.size(); j < BehaviourConstants.DIMENSION; ++j) {
                field[i][BehaviourConstants.DIMENSION - j - 1] = BehaviourConstants.EMPTY;
            }
        }

        insertInRandomEmptyCell(field);
    }
}
