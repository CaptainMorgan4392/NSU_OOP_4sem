package client;

import model.common.Field;
import model.common.Sign;
import protocol.request.Request;
import protocol.response.Response;
import render.Renderer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Client implements MouseListener {
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private final Renderer renderer;
    private Sign sign;

    private Client() throws IOException {
        this.clientSocket = new Socket("localhost", 10000);
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());

        this.renderer = new Renderer(this);
        renderer.addMouseListener(this);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Client().start();
    }

    private void start() throws IOException, ClassNotFoundException {
        while (!clientSocket.isClosed()) {
            Response<? super Serializable> response = (Response<? super Serializable>) inputStream.readObject();

            switch (response.getResponseType()) {
                case INFO -> renderer.handle((String) response.getData());

                case MOVE -> renderer.handle((Field) response.getData());

                case ERROR -> renderer.handle((Exception) response.getData());

                case SIGN_DELEGATION -> {
                    renderer.handle((Sign) response.getData());
                    this.sign = (Sign) response.getData();
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            outputStream.writeObject(new Request(e.getY() / 100, e.getX() / 100, sign));
        } catch (IOException ioException) {
            ioException.printStackTrace();
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
}
