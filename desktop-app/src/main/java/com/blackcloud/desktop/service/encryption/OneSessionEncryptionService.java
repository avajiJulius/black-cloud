package com.blackcloud.desktop.service.encryption;


import org.apache.commons.io.FileUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.SecureRandom;

public class OneSessionEncryptionService implements IEncryptionService{

    private IvParameterSpec ivSpec;
    private Key key;
    private Cipher cipher;
    private int keySize = 192;

    public OneSessionEncryptionService() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
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

        byte[] input = FileUtils.readFileToByteArray(srcFile);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] output = cipher.doFinal(input);
        FileOutputStream out = new FileOutputStream(dstFile);
        out.write(output);
        out.close();
    }

    @Override
    public void decrypt(File srcFile, File dstFile) throws GeneralSecurityException, IOException {
        byte[] input = FileUtils.readFileToByteArray(srcFile);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] output = cipher.doFinal(input);
        FileOutputStream out = new FileOutputStream(dstFile);
        out.write(output);
        out.close();
    }
}
