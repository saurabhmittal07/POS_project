package com.increff.employee.service;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandCategoryService {

    @Autowired
    private BrandCategoryDao brandCategoryDao;

    public void add(BrandCategory brandCategory){

        // Trim & lowerCase
        brandCategory = trimLower(brandCategory);
        brandCategoryDao.add(brandCategory);
    }

    public void updateBrand(int id, BrandCategory brandCategory) {
        brandCategory = trimLower(brandCategory);
        brandCategoryDao.updateBrand(id, brandCategory);
    }

    public BrandCategoryPojo getBrand(int id){
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(id);
        return brandCategoryPojo;
    }

    public List<BrandCategoryPojo> getAllBrands(){
        return brandCategoryDao.getAllBrands();
    }


    //Trim & lowercase
    public BrandCategory trimLower(BrandCategory brandCategory){
        brandCategory.setBrand(brandCategory.getBrand().trim().toLowerCase());
        brandCategory.setCategory(brandCategory.getCategory().trim().toLowerCase());
        return brandCategory;
    }





}
