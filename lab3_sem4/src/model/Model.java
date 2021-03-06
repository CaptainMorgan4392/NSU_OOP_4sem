package model;

import observation.Observable;
import observation.Observer;

import java.util.LinkedList;
import java.util.List;

public class Model implements Observable {
    Integer[][] field;
    List <Observer> observers;


    public Model() {
        field = FieldModifier.init();
        observers = new LinkedList<>();
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
        return field;
    }

    public void request(MoveDirection dir) {
       switch (dir) {
            case UP -> FieldModifier.moveUp(this.field);

            case DOWN -> FieldModifier.moveDown(this.field);

            case LEFT -> FieldModifier.moveLeft(this.field);

            case RIGHT -> FieldModifier.moveRight(this.field);
       }

        notifyObservers();
    }
}
