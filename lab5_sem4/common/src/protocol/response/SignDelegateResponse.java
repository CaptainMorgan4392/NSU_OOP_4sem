package protocol.response;

import model.common.Sign;

public class SignDelegateResponse extends Response<Sign> {
    public SignDelegateResponse(Sign data) {
        super(data);
        this.responseType = ResponseType.SIGN_DELEGATION;
        this.notificationType = NotificationType.SINGLE;
    }

    @Override
    public Sign getData() {
        return data;
    }
}
