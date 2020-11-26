package com.cloudcastle.security.integrity;

import com.cloudcastle.security.Participant;
import com.cloudcastle.security.exception.TransactionException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Transaction {
    private static Set<Transaction> transactions;
    private Participant sender;
    private Participant receiver;
    private byte[] id;

    public static Transaction getInstance(Participant sender, Participant receiver) throws NoSuchAlgorithmException, TransactionException {
        if (!transactions.isEmpty()) {
            for (Transaction t : transactions) {
                if ((t.sender).equals(sender) && (t.receiver).equals(t.receiver)) {
                    transactions.add(t);
                    return t;
                }
            }
        }
        Transaction transaction = new Transaction(sender, receiver);
        transactions.add(transaction);
        return transaction;
    }

    private Transaction(Participant sender, Participant receiver) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        this.id = new byte[8];
        random.nextBytes(id);
        this.sender = sender;
        this.receiver = receiver;
    }

    public byte[] getTransactionId() {
        return id;
    }


}
