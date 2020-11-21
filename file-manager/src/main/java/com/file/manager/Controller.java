package com.file.manager;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class Controller {


    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

}
