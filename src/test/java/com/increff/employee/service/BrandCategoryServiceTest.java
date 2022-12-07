package com.increff.employee.service;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandCategoryServiceTest extends AbstractUnitTest {
    @Autowired
    private BrandCategoryService brandCategoryService;

    private BrandCategoryPojo createBrand() throws ApiException{
        return createBrand("nike","shoes");
    }

    private BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategory brandCategory = new BrandCategory();
        brandCategory.setBrand(brand);
        brandCategory.setCategory(category);
        return brandCategoryService.add(brandCategory);
    }

    @Test
    public void testAdd() throws ApiException {
        BrandCategory brandCategory = new BrandCategory();
        brandCategory.setBrand("WWW");
        brandCategory.setCategory("RRR");
        brandCategoryService.add(brandCategory);
    }

    @Test
    public void testGetBrand() throws ApiException {
        BrandCategoryPojo brandCategory=createBrand();
        brandCategory = brandCategoryService.getBrand(brandCategory.getId());
        assertEquals("nike",brandCategory.getBrand());
        assertEquals("shoes",brandCategory.getCategory());
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandCategoryPojo brandCategory=createBrand();
        BrandCategory newBrand = new BrandCategory();
        newBrand.setCategory("footwear");
        newBrand.setBrand("nike");
        brandCategoryService.updateBrand(brandCategory.getId(),newBrand );
        assertEquals("nike",brandCategory.getBrand());
        assertEquals("footwear",brandCategory.getCategory());
    }

    @Test
    public void testGetAllBrands() throws ApiException{
        BrandCategoryPojo brandCategoryPojo = createBrand();
        List<BrandCategoryPojo> brandPojoList =  brandCategoryService.getAllBrands();

        assertEquals(1,brandPojoList.size());
        assertEquals("nike",brandPojoList.get(0).getBrand());
        assertEquals("shoes",brandPojoList.get(0).getCategory());

    }


}
