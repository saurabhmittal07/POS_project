package com.increff.employee.model;

public class UpdateOrderForm {
    private int id;
    private int preQuantity;
    private String barcode;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPreQuantity() {
        return preQuantity;
    }

    public void setPreQuantity(int preQuantity) {
        this.preQuantity = preQuantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
