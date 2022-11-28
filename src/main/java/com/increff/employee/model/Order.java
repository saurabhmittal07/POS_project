package com.increff.employee.model;


import java.sql.Time;
import java.util.Date;


public class Order {

    private int id;
    private Date date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
