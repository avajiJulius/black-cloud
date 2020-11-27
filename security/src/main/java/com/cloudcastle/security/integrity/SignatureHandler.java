package com.cloudcastle.security.integrity;

import com.cloudcastle.security.exception.TransactionException;
import com.cloudcastle.security.model.Transaction;

import java.security.GeneralSecurityException;

public class SignatureHandler implements SignatureService {
    private Verifier verifier;
    private Signer signer;
    private Transaction transaction;

    public SignatureHandler(Transaction transaction) throws TransactionException {
        this.signer = new Signer(transaction);
        this.verifier = new Verifier(transaction);
        this.transaction = transaction;
    }

    @Override
    public byte[] sign() throws GeneralSecurityException {
        return signer.sign();
    }

    @Override
    public boolean verify(byte[] signature) throws GeneralSecurityException {
        verifier.setSignature(signature);
        return verifier.verify();
    }
}
