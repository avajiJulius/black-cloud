package com.blackcloud.desktop.elements;

import javafx.event.ActionEvent;

public interface IController {
    void exitAction(ActionEvent actionEvent);
    void exportAction(ActionEvent actionEvent);
    void importAction(ActionEvent actionEvent);
    void deleteAction(ActionEvent actionEvent);
    void moveAction(ActionEvent actionEvent);
    void createFolder(ActionEvent actionEvent);


}
