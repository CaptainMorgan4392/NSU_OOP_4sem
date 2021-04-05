package producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import production.Product;
import production.ProductType;
import warehouse.DepartureWarehouse;

public class Factory extends Thread implements Producer {
    private static final Logger logger = LogManager.getLogger(Factory.class);

    private final ProductType productType;
    private final DepartureWarehouse warehouse;
    private final int timeToCreate;

    public Factory(DepartureWarehouse warehouse, int timeToCreate) {
        this.warehouse = warehouse;
        this.timeToCreate = timeToCreate;
        this.productType = this.getWarehouse().getProductType();
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            Product created = this.create();

            logger.trace(created.toString() + "created.");

            try {
                this.warehouse.put(created);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public Product create() {
        final long SECOND_MILLIS = 1000;

        try {
            Thread.sleep(SECOND_MILLIS * timeToCreate);
        } catch (InterruptedException e) {
            return null;
        }

        return new Product(this.getProductType());
    }

    public DepartureWarehouse getWarehouse() {
        return warehouse;
    }

    public int getTimeToCreate() {
        return timeToCreate;
    }

    public ProductType getProductType() {
        return productType;
    }
}
