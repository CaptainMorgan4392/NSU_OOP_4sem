package render;

import model.BehaviourConstants;
import model.Model;
import observation.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Renderer extends Component implements Observer {
    Model model;
    JLabel[][] cells = new JLabel[BehaviourConstants.DIMENSION][BehaviourConstants.DIMENSION];
    static JFrame mainFrame;
    JPanel gameBoard;
    Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

    Dimension cellSize = new Dimension(100, 100);

    public Renderer(Model model) {
        this.model = model;

        mainFrame = new JFrame("2048");
        mainFrame.setPreferredSize(new Dimension(400, 400));
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

    public Component getFrame() {
        return mainFrame;
    }
}
