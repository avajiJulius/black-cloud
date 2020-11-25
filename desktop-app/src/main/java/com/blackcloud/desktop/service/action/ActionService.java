package com.blackcloud.desktop.service.action;


import java.io.IOException;

public class ActionService {

    private ActionHandler actionHandler;

    public ActionService(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    public void createFolder(String folderName) throws IOException {
        actionHandler.createFolder(folderName);
    }

    public void deleteAction() throws IOException {
        actionHandler.deleteAction();
    }

    public void importAction() throws IOException {
        actionHandler.importAction();
    }

    public void exportAction() throws IOException {
        actionHandler.exportAction();
    }

    public void setActionHandler(ActionHandler handler) {
        this.actionHandler = handler;
    }

    public ActionHandler getActionHandler() {
        return actionHandler;
    }
}
