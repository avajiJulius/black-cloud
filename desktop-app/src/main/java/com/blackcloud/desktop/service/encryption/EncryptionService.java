package com.blackcloud.desktop.service.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

public class EncryptionService implements IEncryptionService{

    private IvParameterSpec ivSpec;
    private Key key;
    private Cipher cipher;
    private int keySize = 192;

    public EncryptionService() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(keySize);
            key = generator.generateKey();
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] random = new byte[16];
            secureRandom.nextBytes(random);
            ivSpec = new IvParameterSpec(random);
        } catch (GeneralSecurityException e) {
            //TODO EXCEPTION
        }
    }


    @Override
    public void encrypt(File srcFile, File dstFile) throws GeneralSecurityException, IOException {

    }

    @Override
    public void decrypt(File srcFile, File dstFile) throws GeneralSecurityException, IOException {

    }
}
