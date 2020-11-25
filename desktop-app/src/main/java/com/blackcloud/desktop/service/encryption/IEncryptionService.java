package com.blackcloud.desktop.service.encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;

public interface IEncryptionService {
    void encrypt(File srcFile, File dstFile) throws GeneralSecurityException, IOException;
    void decrypt(File srcFile, File dstFile) throws GeneralSecurityException, IOException;
}
