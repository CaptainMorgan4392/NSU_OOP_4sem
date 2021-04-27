package protocol.response;

import java.io.Serializable;

public abstract class Response<T> implements Serializable {
    protected static final long serialVersionUID = 507523424653628438L;

    public enum ResponseType {
        INFO,
        MOVE,
        ERROR,
        SIGN_DELEGATION
    }

    public enum NotificationType {
        SINGLE,
        ALL
    }

    protected T data;

    protected ResponseType responseType;
    protected NotificationType notificationType;

    protected Response(T data) {
        this.data = data;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public abstract T getData();
}
