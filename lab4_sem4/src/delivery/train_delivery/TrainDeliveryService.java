package delivery.train_delivery;

import delivery.service_interface.Creator;
import delivery.service_interface.DeliveryService;
import delivery.service_interface.Depot;
import delivery.service_interface.Path;
import main_logic.Infrastructure;

public class TrainDeliveryService extends DeliveryService {
    public TrainDeliveryService() {
        super();

        this.creator = new TrainCreator(this);
        this.depot = new TrainDepot(this);

        String[] lengthLiterals = Infrastructure
                                    .getProperties()
                                    .get("paths_lengthsForward")
                                    .toString()
                                    .split(", ");
        int requiredNumberOfPaths = Integer.parseInt(
                Infrastructure.getProperties().get("paths_numberForward").toString()
        );

        if (requiredNumberOfPaths != lengthLiterals.length) {
            throw new ExceptionInInitializerError("Wrong config data!");
        }

        for (int i = 0; i < requiredNumberOfPaths; ++i) {
            int currentLength = Integer.parseInt(lengthLiterals[i]);
            forwardPaths.add(new ForwardTrainPath(currentLength));
        }

        lengthLiterals = Infrastructure
                .getProperties()
                .get("paths_lengthsBackward")
                .toString()
                .split(", ");
        requiredNumberOfPaths = Integer.parseInt(
                Infrastructure.getProperties().get("paths_numberBackward").toString()
        );

        if (requiredNumberOfPaths != lengthLiterals.length) {
            throw new ExceptionInInitializerError("Wrong config data!");
        }

        for (int i = 0; i < requiredNumberOfPaths; ++i) {
            int currentLength = Integer.parseInt(lengthLiterals[i]);
            backwardPaths.add(new BackwardTrainPath(currentLength));
        }

        for (Path path : forwardPaths) {
            if (path.getLength() > maxForward) {
                this.maxForward = path.getLength();
            }
        }

        for (Path path : backwardPaths) {
            if (path.getLength() > maxBackward) {
                this.maxBackward = path.getLength();
            }
        }
    }
}
