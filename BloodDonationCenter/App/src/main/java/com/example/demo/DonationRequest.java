package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DonationRequest {
    private int quantity;
    private Date dateof;
    private String centername;

    public DonationRequest(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            System.out.println("result set invalid");
        } else {
            this.quantity = resultSet.getInt("cantitate");
            this.dateof = resultSet.getDate("dateof");
            this.centername = resultSet.getString("nume_centru");
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getDateof() {
        return dateof;
    }

    public String getCentername() {
        return centername;
    }
}
