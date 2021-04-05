package warehouse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import production.Product;
import production.ProductType;

import java.util.ArrayDeque;

public abstract class Warehouse {
    private final Logger departureLogger = LogManager.getLogger(DepartureWarehouse.class);
    private final Logger arrivalLogger = LogManager.getLogger(ArrivalWarehouse.class);

    private final ProductType productType;
    private final int capacity;
    private volatile ArrayDeque <Product> products;

    protected Warehouse(ProductType productType, int capacity) {
        this.productType = productType;
        this.capacity = capacity;
        this.products = new ArrayDeque<>();
    }

    public synchronized void put(Product product) throws InterruptedException {
        while (this.getProducts().size() == this.capacity) {
            wait();
        }

        this.getProducts().addLast(product);

        if (this instanceof DepartureWarehouse) {
            departureLogger.trace(this.toString());
        } else {
            arrivalLogger.trace(this.toString());
        }

        notifyAll();
    }

    public synchronized Product get() throws InterruptedException {
        final int EMPTY = 0;

        while (this.getProducts().size() == EMPTY) {
            wait();
        }

        Product toExtract = this.getProducts().poll();

        notifyAll();

        return toExtract;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayDeque<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Type of warehouse: "
                + this.getProductType().getTypeLiteral()
                + ", current stock: "
                + this.getProducts().size()
                + "/"
                + this.getCapacity();
    }
}
