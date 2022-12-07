package com.increff.employee.service;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.ProductForm;
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
        return createProduct("milton", "kissan", "food", "qqq", 43);
    }

    private ProductPojo createProduct  (String name, String brand, String category, String barcode, int mrp) throws ApiException{
        createBrand();
        ProductForm product = new ProductForm();
        product.setName(name);
        product.setBarcode(barcode);
        product.setBrand(brand);
        product.setCategory(category);
        product.setMrp(mrp);

        return productService.add(product);
    }

    private void createBrand() throws ApiException{
        BrandCategory brandCategory = new BrandCategory();
        brandCategory.setBrand("kissan");
        brandCategory.setCategory("food");
        brandCategoryService.add(brandCategory);
    }

    private ProductForm productForm(){
        ProductForm productForm = new ProductForm();
        productForm.setMrp(12);
        productForm.setBarcode("Barcode ");
        productForm.setName("abc");
        productForm.setBrand("kissan");
        productForm.setCategory("food");
        return productForm;
    }

    @Test
    public void testAddProduct() throws ApiException {

        createBrand();

        ProductForm productForm = new ProductForm();
        productForm.setMrp(12);
        productForm.setBarcode("barcode");
        productForm.setName("abc");
        productForm.setBrand("kissan");
        productForm.setCategory("food");
        productService.add(productForm);
    }

    @Test
    public void testGetAllProducts() throws ApiException{
        createProduct();
        List<ProductForm> productForms = productService.getAllProducts();
        assertEquals(1,productForms.size());
        assertEquals("kissan",productForms.get(0).getBrand());
        assertEquals("food",productForms.get(0).getCategory());
    }

    @Test
    public void testUpdateProduct() throws ApiException{
        ProductPojo productPojo = createProduct();
        ProductForm productForm = productForm();

        productForm.setBarcode("ppp");

        productService.updateProduct(productPojo.getId(), productForm);

        productForm = productService.getProduct(1);

        assertEquals("qqq" , productForm.getBarcode());
    }

    @Test
    public void testTrimLower(){
       ProductForm productForm = productForm();
        productService.trimLower(productForm);

        assertEquals("barcode", productForm.getBarcode());
    }

    public void testValid() throws ApiException{
        ProductForm productForm = productForm();
        productService.valid(productForm);
    }


}
