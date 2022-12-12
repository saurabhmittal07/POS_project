package com.increff.employee.dto;

import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryUI;
import com.increff.employee.model.UpdateOrderForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.TrimLower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryDto {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;


    public void addInventory(InventoryForm inventory) throws ApiException {
        TrimLower.trimLower(inventory);
        ProductPojo productPojo = productService.getProductByBarcode(inventory.getBarcode());

        if(productPojo == null){
            throw new ApiException("Product with barcode: " + inventory.getBarcode() + " does not exist");
        }

        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setCount(inventory.getCount());

        inventoryService.addInventory(inventoryPojo);
    }

    public List<InventoryUI> showInventory(){
        List<InventoryPojo> inventoryPojos = inventoryService.showInventory();
        List<InventoryUI> inventories = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojos){
            InventoryUI inventoryUI = new InventoryUI();
            inventoryUI.setQuantity(inventoryPojo.getCount());
            ProductPojo productPojo = productService.getProduct(inventoryPojo.getProductId());
            inventoryUI.setName(productPojo.getName());
            inventoryUI.setBarcode(productPojo.getBarcode());
            inventoryUI.setId(inventoryPojo.getId());

            inventories.add(inventoryUI);
        }
        return inventories;
    }


    public void updateInventory(int id,  InventoryForm inventory) throws ApiException {
        TrimLower.trimLower(inventory);
        ProductPojo productPojo = productService.getProductByBarcode(inventory.getBarcode());

        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setCount(inventory.getCount());
        inventoryPojo.setProductId(productPojo.getId());

        inventoryService.updateInventory(id, inventoryPojo);
    }

    public InventoryData getInventory(int id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.getInventory(id);
        InventoryData inventoryData = new InventoryData();
        inventoryData.setId(inventoryPojo.getId());
        inventoryData.setCount(inventoryPojo.getCount());
        ProductPojo productPojo = productService.getProduct(inventoryPojo.getProductId());
        inventoryData.setBarcode(productPojo.getBarcode());
        return inventoryData;
    }

    public double checkIfInventoryAvailable(InventoryForm inventoryForm) throws ApiException{
        ProductPojo productPojo = productService.getProductByBarcode(inventoryForm.getBarcode());
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());

        if (inventoryPojo.getCount() < inventoryForm.getCount()) {
            throw new ApiException(inventoryPojo.getCount()+" Unit/units available in inventory");
        }

        return productPojo.getMrp();
    }

}
