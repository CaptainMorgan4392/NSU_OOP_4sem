package delivery.station;

import delivery.transport.Train;
import main.ConfigFormatException;
import main.Infrastructure;

import java.util.ArrayList;

public abstract class Station {
    protected final ArrayList<Train> trains;

    protected int capacity;

    protected Station() throws ConfigFormatException {
        this.trains = new ArrayList<>();
        if (this instanceof ArrivalStation) {
            this.capacity = Integer.parseInt(
                    Infrastructure.getProperties().get("depot_arrivalCapacity").toString()
            );
        } else {
            this.capacity = Integer.parseInt(
                    Infrastructure.getProperties().get("depot_departureCapacity").toString()
            );
        }
    }

    public ArrayList <Train> getTrains() {
        return trains;
    }

    public synchronized void putTrainInto(Train train) throws InterruptedException {
        while (this.getTrains().size() == this.capacity) {
            wait();
        }

        this.getTrains().add(train);
        notifyAll();
    }

    public synchronized void getTrainFrom(Train train) throws InterruptedException {
        while (!this.getTrains().contains(train)) {
            wait();
        }

        this.getTrains().remove(train);
        notifyAll();
    }
}
