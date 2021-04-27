package protocol.response;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ErrorResponse extends Response<Exception> {
    public ErrorResponse(Exception data) {
        super(data);
        this.responseType = ResponseType.MOVE;
        this.notificationType = NotificationType.SINGLE;
    }

    @Override
    public Exception getData() {
        return data;
    }
}
