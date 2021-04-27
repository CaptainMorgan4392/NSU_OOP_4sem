package server;

import model.common.Sign;
import protocol.request.Request;
import protocol.response.Response;
import protocol.response.SignDelegateResponse;

import java.io.*;
import java.net.Socket;

public class ServerConnectionHandle extends Thread {
    private final Socket socket;

    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ServerConnectionHandle(Socket socket, Sign sign) throws IOException {
        this.socket = socket;

        OutputStream os = socket.getOutputStream();
        this.outputStream = new ObjectOutputStream(os);
        outputStream.writeObject(new SignDelegateResponse(sign));

        InputStream is = socket.getInputStream();
        this.inputStream = new ObjectInputStream(is);
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                Request request = (Request) inputStream.readObject();
                Response<?> response =
                        Server.getModel().doMove(request.getRowCoord(), request.getColCoord(), request.getSign());

                switch (response.getNotificationType()) {
                    case SINGLE -> outputStream.writeObject(response);

                    case ALL -> {
                        for (int i = 0; i < Server.getNumberOfPlayers(); ++i) {
                            Server.getHandles()[i].getOutputStream().writeObject(response);
                        }
                    }
                }

                //check end
            } catch (IOException | ClassNotFoundException e) {

            }
        }
    }

    ObjectOutputStream getOutputStream() {
        return outputStream;
    }
}
