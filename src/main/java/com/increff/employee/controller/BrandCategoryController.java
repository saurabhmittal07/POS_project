package com.increff.employee.controller;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class BrandCategoryController {


    @Autowired
    private BrandCategoryService brandCategoryService;

    @ApiOperation(value = "Adds Brand-Category Pair")
    @RequestMapping(path = "/api/brandCategory", method = RequestMethod.POST)
    public void add(@RequestBody BrandCategory brandCategory) throws ApiException {
        brandCategoryService.add(brandCategory);
    }



}
