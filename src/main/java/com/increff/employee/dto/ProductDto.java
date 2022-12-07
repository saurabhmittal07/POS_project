package com.increff.employee.dto;

import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.TrimLower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    public void add( ProductForm product) throws ApiException {
        TrimLower.trimLower(product);
        valid(product);
        ProductPojo productPojo = new ProductPojo();

        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrandByName(product.getBrand(),product.getCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand-Category pair does not exist");
        }
        productPojo.setBarcode(product.getBarcode());
        productPojo.setBrandCategory(brandCategoryPojo.getId());
        productPojo.setName(product.getName());
        productPojo.setMrp(product.getMrp());
        productService.add(productPojo);
    }

    public List<ProductForm> getAllProducts(){
        List<ProductPojo>  productPojos = productService.getAllProducts();
        List<ProductForm> productForms = new ArrayList<>();
        for(ProductPojo productPojo : productPojos){
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());
            ProductForm productForm = new ProductForm();
            productForm.setName(productPojo.getName());
            productForm.setBrand(brandCategoryPojo.getBrand());
            productForm.setBarcode(productPojo.getBarcode());
            productForm.setCategory(brandCategoryPojo.getCategory());
            productForm.setMrp(productPojo.getMrp());
            productForm.setId(productPojo.getId());

            productForms.add(productForm);
        }

        return productForms;
    }

    
    public void updateProduct( int id,  ProductForm product) throws ApiException {
        TrimLower.trimLower(product);
        valid(product);

        ProductPojo productPojo = new ProductPojo();

        productPojo.setMrp(product.getMrp());
        productPojo.setBarcode(product.getBarcode());
        productPojo.setName(product.getName());
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrandByName(product.getBrand(),product.getCategory());

        if(brandCategoryPojo == null){
            throw new ApiException("Brand - Category pair does not exist");
        }
        productPojo.setBrandCategory(brandCategoryPojo.getId());

        productService.updateProduct(id, productPojo);
    }

    
    public ProductForm getProduct( int id) throws ApiException {
        ProductPojo productPojo = productService.getProduct(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());

        ProductForm productForm = new ProductForm();
        productForm.setBarcode(productPojo.getBarcode());
        productForm.setBrand(brandCategoryPojo.getBrand());
        productForm.setCategory(brandCategoryPojo.getCategory());
        productForm.setMrp(productPojo.getMrp());
        productForm.setName(productPojo.getName());
        productForm.setId(productPojo.getId());

        return productForm;

    }

    protected void valid(ProductForm product) throws ApiException{
        if(product.getName().equals("")){
            throw new ApiException("Product name can not be empty");
        }
        if(product.getBrand().equals("")){
            throw new ApiException("Brand can not be empty");
        }
        if(product.getCategory().equals("")){
            throw new ApiException("Cateogry can not be empty");
        }
        if(product.getBarcode().equals("")){
            throw new ApiException("Barcode can not be empty");
        }
        if(product.getMrp() <=0 ){
            throw new ApiException("MRP should be greater than 0");
        }

    }
}
