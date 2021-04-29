package server;

import model.common.Sign;
import protocol.Pair;
import protocol.request.MoveRequest;
import protocol.response.InfoResponse;
import protocol.response.Response;
import server.model.server_only.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandle extends Thread {
    private final Socket socket;

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final Session session;
    private Sign sign;

    public ConnectionHandle(Socket socket, Session session, Sign sign) throws IOException {
        this.socket = socket;
        this.session = session;
        this.sign = sign;

        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectOutputStream.writeObject(new InfoResponse("You're connected!"));

        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                MoveRequest request = (MoveRequest) objectInputStream.readObject();
                Pair<Integer, Integer> coords = request.getData().getFirst();
                Response<?> response =
                        session.getModel().doMove(coords.getFirst(), coords.getSecond(), request.getData().getSecond());

                switch (response.getNotificationType()) {
                    case SINGLE -> objectOutputStream.writeObject(response);

                    case BOTH -> {
                        for (int i = 0; i < 2; ++i) {
                            session.getHandles()[i].getObjectOutputStream().writeObject(response);
                        }
                    }
                }

                Model.ModelState state = session.getModel().getState();
                if (state == Model.ModelState.MARKS_WIN || state == Model.ModelState.ZEROS_WIN) {
                    for (int i = 0; i < 2; ++i) {
                        session.getHandles()[i].getObjectOutputStream().writeObject(new InfoResponse("Game ended!"));
                    }

                    session.executeSession();
                }


            } catch (IOException | ClassNotFoundException ignored) {
            }
        }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
