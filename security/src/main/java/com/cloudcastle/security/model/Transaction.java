package com.cloudcastle.security.model;

import com.cloudcastle.security.exception.TransactionException;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;

public class Transaction {
    private static Transactions transactions;
    private Participant sender;
    private Participant receiver;
    private byte[] id;

    public static Transaction getInstance(Participant sender, Participant receiver) throws NoSuchAlgorithmException {
        if (transactions != null) {
            for (Transaction t : transactions.getSet()) {
                if ((t.sender.getName()).equals(sender.getName()) && (t.receiver.getName()).equals(t.receiver.getName())) {
                    transactions.add(t);
                    return t;
                }
            }
        }
        Transaction transaction = new Transaction(sender, receiver);
        transactions = transactions.getInstance();
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

    public PublicKey getServerPublicKey() throws TransactionException {
        if(sender instanceof Server) {
            return ((Server) sender).connect().getPublicKey();
        } else if (receiver instanceof Server) {
            return ((Server) receiver).connect().getPublicKey();
        } else {
            throw new TransactionException();
        }
    }

    public byte[] getTransactionId() {
        return id;
    }

    public final void close() {
        transactions.remove(this);
    }

    @Override
    public String toString() {
        return "Transaction: " +
                "id = " + Arrays.toString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return Arrays.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }
}
