package delivery.train_delivery;

import delivery.service_interface.DeliveryService;
import delivery.service_interface.Depot;
import delivery.service_interface.Transport;
import main_logic.Infrastructure;

import java.util.ArrayList;

public class TrainDepot implements Depot {
    private final ArrayList <Transport> trains;
    private final DeliveryService deliveryService;

    public TrainDepot(TrainDeliveryService trainDeliveryService) {
        trains = new ArrayList<>();
        int numberOfTrains = Integer.parseInt(
                Infrastructure.getProperties().get("train_numberOfTrains").toString()
        );

        for (int i = 0; i < numberOfTrains; ++i) {
            trains.add(new Train(this.getDeliveryService()));
        }

        this.deliveryService = trainDeliveryService;
    }

    @Override
    public ArrayList <Transport> getTransport() {
        return trains;
    }

    @Override
    public void getTransportInDepot(Transport transport) {
        trains.add(transport);
    }

    public TrainDeliveryService getDeliveryService() {
        return (TrainDeliveryService) deliveryService;
    }
}
