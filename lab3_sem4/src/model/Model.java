package model;

import observation.Observable;
import observation.Observer;

import java.util.LinkedList;
import java.util.List;

public class Model implements Observable {
    public enum ModelState {
        CURRENTLY_PLAYING,
        GAME_OVER,
        WIN
    }

    Field field;
    List <Observer> observers;
    ModelState currentState;
    Score scores;

    public Model() {
        field = new Field().init();
        observers = new LinkedList<>();
        this.setCurrentState(ModelState.CURRENTLY_PLAYING);
        this.scores = new Score(10);
    }

    public Score getScores() {
        return scores;
    }

    public void setScores(Score scores) {
        this.scores = scores;

        isGameEnded();

        notifyObservers();
    }

    public ModelState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(ModelState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.handleEvent();
        }
    }

    public Integer[][] getField() {
        return field.getTwoDimensionalArray();
    }

    public void request(MoveDirection dir) {
        switch (dir) {
            case UP -> FieldModifier.moveUp(this.field, scores);

            case DOWN -> FieldModifier.moveDown(this.field, scores);

            case LEFT -> FieldModifier.moveLeft(this.field, scores);

            case RIGHT -> FieldModifier.moveRight(this.field, scores);
        }

        isGameEnded();

        notifyObservers();
    }

    public void isGameEnded() {
        if (FieldChecker.checkWin(this.getField())) {
            this.setCurrentState(ModelState.WIN);
        } else if (FieldChecker.checkLose(this.getField()) || this.getScores().getScore() == -1) {
            this.setCurrentState(ModelState.GAME_OVER);
        }
    }
}
