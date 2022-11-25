package com.increff.employee.controller;

import com.increff.employee.model.Product;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.util.List;

@Api
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Adds Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void addProduct(@RequestBody Product product){
        productService.add(product);
    }

    @ApiOperation(value = "Show All Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.GET)
    public List<ProductPojo> getAllProducts(){
        return productService.getAllProducts();
    }

}
