package com.cloudcastle.security.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {


    @AfterEach
    public void postTest() {
        Transactions.getInstance().stop();
    }

    @Test
    public void When_Create1000DifferentTransactions_Expect_TransactionsSizeEquals1000() throws NoSuchAlgorithmException {
        Transactions transactions = Transactions.getInstance();
        for (int i = 0; i < 1000; i++) {
            transactions.add(Transaction.getInstance(new Client(String.valueOf(i), String.valueOf(i)), Server.connect()));
        }
        int result = transactions.getSet().size();

        Assertions.assertEquals(1000, result);

    }

    @Test
    public void When_Create1000SameTransactions_Expect_TransactionsSizeEquals1() throws NoSuchAlgorithmException {
        Transactions transactions = Transactions.getInstance();
        for (int i = 0; i < 1000; i++) {
            transactions.add(Transaction.getInstance(new Client("1", "1"), Server.connect()));
        }
        int result = transactions.getSet().size();

        Assertions.assertEquals(1, result);

    }


    @Test
    public void Given_OneTransaction_When_CloseFirstTransactionAndCreateSecondWithSameName_Then_SecondTransactionWillBeDifferent() throws NoSuchAlgorithmException {
        Transaction first = Transaction.getInstance(new Client("1", "1"), new Client("2", "2"));
        first.close();
        Transaction second = Transaction.getInstance(new Client("1", "1"), new Client("2" ,"2"));


        Assertions.assertNotSame(first, second);
    }

}