package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

public class DBUtils {

    public static User current;
    public static typeUser currenttype;

    public static typeUser getCurrenttype() {
        return currenttype;
    }

    public static void setCurrenttype(typeUser currenttype) {
        DBUtils.currenttype = currenttype;
    }

    public static void setCurrentUser(User user) {
        current = user;
    }

    public static User getCurrentUser() {
        return current;
    }

    public static void logOutUser() {
        current = null;
        currenttype = null;
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) throws IOException {
        Parent root = null;

        if (username == null) {
            if (title.equals("DonateBlood") || title.equals("RequestBlood")) {
                try {
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                    root = loader.load();
                    DonateRequest donateRequest = loader.getController();
                    donateRequest.setTitle();
                    donateRequest.setDateof();
                    donateRequest.setQuantity();
                    donateRequest.setRestriction();
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (title.equals("DonorProfile") || title.equals("ReceiverProfile")) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                Profile profile = loader.getController();
                profile.setUsername();
                profile.setEmail();
                profile.setFirstname();
                profile.setLastname();
                profile.setCnp();
                profile.setBlood();
            } else if (title.equals("DonorHistory") || title.equals("ReceiverHistory")) {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                History history = loader.getController();
                history.setHistory();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                HomePage homepage = loader.getController();
                homepage.setWelcome(username);
                homepage.posWelcome();
                if (currenttype == typeUser.DONOR) {
                    homepage.setDonate_request("DONATE");
                } else if (currenttype == typeUser.RECEIVER) {
                    homepage.setDonate_request("REQUEST");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void logInUser(ActionEvent event, String username, String password, typeUser type) throws SQLException {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
            String user = "root";
            String pass = "Ana29042004";
            connection = DriverManager.getConnection(url, user, pass);

            if (type == typeUser.DONOR) {
                String sql = "SELECT * FROM donator WHERE username=?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, username);
                res = pst.executeQuery();
                if (!res.isBeforeFirst()) {
                    System.out.println("User not found in the databse.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided username does not exist.");
                    alert.show();
                } else while (res.next()) {
                    String userpassword = res.getString("password");
                    if (userpassword.equals(password)) {
                        changeScene(event, "homePage.fxml", "HomePageDonor", username);
                        String sql2 = "SELECT donator.donatorID, donator.username, donator.email, donator.password, info_donator.nume, info_donator.prenume, info_donator.cnp, info_donator.gen, info_donator.grupa_sange\n" +
                                "FROM donator\n" +
                                "JOIN info_donator ON donator.donatorID = info_donator.donatorID\n" +
                                "WHERE donator.username=?";
                        pst = connection.prepareStatement(sql2);
                        pst.setString(1, username);
                        res = pst.executeQuery();
                        while (res.next()) {
                            User new_user = new User(res, type);
                            setCurrentUser(new_user);
                        }
                        setCurrenttype(type);
                    } else {
                        System.out.println("Password incorect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password incorect.");
                        alert.show();
                    }
                }
            } else if (type == typeUser.RECEIVER) {
                String sql = "SELECT * FROM primitor WHERE username=?";
                pst = connection.prepareStatement(sql);
                pst.setString(1, username);
                res = pst.executeQuery();
                if (!res.isBeforeFirst()) {
                    System.out.println("User not found in the databse.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided username does not exist.");
                    alert.show();
                } else while (res.next()) {
                    String userpassword = res.getString("password");
                    if (userpassword.equals(password)) {
                        changeScene(event, "homePage.fxml", "HomePageReceiver", username);
                        String sql2 = "SELECT primitor.primitorID,primitor.username, primitor.password, primitor.email, info_primitor.nume, info_primitor.prenume, info_primitor.cnp, info_primitor.gen, info_primitor.grupa_sange\n" +
                                "FROM primitor\n" +
                                "JOIN info_primitor ON primitor.primitorID = info_primitor.primitorID\n" +
                                "WHERE primitor.username=?";
                        pst = connection.prepareStatement(sql2);
                        pst.setString(1, username);
                        res = pst.executeQuery();
                        while (res.next()) {
                            User new_user = new User(res, type);
                            setCurrentUser(new_user);
                        }
                        setCurrenttype(type);
                    } else {
                        System.out.println("Password incorect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password incorect.");
                        alert.show();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                res.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public static void signUpUser(ActionEvent event, String username, String password, String email, String nume, String prenume, String gen, String grupa, String CNP) throws SQLException, IOException {
        if (username == null || password == null || email == null || nume == null || prenume == null || gen == null || grupa == null || CNP == null) {
            System.out.println("Empty field");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields must be completed.");
            alert.show();
        } else {
            Connection connection = null;
            CallableStatement pst = null;
            ResultSet res = null;
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
                String user = "root";
                String pass = "Ana29042004";
                connection = DriverManager.getConnection(url, user, pass);

                String sql = null;
                if (currenttype == typeUser.DONOR) {
                    sql = "CALL InregistrareDonator(?,?,?,?,?,?,?,?)";
                } else if (currenttype == typeUser.RECEIVER) {
                    sql = "CALL InregistrarePrimitor(?,?,?,?,?,?,?,?)";
                }

                pst = connection.prepareCall(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, email);
                pst.setString(4, nume);
                pst.setString(5, prenume);
                pst.setString(6, CNP);
                pst.setString(7, gen);
                pst.setString(8, grupa);
                pst.execute();
                res = pst.getResultSet();

                while (res.next()) {
                    if (res.getString("rezultat").equals("Numele de utilizator exista deja. Alegeți altul.")) {
                        System.out.println("Username unavailable.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("This username is already used. Please choose another one.");
                        alert.show();
                    } else {
                        System.out.println("Signed up succesfully");
                        changeScene(event, "accountCreated.fxml", "AccountCreated", null);
                    }
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    public static List<String> getMedicalCondition() throws SQLException {
        List<String> medcond = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
            String user = "root";
            String pass = "Ana29042004";
            connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM afectiuni_medicale";
            pst = connection.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                String cond = res.getString("detalii");
                medcond.add(cond);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return medcond;
    }

    public static void addMedicalCondition(ActionEvent event, String medCond, Date datadiag, String tratament) throws SQLException, IOException {
        if (medCond == null || datadiag == null || tratament == null) {
            System.out.println("Empty field");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields must be completed.");
            alert.show();
        } else {
            Connection connection = null;
            CallableStatement pst = null;
            ResultSet res = null;
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
                String user = "root";
                String pass = "Ana29042004";
                connection = DriverManager.getConnection(url, user, pass);

                String sql = null;
                if (currenttype == typeUser.DONOR) {
                    sql = "CALL InregistrareAfectiuneDonator(?,?,?,?,?)";
                    pst = connection.prepareCall(sql);
                    pst.setInt(1, current.getId());
                    pst.setDate(3, datadiag);
                    pst.setString(4, tratament);
                    pst.setInt(5, 0);

                    String sql2 = "SELECT afectiuneID FROM afectiuni_medicale WHERE detalii=?";
                    PreparedStatement pst2 = connection.prepareStatement(sql2);
                    pst2.setString(1, medCond);
                    res = pst2.executeQuery();
                    int id = 0;
                    while (res.next()) {
                        id = res.getInt("afectiuneID");
                    }

                    pst.setInt(2, id);
                    pst.execute();
                    System.out.println("Inregistrare afectiune donator reusita");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Your medical condition has been succesfully added.");
                    alert.show();
                    DBUtils.changeScene(event, "homePage.fxml", "HomePageDonor", DBUtils.current.getUsername());
                } else if (currenttype == typeUser.RECEIVER) {
                    String sql2 = "SELECT afectiuneID FROM afectiuni_medicale WHERE detalii=?";
                    PreparedStatement pst2 = connection.prepareStatement(sql2);
                    pst2.setString(1, medCond);
                    res = pst2.executeQuery();
                    int id = 0;
                    while (res.next()) {
                        id = res.getInt("afectiuneID");
                    }
                    sql = "CALL InregistrareAfectiunePrimitor(?,?,?,?)";
                    pst = connection.prepareCall(sql);
                    pst.setInt(1, current.getId());
                    pst.setInt(2, id);
                    pst.setDate(3, datadiag);
                    pst.setString(4, tratament);
                    pst.execute();
                    System.out.println("Inregistrare afectiune primitor reusita");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Your medical condition has been succesfully added.");
                    alert.show();
                    DBUtils.changeScene(event, "homePage.fxml", "HomePageReceiver", DBUtils.current.getUsername());
                }
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    public static List<String> getCenters() throws SQLException {
        List<String> centers = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
            String user = "root";
            String pass = "Ana29042004";
            connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT * FROM centru";
            pst = connection.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                String center = res.getString("nume_centru");
                centers.add(center);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return centers;
    }

    public static int getMaxQuantity() throws SQLException {
        int max = 0;
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
            String user = "root";
            String pass = "Ana29042004";
            connection = DriverManager.getConnection(url, user, pass);

            String sql = "SELECT min(cantitate_maxima) as max\n" +
                    "FROM afectiuni_Medicale_Donatori\n" +
                    "WHERE donatorID=?;";
            pst = connection.prepareStatement(sql);
            pst.setInt(1, current.getId());
            res = pst.executeQuery();
            while (res.next()) {
                max = res.getInt("max");
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return max;
    }

    public static void addDonate_Request(ActionEvent event, int quantity, Date dateof, String centername) throws SQLException {
        if (quantity == 0 || dateof == null || centername == null) {
            System.out.println("Empty field");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("All fields must be completed.");
            alert.show();
        } else {
            Connection connection = null;
            CallableStatement pst = null;
            ResultSet res = null;
            try {
                String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
                String user = "root";
                String pass = "Ana29042004";
                connection = DriverManager.getConnection(url, user, pass);

                String sql = null;
                if (currenttype == typeUser.DONOR) {
                    sql = "CALL InregistrareDonatie(?,?,?,?)";
                    pst = connection.prepareCall(sql);
                    pst.setInt(1, current.getId());
                    pst.setDate(2, dateof);
                    pst.setInt(3, quantity);

                    String sql2 = "SELECT centruID FROM centru WHERE nume_centru=?";
                    PreparedStatement pst2 = connection.prepareStatement(sql2);
                    pst2.setString(1, centername);
                    ResultSet res2 = pst2.executeQuery();
                    int id = 0;
                    while (res2.next()) {
                        id = res2.getInt("centruID");
                    }

                    pst.setInt(4, id);
                    pst.execute();
                    res = pst.getResultSet();

                    while (res.next()) {
                        if (res.getString("rezultat").equals("Cantitatea depășește limita permisă pentru donatorul cu afecțiune medicală.")) {
                            System.out.println("cantitate prea mare");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Please choose a smaller quantity.");
                            alert.show();
                        } else if (res.getString("rezultat").equals("Înregistrare donație reușită")) {
                            System.out.println("donatie reusita");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Successful donation");
                            alert.show();
                            DBUtils.changeScene(event, "homePage.fxml", "HomePageDonor", DBUtils.current.getUsername());
                        }
                    }
                } else if (currenttype == typeUser.RECEIVER) {
                    sql = "CALL InregistrareCerere(?,?,?,?)";
                    pst = connection.prepareCall(sql);
                    pst.setInt(1, current.getId());
                    pst.setDate(2, dateof);
                    pst.setInt(3, quantity);

                    String sql2 = "SELECT centruID FROM centru WHERE nume_centru=?";
                    PreparedStatement pst2 = connection.prepareStatement(sql2);
                    pst2.setString(1, centername);
                    ResultSet res2 = pst2.executeQuery();
                    int id = 0;
                    while (res2.next()) {
                        id = res2.getInt("centruID");
                    }

                    String sql3 = "SELECT * FROM stock\n" +
                            "WHERE grupa_sange = ? \n" +
                            "  AND cantitate >= ?\n" +
                            "  AND centruID = ?;";
                    PreparedStatement pst3 = connection.prepareStatement(sql3);
                    pst3.setString(1, current.getBlootype());
                    pst3.setInt(2, quantity);
                    pst3.setInt(3, id);
                    ResultSet res3 = pst3.executeQuery();
                    if (!res3.isBeforeFirst()) {
                        System.out.println("cantitate prea mare pt centru ales");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The chosen center does not have enough stock for your request.Please choose another center or a smaller quantity.");
                        alert.show();
                    } else {

                        pst.setInt(4, id);
                        pst.execute();
                        res = pst.getResultSet();

                        while (res.next()) {
                            if (res.getString("rezultat").equals("Înregistrare cerere reușită")) {
                                System.out.println("cerere reusita");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Successful request.");
                                alert.show();
                                DBUtils.changeScene(event, "homePage.fxml", "HomePageReceiver", DBUtils.current.getUsername());
                            }
                        }
                    }
                }

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    connection.close();
                }
            }
        }
    }

    public static List<DonationRequest> getActions() throws SQLException {
        List<DonationRequest> actions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/centrudonaresange?serverTimezone=UTC";
            String user = "root";
            String pass = "Ana29042004";
            connection = DriverManager.getConnection(url, user, pass);
            String sql = null;
            if (current.getUserType(current.getType()).equals("donator")) {
                sql = " SELECT d.cantitate, d.data_donatie as dateof, c.nume_centru\n" +
                        "FROM donatii d\n" +
                        "JOIN centru c ON d.centruID = c.centruID\n" +
                        "WHERE d.donatorID=?";
            } else if (current.getUserType(current.getType()).equals("primitor")) {
                sql = "SELECT ce.cantitate, ce.data_cerere as dateof, c.nume_centru\n" +
                        "FROM cereri ce\n" +
                        "JOIN centru c ON ce.centruID = c.centruID\n" +
                        "WHERE ce.primitorID=?";
            }
            pst = connection.prepareStatement(sql);
            pst.setInt(1, current.getId());
            res = pst.executeQuery();
            if (!res.isBeforeFirst()) {
                if (current.getUserType(current.getType()).equals("donator")) {
                    System.out.println("fara donatii");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("There are no donations for this profile.");
                    alert.show();
                } else if (current.getUserType(current.getType()).equals("primitor")) {
                    System.out.println("fara cereri");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("There are no requests for this profile.");
                    alert.show();
                }
            }
            while (res.next()) {
                DonationRequest ac = new DonationRequest(res);
                actions.add(ac);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return actions;
    }
}
