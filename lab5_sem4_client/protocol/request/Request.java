package protocol.request;

import model.common.Sign;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final int rowCoord;
    private final int colCoord;
    private final Sign sign;

    public Request(int rowCoord, int colCoord, Sign sign) {
        this.rowCoord = rowCoord;
        this.colCoord = colCoord;
        this.sign = sign;
    }

    public int getRowCoord() {
        return rowCoord;
    }

    public int getColCoord() {
        return colCoord;
    }

    public Sign getSign() {
        return sign;
    }
}
