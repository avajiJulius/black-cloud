package com.blackcloud.desktop.elements;

import javafx.event.ActionEvent;

import java.nio.file.Path;

public interface IPanelController {
    void updateList(Path path);
    void btnPathUpAction(ActionEvent actionEvent);
    void selectDiskAction(ActionEvent actionEvent);
    String getSelectedFilename();
    String getCurrentPath();
}
