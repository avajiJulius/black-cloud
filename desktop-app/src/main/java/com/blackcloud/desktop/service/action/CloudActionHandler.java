package com.blackcloud.desktop.service.action;

import com.blackcloud.desktop.elements.PanelController;
import com.blackcloud.desktop.exception.UnsupportedActionException;

public class CloudActionHandler extends DefaultActionHandler implements ActionHandler{

    private PanelController srcPC;
    private PanelController dstPC;

    public CloudActionHandler(PanelController srcPC) {
        super(srcPC);
        this.srcPC = srcPC;
    }

    public CloudActionHandler(PanelController srcPC, PanelController dstPC) {
        super(srcPC);
        this.srcPC = srcPC;
        this.dstPC = dstPC;
    }

    @Override
    public void exportAction() {
        throw new UnsupportedActionException("Export not supported in Cloud storage");
    }

    //TODO add delete import realization for cloud
    @Override
    public void importAction() {
        throw new UnsupportedOperationException();
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
