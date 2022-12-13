package com.increff.employee.util;


import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Convertor {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    public BrandCategoryData convertBrandPojoToData(BrandCategoryPojo brandCategoryPojo){
        BrandCategoryData brandCategory = new BrandCategoryData();
        brandCategory.setBrand(brandCategoryPojo.getBrand());
        brandCategory.setCategory(brandCategoryPojo.getCategory());
        brandCategory.setId(brandCategoryPojo.getId());
        return brandCategory;
    }

    public ProductData convertProductPojoToData(ProductPojo productPojo,  BrandCategoryPojo brandCategoryPojo){
        ProductData productData = new ProductData();
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(brandCategoryPojo.getBrand());
        productData.setCategory(brandCategoryPojo.getCategory());
        productData.setMrp(productPojo.getMrp());
        productData.setName(productPojo.getName());
        productData.setId(productPojo.getId());

        return productData;
    }

    public InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo){
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setCount(inventoryPojo.getCount());
        ProductPojo productPojo = productService.getProduct(inventoryPojo.getProductId());
        inventoryData.setBarcode(productPojo.getBarcode());
        return inventoryData;
    }

    public ProductPojo convertProductFormToProductPojo(ProductForm product) throws ApiException{
        ProductPojo productPojo = new ProductPojo();

        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrandByName(product.getBrand(),product.getCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand-Category pair does not exist");
        }
        productPojo.setBarcode(product.getBarcode());
        productPojo.setBrandCategory(brandCategoryPojo.getId());
        productPojo.setName(product.getName());
        productPojo.setMrp(product.getMrp());
        return productPojo;
    }

    public BrandCategoryPojo convertBrandFormToPojo(BrandCategoryForm brandCategory){
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setCategory(brandCategory.getCategory());
        brandCategoryPojo.setBrand(brandCategory.getBrand());
        return brandCategoryPojo;
    }

    public InventoryPojo convertInventoryFormToPojo(InventoryForm inventory, ProductPojo productPojo){
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setCount(inventory.getCount());
        inventoryPojo.setProductId(productPojo.getId());
        return inventoryPojo;
    }
}
