package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;



@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;


    @Transactional
    public InventoryPojo addInventory(InventoryPojo inventory) throws ApiException{
        if(inventory.getCount() <=0 ){
            throw new ApiException("Quantity should be a positive number");
        }

        // Check count of that product in inventory
        InventoryPojo inventoryPojo = getInventoryByProductId(inventory.getProductId());
        if(inventoryPojo == null){
            return inventoryDao.add(inventory.getProductId(), inventory.getCount());
        }

        inventoryPojo.setCount(inventory.getCount() + inventoryPojo.getCount());
        return inventoryPojo;
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


   public InventoryPojo getInventoryByProductId(int id){
        return inventoryDao.getInventoryByProductId(id);
   }

   public void isInventoryAvailable(int productId, int quantity) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.getInventoryByProductId(productId);

       if(inventoryPojo == null){
           throw new ApiException(0 + " Unit(s) available in inventory");
       }else if(inventoryPojo.getCount() < quantity){
            throw  new ApiException(inventoryPojo.getCount()+ " Unit(s) available in the inventory");
        }
   }
}
