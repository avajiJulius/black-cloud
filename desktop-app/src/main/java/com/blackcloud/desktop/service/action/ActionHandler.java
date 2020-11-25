package com.blackcloud.desktop.service.action;


import com.blackcloud.desktop.elements.PanelController;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface ActionHandler {

    void exportAction() throws IOException, GeneralSecurityException;

    void importAction() throws IOException, GeneralSecurityException;

    void deleteAction() throws IOException;

    void createFolder(String folderName) throws IOException;

    PanelController getSrcPanelController();

    PanelController getDstPanelController();
}
