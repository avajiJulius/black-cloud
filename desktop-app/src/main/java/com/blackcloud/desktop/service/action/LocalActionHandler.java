package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import com.blackcloud.desktop.exception.UnsupportedActionException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.function.Consumer;

public class LocalActionHandler extends DefaultActionHandler implements ActionHandler {

    private PanelController srcPC;
    private PanelController dstPC;

    public LocalActionHandler(PanelController srcPC) {
        super(srcPC);
        this.srcPC = srcPC;
    }

    public LocalActionHandler(PanelController srcPC, PanelController dstPC) {
        super(srcPC, dstPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }


    @Override
    public void exportAction() throws IOException {
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());
        FileUtils.copyDirectory(srcPath.toFile(), dstPath.toFile());
        dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
    }

    @Override
    public void importAction() {
        throw new UnsupportedActionException("Import in local tab is unsupported");
    }





}
