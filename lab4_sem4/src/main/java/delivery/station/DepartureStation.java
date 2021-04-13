package delivery.station;

import delivery.service.DeliveryService;
import delivery.transport.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import production.ProductType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DepartureStation extends Station implements TrainCreator {
    private class CreationRunnable implements Runnable {
        private final ProductType productType;
        private final int toDeliver;
        private final int timeToCreate;

        private CreationRunnable(ProductType productType, int toDeliver, int timeToCreate) {
            this.productType = productType;
            this.toDeliver = toDeliver;
            this.timeToCreate = timeToCreate;
        }

        @Override
        public void run() {
            final long SECOND_MILLIS = 1000;
            int amortisation = Integer.parseInt(deliveryService.getInfrastructure().
                                                        getProperties().get("train_amortisation").toString());

            try {
                Thread.sleep(this.getTimeToCreate() * SECOND_MILLIS);
                Train newTrain = new Train(deliveryService, productType, amortisation, toDeliver);
                DepartureStation.super.putTrainInto(newTrain);
                newTrain.start();

                logger.trace("Train of contract: " + newTrain.getProductType() + " - " + newTrain.getToDeliver()
                        + " created.");
            } catch (InterruptedException ignored) {
            }
        }

        public int getTimeToCreate() {
            return timeToCreate;
        }
    }

    private final int timeToCreate;
    private final ExecutorService trainCreationPool;

    public DepartureStation(DeliveryService deliveryService) {
        super(deliveryService);

        this.capacity = Integer.parseInt(
                deliveryService.getInfrastructure().getProperties().get("depot_departureCapacity").toString()
        );

        this.timeToCreate = Integer.parseInt(deliveryService.getInfrastructure().
                getProperties().get("depot_timeToCreate").toString());
        this.trainCreationPool = Executors.newCachedThreadPool();
    }

    @Override
    public void createTrain(ProductType productType, int toDeliver) {
        CreationRunnable create = new CreationRunnable(productType, toDeliver, timeToCreate);
        this.trainCreationPool.submit(create);
    }

    public void launchTrains() {
        for (Train train : trains) {
            train.start();
        }
    }

    @Override
    public synchronized void putTrainInto(Train train) throws InterruptedException {
        if (!train.isCapable()) {
            logger.trace("Train of contract: " + train.getProductType() + " - " + train.getToDeliver()
                    + " can't be put to " + this.getClass() + ". Creating new...");

            createTrain(train.getProductType(), train.getToDeliver());
        } else {
            DepartureStation.super.putTrainInto(train);
        }
    }

    public void destroyTrains() {
        trainCreationPool.shutdownNow();

        for (Train train : trains) {
            train.stop();
        }

        for (Train train : nowGoing) {
            train.stop();
        }
    }
}
