package com.increff.employee.service;

import com.increff.employee.pojo.BrandCategoryPojo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandCategoryServiceTest extends AbstractUnitTest {


    @Autowired
    private BrandCategoryService brandCategoryService;
    @Test
    public void testAdd() throws ApiException {
        createBrand();
    }

    @Test
    public void testAddDuplicates(){
        try{
            createBrand();
            createBrand();
        } catch (ApiException exception) {
            assertEquals("Brand - Category pair already exists",exception.getMessage().trim());
        }
    }


    @Test
    public void testGetBrand() throws ApiException {
        BrandCategoryPojo brandCategory =createBrand();

        brandCategory = brandCategoryService.getBrand(brandCategory.getId());
        assertEquals("nike",brandCategory.getBrand());
        assertEquals("shoes",brandCategory.getCategory());
    }

    @Test
    public void testWithWrongId() throws ApiException {
        try{
            brandCategoryService.getBrand(0);
        } catch (ApiException exception) {
            assertEquals("Brand with the given id does not exists",exception.getMessage().trim());
        }
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
    public void testUpdateWrongInput() throws ApiException {
        BrandCategoryPojo brandCategory=createBrand();

        brandCategory.setBrand("");
        brandCategory.setCategory("bbb");
        try{
            brandCategoryService.updateBrand(brandCategory.getId(), brandCategory);
        }catch (ApiException exception){
            assertEquals("Brand or Category can not be empty",exception.getMessage().trim());
        }
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
