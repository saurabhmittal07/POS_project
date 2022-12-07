package com.increff.employee.service;
import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Product;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;

import com.increff.employee.util.TrimLower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandCategoryDao brandCategoryDao;



    public ProductPojo add(ProductForm product) throws ApiException{

        TrimLower.trimLower(product);

        //Validate
        valid(product);
        // Get Brand I'd by brand - cateogry names
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrandByName(product.getBrand(), product.getCategory());
        Product newProduct = new Product();
        newProduct.setBrandCategory(brandCategoryPojo.getId());
        newProduct.setBarcode(product.getBarcode());
        newProduct.setName(product.getName());
        newProduct.setMrp(product.getMrp());
        return productDao.add(newProduct);
    }

    public List<ProductForm> getAllProducts(){
        List<ProductPojo> productPojos =  productDao.getAllProducts();
        List<ProductForm> productForms = new ArrayList<>();
        for(ProductPojo productPojo : productPojos){
            BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(productPojo.getBrandCategory());
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



    public void updateProduct(int id, ProductForm product) throws ApiException{
        TrimLower.trimLower(product);
        valid(product);
        ProductPojo productPojo = productDao.getProduct(id);
        // check if barcode already exist

        System.out.println(productPojo.getBarcode() + "  " + product.getBarcode());
        if( !(productPojo.getBarcode().equals(product.getBarcode())) &&  barCodeExist(product.getBarcode())){
            throw new ApiException("Barcode already exist");
        }
        // Check if Brand-Category Already exist or not
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrandByName(product.getBrand(), product.getCategory());
        Product newProduct = new Product();
        newProduct.setBrandCategory(brandCategoryPojo.getId());
        newProduct.setBarcode(product.getBarcode());
        newProduct.setName(product.getName());
        newProduct.setMrp(product.getMrp());
        productDao.updateProduct(id, newProduct);
    }

    public ProductForm getProduct(int id){
        ProductPojo productPojo = productDao.getProduct(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(productPojo.getBrandCategory());
        ProductForm productForm = new ProductForm();
        productForm.setId(id);
        productForm.setBrand(brandCategoryPojo.getBrand());
        productForm.setCategory(brandCategoryPojo.getCategory());
        productForm.setName(productPojo.getName());
        productForm.setMrp(productPojo.getMrp());
        productForm.setBarcode(productPojo.getBarcode());
        return productForm;
    }

    public boolean barCodeExist(String barcode){
        ProductPojo productPojo = productDao.getProductByBarcode(barcode);
        if(productPojo == null){
            return false;
        }
        return true;
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


        BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrandByName(product.getBrand(), product.getCategory());
        if(brandCategoryPojo == null){
            throw new ApiException("Brand Cateogry pair does not exist");
        }

    }


}
