package com.cloudcastle.security.certificate;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class CertificateStore {

    private String password = "MyPassword";
    public void store() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
//        keyStore.load(InputStream.nullInputStream(), password.toCharArray());
//        Key key = keyStore.getKey()
    }
}
