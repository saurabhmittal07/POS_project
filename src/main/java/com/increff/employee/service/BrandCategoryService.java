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

        // Check if Brand-Category already exist
        if(brandCategoryAlreadyExist(brandCategory)) {
            return;
        }
        brandCategoryDao.add(brandCategory);
    }

    public List<BrandCategoryPojo> getAllBrands(){
        return brandCategoryDao.getAllBrands();
    }

    public boolean brandCategoryAlreadyExist(BrandCategory brandCategory){
        List<BrandCategoryPojo> list = getAllBrands();
        for(BrandCategoryPojo item : list){
            if(item.getCategory() == brandCategory.getCategory() && item.getBrand() == brandCategory.getBrand()){
                return true;
            }
        }
        return true;
    }

}
