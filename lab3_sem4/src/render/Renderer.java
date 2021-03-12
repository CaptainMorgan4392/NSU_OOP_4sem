package render;

import model.BehaviourConstants;
import model.*;
import observation.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static model.Model.ModelState.GAME_OVER;
import static model.Model.ModelState.WIN;

public class Renderer extends Component implements Observer {
    Model model;
    JLabel[][] cells = new JLabel[BehaviourConstants.DIMENSION][BehaviourConstants.DIMENSION];
    static JFrame mainFrame;
    JPanel scoresBoard;
    JLabel scores;
    JPanel gameBoard;
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

    Timer clock;

    Dimension cellSize = new Dimension(BehaviourConstants.CELL_PIXEL_SIZE, BehaviourConstants.CELL_PIXEL_SIZE);

    public Renderer(Model model) {
        this.model = model;

        mainFrame = new JFrame("2048");
        mainFrame.setPreferredSize(new Dimension(
           BehaviourConstants.DIMENSION * BehaviourConstants.CELL_PIXEL_SIZE,
        BehaviourConstants.DIMENSION * BehaviourConstants.CELL_PIXEL_SIZE + 2 * BehaviourConstants.CELL_PIXEL_SIZE));
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        scoresBoard = new JPanel();
        scores = new JLabel("Scores: " + this.model.getScores().getScore());
        scores.setPreferredSize(new Dimension(BehaviourConstants.DIMENSION * BehaviourConstants.CELL_PIXEL_SIZE,
                BehaviourConstants.CELL_PIXEL_SIZE));
        scores.setHorizontalAlignment(SwingConstants.CENTER);
        scoresBoard.add(scores);

        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(BehaviourConstants.DIMENSION, BehaviourConstants.DIMENSION));

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                Integer current = model.getField()[i][j];
                if (current == BehaviourConstants.EMPTY) {
                    cells[i][j] = new JLabel(" ");
                    cells[i][j].setBackground(Color.WHITE);
                } else {
                    cells[i][j] = new JLabel(current.toString());
                    cells[i][j].setBackground(Color.YELLOW);

                    cells[i][j].setOpaque(true);
                }
                cells[i][j].setBorder(solidBorder);
                cells[i][j].setPreferredSize(cellSize);
                cells[i][j].setVerticalAlignment(SwingConstants.CENTER);
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                gameBoard.add(cells[i][j]);
            }
        }

        mainFrame.pack();
        mainFrame.getContentPane().add(scoresBoard);
        mainFrame.getContentPane().add(gameBoard);
        mainFrame.setVisible(true);

        clock = new Timer(1000, null);
    }

    @Override
    public void handleEvent() {
        scores.setText("Scores: " + this.model.getScores().getScore());

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                Integer current = model.getField()[i][j];
                if (current == BehaviourConstants.EMPTY) {
                    cells[i][j].setText(" ");
                    cells[i][j].setBackground(Color.WHITE);
                } else {
                    cells[i][j].setText(current.toString());
                    cells[i][j].setBackground(
                            switch (current) {
                                case 2 -> Color.YELLOW;
                                case 4 -> Color.GREEN;
                                case 8 -> Color.ORANGE;
                                case 16 -> Color.PINK;
                                case 32 -> Color.RED;
                                case 64 -> Color.WHITE;
                                case 128 -> Color.LIGHT_GRAY;
                                case 256 -> Color.GRAY;
                                case 512 -> Color.DARK_GRAY;
                                default -> Color.BLACK;
                            });
                    if (current >= 128 || current == 32) {
                        cells[i][j].setForeground(Color.WHITE);
                    } else {
                        cells[i][j].setForeground(Color.BLACK);
                    }

                    cells[i][j].setOpaque(true);
                }
                cells[i][j].setBorder(solidBorder);
                cells[i][j].setPreferredSize(cellSize);
                cells[i][j].setVerticalAlignment(SwingConstants.CENTER);
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);
            }
        }

        mainFrame.revalidate();
        mainFrame.repaint();

        if (model.getCurrentState() == WIN) {
            JOptionPane.showMessageDialog(null, "Congratulations! You win!");
        } else if (model.getCurrentState() == GAME_OVER) {
            JOptionPane.showMessageDialog(null, "Game over! :(");
        }
    }

    public Component getFrame() {
        return mainFrame;
    }

    public Timer getClock() {
        return clock;
    }
}
