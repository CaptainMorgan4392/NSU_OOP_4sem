package delivery.service_interface;

import java.util.ArrayList;

public interface Depot {
    Transport delegateTransport();
    void getTransportInDepot(Transport transport);
}
