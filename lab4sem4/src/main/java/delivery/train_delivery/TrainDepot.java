package delivery.train_delivery;

import delivery.service_interface.DeliveryService;
import delivery.service_interface.Depot;
import delivery.service_interface.Transport;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TrainDepot implements Depot {
    private ArrayList <Train> trains;
    private final DeliveryService deliveryService;

    public TrainDepot(TrainDeliveryService trainDeliveryService) {
        trains = new ArrayList<>();

        //init trains

        this.deliveryService = trainDeliveryService;
    }

    private void extractUselessTrains() {
        final int sizeBefore = trains.size();

        Predicate <Train> useful = (
                train -> train.getTimeToDestruction() >= deliveryService.getMaxForward() + deliveryService.getMaxBackward()
        );

        trains = (ArrayList<Train>) trains.stream().filter(useful).collect(Collectors.toList());

        final int sizeAfter = trains.size();

        for (int i = 0; i < sizeBefore - sizeAfter; ++i) {
            new Thread(new TrainCreator(getDeliveryService())).start();
        }
    }

    public ArrayList <Train> getTrains() {
        return trains;
    }

    @Override
    public Transport delegateTransport() {
        extractUselessTrains();

        Train toExtract = trains.get(0);
        trains.remove(0);

        return toExtract;
    }

    @Override
    public void getTransportInDepot(Transport transport) {

    }

    public TrainDeliveryService getDeliveryService() {
        return (TrainDeliveryService) deliveryService;
    }
}
