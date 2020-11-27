package com.cloudcastle.security.model;

import java.util.HashSet;
import java.util.Set;
//TODO add Iterator
public class Transactions {
    private static Transactions transactions;
    private Set<Transaction> set;

    public static Transactions getInstance() {
        if (transactions == null) {
            transactions = new Transactions();
        }
        return transactions;
    }

    private Transactions() {
        this.set = new HashSet<>();
    }

    public void add(Transaction transaction) {
        this.set.add(transaction);
    }

    public void remove(Transaction transaction) {
        this.set.remove(transaction);
    }

    public void stop() {
        transactions = null;
    }

    public Set<Transaction> getSet(){
        return this.set;
    }
}
