package com.increff.employee.service;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;

import com.increff.employee.util.TrimLower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BrandCategoryService {

    @Autowired
    private BrandCategoryDao brandCategoryDao;

    public BrandCategoryPojo add(BrandCategoryPojo brandCategory) throws ApiException{

        // Trim & lowerCase
        TrimLower.trimLower(brandCategory);
        validate(brandCategory);
        return brandCategoryDao.add(brandCategory);
    }

    @Transactional
    public void updateBrand(int id, BrandCategoryPojo brandCategory) throws ApiException{
        TrimLower.trimLower(brandCategory);

        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(id);
        if(brandCategoryPojo.getBrand().equals(brandCategory.getBrand())
                && brandCategoryPojo.getCategory().equals(brandCategory.getCategory())){
            return;
        }
        validate(brandCategory);
        brandCategoryPojo.setCategory(brandCategory.getCategory());
        brandCategoryPojo.setBrand(brandCategory.getBrand());
    }

    public BrandCategoryPojo getBrand(int id){
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(id);
        return brandCategoryPojo;
    }

    public List<BrandCategoryPojo> getAllBrands(){
        return brandCategoryDao.getAllBrands();
    }


    private void validate(BrandCategoryPojo brandCategory) throws ApiException{
        if(brandCategory.getBrand().equals("") || brandCategory.getCategory().equals("")){
            throw new ApiException("Brand or Category can not be empty");
        }

        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrandByName(brandCategory.getBrand(), brandCategory.getCategory());
        if(brandCategoryPojo != null){
            throw new ApiException("Brand - Category pair already exists");
        }
    }


    public BrandCategoryPojo getBrandByName(String brand, String category){
        return brandCategoryDao.getBrandByName(brand, category);
    }


}
