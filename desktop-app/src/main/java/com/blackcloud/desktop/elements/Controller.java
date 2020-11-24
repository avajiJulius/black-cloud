package com.blackcloud.desktop.elements;

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
import java.nio.file.FileAlreadyExistsException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.localPC = (PanelController) localPanel.getProperties().get("ctrl");
        localPC.updateList(Paths.get(localPath));
        this.securePC = (PanelController) securePanel.getProperties().get("ctrl");
        securePC.updateList(Paths.get(securePath));
        this.cloudPC = (PanelController) cloudPanel.getProperties().get("ctrl");
        cloudPC.updateList(Paths.get(cloudPath));
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


        PanelController srcPC = null, dstPC = null;

        //From local to secure
        if(localPC.getSelectedFilename() != null) {
            srcPC = localPC;
            dstPC = securePC;
        }

        //From secure to cloud
        if(securePC.getSelectedFilename() != null) {
            srcPC = securePC;
            dstPC = cloudPC;
        }





        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Can not copy file", ButtonType.OK);
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

        PanelController srcPC = null, dstPC = null;

        //From cloud to secure
        if(cloudPC.getSelectedFilename() != null) {
            srcPC = cloudPC;
            dstPC = securePC;
        }
        //From secure to local
        if(securePC.getSelectedFilename() != null) {
            srcPC = securePC;
            dstPC = localPC ;
        }

        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        Path dstPath = Paths.get(dstPC.getCurrentPath()).resolve(srcPath.getFileName().toString());

        try {
            Files.copy(srcPath, dstPath);
            dstPC.updateList(Paths.get(dstPC.getCurrentPath()));
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

        PanelController srcPC = cloudPC.getSelectedFilename() != null ? cloudPC : securePC.getSelectedFilename() != null ? securePC : localPC;
        Path srcPath = Paths.get(srcPC.getCurrentPath(), srcPC.getSelectedFilename());
        String alertMessage = Files.isDirectory(srcPath) ? "Confirm delete directory" : "Confirm delete file";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get() == ButtonType.OK) {
            try {
                if(Files.isDirectory(srcPath))
                    deleteDirectory(srcPath);
                else
                    Files.deleteIfExists(srcPath);
                srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Error when deleted file", ButtonType.OK);
                error.showAndWait();
            }
        } else {
            return;
        }


    }

    @Override
    public void moveAction(ActionEvent actionEvent) {

    }

    @Override
    public void createFolder(ActionEvent actionEvent) {
        PanelController srcPC = cloudPC.getCurrentPath() != null ? cloudPC : securePC.getCurrentPath() != null ? securePC : localPC;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Create");
        dialog.setHeaderText("Create folder");
        dialog.setContentText("Enter folder name:");
        dialog.showAndWait().ifPresent(name -> {
            try {
                Path srcPath = Paths.get(srcPC.getCurrentPath(), name);
                Files.createDirectory(srcPath);
                srcPC.updateList(Paths.get(srcPC.getCurrentPath()));
            } catch (FileAlreadyExistsException e) {
                Alert alreadyExist = new Alert(Alert.AlertType.ERROR, "Folder already exist", ButtonType.OK);
                alreadyExist.showAndWait();
            } catch (IOException e) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Error when creating folder", ButtonType.OK);
                error.showAndWait();
            }
        });

    }

    void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


}
