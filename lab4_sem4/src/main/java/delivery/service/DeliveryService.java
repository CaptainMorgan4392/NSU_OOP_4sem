package delivery.service;

import delivery.path.BackwardPath;
import delivery.path.ForwardPath;
import delivery.station.ArrivalStation;
import delivery.station.DepartureStation;
import delivery.transport.Train;
import main.ConfigFormatException;
import main.Infrastructure;

import java.util.PriorityQueue;

public class DeliveryService {
    private volatile PriorityQueue <ForwardPath> forwardPaths = new PriorityQueue<>();
    private volatile PriorityQueue <BackwardPath> backwardPaths = new PriorityQueue<>();

    private final ArrivalStation arrivalStation = new ArrivalStation();
    private final DepartureStation departureStation;

    private int maxForward;
    private int maxBackward;

    public DeliveryService() throws ConfigFormatException {
        departureStation = new DepartureStation();

        String[] lengthsOfForwards = Infrastructure.getProperties().get("path_forwardPaths")
                .toString().split(", ");

        for (String lengthsOfForward : lengthsOfForwards) {
            forwardPaths.add(new ForwardPath(Integer.parseInt(lengthsOfForward)));
        }

        String[] lengthsOfBackwards = Infrastructure.getProperties().get("path_backwardPaths")
                .toString().split(", ");

        for (String lengthsOfBackward : lengthsOfBackwards) {
            backwardPaths.add(new BackwardPath(Integer.parseInt(lengthsOfBackward)));
        }

        this.maxBackward = -1;
        this.maxForward = -1;

        for (ForwardPath path : forwardPaths) {
            if (path.getLength() > maxForward) {
                this.maxForward = path.getLength();
            }
        }

        for (BackwardPath path : backwardPaths) {
            this.maxBackward = path.getLength();
        }
    }

    public void start() {
        for (Train train : this.getDepartureStation().getTrains()) {
            new Thread(train).start();
        }
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
}
