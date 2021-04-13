package delivery.service;

import delivery.path.BackwardPath;
import delivery.path.ForwardPath;
import delivery.station.ArrivalStation;
import delivery.station.DepartureStation;
import main.ConfigFormatException;
import main.Infrastructure;

import java.util.PriorityQueue;

public class DeliveryService {
    private class DeliveryParametersParser {
        public void initForwardPaths() {
            String[] lengthsOfForwards = infrastructure.getProperties().get("path_forwardPaths")
                    .toString().split(", ");

            for (String lengthsOfForward : lengthsOfForwards) {
                forwardPaths.add(new ForwardPath(Integer.parseInt(lengthsOfForward)));
            }
        }

        public void initBackwardPaths() {
            String[] lengthsOfBackwards = infrastructure.getProperties().get("path_backwardPaths")
                    .toString().split(", ");

            for (String lengthsOfBackward : lengthsOfBackwards) {
                backwardPaths.add(new BackwardPath(Integer.parseInt(lengthsOfBackward)));
            }
        }

        public void detectMaxPaths() {
            maxBackward = -1;
            maxForward = -1;

            for (ForwardPath path : forwardPaths) {
                if (path.getLength() > maxForward) {
                    maxForward = path.getLength();
                }
            }

            for (BackwardPath path : backwardPaths) {
                maxBackward = path.getLength();
            }
        }
    }

    private final Infrastructure infrastructure;

    private volatile PriorityQueue <ForwardPath> forwardPaths = new PriorityQueue<>();
    private volatile PriorityQueue <BackwardPath> backwardPaths = new PriorityQueue<>();

    private final ArrivalStation arrivalStation;
    private final DepartureStation departureStation;

    private int maxForward;
    private int maxBackward;

    public DeliveryService(Infrastructure infrastructure) throws ConfigFormatException {
        this.infrastructure = infrastructure;
        arrivalStation = new ArrivalStation(this);
        departureStation = new DepartureStation(this);

        DeliveryParametersParser parser = new DeliveryParametersParser();
        parser.initForwardPaths();
        parser.initBackwardPaths();
        parser.detectMaxPaths();
    }

    public void start() {
        departureStation.launchTrains();
    }

    public PriorityQueue <ForwardPath> getForwardPaths() {
        return forwardPaths;
    }

    public PriorityQueue <BackwardPath> getBackwardPaths() {
        return backwardPaths;
    }

    public ArrivalStation getArrivalStation() {
        return arrivalStation;
    }

    public DepartureStation getDepartureStation() {
        return departureStation;
    }

    public synchronized ForwardPath delegateForwardPath() throws InterruptedException {
        while (this.getForwardPaths().isEmpty()) {
            wait();
        }

        return this.getForwardPaths().poll();
    }

    public synchronized BackwardPath delegateBackwardPath() throws InterruptedException {
        while (this.getBackwardPaths().isEmpty()) {
            wait();
        }

        return this.getBackwardPaths().poll();
    }

    public synchronized void getForwardPathBack(ForwardPath path) {
        getForwardPaths().add(path);

        notifyAll();
    }

    public synchronized void getBackwardPathBack(BackwardPath path) {
        getBackwardPaths().add(path);

        notifyAll();
    }

    public int getMaxForward() {
        return maxForward;
    }

    public int getMaxBackward() {
        return maxBackward;
    }

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }
}
