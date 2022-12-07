package com.increff.employee.dto;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService brandCategoryService;

    public void add(BrandCategory brandCategory) throws ApiException {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo(brandCategory.getBrand(), brandCategory.getCategory());
        brandCategoryService.add(brandCategoryPojo);
    }


    public List<BrandCategoryData> getAllBrands(){
        List<BrandCategoryPojo> brandCategoryPojos = brandCategoryService.getAllBrands();
        List<BrandCategoryData> brands = new ArrayList<>();
        for(BrandCategoryPojo brandCategoryPojo : brandCategoryPojos){
            BrandCategoryData brandCategory = new BrandCategoryData();
            brandCategory.setCategory(brandCategoryPojo.getCategory());
            brandCategory.setBrand(brandCategoryPojo.getBrand());
            brandCategory.setId(brandCategoryPojo.getId());
            brands.add(brandCategory);
        }
        return brands;
    }

    public void update( int id, BrandCategory brandCategory) throws ApiException {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo(brandCategory.getBrand(), brandCategory.getCategory());
        brandCategoryService.updateBrand(id, brandCategoryPojo);
    }

    public BrandCategoryData getBrand( int id) throws ApiException {
        BrandCategoryPojo brandCategoryPojo =  brandCategoryService.getBrand(id);
        BrandCategoryData brandCategory = new BrandCategoryData();
        brandCategory.setBrand(brandCategoryPojo.getBrand());
        brandCategory.setCategory(brandCategoryPojo.getCategory());
        brandCategory.setId(brandCategoryPojo.getId());
        return brandCategory;
    }
}
