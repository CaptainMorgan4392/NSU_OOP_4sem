package warehouse;

import main_logic.Infrastructure;
import product.Product;

import java.util.ArrayDeque;

public abstract class Warehouse {
    private ArrayDeque <Product> storage;

    private final int capacity;
    private final int currentStored;

    protected Warehouse() {
        this.currentStored = 0;

        this.capacity = Integer.parseInt(
                                Infrastructure.getProperties()
                                        .get("warehouse_capacity")
                                            .toString());
    }

    public boolean isFull() {
        return this.currentStored == this.capacity;
    }

    public synchronized final void put(Product product) {
        this.storage.addLast(product);
    }

    public synchronized final Product get() {
        Product toExtract = this.storage.getFirst();
        this.storage.removeFirst();

        return toExtract;
    }

    public int getCurrentStored() {
        return currentStored;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayDeque<Product> getStorage() {
        return storage;
    }
}
