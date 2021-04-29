package client;

import model.common.Field;
import model.common.Sign;
import protocol.response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final Socket clientSocket;

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final ClientController clientController;

    public Client(Socket socket, ClientController clientController) throws IOException, ClassNotFoundException {
        this.clientSocket = socket;
        this.clientController = clientController;

        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        start();
    }

    private void start() throws IOException, ClassNotFoundException {
         while (!clientSocket.isClosed()) {
             Response<?> response = (Response<?>) objectInputStream.readObject();

             switch (response.getResponseType()) {
                 case INFO -> clientController.getRenderer().handle((String) response.getData());

                 case MOVE -> clientController.getRenderer().handle((Field) response.getData());

                 case ERROR -> clientController.getRenderer().handle((Exception) response.getData());

                 case SIGN_DELEGATION -> clientController.getRenderer().handle((Sign) response.getData());
             }
         }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
