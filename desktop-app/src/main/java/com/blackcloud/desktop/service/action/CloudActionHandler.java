package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import com.blackcloud.desktop.exception.UnsupportedActionException;
import com.blackcloud.desktop.service.encryption.IEncryptionService;
import com.blackcloud.desktop.service.zip.IZipService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

public class CloudActionHandler extends DefaultActionHandler implements ActionHandler{

    private PanelController srcPC;
    private PanelController dstPC;
    private IZipService zipService;
    private IEncryptionService encryptionService;

    public CloudActionHandler(PanelController srcPC) {
        super(srcPC);
        this.srcPC = srcPC;
    }

    public CloudActionHandler(PanelController srcPC, PanelController dstPC) {
        super(srcPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }

    public CloudActionHandler(PanelController srcPC, PanelController dstPC,
                              IZipService zipService, IEncryptionService encryptionService) {
        super(srcPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
        this.zipService = zipService;
        this.encryptionService = encryptionService;
    }

    @Override
    public void exportAction() {
        throw new UnsupportedActionException("Export not supported in Cloud storage");
    }

    //TODO add delete import realization for cloud
    @Override
    public void importAction() throws IOException, GeneralSecurityException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());


        encryptionService.decrypt(srcPath.toFile(), dstPath.toFile());
        zipService.unzip(srcPath);

//        String newName = dstPath.getFileName().toString();
//        newName = newName.substring(0, newName.lastIndexOf("."));
//        dstPath.toFile().renameTo(new File(newName));

        srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }

    //TODO add delete realization for cloud
    @Override
    public void deleteAction() {
        throw new UnsupportedOperationException();
    }

    //TODO add create folder realization for cloud
    @Override
    public void createFolder(String folderName) {
        throw new UnsupportedOperationException();
    }

}
