package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public abstract class DefaultActionHandler implements ActionHandler {
    private PanelController srcPC;
    private PanelController dstPC;

    public DefaultActionHandler(PanelController srcPC) {
        this.srcPC = srcPC;
    }

    public DefaultActionHandler(PanelController srcPC, PanelController dstPC) {
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }


    @Override
    public void deleteAction() throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        if(Files.isDirectory(srcPath))
            Files.walk(srcPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        else
            Files.deleteIfExists(srcPath);
        srcPC.updateList(Paths.get(srcPC.getCurrentPath()));

    }

    @Override
    public void createFolder(String folderName) throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), folderName);
        Files.createDirectory(srcPath);
        srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
    }

    @Override
    public PanelController getSrcPanelController() {
        return srcPC;
    }

    @Override
    public PanelController getDstPanelController() {
        return dstPC;
    }

}
