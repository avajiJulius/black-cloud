package com.cloudcastle.security.integrity;


import java.security.*;

public class IntegrityService {

    String message = "It's signature";

    public boolean confirmation() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair keyPair = generator.generateKeyPair();


        //Local computer (from)
        Signature algorithm = Signature.getInstance("SHA256WithRSA");
        algorithm.initSign(keyPair.getPrivate());
        algorithm.update(message.getBytes());
        byte[] signature = algorithm.sign();

        //Cloud storage (to)
        Signature verification = Signature.getInstance("SHA256WithRSA");
        //or certificate
        verification.initVerify(keyPair.getPublic());
        verification.update(message.getBytes());

        return verification.verify(signature);
    }
}
