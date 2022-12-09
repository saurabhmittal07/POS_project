package com.increff.employee.service;

import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import javafx.util.Pair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest{
    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;


    public BrandCategoryPojo createBrand() throws ApiException {
        return createBrand("nike","shoes");
    }

    public BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo(brand,category);
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

    @Transactional
    private InventoryPojo addInventory() throws ApiException {
        ProductPojo productPojo = createProduct();
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setCount(5);

        return inventoryService.addInventory(inventoryPojo);
    }



    @Test
    public  void testAddInventory() throws  ApiException{
        addInventory();
    }

    @Test
    public void testShowInventory() throws ApiException{
        InventoryPojo inventoryPojo =  addInventory();
        List<InventoryPojo> inventoryPojos =  inventoryService.showInventory();
        assertEquals(1, inventoryPojos.size());
        assertEquals(5,inventoryPojos.get(0).getCount());
    }

    @Test
    public void testUpdateInventory() throws ApiException{
        InventoryPojo inventoryPojo =  addInventory();
        inventoryPojo.setCount(12);
        inventoryService.updateInventory(inventoryPojo.getProductId(),inventoryPojo);
        assertEquals(12,inventoryPojo.getCount());
    }

    @Test
    public void testGetInventory() throws ApiException{
        InventoryPojo inventoryPojo =  addInventory();
        InventoryPojo  newInventoryPojo = inventoryService.getInventory(inventoryPojo.getId());
        assertEquals(5,newInventoryPojo.getCount());
    }

    @Test
    public void testGetInventoryByProductId() throws ApiException{
        InventoryPojo inventoryPojo =  addInventory();

        InventoryPojo  newInventoryPojo = inventoryService.getInventoryByProductId(inventoryPojo.getProductId());
        assertEquals(5,newInventoryPojo.getCount());
    }

    @Test
    public void testBarcodeExist(){
        Pair p = inventoryService.inventoryExist(2);
        assertEquals(0,p.getKey());
        assertEquals(0,p.getValue());

    }

}
