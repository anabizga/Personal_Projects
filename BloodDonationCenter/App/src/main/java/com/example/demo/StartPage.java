package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPage implements Initializable {

    @FXML
    private Button donor;
    @FXML
    private Button recipient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        donor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.setCurrenttype(typeUser.DONOR);
                try {
                    DBUtils.changeScene(actionEvent, "logInPage.fxml", "DonorLogIn",null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        recipient.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.setCurrenttype(typeUser.RECEIVER);
                try {
                    DBUtils.changeScene(actionEvent, "logInPage.fxml", "RecipientLogIn",null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

