package consume;

import main_logic.Infrastructure;
import product.Product;
import product.ProductType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements Runnable {
    private final Map <ProductType, Integer> contract;
    private final int consumeTime;

    public Consumer() {
        this.consumeTime = Integer.parseInt(
                Infrastructure.getProperties().get("consumer_time").toString()
        );

        String[] productTypes = Infrastructure
                                    .getProperties()
                                    .get("consumer_productTypes")
                                    .toString()
                                    .split(", ");

        int requiredNumberOfElements = Integer.parseInt(
                Infrastructure.getProperties().get("consumer_number").toString()
        );

        String[] quantities = Infrastructure
                                    .getProperties()
                                    .get("consumer_numbersOfProducts")
                                    .toString()
                                    .split(", ");

        if (requiredNumberOfElements != quantities.length || requiredNumberOfElements != productTypes.length) {
            throw new ExceptionInInitializerError("Wrong config data!");
        }

        contract = new HashMap<>();
        for (int i = 0; i < requiredNumberOfElements; ++i) {
            ProductType currentProductType = new ProductType(productTypes[i]);
            int currentQuantity = Integer.parseInt(quantities[i]);

            contract.put(currentProductType, currentQuantity);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (!canContractBeSatisfied()) {
                try {
                    wait();
                } catch (InterruptedException ignored) {
                }
            }

            for (Map.Entry <ProductType, Integer> entry : contract.entrySet()) {
                for (int i = 0; i < entry.getValue(); ++i) {
                    Infrastructure.getArrivals().get(entry.getKey()).get();
                }
            }
            notifyAll();

            try {
                Thread.sleep(getConsumeTime());
            } catch (InterruptedException ignored) {
            }
        }
    }

    private boolean canContractBeSatisfied() {
        AtomicBoolean satisfied = new AtomicBoolean(true);
        contract.forEach((type, numberOf) -> {
            if (Infrastructure.getArrivals().get(type).getCurrentStored() < numberOf) {
                satisfied.set(false);
            }
        });

        return satisfied.get();
    }

    public Map <ProductType, Integer> getContract() {
        return contract;
    }

    public int getConsumeTime() {
        return consumeTime;
    }
}
