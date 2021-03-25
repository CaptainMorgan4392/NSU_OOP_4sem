package delivery.service_interface;

import warehouse.DepartureWarehouse;
import warehouse.Warehouse;

import java.util.ArrayList;

public interface Transport extends Runnable {
    enum TypeOfPathToSelect {
        FROM,
        TO
    };

    Path choosePath(TypeOfPathToSelect type);

    void deliver();
    void comeToDepot();
}
