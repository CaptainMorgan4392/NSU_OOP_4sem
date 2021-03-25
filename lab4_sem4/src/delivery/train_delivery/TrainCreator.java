package delivery.train_delivery;

import delivery.service_interface.Creator;
import delivery.service_interface.Transport;
import main_logic.Infrastructure;

public class TrainCreator implements Creator {
    private final int timeToCreate;
    private final TrainDeliveryService deliveryService;

    public TrainCreator(TrainDeliveryService trainDeliveryService) {
        this.timeToCreate = Integer.parseInt(Infrastructure.getProperties().get("train_timeCreate").toString());
        this.deliveryService = trainDeliveryService;
    }

    @Override
    public Transport create() {
        try {
            final int SECOND_MILLIS = 1000;
            Thread.sleep((long) SECOND_MILLIS * timeToCreate);
        } catch (InterruptedException ignored) {
        }

        return new Train(deliveryService);
    }

    @Override
    public void run() {
        Train newTrain = (Train)create();
        deliveryService.getDepot().getTransportInDepot(newTrain);
    }
}
