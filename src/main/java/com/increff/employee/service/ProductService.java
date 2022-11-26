package com.increff.employee.service;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.Product;
import com.increff.employee.model.Product;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.AlgorithmConstraints;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandCategoryDao brandCategoryDao;

    public void add(Product product) throws ApiException{

        product = trimLower(product);

        // check if barcode already exist
        if(barCodeExist(product.getBarcode())){
            throw new ApiException("Barcode already exist");
        }

        // Check if Brand-Category Already exist or not
        if(!brandCategoryExist(product.getBrandCategory())){
            throw new ApiException("Brand Category does not exist");
        }

        productDao.add(product);
    }

    public List<ProductPojo> getAllProducts(){
        return productDao.getAllProducts();
    }

    public void deleteProduct(int id){
        productDao.deleteProduct(id);
    }

    public void updateProduct(int id, Product product) throws ApiException{
        product = trimLower(product);

        // check if barcode already exist
        if(barCodeExist(product.getBarcode())){
            throw new ApiException("Barcode already exist");
        }

        // Check if Brand-Category Already exist or not
        if(!brandCategoryExist(product.getBrandCategory())){
            throw new ApiException("Brand Category does not exist");
        }

        productDao.updateProduct(id, product);
    }

    public ProductPojo getProduct(int id){
        ProductPojo productPojo = productDao.getProduct(id);
        return productPojo;
    }



    // Validation functions
    public Product trimLower(Product product){
        product.setName(product.getName().trim().toLowerCase());
        product.setBarcode(product.getBarcode().trim().toLowerCase());

        return product;
    }


    public boolean brandCategoryExist(int id){
        List<BrandCategoryPojo> brands = brandCategoryDao.getAllBrands();
        for(BrandCategoryPojo brand : brands){
            if(brand.getId() == id)
            {
                return true;
            }
        }
        return false;
    }

    public boolean barCodeExist(String barcode){
        List<ProductPojo> products = productDao.getAllProducts();
        for(ProductPojo product : products){
            if(product.getBarcode().equals(barcode)){
                return true;
            }
        }
        return false;
    }
}
