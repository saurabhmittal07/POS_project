package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Inventory;
import com.increff.employee.model.Product;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
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
        if(!productExist(inventory.getProductId())){
            throw new ApiException("Product with id : " + inventory.getProductId() + " does not exist");
        }

        // Check count of that product in inventory
        Pair<Integer, Integer> previousCount = inventoryExist(inventory.getProductId());
        if(previousCount.getKey() == 0 ){
            inventoryDao.add(inventory);
            return ;
        }

        InventoryPojo inventoryPojo = inventoryDao.getInventory(previousCount.getKey());
        inventoryPojo.setCount(inventory.getCount() + previousCount.getValue());

    }

    public List<InventoryPojo> showInventory(){
        return inventoryDao.showInventory();
    }



    @Transactional
    public void updateInventory(int id, Inventory inventory) throws ApiException{
        InventoryPojo inventoryPojo = getInventory(id);
        inventoryPojo.setCount(inventory.getCount());

    }

    public InventoryPojo getInventory(int id){
        InventoryPojo inventoryPojo = inventoryDao.getInventory(id);
        return inventoryPojo;
    }


   public boolean productExist(int id){
        List<ProductPojo> products = productDao.getAllProducts();
        for(ProductPojo product : products){
            if(product.getId() == id){
                return true;
            }
        }
        return false;
   }

   public Pair  inventoryExist(int id){
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
