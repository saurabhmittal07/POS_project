package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.TrimLower;
import com.increff.employee.util.Validate;
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

    @Autowired
    private InventoryService inventoryService;

    public void addProduct( ProductForm product) throws ApiException {
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
        productService.addProduct(productPojo);
    }

    public List<ProductData> getAllProducts(){
        List<ProductPojo>  productPojos = productService.getAllProducts();
        List<ProductData> productDatas = new ArrayList<>();
        for(ProductPojo productPojo : productPojos){
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());
            ProductData productData = new ProductData();
            productData.setName(productPojo.getName());
            productData.setBrand(brandCategoryPojo.getBrand());
            productData.setBarcode(productPojo.getBarcode());
            productData.setCategory(brandCategoryPojo.getCategory());
            productData.setMrp(productPojo.getMrp());
            productData.setId(productPojo.getId());

            productDatas.add(productData);
        }

        return productDatas;
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

    
    public ProductData getProduct( int id) throws ApiException {
        ProductPojo productPojo = productService.getProduct(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());

        ProductData productData = new ProductData();
        productData.setBarcode(productPojo.getBarcode());
        productData.setBrand(brandCategoryPojo.getBrand());
        productData.setCategory(brandCategoryPojo.getCategory());
        productData.setMrp(productPojo.getMrp());
        productData.setName(productPojo.getName());
        productData.setId(productPojo.getId());

        return productData;

    }

    public double getMrp(String barcode , String quantity) throws ApiException{
        int cur = Integer.parseInt(quantity);
        validate(barcode, cur);

        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        // Check if required quantity available
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());


        if(inventoryPojo == null){
            throw new ApiException(0 + " Unit/units available in inventory");
        } else if(inventoryPojo.getCount() < cur){
            throw new ApiException(inventoryPojo.getCount() + " Unit/units available in inventory");
        }

        return  productPojo.getMrp();
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

    public void validate(String barcode, int quantity) throws ApiException {
        if( barcode.equals("")){
            throw new ApiException("Please enter barcode");
        }
        if(quantity <= 0){
            throw new ApiException("Quantity should be more than 0");
        }
        ProductPojo productPojo = productService.getProductByBarcode(barcode);

        if(productPojo == null){
            throw new ApiException("Product with barcode:" + barcode +" does not exist");
        }
    }


}
