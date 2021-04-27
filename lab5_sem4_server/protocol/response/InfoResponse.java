package protocol.response;

public class InfoResponse extends Response<String> {
    public InfoResponse(String data) {
        super(data);
        this.responseType = ResponseType.INFO;
        this.notificationType = NotificationType.SINGLE;
    }

    @Override
    public String getData() {
        return data;
    }
}
