package com.increff.employee.service;


import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductServiceTest extends AbstractUnitTest{

    @Autowired
    private ProductService productService;

    @Autowired BrandCategoryService brandCategoryService;


    @Test
    public void testAddProduct() throws ApiException {
        createProduct();
    }

    @Test
    public void testAddEmptyBarcode() throws ApiException{
        createProduct();
        try{
            createProduct("qdqwd", 1, "", 12);
        }catch (ApiException exception){
            assertEquals("Barcode can not be empty", exception.getMessage().trim());
        }
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

        productPojo = productService.getProduct(productPojo.getId());

        assertEquals("ppp" , productPojo.getBarcode());
    }

    @Test
    public void testUpdateWrongBrand() throws ApiException{
        ProductPojo productPojo = createProduct();
        productPojo.setBrandCategory(2);
        try{
            productService.updateProduct(productPojo.getId(), productPojo);
        }catch(ApiException exception){
            assertEquals("Brand - Category Pair does not exist", exception.getMessage().trim());
        }
    }

    @Test
    public void testGetProduct() throws ApiException{
        ProductPojo productPojo =  createProduct();
        ProductPojo newProduct = productService.getProduct(productPojo.getId());
        assertEquals(productPojo.getBarcode(), newProduct.getBarcode());
    }

    @Test
    public void testWithWrongId() throws ApiException {
        try{
            productService.getProduct(0);
        } catch (ApiException exception) {
            assertEquals("Product with given id does not exists",exception.getMessage().trim());
        }
    }

    @Test
    public void testBarcodeExist() throws ApiException{
        createProduct();
        ProductPojo productPojo = productService.getProductByBarcode("qqq");
        assertEquals(productPojo.getBarcode(), "qqq");
        assertEquals(productPojo.getName(), "milton");
    }

    @Test
    public void testNonExistingBarcode() throws ApiException {
        try{
            ProductPojo productPojo = productService.getProductByBarcode("qqq");
        }catch (ApiException exception){
            assertEquals("Barcode :qqq does not exist", exception.getMessage().trim());
        }
    }

}
