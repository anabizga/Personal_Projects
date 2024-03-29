package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePage implements Initializable {
    @FXML
    private Label welcome;
    @FXML
    private Button donate_request;
    @FXML
    private Button history;
    @FXML
    private Button add_cond;
    @FXML
    private Button log_out;
    @FXML
    private Button profile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log_out.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.logOutUser();
                try {
                    DBUtils.changeScene(actionEvent, "startPage.fxml", "BloodBank", null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        add_cond.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
                    try {
                        DBUtils.changeScene(actionEvent, "medicalcondition.fxml", "AddMedicalConditionDonor", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
                    try {
                        DBUtils.changeScene(actionEvent, "medicalcondition.fxml", "AddMedicalConditionReceiver", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        donate_request.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
                    try {
                        DBUtils.changeScene(actionEvent, "donateRequest.fxml", "DonateBlood", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
                    try {
                        DBUtils.changeScene(actionEvent, "donateRequest.fxml", "RequestBlood", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
                    try {
                        DBUtils.changeScene(actionEvent, "profile.fxml", "DonorProfile", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
                    try {
                        DBUtils.changeScene(actionEvent, "profile.fxml", "ReceiverProfile", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        history.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
                    try {
                        DBUtils.changeScene(actionEvent, "history.fxml", "DonorHistory", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
                    try {
                        DBUtils.changeScene(actionEvent, "history.fxml", "ReceiverHistory", null);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void setWelcome(String username) {
        welcome.setText("Welcome " + username + "!");
    }

    public void posWelcome() {
        welcome.setAlignment(Pos.CENTER);
    }

    public void setDonate_request(String type) {
        donate_request.setText(type);
    }
}
