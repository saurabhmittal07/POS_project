package com.increff.employee.service;
import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.dao.ProductDao;

import com.increff.employee.pojo.ProductPojo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandCategoryDao brandCategoryDao;


    public ProductPojo add(ProductPojo product) throws ApiException{
        return productDao.add(product);
    }

    public List<ProductPojo> getAllProducts(){
        List<ProductPojo> productPojos =  productDao.getAllProducts();
        return productPojos;
    }


    @Transactional
    public void updateProduct(int id, ProductPojo product) throws ApiException{

        ProductPojo productPojo = productDao.getProduct(id);

        // check if barcode already exist
        if( !(productPojo.getBarcode().equals(product.getBarcode())) &&  barCodeExist(product.getBarcode())){
            throw new ApiException("Barcode already exist");
        }

        productPojo.setBrandCategory(product.getBrandCategory());
        productPojo.setMrp(product.getMrp());
        productPojo.setName(product.getName());
        productPojo.setBarcode(product.getBarcode());

    }

    public ProductPojo getProduct(int id){
        ProductPojo productPojo = productDao.getProduct(id);
        return productPojo;
    }

    private boolean barCodeExist(String barcode){
        ProductPojo productPojo = productDao.getProductByBarcode(barcode);
        if(productPojo == null){
            return false;
        }
        return true;
    }

    public ProductPojo getProductByBarcode(String barcode){
        return productDao.getProductByBarcode(barcode);
    }




}
