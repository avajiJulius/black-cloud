package com.blackcloud.desktop.service.zip;

import com.blackcloud.desktop.service.encryption.EncryptionService;
import com.blackcloud.desktop.service.encryption.IEncryptionService;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipService implements IZipService {


    @Override
    public String zip(Path srcPath) throws IOException {
        File srcFile = srcPath.toFile();
        String filename = srcPath.getFileName().toString();
        String outFile = String.valueOf(srcPath).concat(".zip");
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(outFile));
        zipFile(srcFile, filename, zipOut);
        zipOut.close();
        return outFile;
    }

    @Override
    public void unzip(Path srcPath) throws IOException{
        File srcFile = srcPath.toFile();
        int length = srcFile.getAbsolutePath().length();
        File dstFile = new File(srcFile.getAbsolutePath().substring(0, length - 4));

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(srcFile));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(dstFile, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    private File newFile(File dstFile, ZipEntry zipEntry) throws IOException {
        File destFile = new File(dstFile, zipEntry.getName());

        String destDirPath = dstFile.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


    private void zipFile(File srcFile, String filename, ZipOutputStream zipOut) throws IOException {
        if (srcFile.isHidden()) {
            return;
        }
        if (srcFile.isDirectory()) {
            if (filename.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(filename));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(filename + "/"));
                zipOut.closeEntry();
            }
            File[] children = srcFile.listFiles();
            for (File childFile : children) {
                zipFile(childFile,filename + "/" + childFile.getName(), zipOut);
            }
            return;
        }

        FileInputStream fis = new FileInputStream(srcFile);
        ZipEntry zipEntry = new ZipEntry(filename);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
