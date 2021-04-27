package protocol.response;

import model.common.Field;

public class MoveResponse extends Response<Field> {
    public MoveResponse(Field data) {
        super(data);
        this.responseType = ResponseType.MOVE;
        this.notificationType = NotificationType.ALL;
    }

    @Override
    public Field getData() {
        return null;
    }
}
