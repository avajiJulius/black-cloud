package com.cloudcastle.security.integrity;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

public class Signer {
    private Transaction transaction;
    private PrivateKey key;

    public byte[] sign() throws GeneralSecurityException {
        Signature signAlgorithm = Signature.getInstance("SHA256WithRSA");
        signAlgorithm.initSign(key);
        signAlgorithm.update(transaction.getTransactionId());
        return signAlgorithm.sign();
    }
}
