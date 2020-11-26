package com.cloudcastle.security.integrity;

import com.cloudcastle.security.exception.TransactionException;
import com.cloudcastle.security.model.Transaction;

import java.security.GeneralSecurityException;

public class SignatureHandler implements SignatureService {
    private Verifier verifier;
    private Signer signer;
    private Transaction transaction;

    public SignatureHandler(Transaction transaction) throws GeneralSecurityException, TransactionException {
        this.signer = new Signer(transaction);
        this.verifier = new Verifier(transaction, signer.sign());
        this.transaction = transaction;
    }

    @Override
    public byte[] sign() throws GeneralSecurityException {
        return signer.sign();
    }

    @Override
    public boolean verify() throws GeneralSecurityException {
        return verifier.verify();
    }
}
