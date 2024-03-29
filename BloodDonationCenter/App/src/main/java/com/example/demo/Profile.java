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

public class Profile implements Initializable {
    @FXML
    private Label firstname;
    @FXML
    private Label lastname;
    @FXML
    private Label cnp;
    @FXML
    private Label details;
    @FXML
    private Label username;
    @FXML
    private Label blood;
    @FXML
    private Label email;
    @FXML
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")){
                    try {
                        DBUtils.changeScene(actionEvent,"homePage.fxml","HomePageDonor",DBUtils.current.getUsername());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")){
                    try {
                        DBUtils.changeScene(actionEvent,"homePage.fxml","HomePageReceiver",DBUtils.current.getUsername());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void setUsername(){
        username.setText("Username: " + DBUtils.current.getUsername());
    }

    public void setEmail(){
        email.setText("Email: " + DBUtils.current.getEmail());
    }

    public void setFirstname(){
        firstname.setText("First name: " + DBUtils.current.getFirstname());
    }

    public void setLastname(){
        lastname.setText("Last name: " + DBUtils.current.getLastname());
    }

    public void setCnp(){
        cnp.setText("CNP: " + DBUtils.current.getCnp());
    }

    public void setBlood(){
        blood.setText("Blood type: " + DBUtils.current.getBlootype());
    }

}
