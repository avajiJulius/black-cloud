package com.cloudcastle.security.integrity;

import com.cloudcastle.security.exception.TransactionException;
import com.cloudcastle.security.model.Client;
import com.cloudcastle.security.model.Server;
import com.cloudcastle.security.model.Transaction;
import com.cloudcastle.security.model.Transactions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class SignatureServiceTest {


    @AfterEach
    public void postTest() {
        Transactions.getInstance().stop();
    }

    @Test
    void When_TransactionIsSame_Expect_VerifyAsTrue() throws GeneralSecurityException, TransactionException {
        Transaction transaction = Transaction.getInstance(new Client("avaji", "123"), Server.connect());

        SignatureService signatureService = new SignatureHandler(transaction);
        byte[] signature = signatureService.sign();
        boolean verify = signatureService.verify(signature);

        Assertions.assertTrue(verify);

    }


    @Test
    public void When_TransactionInSignatureServiceIsDifferent_Expect_VerifyAsFalse() throws GeneralSecurityException, TransactionException {
        Transaction signTransaction = Transaction.getInstance(new Client("avaji", "123"), Server.connect());
        Transaction verifyTransaction = Transaction.getInstance(new Client("avaji1", "123"), Server.connect());

        SignatureService signService = new SignatureHandler(signTransaction);
        SignatureService verifyService = new SignatureHandler(verifyTransaction);

        byte[] signature = signService.sign();
        boolean verify = verifyService.verify(signature);

        Assertions.assertFalse(verify);
    }


    @Test
    public void Given_FirstTransactionInSignatureService_When_CloseTransactionAndInitSecondWithSameParticipants_Then_VerifyWillFail() throws GeneralSecurityException, TransactionException {
        Transaction first = Transaction.getInstance(new Client("avaji", "123"), Server.connect());
        SignatureService firstService = new SignatureHandler(first);
        byte[] signature = firstService.sign();
        first.close();

        Transaction second = Transaction.getInstance(new Client("avaji", "123"), Server.connect());
        SignatureService secondService = new SignatureHandler(second);

        boolean verify = secondService.verify(signature);

        Assertions.assertFalse(verify);
    }

}