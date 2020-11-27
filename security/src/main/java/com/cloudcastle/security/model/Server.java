package com.cloudcastle.security.model;

import com.cloudcastle.security.exception.ServerInitializeError;

import java.security.*;

public class Server extends Participant {

    private static Server server;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public static Server connect() {
        if(server == null) {
            server = new Server();
        }
        return server;
    }

    private Server() {
        super("Cloud Castle server");
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            throw new ServerInitializeError("Server Init Error",e);
        }
}

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
