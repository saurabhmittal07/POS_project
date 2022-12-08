package com.increff.employee.model;


import java.sql.Time;
import java.util.Date;


public class Order {

    private int id;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
