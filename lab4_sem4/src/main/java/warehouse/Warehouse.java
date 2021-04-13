package warehouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import production.Product;
import production.ProductType;

import java.util.ArrayDeque;

public abstract class Warehouse {
    private static final Logger logger = LogManager.getLogger(Warehouse.class);

    private final ProductType productType;
    private final int capacity;
    private volatile ArrayDeque <Product> products;

    protected Warehouse(ProductType productType, int capacity) {
        this.productType = productType;
        this.capacity = capacity;
        this.products = new ArrayDeque<>();
    }

    public synchronized void put(Product product) throws InterruptedException {
        while (this.products.size() == this.capacity) {
            wait();
        }

        this.products.addLast(product);

        logger.trace(this.toString());

        notifyAll();
    }

    public synchronized Product get() throws InterruptedException {
        final int EMPTY = 0;

        while (this.products.size() == EMPTY) {
            wait();
        }

        Product toExtract = this.products.poll();

        logger.trace(this.toString());

        notifyAll();

        return toExtract;
    }

    public ProductType getProductType() {
        return productType;
    }

    private int getCapacity() {
        return capacity;
    }

    @Override
    public String toString() {
        return "Type of warehouse: "
                + this.getProductType().getTypeLiteral()
                + ", point of delivering: "
                + this.getClass()
                + ", current stock: "
                + this.products.size()
                + "/"
                + this.getCapacity();
    }
}
