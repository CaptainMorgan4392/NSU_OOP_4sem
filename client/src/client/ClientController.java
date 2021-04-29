package client;

import render.Renderer;

public class ClientController {
    private Client client;
    private final Renderer renderer;

    private ClientController() {
        this.renderer = new Renderer(this);
    }

    public static void main(String[] args) {
        new ClientController();
    }

    public void initClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
