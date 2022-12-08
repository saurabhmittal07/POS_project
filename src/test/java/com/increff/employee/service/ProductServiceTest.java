package com.increff.employee.service;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductServiceTest extends AbstractUnitTest{

    @Autowired
    private ProductService productService;

    @Autowired BrandCategoryService brandCategoryService;



    private ProductPojo createProduct() throws ApiException{
        return createProduct("milton", 1, "qqq", 43);
    }

   private ProductPojo createProduct  (String name, int brandId, String barcode, int mrp) throws ApiException{
        createBrand();
        ProductPojo product = new ProductPojo();
        product.setName(name);
        product.setBarcode(barcode);
        product.setMrp(mrp);
        product.setBrandCategory(brandId);

        return productService.addProduct(product);
    }

    private void createBrand() throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo();
        brandCategory.setBrand("kissan");
        brandCategory.setCategory("food");
        brandCategoryService.addBrand(brandCategory);
    }


    @Test
    public void testAddProduct() throws ApiException {

        createBrand();
        ProductPojo productPojo = createProduct();
    }

    @Test
    public void testGetAllProducts() throws ApiException{
        createProduct();
        List<ProductPojo> productPojos = productService.getAllProducts();
        assertEquals(1,productPojos.size());
        assertEquals("milton",productPojos.get(0).getName());
        assertEquals("qqq",productPojos.get(0).getBarcode());
    }

    @Test
    public void testUpdateProduct() throws ApiException{
        ProductPojo productPojo = createProduct();

        productPojo.setBarcode("ppp");

        productService.updateProduct(productPojo.getId(), productPojo);

        productPojo = productService.getProduct(1);

        assertEquals("qqq" , productPojo.getBarcode());
    }



}
