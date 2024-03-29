package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String blootype;
    private String cnp;
    private String gender;
    private typeUser type;

    public User(ResultSet resultSet, typeUser type) throws SQLException {
        if (resultSet == null) {
            System.out.println("invalid result set");
        } else {
            if (type==typeUser.DONOR) {
                this.id = resultSet.getInt("donatorID");
            }
            if (type==typeUser.RECEIVER) {
                this.id = resultSet.getInt("primitorID");
            }
            this.username = resultSet.getString("username");
            this.email = resultSet.getString("email");
            this.password = resultSet.getString("password");
            this.firstname = resultSet.getString("nume");
            this.lastname = resultSet.getString("prenume");
            this.cnp = resultSet.getString("cnp");
            this.blootype = resultSet.getString("grupa_sange");
            this.gender = resultSet.getString("gen");
            this.setType(type);
        }
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getBlootype() {
        return blootype;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCnp() {
        return cnp;
    }

    public String getUserType(typeUser type) {
        return switch (type) {
            case DONOR -> "donator";
            case RECEIVER -> "primitor";
        };
    }

    public typeUser setUserType(String type) {
        return switch (type) {
            case "donator" -> typeUser.DONOR;
            case "primitor" -> typeUser.RECEIVER;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public typeUser getType() {
        return type;
    }

    public void setType(typeUser type) {
        this.type = type;
    }
}
