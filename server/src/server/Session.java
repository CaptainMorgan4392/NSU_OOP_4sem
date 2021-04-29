package server;

import model.common.Sign;
import protocol.response.InfoResponse;
import protocol.response.FieldResponse;
import server.model.server_only.Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Session {
    private final int numberOfPlayers = 2;
    private final ConnectionHandle[] handles = new ConnectionHandle[numberOfPlayers];

    private final Server server;
    private final Model model;
    private final ServerSocket serverSocket;

    public Session(Server server, ServerSocket serverSocket) {
        this.server = server;
        this.serverSocket = serverSocket;

        this.model = new Model();
    }

    public void startSession() throws IOException {
        for (int i = 0; i < numberOfPlayers; ++i) {
            Socket socket = serverSocket.accept();
            handles[i] = new ConnectionHandle(socket, this, (i == 0 ? Sign.MARK : Sign.ZERO));

            handles[i].start();
            if (i == 0) {
                handles[i].getObjectOutputStream().writeObject(new InfoResponse("Waiting for other player!"));
            }
        }

        for (int i = 0; i < numberOfPlayers; ++i) {
            handles[i].getObjectOutputStream().writeObject(new FieldResponse(model.getField()));
        }
    }

    public void executeSession() {
        handles[0].interrupt();
        handles[1].interrupt();

        server.getSessions().remove(this);
    }

    public Model getModel() {
        return model;
    }

    public ConnectionHandle[] getHandles() {
        return handles;
    }
}
