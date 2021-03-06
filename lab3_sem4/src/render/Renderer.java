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
    JPanel gameBoard;
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

    Dimension cellSize = new Dimension(BehaviourConstants.CELL_PIXEL_SIZE, BehaviourConstants.CELL_PIXEL_SIZE);

    public Renderer(Model model) {
        this.model = model;

        mainFrame = new JFrame("2048");
        mainFrame.setPreferredSize(new Dimension(BehaviourConstants.DIMENSION * BehaviourConstants.CELL_PIXEL_SIZE,
                                                BehaviourConstants.DIMENSION * BehaviourConstants.CELL_PIXEL_SIZE));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                Integer current = model.getField()[i][j];
                if (current == BehaviourConstants.EMPTY) {
                    cells[i][j] = new JLabel(" ");
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
        mainFrame.getContentPane().add(gameBoard);
        mainFrame.setVisible(true);
    }

    @Override
    public void handleEvent() {
        gameBoard = new JPanel();

        gameBoard.setLayout(new GridLayout(4, 4));

        for (int i = 0; i < BehaviourConstants.DIMENSION; ++i) {
            for (int j = 0; j < BehaviourConstants.DIMENSION; ++j) {
                Integer current = model.getField()[i][j];
                if (current == BehaviourConstants.EMPTY) {
                    cells[i][j] = new JLabel(" ");
                } else {
                    cells[i][j] = new JLabel(current.toString());
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
                    }

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
        mainFrame.getContentPane().add(gameBoard);
        mainFrame.setVisible(true);

        if (model.getCurrentState() == WIN) {
            JOptionPane.showMessageDialog(null, "Congratulations! You win!");
        } else if (model.getCurrentState() == GAME_OVER) {
            JOptionPane.showMessageDialog(null, "Game over! :(");
        }
    }

    public Component getFrame() {
        return mainFrame;
    }
}
