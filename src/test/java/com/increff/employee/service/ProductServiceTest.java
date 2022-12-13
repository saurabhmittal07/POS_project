package com.increff.employee.service;

import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductServiceTest extends AbstractUnitTest{

    @Autowired
    private ProductService productService;

    @Autowired BrandCategoryService brandCategoryService;

    public BrandCategoryPojo createBrand() throws ApiException {
        return createBrand("nike","shoes");
    }

    public BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo();
        brandCategory.setBrand(brand);
        brandCategory.setCategory(category);
        return brandCategoryService.addBrand(brandCategory);
    }

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



    @Test
    public void testAddProduct() throws ApiException {
        createProduct();
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
    public void testGetProduct() throws ApiException{
        ProductPojo productPojo =  createProduct();
        ProductPojo newProduct = productService.getProduct(productPojo.getId());
        assertEquals(productPojo.getBarcode(), newProduct.getBarcode());
    }

    @Test
    public void testBarcodeExist() throws ApiException{
        createProduct();
        ProductPojo productPojo = productService.getProductByBarcode("qqq");
        assertEquals(productPojo.getBarcode(), "qqq");
        assertEquals(productPojo.getName(), "milton");

    }


}
