package com.increff.employee.dto;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService brandCategoryService;

    public void add(BrandCategory brandCategory) throws ApiException {

        brandCategoryService.add(brandCategory);
    }
}
