package com.blackcloud.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Black Cloud");
        primaryStage.setScene(new Scene(root, 1244, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
