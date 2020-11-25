package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import com.blackcloud.desktop.service.zip.IZipService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SecureActionHandler extends DefaultActionHandler implements ActionHandler {

    private PanelController srcPC;
    private PanelController dstPC;
    private IZipService zipService;

    public SecureActionHandler(PanelController srcPC) {
        super(srcPC);
        this.srcPC = srcPC;
    }

    public SecureActionHandler(PanelController srcPC, PanelController dstPC) {
        super(srcPC, dstPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }

    public SecureActionHandler(PanelController srcPC, PanelController dstPC, IZipService zipService) {
        super(srcPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
        this.zipService = zipService;
    }


    //TODO ENCRYPT
    @Override
    public void exportAction() throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath());
        zipService.zip(srcPath, dstPath);
        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }


    //TODO DECRYPT and change filename without '.zip'
    @Override
    public void importAction() throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
        zipService.unzip(srcPath, dstPath);

//        String newName = dstPath.getFileName().toString();
//        newName = newName.substring(0, newName.lastIndexOf("."));
//        dstPath.toFile().renameTo(new File(newName));

        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }


}
