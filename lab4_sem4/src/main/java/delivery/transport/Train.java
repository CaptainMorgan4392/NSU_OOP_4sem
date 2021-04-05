package delivery.transport;

import delivery.path.BackwardPath;
import delivery.path.ForwardPath;
import delivery.path.Path;
import delivery.service.DeliveryService;
import main.Infrastructure;
import production.Product;
import production.ProductType;
import warehouse.ArrivalWarehouse;
import warehouse.DepartureWarehouse;

import java.util.ArrayDeque;
import org.apache.logging.log4j.*;

public class Train extends Thread {
    private static final Logger logger = LogManager.getLogger(Train.class);

    private final int amortisation;
    private int remaining;

    private final ProductType productType;
    private final int toDeliver;

    private final ArrayDeque <Product> storage;

    private final DepartureWarehouse from;
    private final ArrivalWarehouse to;

    private final DeliveryService deliveryService;

    private Path busyPath;

    public Train(ProductType productType, int amortisation, int toDeliver) {
        this.productType = productType;
        this.toDeliver = toDeliver;

        this.amortisation = amortisation;
        this.remaining = this.amortisation;

        this.from = Infrastructure.getDepartures().get(this.getProductType());
        this.to = Infrastructure.getArrivals().get(this.getProductType());

        this.storage = new ArrayDeque<>();
        this.busyPath = null;

        this.deliveryService = Infrastructure.getDeliveryService();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            for (int i = 0; i < this.getToDeliver(); ++i) {
                try {
                    this.getStorage().addLast(this.getFromWarehouse().get());

                } catch (InterruptedException e) {
                    break;
                }
            }

            try {
                this.deliveryService.getDepartureStation().getTrainFrom(this);
                this.busyPath = this.deliveryService.delegateForwardPath();
            } catch (InterruptedException e) {
                break;
            }

            final long SECOND_MILLIS = 1000;
            try {
                Thread.sleep(SECOND_MILLIS * this.getBusyPath().getLength());
            } catch (InterruptedException e) {
                break;
            }
            this.decreaseRemaining(this.getBusyPath().getLength());

            try {
                this.deliveryService.getArrivalStation().putTrainInto(this);
            } catch (InterruptedException e) {
                break;
            }
            this.deliveryService.getForwardPathBack((ForwardPath) this.getBusyPath());
            this.busyPath = null;

            for (int i = 0; i < this.getToDeliver(); ++i) {
                try {
                    this.getToWarehouse().put(this.getStorage().poll());
                } catch (InterruptedException e) {
                    break;
                }
            }

            try {
                this.deliveryService.getArrivalStation().getTrainFrom(this);
                this.busyPath = this.deliveryService.delegateBackwardPath();
            } catch (InterruptedException e) {
                break;
            }

            try {
                Thread.sleep(SECOND_MILLIS * this.getBusyPath().getLength());
            } catch (InterruptedException e) {
                break;
            }
            this.decreaseRemaining(this.getBusyPath().getLength());

            try {
                this.deliveryService.getDepartureStation().putTrainInto(this);
            } catch (InterruptedException e) {
                break;
            }
            this.deliveryService.getBackwardPathBack((BackwardPath) this.getBusyPath());
            this.busyPath = null;

            if (!isCapable()) {
                this.getDeliveryService().getDepartureStation().createTrain(this.getProductType(), this.getToDeliver());
            }
        }
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getAmortisation() {
        return amortisation;
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
