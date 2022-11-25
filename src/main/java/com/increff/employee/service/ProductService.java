package com.increff.employee.service;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Product;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public void add(Product product){
        productDao.add(product);
    }

    public List<ProductPojo> getAllProducts(){
        return productDao.getAllProducts();
    }
}
