package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class History implements Initializable {
    @FXML
    private Label history;
    @FXML
    private Button back;
    @FXML
    private TableView<DonationRequest> historyTableView;
    @FXML
    private TableColumn<DonationRequest, Integer> quantityColumn;
    @FXML
    private TableColumn<DonationRequest, Date> dateColumn;
    @FXML
    private TableColumn<DonationRequest, String> centerNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (historyTableView != null) {
            try {
                List<DonationRequest> actions = DBUtils.getActions();
                historyTableView.getItems().addAll(actions);

                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateof"));
                centerNameColumn.setCellValueFactory(new PropertyValueFactory<>("centername"));

                dateColumn.setSortType(TableColumn.SortType.DESCENDING);
                historyTableView.getSortOrder().add(dateColumn);
                historyTableView.sort();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
    }

    public void setHistory() {
        if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("donator")) {
            history.setText("Your donation history");
        } else if (DBUtils.current.getUserType(DBUtils.current.getType()).equals("primitor")) {
            history.setText("Your request history");
        }
    }
}
