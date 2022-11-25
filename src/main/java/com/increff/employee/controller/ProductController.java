package com.increff.employee.controller;

import com.increff.employee.model.Product;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.util.List;

@Api
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Adds Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void addProduct(@RequestBody Product product) throws ApiException {
        productService.add(product);
    }

    @ApiOperation(value = "Show All Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductPojo> getAllProducts(){
        return productService.getAllProducts();
    }

    @ApiOperation(value = "Delete a brand")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id){
        productService.deleteProduct(id);
    }


}
