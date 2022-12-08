package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;



@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;


    @Transactional
    public void addInventory(InventoryPojo inventory) throws ApiException{
        if(inventory.getCount() <=0 ){
            throw new ApiException("Quantity should be a positive number");
        }

        // Check count of that product in inventory
        Pair<Integer, Integer> previousCount = inventoryExist(inventory.getProductId());
        if(previousCount.getKey() == 0 ){
            inventoryDao.add(inventory.getProductId(), inventory.getCount());
            return ;
        }

        InventoryPojo inventoryPojo = inventoryDao.getInventory(previousCount.getKey());
        inventoryPojo.setCount(inventory.getCount() + previousCount.getValue());

    }

    public List<InventoryPojo> showInventory(){
        List<InventoryPojo> inventoryPojos =  inventoryDao.showInventory();
        return inventoryPojos;
    }


    @Transactional
    public void updateInventory(int id, InventoryPojo inventory) throws ApiException{
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

   public InventoryPojo getInventoryByProductId(int id){
        return inventoryDao.getInventoryByProductId(id);
   }

}
