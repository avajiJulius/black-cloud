package com.cloudcastle.server.exception;

import java.security.GeneralSecurityException;

public class ServerInitializeError extends GeneralSecurityException {
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
