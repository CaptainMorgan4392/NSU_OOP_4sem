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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Infrastructure {
    private class ConfigParser {
        private void initWarehouses() throws ConfigFormatException {
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
        }

        private void initFactories() throws ConfigFormatException {
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
        }

        private void initConsumers() throws ConfigFormatException {
            final int numberOfConsumers = Integer.parseInt(getProperties().get("consumer_numberOfConsumers").toString());

            String[] consumerTypes = getProperties().get("consumer_consumerTypes").toString().split(", ");
            String[] consumerTimes = getProperties().get("consumer_consumerTimes").toString().split(", ");

            if (consumerTypes.length != numberOfConsumers || consumerTimes.length != numberOfConsumers) {
                throw new ConfigFormatException();
            }

            for (int i = 0; i < numberOfConsumers; ++i) {
                ProductType curType = new ProductType(consumerTypes[i]);

                consumers.add(new ConcreteConsumer(arrivals.get(curType), Integer.parseInt(consumerTimes[i])));
            }
        }

        private void initStartingTrains() throws ConfigFormatException {
            int numberOfTrains = Integer.parseInt(getProperties().get("train_numberOfTrains").toString());
            String[] types = getProperties().get("train_typesOfTrains").toString().split(", ");
            String[] contracts = getProperties().get("train_contracts").toString().split(", ");

            if (types.length != numberOfTrains || contracts.length != numberOfTrains) {
                throw new ConfigFormatException();
            }

            int amortisation = Integer.parseInt(getProperties().get("train_amortisation").toString());
            for (int i = 0; i < numberOfTrains; ++i) {
                delivery.getDepartureStation().getTrains().add(
                        new Train(getDeliveryService(), new ProductType(types[i]),
                                amortisation, Integer.parseInt(contracts[i])));
            }
        }
    }



    private static final Logger logger = LogManager.getLogger(Infrastructure.class);
    private final ConfigParser parser = new ConfigParser();

    private final Properties properties = new Properties();

    private final ArrayList <Factory> factories = new ArrayList<>();
    private final ArrayList <ConcreteConsumer> consumers = new ArrayList<>();

    private final HashMap <ProductType, DepartureWarehouse> departures = new HashMap<>();
    private final HashMap <ProductType, ArrivalWarehouse> arrivals = new HashMap<>();

    private DeliveryService delivery;

    public Infrastructure(String filename) throws Exception {
        InputStream inputStream;
        inputStream = Infrastructure.class.getResourceAsStream(filename);

        properties.load(inputStream);
        parseConfig();
    }

    public Infrastructure(InputStream inputStream) throws Exception {
        properties.load(inputStream);
        parseConfig();
    }

    private void parseConfig() throws Exception {
        //Warehouses
        parser.initWarehouses();

        //Factories
        parser.initFactories();

        //Consumers
        parser.initConsumers();
    }

    public void start() throws ConfigFormatException {
        delivery = new DeliveryService(this);
        parser.initStartingTrains();

        logger.trace("Starting...");

        for (ConcreteConsumer consumer : consumers) {
            consumer.start();
        }

        delivery.start();

        for (Factory factory : factories) {
            factory.start();
        }

        logger.trace("All threads launched!");
    }

    public void stop() {
        logger.trace("ATTENTION! You have destroyed infrastructure!");

        for (ConcreteConsumer consumer : consumers) {
            consumer.stop();
        }

        delivery.getDepartureStation().destroyTrains();

        for (Factory factory : factories) {
            factory.stop();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public ArrayList<Factory> getFactories() {
        return factories;
    }

    public ArrayList<ConcreteConsumer> getConsumers() {
        return consumers;
    }

    public HashMap<ProductType, DepartureWarehouse> getDepartures() {
        return departures;
    }

    public HashMap<ProductType, ArrivalWarehouse> getArrivals() {
        return arrivals;
    }

    public DeliveryService getDeliveryService() {
        return delivery;
    }
}
