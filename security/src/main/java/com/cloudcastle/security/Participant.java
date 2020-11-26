package com.cloudcastle.security;

public class Participant {
    private String name;
    public static final int SENDER = 1;
    public static final int RECEIVER = 2;

    public Participant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
