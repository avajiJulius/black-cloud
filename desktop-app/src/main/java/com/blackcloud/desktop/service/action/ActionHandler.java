package com.blackcloud.desktop.service.action;


import com.blackcloud.desktop.elements.PanelController;

import java.io.IOException;

public interface ActionHandler {

    void exportAction() throws IOException;

    void importAction() throws IOException;

    void deleteAction() throws IOException;

    void createFolder(String folderName) throws IOException;

    PanelController getSrcPanelController();

    PanelController getDstPanelController();
}
