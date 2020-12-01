package com.cloudcastle.server.secure;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class SslHandlerProvider {

    private static final String PROTOCOL = "TLS";
    private static final String ALGORITHM_SUN_X509 = "SunX509";
    private static final String ALGORITHM = "ssl.KeyManagerFactory.algorithm";
    private static final String KEYSTORE = "C:\\ssl_cert\\keystore.jks";
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String KEYSTORE_PASSWORD = "password";
    private static final String CERT_PASSWORD = "password";
    private static SSLContext serverSSLContext = null;
    private static KeyManagerFactory kmf = null;

    public static SslHandler getSSLHandler(){
        SSLEngine sslEngine = null;
        if(serverSSLContext == null)
            System.exit(-1);
        else {
            sslEngine = serverSSLContext.createSSLEngine();
            sslEngine.setUseClientMode(false);
            sslEngine.setNeedClientAuth(false);
        }
        return new SslHandler(sslEngine);
    }

    public static void initSslContext() throws GeneralSecurityException, IOException {
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        InputStream inputStream = new FileInputStream(new File(KEYSTORE));
        String algorithm = Security.getProperty(ALGORITHM);

        if (algorithm == null)
            algorithm = ALGORITHM_SUN_X509;

        ks.load(inputStream, KEYSTORE_PASSWORD.toCharArray());
        inputStream.close();

        try {

            kmf = KeyManagerFactory.getInstance(algorithm);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(algorithm);

            kmf.init(ks, CERT_PASSWORD.toCharArray());
            tmf.init(ks);
            KeyManager[] keyManagers = kmf.getKeyManagers();
            TrustManager[] trustManagers = tmf.getTrustManagers();

            serverSSLContext = SSLContext.getInstance(PROTOCOL);
            serverSSLContext.init(keyManagers, trustManagers, null);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static KeyManagerFactory getKMF() {
        return kmf;
    }


}