package control;

import model.Model;
import model.MoveDirection;
import model.BehaviourConstants;
import model.Score;
import render.Renderer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter implements ActionListener {
    Model model;
    Renderer renderer;


    public Controller() {
        model = new Model();
        renderer = new Renderer(model);

        renderer.getFrame().addKeyListener(this);
        renderer.getClock().addActionListener(this);
        renderer.getClock().start();

        model.addObserver(renderer);
    }

    public synchronized void createRequestByAction(Action action) {
        if (model.getCurrentState() == Model.ModelState.CURRENTLY_PLAYING) {
            switch (action) {
                case MOVE_UP -> model.request(MoveDirection.UP);

                case MOVE_DOWN -> model.request(MoveDirection.DOWN);

                case MOVE_LEFT -> model.request(MoveDirection.LEFT);

                case MOVE_RIGHT -> model.request(MoveDirection.RIGHT);

                case GET_ONE_POINT_OUT -> model.setScores(new Score(model.getScores().getScore() - 1));
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

    @Override
    public synchronized void actionPerformed(ActionEvent e) {
        this.createRequestByAction(Action.GET_ONE_POINT_OUT);
    }
}
