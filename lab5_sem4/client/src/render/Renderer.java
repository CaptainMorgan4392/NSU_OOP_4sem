package render;

import client.Client;
import client.ClientController;
import model.common.Field;
import model.common.Sign;
import protocol.Pair;
import protocol.request.MoveRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;

public class Renderer extends Component {
    private JFrame entering;
    private JFrame mainBoard;

    private JLabel[][] cells;
    private JPanel boardPanel;

    private final MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                clientController.getClient().getObjectOutputStream().writeObject(
                        new MoveRequest(new Pair<>(new Pair<>(e.getX() / 100, e.getY() / 100), null))
                );
            } catch (IOException ignored) {
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };

    private final ClientController clientController;

    public Renderer(ClientController clientController) {
        this.clientController = clientController;
        initStartWindow();
        entering.setVisible(true);
    }

    private void initMainBoard(Field field) {
        boolean alreadyCreated = false;

        if (null == mainBoard) {
            alreadyCreated = true;
            mainBoard = new JFrame("Game");
            cells = new JLabel[field.getCellsPerDim()][field.getCellsPerDim()];
        }

        if (null == boardPanel) {
            boardPanel = new JPanel();
            boardPanel.setLayout(new GridLayout(field.getCellsPerDim(), field.getCellsPerDim()));
            boardPanel.setPreferredSize(new Dimension(300, 300));
        }

        for (int i = 0; i < field.getCellsPerDim(); ++i) {
            for (int j = 0; j < field.getCellsPerDim(); ++j) {
                if (null == cells[i][j]) {
                    cells[i][j] = new JLabel();
                    cells[i][j].setPreferredSize(new Dimension(100, 100));
                    boardPanel.add(cells[i][j]);
                } else {
                    cells[i][j].setText(field.get(i, j).toString());
                }
            }
        }

        if (alreadyCreated) {
            mainBoard.pack();
            mainBoard.getContentPane().add(boardPanel);
            mainBoard.addMouseListener(mouseListener);
        }
    }

    private void initStartWindow() {
        entering = new JFrame("Connection...");
        entering.setPreferredSize(new Dimension(150, 100));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(150, 100));

        JTextField ip = new JTextField();
        ip.setPreferredSize(new Dimension(150, 100));
        panel.add(ip);

        entering.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    try {
                        clientController.initClient(new Client(new Socket(ip.getText(), 10000), clientController));
                    } catch (IOException | ClassNotFoundException ignored) {
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        entering.pack();
        entering.getContentPane().add(panel);
    }

    public void handle(Field field) {
        initMainBoard(field);

        if (null == mainBoard) {
            entering.setVisible(false);
            mainBoard.setVisible(true);
        } else {
            mainBoard.revalidate();
            mainBoard.repaint();
        }
    }

    public void handle(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

    public void handle(Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }

    public void handle(Sign sign) {
        JOptionPane.showMessageDialog(null, "Your sign is " + sign.toString());
    }
}
