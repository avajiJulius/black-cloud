package com.cloudcastle.security.model;

public class Client extends Participant {
    private String password;

    public Client(String name, String password) {
        super(name);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
