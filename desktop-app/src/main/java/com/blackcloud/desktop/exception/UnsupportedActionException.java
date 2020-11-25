package com.blackcloud.desktop.exception;

public class UnsupportedActionException extends UnsupportedOperationException {

    public UnsupportedActionException() {
        super();
    }

    public UnsupportedActionException(String message) {
        super(message);
    }

    public UnsupportedActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedActionException(Throwable cause) {
        super(cause);
    }
}
