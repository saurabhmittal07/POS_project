package com.increff.employee.model;

public class BrandCategory {
    private String Brand;
    private String Category;

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public BrandCategory() {

    }

    public BrandCategory(String brand, String category) {
        Brand = brand;
        Category = category;
    }
}
