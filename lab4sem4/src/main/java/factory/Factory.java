package factory;

import main_logic.Infrastructure;
import product.Product;
import product.ProductType;
import warehouse.DepartureWarehouse;
import warehouse.Warehouse;

public class Factory implements Runnable {
    DepartureWarehouse dependentWarehouse;
    ProductType productType;
    int timeToCreate;

    public Factory(ProductType productType) {
        this.timeToCreate = Integer.parseInt(
                Infrastructure
                        .getProperties()
                            .get("factory_time")
                                .toString()
        );

        this.productType = productType;

        if (Infrastructure.getDepartures().containsKey(productType)) {
            this.dependentWarehouse = Infrastructure.getDepartures().get(productType);
        } else {
            DepartureWarehouse newWarehouse = new DepartureWarehouse(productType);
            this.dependentWarehouse = newWarehouse;

            Infrastructure.getDepartures().put(productType, newWarehouse);
        }
    }

    @Override
    public void run() {

    }

    private Product createProduct() {
        try {
            Thread.sleep(timeToCreate);
        } catch (InterruptedException ignored) {
        }

        return new Product(this.productType);
    }
}
