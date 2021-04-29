package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private final ArrayList <Session> sessions;
    private static final int port = 10000;

    private Server() {
        this.sessions = new ArrayList<>();
    }

    public static void main(String[] args) {
        new Server().start();
    }

    private void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Session newSession = new Session(this, server);
                sessions.add(newSession);

                newSession.startSession();
            }
        } catch (IOException e) {
            for (Session session : sessions) {
                session.executeSession();
            }
        }
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }
}
