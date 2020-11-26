package com.cloudcastle.security.authentication;

import com.cloudcastle.security.model.Client;

public class Authentication {
    private ClientDatabase clientDatabase = ClientDatabase.getInstance();
    private Client client;

    public Authentication(Client client) {
        this.client = client;
    }

    public void singUp() {
        clientDatabase.add(client);
    }

    public  boolean login() {
        return clientDatabase.login(client);
    }

    public void logout() {
        //TODO logout
    }
}
