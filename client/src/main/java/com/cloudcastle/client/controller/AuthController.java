package com.cloudcastle.client.controller;

import com.cloudcastle.client.Network;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthController implements Initializable {

    Network network;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        network = new Network();
    }
}
