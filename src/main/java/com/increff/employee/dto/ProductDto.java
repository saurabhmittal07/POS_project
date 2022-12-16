package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.Convertor;
import com.increff.employee.util.TrimLower;
import com.increff.employee.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;


    public void addProduct( ProductForm product) throws ApiException {
        TrimLower.trimLower(product);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrandByName(product.getBrand(),product.getCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand Category pair does not exist");
        }
        ProductPojo productPojo = Convertor.convertProductFormToProductPojo(product, brandCategoryPojo);
        productService.addProduct(productPojo);
    }

    public List<ProductData> getAllProducts() throws ApiException {
        List<ProductPojo>  productPojos = productService.getAllProducts();
        List<ProductData> productDatas = new ArrayList<>();
        for(ProductPojo productPojo : productPojos){
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());
            productDatas.add(Convertor.convertProductPojoToData(productPojo, brandCategoryPojo));
        }

        return productDatas;
    }

    
    public void updateProduct( int id,  ProductForm product) throws ApiException {
        TrimLower.trimLower(product);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrandByName(product.getBrand(),product.getCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand Category pair does not exist");
        }
        ProductPojo productPojo = Convertor.convertProductFormToProductPojo(product, brandCategoryPojo);
        productService.updateProduct(id, productPojo);
    }

    
    public ProductData getProduct( int id) throws ApiException {
        ProductPojo productPojo = productService.getProduct(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand Category pair does not exist");
        }
        return Convertor.convertProductPojoToData(productPojo,brandCategoryPojo);
    }

    public double getMrp(String barcode) throws ApiException{
        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        if(productPojo == null){
            throw new ApiException("Product does not exist");
        }
        return productPojo.getMrp();
    }
}
