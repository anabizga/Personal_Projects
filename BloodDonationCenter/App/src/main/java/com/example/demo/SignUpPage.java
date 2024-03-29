package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingDeque;

public class SignUpPage implements Initializable {

    @FXML
    private Button create_account;
    @FXML
    private Button back;
    @FXML
    private Label log;
    @FXML
    private Label firstname;
    @FXML
    private Label lastname;
    @FXML
    private Label cnp;
    @FXML
    private Label gender;
    @FXML
    private Label username;
    @FXML
    private Label blood;
    @FXML
    private Label email;
    @FXML
    private Label password;
    @FXML
    private Label password1;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_cnp;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField pf_password1;
    @FXML
    private PasswordField pf_password2;
    @FXML
    private ChoiceBox<String> choiceGender = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> type1 = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> type2 = new ChoiceBox<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceGender.getItems().add("M");
        choiceGender.getItems().add("F");
        type1.getItems().add("O");
        type1.getItems().add("A");
        type1.getItems().add("B");
        type1.getItems().add("AB");
        type2.getItems().add("+");
        type2.getItems().add("-");

        create_account.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (pf_password1.getText().isEmpty() || pf_password2.getText().isEmpty()) {
                    System.out.println("Empty field");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("All fields must be completed.");
                    alert.show();
                } else if (!pf_password1.getText().equals(pf_password2.getText())) {
                    System.out.println("Parolele nu corespund.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Passwords not matching.");
                    alert.show();
                } else {
                    try {
                        DBUtils.signUpUser(actionEvent, tf_username.getText(), pf_password1.getText(), tf_email.getText(), tf_lastname.getText(), tf_firstname.getText(), choiceGender.getValue(), type1.getValue() + type2.getValue(), tf_cnp.getText());
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(DBUtils.currenttype==typeUser.DONOR){
                    try {
                        DBUtils.changeScene(actionEvent, "logInPage.fxml", "DonorLogIn",null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }  else if(DBUtils.currenttype==typeUser.RECEIVER){
                    try {
                        DBUtils.changeScene(actionEvent, "logInPage.fxml", "ReceiverLogIn",null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}

