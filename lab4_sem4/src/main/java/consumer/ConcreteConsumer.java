package consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import production.Product;
import warehouse.ArrivalWarehouse;

public class ConcreteConsumer extends Thread implements Consumer {
    private static final Logger logger = LogManager.getLogger(ConcreteConsumer.class);

    private final ArrivalWarehouse warehouse;
    private final int timeToConsume;

    public ConcreteConsumer(ArrivalWarehouse warehouse, int timeToConsume) {
        this.warehouse = warehouse;
        this.timeToConsume = timeToConsume;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                Product product = this.consume();

                logger.trace(product.toString() + "consumed.");

                final long SECOND_MILLIS = 1000;
                Thread.sleep(SECOND_MILLIS * this.getTimeToConsume());
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public Product consume() throws InterruptedException {
        return this.getWarehouse().get();
    }

    public ArrivalWarehouse getWarehouse() {
        return warehouse;
    }

    public int getTimeToConsume() {
        return timeToConsume;
    }
}
