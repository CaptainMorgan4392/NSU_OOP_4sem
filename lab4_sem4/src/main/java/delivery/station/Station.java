package delivery.station;

import delivery.service.DeliveryService;
import delivery.transport.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class Station {
    protected static final Logger logger = LogManager.getLogger(DepartureStation.class);

    protected final ArrayList<Train> trains;
    protected final ArrayList<Train> nowGoing;

    protected int capacity;
    protected DeliveryService deliveryService;

    protected Station(DeliveryService deliveryService) {
        this.trains = new ArrayList<>();
        this.nowGoing = new ArrayList<>();
        this.deliveryService = deliveryService;
    }

    public synchronized void putTrainInto(Train train) throws InterruptedException {
        while (this.trains.size() == this.capacity) {
            wait();
        }

        this.trains.add(train);
        this.nowGoing.remove(train);

        logger.trace("Train of contract: " + train.getProductType() + " - " + train.getToDeliver()
                + " put to " + this.getClass());

        notifyAll();
    }

    public synchronized void getTrainFrom(Train train) throws InterruptedException {
        while (!this.trains.contains(train)) {
            wait();
        }

        this.trains.remove(train);
        this.nowGoing.add(train);

        logger.trace("Train of contract: " + train.getProductType() + " - " + train.getToDeliver()
                + " extracted from " + this.getClass());

        notifyAll();
    }
}
