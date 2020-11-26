package com.cloudcastle.security.authentication;

import com.cloudcastle.security.model.Client;

import java.util.HashSet;
import java.util.Set;

//TODO Delete
public class ClientDatabase {
    private static ClientDatabase database;
    private Set<Client> clients;


    private ClientDatabase() {
        this.clients = new HashSet<>();
    }

    public static ClientDatabase getInstance() {
        if(database == null) {
            database = new ClientDatabase();
        }
        return database;
    }


    public boolean login(Client client) {
        for (Client c : clients) {
            if (c.getName().equals(client.getName()) && c.getPassword().equals(client.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public void add(Client client) {
        this.clients.add(client);
    }
}
