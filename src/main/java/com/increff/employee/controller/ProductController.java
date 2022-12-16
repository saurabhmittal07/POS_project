package com.increff.employee.controller;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Api
@RestController
public class ProductController {


    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Adds Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void addProduct(@RequestBody ProductForm product) throws ApiException {
        productDto.addProduct(product);
    }

    @ApiOperation(value = "Show All Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductData> getAllProducts() throws ApiException {
        return productDto.getAllProducts();
    }

    @ApiOperation(value = "Updates a product detail")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable int id, @RequestBody ProductForm product) throws ApiException {
        productDto.updateProduct(id, product);
    }

    @ApiOperation(value = "Gets an Product by ID")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable int id) throws ApiException {
        return productDto.getProduct(id);
    }

    @ApiOperation(value = "Check if required inventory available or not & Return Mrp")
    @RequestMapping(path = "/api/order/inventoryExist/{barcode}/{quantity}", method = RequestMethod.GET)
    public double getMrpIfInventoryExist(@PathVariable String barcode ,@PathVariable String quantity) throws ApiException{
        return productDto.getMrpIfInventoryExist(barcode , quantity);
    }

}
