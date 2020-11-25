package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import com.blackcloud.desktop.service.encryption.IEncryptionService;
import com.blackcloud.desktop.service.zip.IZipService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

public class SecureActionHandler extends DefaultActionHandler implements ActionHandler {

    private PanelController srcPC;
    private PanelController dstPC;
    private IZipService zipService;
    private IEncryptionService encryptionService;

    public SecureActionHandler(PanelController srcPC) {
        super(srcPC);
        this.srcPC = srcPC;
    }

    public SecureActionHandler(PanelController srcPC, PanelController dstPC) {
        super(srcPC, dstPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }

    public SecureActionHandler(PanelController srcPC, PanelController dstPC,
                               IZipService zipService, IEncryptionService encryptionService) {
        super(srcPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
        this.zipService = zipService;
        this.encryptionService = encryptionService;
    }


    //TODO
    @Override
    public void exportAction() throws IOException, GeneralSecurityException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());


        String zip = zipService.zip(srcPath);
        Path zipPath = Paths.get(zip);
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(zipPath.getFileName().toString());

        encryptionService.encrypt(zipPath.toFile(), dstPath.toFile());

        srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }


    @Override
    public void importAction() throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
        FileUtils.copyDirectory(srcPath.toFile(), dstPath.toFile());
        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }


}
