package protocol.response;

import model.common.Field;

public class FieldResponse extends Response<Field> {
    public FieldResponse(Field data) {
        super(data);
        this.responseType = ResponseType.MOVE;
        this.notificationType = NotificationType.BOTH;
    }

    @Override
    public Field getData() {
        return data;
    }
}
