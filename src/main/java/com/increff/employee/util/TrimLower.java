package com.increff.employee.util;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;


public class TrimLower {

    public static void trimLower(BrandCategoryPojo brandCategory){
        brandCategory.setBrand(brandCategory.getBrand().trim().toLowerCase());
        brandCategory.setCategory(brandCategory.getCategory().trim().toLowerCase());
    }
    public static void trimLower(InventoryForm inventory){
        inventory.setBarcode(inventory.getBarcode().trim().toLowerCase());
    }


    public static void trimLower(OrderItem orderItem){
        orderItem.setBarcode(orderItem.getBarcode().trim().toLowerCase());
    }



    public  static void trimLower(ProductForm product){
        product.setName(product.getName().trim().toLowerCase());
        product.setBarcode(product.getBarcode().trim().toLowerCase());
        product.setBrand(product.getBrand().trim().toLowerCase());
        product.setCategory(product.getCategory().trim().toLowerCase());
    }


}
