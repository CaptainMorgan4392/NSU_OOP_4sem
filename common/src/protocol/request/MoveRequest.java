package protocol.request;

import model.common.Sign;
import protocol.Pair;

public class MoveRequest {
    Pair <Pair<Integer, Integer>, Sign> data;

    public MoveRequest(Pair <Pair<Integer, Integer>, Sign> data) {
        this.data = data;
    }

    public Pair <Pair<Integer, Integer>, Sign> getData() {
        return data;
    }
}
