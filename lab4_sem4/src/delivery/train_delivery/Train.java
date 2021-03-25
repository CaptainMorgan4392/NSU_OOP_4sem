package delivery.train_delivery;

import delivery.service_interface.DeliveryService;
import delivery.service_interface.Depot;
import delivery.service_interface.Path;
import delivery.service_interface.Transport;
import main_logic.Infrastructure;
import product.Product;
import product.ProductType;
import warehouse.DepartureWarehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Train implements Transport {
    private final Map <ProductType, Integer> contract;
    private final int timeToDestruction;
    private final DeliveryService deliveryService;

    public Train(TrainDeliveryService service) {
        this.timeToDestruction = Integer.parseInt(
            Infrastructure.getProperties().get("train_timeAmortisation").toString()
        );

        this.deliveryService = service;

        String[] productTypes = Infrastructure
                .getProperties()
                .get("train_productTypes")
                .toString()
                .split(", ");

        String[] quantities = Infrastructure
                .getProperties()
                .get("train_numbersOfProducts")
                .toString()
                .split(", ");

        int requiredNumberOfElements = Integer.parseInt(
                Infrastructure.getProperties().get("train_numberOfTrains").toString()
        );

        if (requiredNumberOfElements != quantities.length || requiredNumberOfElements != productTypes.length) {
            throw new ExceptionInInitializerError("Wrong config data!");
        }

        contract = new HashMap<>();
        for (int i = 0; i < requiredNumberOfElements; ++i) {
            ProductType currentProductType = new ProductType(productTypes[i]);
            int currentQuantity = Integer.parseInt(quantities[i]);

            this.contract.put(currentProductType, currentQuantity);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (this.isUseless()) {
                new Thread(new TrainCreator(this.getDeliveryService())).start();
                Thread.currentThread().interrupt();
            }

            deliver();
            notifyAll();

            comeToDepot();
            notifyAll();
        }
    }

    private boolean canContractBeSatisfied() {
        AtomicBoolean satisfied = new AtomicBoolean(true);
        contract.forEach((type, numberOf) -> {
            if (Infrastructure.getDepartures().get(type).getCurrentStored() < numberOf) {
                satisfied.set(false);
            }
        });

        return satisfied.get();
    }

    @Override
    public synchronized Path choosePath(TypeOfPathToSelect type) {
        PriorityQueue <Path> selectedQueue = switch (type) {
            case FROM -> Infrastructure.getDeliveryService().getForwardPaths();

            case TO -> Infrastructure.getDeliveryService().getBackwardPaths();
        };

        if (selectedQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        return selectedQueue.poll();
    }

    private boolean isUseless() {
        return this.getTimeToDestruction() < this.getDeliveryService().getMaxForward()
                                            + this.getDeliveryService().getMaxBackward();
    }

    @Override
    public void deliver() {
        Path path = choosePath(TypeOfPathToSelect.FROM);

        try {
            Thread.sleep(path.getLength());
        } catch (InterruptedException ignored) {
        }

        this.getDeliveryService().getForwardPaths().add(path);
    }

    @Override
    public void comeToDepot() {
        Path path = choosePath(TypeOfPathToSelect.TO);

        try {
            Thread.sleep(path.getLength());
        } catch (InterruptedException ignored) {
        }

        this.getDeliveryService().getBackwardPaths().add(path);
        this.getDeliveryService().getDepot().getTransportInDepot(this);
    }

    public int getTimeToDestruction() {
        return timeToDestruction;
    }

    public TrainDeliveryService getDeliveryService() {
        return (TrainDeliveryService)deliveryService;
    }
}
