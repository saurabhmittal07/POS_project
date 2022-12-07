package com.increff.employee.controller;
import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class BrandCategoryController {


    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @ApiOperation(value = "Adds Brand-Category Pair")
    @RequestMapping(path = "/api/brandCategory", method = RequestMethod.POST)
    public void add(@RequestBody BrandCategory brandCategory) throws ApiException {
        brandCategoryService.add(brandCategory);
    }

    @ApiOperation(value = "Shows Brands")
    @RequestMapping(path = "/api/brandCategory" , method = RequestMethod.GET)
    public List<BrandCategoryPojo> getAllBrands(){
        return brandCategoryService.getAllBrands();
    }

    @ApiOperation(value = "Updates a brand detail")
    @RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandCategory brandCategory) throws ApiException {
        brandCategoryService.updateBrand(id, brandCategory);
    }


    @ApiOperation(value = "Gets an Brand by ID")
    @RequestMapping(path = "/api/brandCategory/{id}", method = RequestMethod.GET)
    public BrandCategoryPojo getBrand(@PathVariable int id) throws ApiException {
       return brandCategoryService.getBrand(id);
    }


}
