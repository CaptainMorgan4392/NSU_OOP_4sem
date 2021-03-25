package delivery.service_interface;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class DeliveryService {
    protected Creator creator;
    protected Depot depot;
    protected PriorityQueue <Path> forwardPaths;
    protected PriorityQueue <Path> backwardPaths;

    protected int maxForward, maxBackward;

    protected DeliveryService() {
        forwardPaths = new PriorityQueue<>();
        backwardPaths = new PriorityQueue<>();
    }

    public int getMaxForward() {
        return maxForward;
    }

    public int getMaxBackward() {
        return maxBackward;
    }

    public Creator getCreator() {
        return creator;
    }

    public Depot getDepot() {
        return depot;
    }

    public PriorityQueue<Path> getForwardPaths() {
        return forwardPaths;
    }

    public PriorityQueue<Path> getBackwardPaths() {
        return backwardPaths;
    }
}
