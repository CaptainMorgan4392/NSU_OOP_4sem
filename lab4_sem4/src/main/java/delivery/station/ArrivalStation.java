package delivery.station;

import delivery.service.DeliveryService;
import main.ConfigFormatException;

public class ArrivalStation extends Station {
    public ArrivalStation(DeliveryService deliveryService) throws ConfigFormatException {
        super(deliveryService);

        this.capacity = Integer.parseInt(
                deliveryService.getInfrastructure().getProperties().get("depot_arrivalCapacity").toString()
        );
    }
}
