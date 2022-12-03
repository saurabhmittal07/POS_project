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

    public void add(BrandCategory brandCategory) throws ApiException{

        // Trim & lowerCase
        brandCategory = trimLower(brandCategory);
        if(validate(brandCategory)){

        }
        
        
        brandCategoryDao.add(brandCategory);
    }

    public void updateBrand(int id, BrandCategory brandCategory) throws ApiException{
        brandCategory = trimLower(brandCategory);

        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(id);
        if(brandCategoryPojo.getBrand().equals(brandCategory.getBrand())
                && brandCategoryPojo.getCategory().equals(brandCategory.getCategory())){
            return;
        }
        validate(brandCategory);
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


    boolean validate(BrandCategory brandCategory) throws ApiException{
        if(brandCategory.getBrand().equals("") || brandCategory.getCategory().equals("")){
            throw new ApiException("Brand or Category can not be empty");
        }
        
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrandByName(brandCategory.getBrand(), brandCategory.getCategory());
        if(brandCategoryPojo != null){
            throw new ApiException("Brand - Category pair already exists");
        }
        
        return true;
    }


}
