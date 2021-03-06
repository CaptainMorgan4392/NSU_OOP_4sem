package control;

import model.Model;
import model.MoveDirection;
import model.BehaviourConstants;
import render.Renderer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller extends KeyAdapter {
    Model model;
    Renderer renderer;

    public Controller() {
        model = new Model();
        renderer = new Renderer(model);

        renderer.getFrame().addKeyListener(this);

        model.addObserver(renderer);
    }

    public void createRequestByAction(Action action) {
        if (model.getCurrentState() == Model.ModelState.CURRENTLY_PLAYING) {
            switch (action) {
                case MOVE_UP -> model.request(MoveDirection.UP);

                case MOVE_DOWN -> model.request(MoveDirection.DOWN);

                case MOVE_LEFT -> model.request(MoveDirection.LEFT);

                case MOVE_RIGHT -> model.request(MoveDirection.RIGHT);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case BehaviourConstants.ARROW_LEFT -> this.createRequestByAction(Action.MOVE_LEFT);

            case BehaviourConstants.ARROW_UP -> this.createRequestByAction(Action.MOVE_DOWN);

            case BehaviourConstants.ARROW_RIGHT -> this.createRequestByAction(Action.MOVE_RIGHT);

            case BehaviourConstants.ARROW_DOWN -> this.createRequestByAction(Action.MOVE_UP);
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
    }
}
