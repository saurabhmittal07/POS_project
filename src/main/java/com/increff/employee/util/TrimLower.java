package com.increff.employee.util;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.Inventory;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.ProductForm;

public class TrimLower {


    private void trimLower(BrandCategory brandCategory){
        brandCategory.setBrand(brandCategory.getBrand().trim().toLowerCase());
        brandCategory.setCategory(brandCategory.getCategory().trim().toLowerCase());

    }



    public void trimLower(Inventory inventory){
        inventory.setBarcode(inventory.getBarcode().trim().toLowerCase());
    }

    public void trimLower(OrderItem orderItem){
        orderItem.setBarcode(orderItem.getBarcode().trim().toLowerCase());
    }
}
