package com.increff.employee.dto;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.util.Convertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService brandCategoryService;


    public void addBrand(BrandCategoryForm brandCategory) throws ApiException {
        brandCategoryService.addBrand(Convertor.convertBrandFormToPojo(brandCategory));
    }


    public List<BrandCategoryData> getAllBrands(){
        List<BrandCategoryPojo> brandCategoryPojos = brandCategoryService.getAllBrands();
        List<BrandCategoryData> brands = new ArrayList<>();
        for(BrandCategoryPojo brandCategoryPojo : brandCategoryPojos){
            brands.add(Convertor.convertBrandPojoToData(brandCategoryPojo));
        }
        return brands;
    }

    public void updateBrand( int id, BrandCategoryForm brandCategory) throws ApiException {
        brandCategoryService.updateBrand(id, Convertor.convertBrandFormToPojo(brandCategory));
    }

    public BrandCategoryData getBrand( int id) throws ApiException {
        BrandCategoryPojo brandCategoryPojo =  brandCategoryService.getBrand(id);
        return Convertor.convertBrandPojoToData(brandCategoryPojo);
    }

}
