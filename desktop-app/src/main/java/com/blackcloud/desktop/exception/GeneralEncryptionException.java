package com.blackcloud.desktop.exception;

import java.security.GeneralSecurityException;

public class GeneralEncryptionException extends GeneralSecurityException {


    public GeneralEncryptionException() {
        super();
    }

    public GeneralEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralEncryptionException(Throwable cause) {
        super(cause);
    }
}
