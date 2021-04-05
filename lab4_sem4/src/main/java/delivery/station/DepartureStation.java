package delivery.station;

import delivery.transport.Train;
import main.ConfigFormatException;
import main.Infrastructure;
import production.ProductType;

public class DepartureStation extends Station implements TrainCreator {
    private final int timeToCreate;
    public DepartureStation() throws ConfigFormatException {
        super();

        this.timeToCreate = Integer.parseInt(Infrastructure.getProperties().get("depot_timeToCreate").toString());
    }

    @Override
    public void createTrain(ProductType productType, int toDeliver) {
        final long SECOND_MILLIS = 1000;
        int amortisation = Integer.parseInt(Infrastructure.getProperties().get("train_amortisation").toString());

        try {
            Thread.sleep(this.getTimeToCreate() * SECOND_MILLIS);
            Train newTrain = new Train(productType, amortisation, toDeliver);
            this.putTrainInto(newTrain);
            new Thread(newTrain).start();
        } catch (InterruptedException e) {
            return;
        }
    }

    public int getTimeToCreate() {
        return timeToCreate;
    }
}
