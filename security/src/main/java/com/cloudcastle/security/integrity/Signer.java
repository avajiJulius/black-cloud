package com.cloudcastle.security.integrity;

import com.cloudcastle.security.model.Server;
import com.cloudcastle.security.model.Transaction;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;

public class Signer {
    private Server server = Server.connect();
    private Transaction transaction;
    private PrivateKey key;

    public Signer(Transaction transaction) {
        this.transaction = transaction;
        this.key = server.getPrivateKey();
    }

    public byte[] sign() throws GeneralSecurityException {
        Signature signAlgorithm = Signature.getInstance("SHA256WithRSA");
        signAlgorithm.initSign(key);
        signAlgorithm.update(transaction.getTransactionId());
        return signAlgorithm.sign();
    }
}
