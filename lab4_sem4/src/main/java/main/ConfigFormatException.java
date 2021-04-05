package main;

public class ConfigFormatException extends Exception {
    private final String msg = "Config has wrong format!";

    public ConfigFormatException() {
        super();
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
