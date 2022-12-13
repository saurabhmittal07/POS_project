package com.increff.employee.service;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest{
    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;


    @Test
    public  void testAddInventory() throws  ApiException{
        addInventory();
    }

    @Test
    public void testAddInventoryOfNegativeQuantity() throws  ApiException{
        ProductPojo productPojo = createProduct();
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setCount(-5);
        try{
            inventoryService.addInventory(inventoryPojo);
        }catch(ApiException exception){
            assertEquals("Quantity should be a positive number",exception.getMessage().trim());
        }
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
    public void testUpdateNegativeQuantity() throws ApiException{
        InventoryPojo inventoryPojo =  addInventory();
        inventoryPojo.setCount(0);
        try{
            inventoryService.updateInventory(inventoryPojo.getProductId(),inventoryPojo);
        }catch(ApiException exception){
            assertEquals("Quantity should be a positive number", exception.getMessage().trim());
        }
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

}
