package com.cloudcastle.security;


import javax.crypto.Cipher;
import java.security.*;

public class MasterSecretService {

    private Key key;

    public MasterSecretService(Key key) {
        this.key = key;
    }

    public byte[] encrypt() throws GeneralSecurityException {
        //TODO Generator class for generate symmetric key
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] symmetricKey = new byte[32];
        secureRandom.nextBytes(symmetricKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(symmetricKey);
    }

    public byte[] decrypt(byte[] symmetricKey) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(symmetricKey);
    }
}
