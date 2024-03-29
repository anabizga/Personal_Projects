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

public class DonateRequest implements Initializable {
    @FXML
    private Label title;
    @FXML
    private Label quantity;
    @FXML
    private Label dateof;
    @FXML
    private Label center;
    @FXML
    private Label restriction;
    @FXML
    private TextField tf_quantity;
    @FXML
    private ChoiceBox<String> centers;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button save;
    @FXML
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> centerslist;
        try {
            centerslist=DBUtils.getCenters();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(String cent: centerslist){
            centers.getItems().add(cent);
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
                if(tf_quantity.getText()==null || datePicker.getValue()==null || centers.getValue()==null){
                    System.out.println("Empty field");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("All fields must be completed.");
                    alert.show();
                }
                else {
                    try {
                        DBUtils.addDonate_Request(actionEvent,Integer.parseInt(tf_quantity.getText()), Date.valueOf(datePicker.getValue()),centers.getValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    public void setTitle(){
        if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
            title.setText("Blood donation");
        } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
            title.setText("Blood request");
        }
    }

    public void setQuantity(){
        if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
            quantity.setText("Donated\nquantity*");
        } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
            quantity.setText("Requested\nquantity");
        }
    }

    public void setDateof(){
        if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
            dateof.setText("Date of\ndonation");
        } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
            dateof.setText("Date of\nrequest");
        }
    }

    public void setRestriction() throws SQLException {
        if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
            int max = DBUtils.getMaxQuantity();
            restriction.setText("*You can donate up to " + max + " ml.");
        } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
            restriction.setText(" ");
        }
    }
}
