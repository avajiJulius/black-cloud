package com.cloudcastle.security.exception;

public class ServerInitializeError extends Error {

    public ServerInitializeError() {
        super();
    }

    public ServerInitializeError(String message) {
        super(message);
    }

    public ServerInitializeError(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerInitializeError(Throwable cause) {
        super(cause);
    }
}
