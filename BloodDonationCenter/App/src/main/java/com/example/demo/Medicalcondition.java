package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Medicalcondition implements Initializable {

    @FXML
    private Label text;
    @FXML
    private Label condition;
    @FXML
    private Label date;
    @FXML
    private Label treatment;
    @FXML
    private Button save;
    @FXML
    private Button back;
    @FXML
    private TextField tf_treatment;
    @FXML
    private ChoiceBox<String> conditii;
    @FXML
    private DatePicker datePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> medcond;
        try {
            medcond = DBUtils.getMedicalCondition();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (String cond : medcond) {
            conditii.getItems().add(cond);
        }

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
                    try {
                        DBUtils.changeScene(actionEvent, "homePage.fxml", "HomePageDonor", DBUtils.current.getUsername());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
                    try {
                        DBUtils.changeScene(actionEvent, "homePage.fxml", "HomePageReceiver", DBUtils.current.getUsername());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (conditii.getValue() == null || datePicker.getValue() == null || tf_treatment.getText() == null) {
                    System.out.println("Empty field");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("All fields must be completed for saving.");
                    alert.show();
                } else {
                    try {
                        DBUtils.addMedicalCondition(actionEvent, conditii.getValue(), Date.valueOf(datePicker.getValue()), tf_treatment.getText());
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
