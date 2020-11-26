package com.cloudcastle.security.integrity;

import java.security.GeneralSecurityException;

public interface SignatureService {
    byte[] sign() throws GeneralSecurityException;
    boolean verify() throws GeneralSecurityException;
}
