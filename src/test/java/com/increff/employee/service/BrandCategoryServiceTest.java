package com.increff.employee.service;

import com.increff.employee.pojo.BrandCategoryPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandCategoryServiceTest extends AbstractUnitTest {


    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Autowired
    private BrandCategoryService brandCategoryService;

    public  BrandCategoryPojo createBrand() throws ApiException {
        return createBrand("nike","shoes");
    }

    public BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo();
        brandCategory.setBrand(brand);
        brandCategory.setCategory(category);
        return brandCategoryService.addBrand(brandCategory);
    }


    @Test
    public void testAdd() throws ApiException {
        createBrand();
        exceptionRule.expect(ApiException.class);
        createBrand();
    }

    @Test
    public void testGetBrand() throws ApiException {
        BrandCategoryPojo brandCategory =createBrand();


        brandCategory = brandCategoryService.getBrand(brandCategory.getId());
        assertEquals("nike",brandCategory.getBrand());
        assertEquals("shoes",brandCategory.getCategory());
    }

    @Test
    public void testUpdate() throws ApiException {
        BrandCategoryPojo brandCategory=createBrand();

        brandCategory.setBrand("aaa");
        brandCategory.setCategory("bbb");
        brandCategoryService.updateBrand(brandCategory.getId(), brandCategory);
        assertEquals("aaa",brandCategory.getBrand());
        assertEquals("bbb",brandCategory.getCategory());
    }

    @Test
    public void testGetAllBrands() throws ApiException{
        BrandCategoryPojo brandCategory=createBrand();

        List<BrandCategoryPojo> brandPojoList =  brandCategoryService.getAllBrands();

        assertEquals(1,brandPojoList.size());
        assertEquals("nike",brandPojoList.get(0).getBrand());
        assertEquals("shoes",brandPojoList.get(0).getCategory());

    }

}
