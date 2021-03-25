package delivery.train_delivery;

import delivery.service_interface.Creator;
import delivery.service_interface.DeliveryService;
import delivery.service_interface.Depot;

public class TrainDeliveryService extends DeliveryService {
    public TrainDeliveryService() {
        super();

        this.creator = new TrainCreator(this);
        this.depot = new TrainDepot(this);

        //paths

        //get max and min
    }
}
