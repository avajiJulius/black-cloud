package com.cloudcastle.security.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Transactions implements Iterable<Transaction> {
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

    @Override
    public Iterator<Transaction> iterator() {
        return transactions.getSet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Transaction> action) {
        transactions.getSet().forEach(action);
    }

    @Override
    public Spliterator<Transaction> spliterator() {
        return transactions.getSet().spliterator();
    }
}
