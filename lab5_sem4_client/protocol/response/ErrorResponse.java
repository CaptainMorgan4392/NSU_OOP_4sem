package protocol.response;

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
