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

    public Model() {
        field = new Field().init();
        observers = new LinkedList<>();
        this.setCurrentState(ModelState.CURRENTLY_PLAYING);
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
            case UP -> FieldModifier.moveUp(this.field);

            case DOWN -> FieldModifier.moveDown(this.field);

            case LEFT -> FieldModifier.moveLeft(this.field);

            case RIGHT -> FieldModifier.moveRight(this.field);
        }

        isGameEnded();

        notifyObservers();
    }

    public void isGameEnded() {
        if (FieldChecker.checkWin(this.getField())) {
            this.setCurrentState(ModelState.WIN);
        } else if (FieldChecker.checkLose(this.getField())) {
            this.setCurrentState(ModelState.GAME_OVER);
        }
    }
}
