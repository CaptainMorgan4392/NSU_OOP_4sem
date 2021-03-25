package delivery.service_interface;

import delivery.train_delivery.Train;

import java.util.ArrayList;

public interface Depot {
    void getTransportInDepot(Transport transport);
    public ArrayList<Transport> getTransport();
}
