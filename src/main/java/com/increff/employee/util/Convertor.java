package com.increff.employee.util;


import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;

public class Convertor {

    public static BrandCategoryData convertBrandPojoToData(BrandCategoryPojo brandCategoryPojo){
        BrandCategoryData brandCategory = new BrandCategoryData();
        brandCategory.setBrand(brandCategoryPojo.getBrand());
        brandCategory.setCategory(brandCategoryPojo.getCategory());
        brandCategory.setId(brandCategoryPojo.getId());
        return brandCategory;
    }

    public static ProductData convertProductPojoToData(ProductPojo productPojo,  BrandCategoryPojo brandCategoryPojo){
        ProductData productData = new ProductData();
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(brandCategoryPojo.getBrand());
        productData.setCategory(brandCategoryPojo.getCategory());
        productData.setMrp(productPojo.getMrp());
        productData.setName(productPojo.getName());
        productData.setId(productPojo.getId());

        return productData;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo,ProductPojo productPojo ){
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setCount(inventoryPojo.getCount());
        inventoryData.setBarcode(productPojo.getBarcode());
        return inventoryData;
    }

    public static ProductPojo convertProductFormToProductPojo(ProductForm product, BrandCategoryPojo brandCategoryPojo) throws ApiException{
        ProductPojo productPojo = new ProductPojo();


        if(brandCategoryPojo == null){
            throw new ApiException("Brand-Category pair does not exist");
        }
        productPojo.setBarcode(product.getBarcode());
        productPojo.setBrandCategory(brandCategoryPojo.getId());
        productPojo.setName(product.getName());
        productPojo.setMrp(product.getMrp());
        return productPojo;
    }

    public static BrandCategoryPojo convertBrandFormToPojo(BrandCategoryForm brandCategory){
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setCategory(brandCategory.getCategory());
        brandCategoryPojo.setBrand(brandCategory.getBrand());
        return brandCategoryPojo;
    }

    public static InventoryPojo convertInventoryFormToPojo(InventoryForm inventory, ProductPojo productPojo){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setCount(inventory.getCount());
        inventoryPojo.setProductId(productPojo.getId());
        return inventoryPojo;
    }
}
