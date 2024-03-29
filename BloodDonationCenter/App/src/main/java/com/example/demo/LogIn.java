package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogIn implements Initializable {

    @FXML
    private Button logIn;
    @FXML
    private Button create_account;
    @FXML
    private Button back;
    @FXML
    private Label log;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label not_member;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        create_account.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    DBUtils.changeScene(actionEvent,"signUpPage.fxml","SignUp!",null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    DBUtils.logInUser(actionEvent,tf_username.getText(),pf_password.getText(),DBUtils.getCurrenttype());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.setCurrenttype(null);
                try {
                    DBUtils.changeScene(actionEvent, "startPage.fxml", "BloodBank", null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}

