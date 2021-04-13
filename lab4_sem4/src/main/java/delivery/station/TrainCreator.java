package delivery.station;

import production.ProductType;

public interface TrainCreator {
    void createTrain(ProductType productType, int toDeliver);
}
