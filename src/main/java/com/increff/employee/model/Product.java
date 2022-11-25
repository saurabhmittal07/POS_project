package com.increff.employee.model;

public class Product {

    private String name;
    private String barcode;
    private int brandCategory;
    private double mrp;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(int brandCategory) {
        this.brandCategory = brandCategory;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }
}
