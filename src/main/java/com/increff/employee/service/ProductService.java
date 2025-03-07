package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;


    public ProductPojo addProduct(ProductPojo product) throws ApiException{

        validProduct(product);

        if(getProductByBarcode(product.getBarcode()) != null){
            throw new ApiException("Barcode already exist");
        }
        return productDao.addProduct(product);
    }

    public List<ProductPojo> getAllProducts(){
        List<ProductPojo> productPojos =  productDao.getAllProducts();
        return productPojos;
    }


    @Transactional
    public void updateProduct(int id, ProductPojo product) throws ApiException{

        validProduct(product);
        ProductPojo productPojo = productDao.getProduct(id);

        // check if barcode already exist
        if( !(productPojo.getBarcode().equals(product.getBarcode())) &&  getProductByBarcode(product.getBarcode()) != null){
            throw new ApiException("Barcode already exist");
        }

        productPojo.setBrandCategory(product.getBrandCategory());
        productPojo.setMrp(product.getMrp());
        productPojo.setName(product.getName());
        productPojo.setBarcode(product.getBarcode());

    }

    public ProductPojo getProduct(int id) throws ApiException{
        ProductPojo productPojo = productDao.getProduct(id);
        if(productPojo == null){
            throw new ApiException("Product with given id does not exists");
        }
        return productPojo;
    }


    public ProductPojo getProductByBarcode(String barcode) throws ApiException{
        ProductPojo productPojo= productDao.getProductByBarcode(barcode);
        return productPojo;
    }

    protected void validProduct(ProductPojo product) throws ApiException{
        if(product.getName().equals("")){
            throw new ApiException("Product name can not be empty");
        }
        if(product.getBarcode().equals("")){
            throw new ApiException("Barcode can not be empty");
        }
        if(product.getMrp() <=0 ){
            throw new ApiException("MRP should be greater than 0");
        }

    }

}
