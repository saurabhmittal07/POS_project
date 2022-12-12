package com.increff.employee.controller;
import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandCategoryController {



    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @ApiOperation(value = "Adds Brand-Category Pair")
    @RequestMapping(path = "/api/brandCategory", method = RequestMethod.POST)
    public void add(@RequestBody BrandCategoryForm brandCategory) throws ApiException {
        brandCategoryDto.addBrand(brandCategory);
    }

    @ApiOperation(value = "Shows Brands")
    @RequestMapping(path = "/api/brandCategory" , method = RequestMethod.GET)
    public List<BrandCategoryData> getAllBrands(){
        return brandCategoryDto.getAllBrands();
    }

    @ApiOperation(value = "Updates a brand detail")
    @RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandCategoryForm brandCategory) throws ApiException {
        brandCategoryDto.update(id, brandCategory);
    }


    @ApiOperation(value = "Gets an Brand by ID")
    @RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.GET)
    public BrandCategoryData getBrand(@PathVariable int id) throws ApiException {
       return brandCategoryDto.getBrand(id);
    }


}
