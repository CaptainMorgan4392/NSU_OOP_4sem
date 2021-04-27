package render;

import client.Client;
import model.common.Field;
import model.common.Sign;

import javax.swing.*;
import java.awt.*;

public class Renderer extends Component {
    private static JFrame mainFrame;
    private final JPanel gameBoard;
    private final JLabel[][] cells;

    private Client client;

    public Renderer(Client client) {
        this.client = client;

        mainFrame = new JFrame("Tic-tac-toe");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(
                new Dimension(
                        Field.getCellsPerDim() * 100, Field.getCellsPerDim() * 100));

        this.gameBoard = new JPanel();
        this.gameBoard.setLayout(new GridLayout(Field.getCellsPerDim(), Field.getCellsPerDim()));

        this.cells = new JLabel[Field.getCellsPerDim()][Field.getCellsPerDim()];
        for (int i = 0; i < Field.getCellsPerDim(); ++i) {
            for (int j = 0; j < Field.getCellsPerDim(); ++j) {
                cells[i][j] = new JLabel();

                cells[i][j].setPreferredSize(new Dimension(100, 100));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                cells[i][j].setVerticalAlignment(SwingConstants.CENTER);
                cells[i][j].setHorizontalAlignment(SwingConstants.CENTER);

                gameBoard.add(cells[i][j]);
            }
        }

        mainFrame.pack();
        mainFrame.getContentPane().add(gameBoard);
        mainFrame.setVisible(true);
    }

    public void handle(Field field) {
        for (int i = 0; i < Field.getCellsPerDim(); ++i) {
            for (int j = 0; j < Field.getCellsPerDim(); ++j) {
                cells[i][j].setText(field.get(i, j).toString());
            }
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void handle(Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage());

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void handle(String str) {
        JOptionPane.showMessageDialog(null, str);

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public void handle(Sign sign) {
        JOptionPane.showMessageDialog(null, "Your sign is " + sign.toString());

        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
