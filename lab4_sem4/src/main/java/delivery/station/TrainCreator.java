package delivery.station;

import delivery.transport.Train;
import production.ProductType;

public interface TrainCreator {
    void createTrain(ProductType productType, int toDeliver);
}
