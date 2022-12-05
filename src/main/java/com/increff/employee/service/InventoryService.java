package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Inventory;
import com.increff.employee.model.InventoryUI;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import io.swagger.models.auth.In;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import javafx.util.*;


@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ProductDao productDao;


    @Transactional
    public void add(Inventory inventory) throws ApiException{

        // Check if productId exist
        if(inventory.getCount() <=0 ){
            throw new ApiException("Quantity should be a positive number");
        }

        ProductPojo productPojo = productDao.getProductByBarcode(inventory.getBarcode());
        if(productPojo == null){
            throw new ApiException("Product with barcode : " + inventory.getBarcode() + " does not exist");
        }

        // Check count of that product in inventory
        Pair<Integer, Integer> previousCount = inventoryExist(productPojo.getId());
        if(previousCount.getKey() == 0 ){
            inventoryDao.add(productPojo.getId(), inventory.getCount());
            return ;
        }

        InventoryPojo inventoryPojo = inventoryDao.getInventory(previousCount.getKey());
        inventoryPojo.setCount(inventory.getCount() + previousCount.getValue());

    }

    public List<InventoryUI> showInventory(){
        List<InventoryPojo> inventoryPojos =  inventoryDao.showInventory();
        List<InventoryUI> inventories = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojos){
            InventoryUI inventoryUI = new InventoryUI();
            inventoryUI.setQuantity(inventoryPojo.getCount());
            ProductPojo productPojo = productDao.getProduct(inventoryPojo.getProductId());
            inventoryUI.setName(productPojo.getName());
            inventoryUI.setBarcode(productPojo.getBarcode());
            inventoryUI.setId(inventoryPojo.getId());

            inventories.add(inventoryUI);
        }
        return inventories;
    }


    @Transactional
    public void updateInventory(int id, Inventory inventory) throws ApiException{
        if(inventory.getCount() <=0 ){
            throw new ApiException("Quantity should be a positive number");
        }
        InventoryPojo inventoryPojo = getInventory(id);
        inventoryPojo.setCount(inventory.getCount());

    }



    public InventoryPojo getInventory(int id){
        InventoryPojo inventoryPojo = inventoryDao.getInventory(id);
        return inventoryPojo;
    }




   private Pair  inventoryExist(int id){
        List<InventoryPojo> inventories = inventoryDao.showInventory();
        for(InventoryPojo inventoryPojo : inventories){
            if(inventoryPojo.getProductId() == id){
                Pair<Integer, Integer> p = new Pair(inventoryPojo.getId(),inventoryPojo.getCount());

              return p;
            }
        }
       Pair<Integer, Integer> p = new Pair(0,0);
       return p;
   }

}
