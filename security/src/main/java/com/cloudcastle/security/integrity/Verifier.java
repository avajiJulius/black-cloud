package com.cloudcastle.security.integrity;

import com.cloudcastle.security.exception.TransactionException;
import com.cloudcastle.security.model.Transaction;

import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;

public class Verifier {

    private Transaction transaction;
    private byte[] signature;
    private PublicKey key;

    public Verifier(Transaction transaction) throws TransactionException {
        this.transaction = transaction;
        this.key = transaction.getServerPublicKey();
    }

    public Verifier(Transaction transaction, byte[] signature) throws TransactionException {
        this.transaction = transaction;
        this.signature = signature;
        this.key = transaction.getServerPublicKey();
    }

    public boolean verify() throws GeneralSecurityException {
        Signature verification = Signature.getInstance("SHA256WithRSA");
        verification.initVerify(key);
        verification.update(transaction.getTransactionId());
        return verification.verify(signature);
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
