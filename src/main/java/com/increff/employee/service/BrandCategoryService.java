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

        // Check if Brand-Category already exist
        if(brandCategoryAlreadyExist(brandCategory) == true) {
            return;
        }

        brandCategoryDao.add(brandCategory);
    }

    public void updateBrand(int id, BrandCategory brandCategory){
        brandCategoryDao.updateBrand(id, brandCategory);
    }

    public void deleteBrand(int id){
            brandCategoryDao.deleteBrand(id);
    }



    public boolean brandCategoryAlreadyExist(BrandCategory brandCategory){
        List<BrandCategoryPojo> list = getAllBrands();

        for(BrandCategoryPojo item : list){
            if(item.getCategory().equals(brandCategory.getCategory()) && item.getBrand().equals(brandCategory.getBrand())  ){

                return true;
            }
        }

        return false;
    }

    //Trim & lowercase
    public BrandCategory trimLower(BrandCategory brandCategory){
        brandCategory.setBrand(brandCategory.getBrand().trim().toLowerCase());
        brandCategory.setCategory(brandCategory.getCategory().trim().toLowerCase());
        return brandCategory;
    }

    public List<BrandCategoryPojo> getAllBrands(){
        return brandCategoryDao.getAllBrands();
    }

}
