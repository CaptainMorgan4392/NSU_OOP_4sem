package main;

import consumer.ConcreteConsumer;
import delivery.service.DeliveryService;
import delivery.transport.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import producer.Factory;
import production.ProductType;
import warehouse.ArrivalWarehouse;
import warehouse.DepartureWarehouse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Infrastructure {
    private static final Logger logger = LogManager.getLogger(Infrastructure.class);

    private static final Properties properties = new Properties();

    private static final ArrayList <Factory> factories = new ArrayList<>();
    private static final ArrayList <ConcreteConsumer> consumers = new ArrayList<>();

    private static final HashMap <ProductType, DepartureWarehouse> departures = new HashMap<>();
    private static final HashMap <ProductType, ArrivalWarehouse> arrivals = new HashMap<>();

    private static final DeliveryService delivery;

    static {
        InputStream inputStream = Infrastructure.class.getResourceAsStream("/config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Unable to create infrastructure...");
            throw new ExceptionInInitializerError(e);
        }

        try {
            delivery = new DeliveryService();
            parseConfig();
        } catch (Exception e) {
            logger.error("Unable to create infrastructure...");
            throw new ExceptionInInitializerError(e);
        }

        logger.trace("Infrastructure created!");
    }

    private static void parseConfig() throws Exception {
        //Warehouses
        final int numberOfTypes = Integer.parseInt(getProperties().get("numberOfTypes").toString());

        String[] productTypes = getProperties().get("productTypes").toString().split(", ");
        if (productTypes.length != numberOfTypes) {
            throw new ConfigFormatException();
        }

        final int ARRIVAL_CAPACITY = Integer.parseInt(getProperties().get("warehouse_arrivalCapacity").toString());
        final int DEPARTURE_CAPACITY = Integer.parseInt(getProperties().get("warehouse_departureCapacity").toString());

        for (int i = 0; i < numberOfTypes; ++i) {
            ProductType curType = new ProductType(productTypes[i]);

            departures.put(curType, new DepartureWarehouse(curType, DEPARTURE_CAPACITY));
            arrivals.put(curType, new ArrivalWarehouse(curType, ARRIVAL_CAPACITY));
        }

        //Factories
        final int numberOfFactories = Integer.parseInt(getProperties().get("factory_numberOfFactories").toString());

        String[] factoryTypes = getProperties().get("factory_factoryTypes").toString().split(", ");
        String[] factoryTimes = getProperties().get("factory_factoryTimes").toString().split(", ");

        if (factoryTypes.length != numberOfFactories || factoryTimes.length != numberOfFactories) {
            throw new ConfigFormatException();
        }

        for (int i = 0; i < numberOfFactories; ++i) {
            ProductType curType = new ProductType(factoryTypes[i]);

            factories.add(new Factory(departures.get(curType), Integer.parseInt(factoryTimes[i])));
        }

        //Consumers
        final int numberOfConsumers = Integer.parseInt(getProperties().get("consumer_numberOfConsumers").toString());

        String[] consumerTypes = getProperties().get("consumer_consumerTypes").toString().split(", ");
        String[] consumerTimes = getProperties().get("consumer_consumerTimes").toString().split(", ");

        if (consumerTypes.length != numberOfFactories || consumerTimes.length != numberOfFactories) {
            throw new ConfigFormatException();
        }

        for (int i = 0; i < numberOfFactories; ++i) {
            ProductType curType = new ProductType(consumerTypes[i]);

            consumers.add(new ConcreteConsumer(arrivals.get(curType), Integer.parseInt(consumerTimes[i])));
        }

        int numberOfTrains = Integer.parseInt(Infrastructure.getProperties().get("train_numberOfTrains").toString());
        String[] types = Infrastructure.getProperties().get("train_typesOfTrains").toString().split(", ");
        String[] contracts = Infrastructure.getProperties().get("train_contracts").toString().split(", ");

        if (types.length != numberOfTrains || contracts.length != numberOfTrains) {
            throw new ConfigFormatException();
        }

        int amortisation = Integer.parseInt(Infrastructure.getProperties().get("train_amortisation").toString());
        for (int i = 0; i < numberOfTrains; ++i) {
            delivery.getDepartureStation().getTrains().add(
                    new Train(new ProductType(types[i]), amortisation, Integer.parseInt(contracts[i])));
        }
    }

    public static void start() {
        logger.trace("Starting...");

        for (ConcreteConsumer consumer : consumers) {
            new Thread(consumer).start();
        }

        delivery.start();

        for (Factory factory : factories) {
            new Thread(factory).start();
        }

        logger.trace("All threads launched!");
    }

    public static Properties getProperties() {
        return properties;
    }

    public static ArrayList<Factory> getFactories() {
        return factories;
    }

    public static ArrayList<ConcreteConsumer> getConsumers() {
        return consumers;
    }

    public static HashMap<ProductType, DepartureWarehouse> getDepartures() {
        return departures;
    }

    public static HashMap<ProductType, ArrivalWarehouse> getArrivals() {
        return arrivals;
    }

    public static DeliveryService getDeliveryService() {
        return delivery;
    }
}
