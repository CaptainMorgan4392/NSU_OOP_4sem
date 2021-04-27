package server;

import model.common.Sign;
import model.server_only.Model;
import protocol.response.InfoResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int port = 10000;
    private static final Model model = new Model();

    private static final int numberOfPlayers = 2;
    private static final ServerConnectionHandle[] handles = new ServerConnectionHandle[numberOfPlayers];

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(port);

            for (int i = 0; i < numberOfPlayers; ++i) {
                Socket clientSocket = server.accept();

                handles[i] = new ServerConnectionHandle(clientSocket, i == 0 ? Sign.MARK : Sign.ZERO);
                handles[i].getOutputStream().writeObject(new InfoResponse("You're successfully connected"));
            }
        } catch (IOException e) {

        }
    }

    public static Model getModel() {
        return model;
    }

    public static ServerConnectionHandle[] getHandles() {
        return handles;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
