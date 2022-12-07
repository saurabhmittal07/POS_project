package com.increff.employee.dto;

import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService productService;


    public void add( ProductForm product) throws ApiException {
        productService.add(product);
    }

    public List<ProductForm> getAllProducts(){
        return productService.getAllProducts();
    }

    
    public void updateProduct( int id,  ProductForm product) throws ApiException {
        productService.updateProduct(id, product);
    }

    
    public ProductForm getProduct( int id) throws ApiException {
        return productService.getProduct(id);
    }
}
