package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountCreated implements Initializable {
    @FXML
    private Label succes;
    @FXML
    private Button sign;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    DBUtils.changeScene(actionEvent,"startPage.fxml","BloodBank",null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
