package delivery.transport;

import delivery.path.BackwardPath;
import delivery.path.ForwardPath;
import delivery.path.Path;
import delivery.service.DeliveryService;
import delivery.station.Station;
import main.Infrastructure;
import production.Product;
import production.ProductType;
import warehouse.ArrivalWarehouse;
import warehouse.DepartureWarehouse;

import java.util.ArrayDeque;
import org.apache.logging.log4j.*;

public class Train extends Thread {
    private static final Logger logger = LogManager.getLogger(Train.class);

    private int remaining;

    private final ProductType productType;
    private final int toDeliver;

    private final ArrayDeque <Product> storage;

    private final DepartureWarehouse from;
    private final ArrivalWarehouse to;

    private final DeliveryService deliveryService;

    private Path busyPath;

    public Train(DeliveryService deliveryService, ProductType productType, int amortisation, int toDeliver) {
        this.productType = productType;
        this.toDeliver = toDeliver;

        this.remaining = amortisation;

        this.deliveryService = deliveryService;

        this.from = getDeliveryService().getInfrastructure().getDepartures().get(this.getProductType());
        this.to = getDeliveryService().getInfrastructure().getArrivals().get(this.getProductType());

        this.storage = new ArrayDeque<>();
        this.busyPath = null;
    }

    private void fillTrain() throws InterruptedException {
        for (int i = 0; i < this.getToDeliver(); ++i) {
            this.getStorage().addLast(this.getFromWarehouse().get());
        }
    }

    private void extractThisTrain(Station station) throws InterruptedException {
        station.getTrainFrom(this);
    }

    private void deliverProduction() throws InterruptedException {
        this.busyPath = this.deliveryService.delegateForwardPath();

        final long SECOND_MILLIS = 1000;
        Thread.sleep(SECOND_MILLIS * this.getBusyPath().getLength());
        this.decreaseRemaining(this.getBusyPath().getLength());
        this.deliveryService.getArrivalStation().putTrainInto(this);

        this.deliveryService.getForwardPathBack((ForwardPath) this.getBusyPath());
        this.busyPath = null;
    }

    private void bringToArrival() throws InterruptedException {
        for (int i = 0; i < this.getToDeliver(); ++i) {
            this.getToWarehouse().put(this.getStorage().poll());
        }
    }

    private void getTrainBack() throws InterruptedException {
        this.busyPath = this.deliveryService.delegateBackwardPath();

        final long SECOND_MILLIS = 1000;
        Thread.sleep(SECOND_MILLIS * this.getBusyPath().getLength());
        this.decreaseRemaining(this.getBusyPath().getLength());
        this.getDeliveryService().getDepartureStation().putTrainInto(this);

        this.deliveryService.getBackwardPathBack((BackwardPath) this.getBusyPath());
        this.busyPath = null;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                fillTrain();
                extractThisTrain(deliveryService.getDepartureStation());
                deliverProduction();
                bringToArrival();
                extractThisTrain(deliveryService.getArrivalStation());
                getTrainBack();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getRemaining() {
        return remaining;
    }

    public DepartureWarehouse getFromWarehouse() {
        return from;
    }

    public ArrivalWarehouse getToWarehouse() {
        return to;
    }

    public int getToDeliver() {
        return toDeliver;
    }

    public ArrayDeque<Product> getStorage() {
        return storage;
    }

    public Path getBusyPath() {
        return busyPath;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    private void decreaseRemaining(int toDecrease) {
        this.remaining -= toDecrease;
    }

    public boolean isCapable() {
        return this.getRemaining() >= this.getDeliveryService().getMaxForward() +
                                        this.getDeliveryService().getMaxBackward();
    }
}
