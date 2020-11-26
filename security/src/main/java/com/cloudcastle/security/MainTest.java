package com.cloudcastle.security;

import com.cloudcastle.security.authentication.Authentication;
import com.cloudcastle.security.authentication.ClientDatabase;
import com.cloudcastle.security.exception.TransactionException;
import com.cloudcastle.security.integrity.SignatureHandler;
import com.cloudcastle.security.integrity.SignatureService;
import com.cloudcastle.security.model.Client;
import com.cloudcastle.security.model.Server;
import com.cloudcastle.security.model.Transaction;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

public class MainTest {
    public static void main(String[] args) throws TransactionException, GeneralSecurityException {
        Transaction transaction = Transaction.getInstance(new Client("avaji", "123"), Server.connect());

        SignatureService signatureService = new SignatureHandler(transaction);
        signatureService.sign();
        if(signatureService.verify()) {
            System.out.println("Yesssss!");
        }

    }
}
