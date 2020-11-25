package com.blackcloud.desktop.elements;

import com.blackcloud.desktop.service.action.*;
import com.blackcloud.desktop.service.zip.IZipService;
import com.blackcloud.desktop.service.zip.ZipService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements IController, Initializable {

    private final String cloudPath = "C:\\Users\\avaji\\Desktop\\cloud";
    private final String securePath = "C:\\Users\\avaji\\Desktop\\secure";
    private final String localPath = "C:\\Users\\avaji\\Desktop";
    @FXML
    private VBox localPanel, securePanel;

    @FXML
    private VBox cloudPanel;

    private PanelController localPC, securePC, cloudPC;
    private IZipService zipService;
    private ActionService actionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.localPC = (PanelController) localPanel.getProperties().get("ctrl");
        localPC.updateList(Paths.get(localPath));
        this.securePC = (PanelController) securePanel.getProperties().get("ctrl");
        securePC.updateList(Paths.get(securePath));
        this.cloudPC = (PanelController) cloudPanel.getProperties().get("ctrl");
        cloudPC.updateList(Paths.get(cloudPath));
        zipService = new ZipService();
        actionService = new ActionService(null);
    }


    @Override
    public void exitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    @Override
    public void exportAction(ActionEvent actionEvent) {

        //Error
        if(localPC.getSelectedFilename() == null && securePC.getSelectedFilename() == null && cloudPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file selected to export", ButtonType.OK);
            alert.showAndWait();
            return;
        }



        //From local to secure
        if(localPC.getSelectedFilename() != null && localPC.isFocused()) {
            actionService.setActionHandler(new LocalActionHandler(localPC, securePC));

        }

        //From secure to cloud
        if(securePC.getSelectedFilename() != null && securePC.isFocused()) {
            actionService.setActionHandler(new SecureActionHandler(securePC, cloudPC, zipService));
        }


        try {
            actionService.exportAction();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can not export file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void importAction(ActionEvent actionEvent) {
        //Error
        if(localPC.getSelectedFilename() == null && securePC.getSelectedFilename() == null && cloudPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file selected to import", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        //From cloud to secure
        if(cloudPC.getSelectedFilename() != null) {
            actionService.setActionHandler(new CloudActionHandler(cloudPC, securePC));
        }
        //From secure to local
        if(securePC.getSelectedFilename() != null) {
            actionService.setActionHandler(new SecureActionHandler(securePC, localPC, zipService));
        }

        try {
            actionService.importAction();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can not copy file", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @Override
    public void deleteAction(ActionEvent actionEvent) {
        if (localPC.getSelectedFilename() == null && securePC.getSelectedFilename() == null && cloudPC.getSelectedFilename() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No file selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (cloudPC.getSelectedFilename() != null && cloudPC.isFocused()) {
            actionService.setActionHandler(new CloudActionHandler(cloudPC));
        }
        if (securePC.getSelectedFilename() != null && securePC.isFocused()) {
            actionService.setActionHandler(new SecureActionHandler(securePC));
        }
        if (localPC.getSelectedFilename() != null && localPC.isFocused()){
            actionService.setActionHandler(new LocalActionHandler(localPC));
        }

        PanelController srcPC = actionService.getActionHandler().getSrcPanelController();
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        String alertMessage = Files.isDirectory(srcPath) ? "Confirm delete directory" : "Confirm delete file";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
        ButtonType buttonType = alert.showAndWait().get();

        if(buttonType == ButtonType.OK) {
            try {
                actionService.deleteAction();
            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Error when deleted file", ButtonType.OK);
                error.showAndWait();
            }
        }

    }

    @Override
    public void moveAction(ActionEvent actionEvent) {

    }

    @Override
    public void createFolder(ActionEvent actionEvent) {

        try {
            if (cloudPC.isFocused()) {
                actionService.setActionHandler(new CloudActionHandler(cloudPC));
            }
            if (securePC.isFocused()) {
                actionService.setActionHandler(new SecureActionHandler(securePC));
            }
            if (localPC.isFocused()){
                actionService.setActionHandler(new LocalActionHandler(localPC));
            }
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create");
            dialog.setHeaderText("Create folder");
            dialog.setContentText("Enter folder name:");
            String folderName = dialog.showAndWait().get();

            actionService.createFolder(folderName);
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR, "Error when creating folder", ButtonType.OK);
            error.showAndWait();
        }

    }




}
