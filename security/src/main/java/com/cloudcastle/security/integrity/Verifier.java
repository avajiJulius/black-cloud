package com.cloudcastle.security.integrity;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class Verifier {

    private Transaction transaction;
    private byte[] signature;
    private PublicKey senderKey;

    public boolean verify() throws GeneralSecurityException {
        Signature verification = Signature.getInstance("SHA256WithRSA");
        verification.initVerify(senderKey);
        verification.update(transaction.getTransactionId());
        return verification.verify(signature);
    }
}
