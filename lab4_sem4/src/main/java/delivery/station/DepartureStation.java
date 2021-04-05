package delivery.station;

import delivery.transport.Train;
import main.ConfigFormatException;
import main.Infrastructure;
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
            int amortisation = Integer.parseInt(Infrastructure.getProperties().get("train_amortisation").toString());

            try {
                Thread.sleep(this.getTimeToCreate() * SECOND_MILLIS);
                Train newTrain = new Train(productType, amortisation, toDeliver);
                putTrainInto(newTrain);
                new Thread(newTrain).start();
            } catch (InterruptedException ignored) {
            }
        }

        public ProductType getProductType() {
            return productType;
        }

        public int getToDeliver() {
            return toDeliver;
        }

        public int getTimeToCreate() {
            return timeToCreate;
        }
    }

    private final int timeToCreate;
    private final ExecutorService trainCreationPool;

    public DepartureStation() throws ConfigFormatException {
        super();

        this.timeToCreate = Integer.parseInt(Infrastructure.getProperties().get("depot_timeToCreate").toString());
        this.trainCreationPool = Executors.newCachedThreadPool();
    }

    @Override
    public void createTrain(ProductType productType, int toDeliver) {
        CreationRunnable create = new CreationRunnable(productType, toDeliver, timeToCreate);
        this.trainCreationPool.submit(create);
    }

    public int getTimeToCreate() {
        return timeToCreate;
    }
}
