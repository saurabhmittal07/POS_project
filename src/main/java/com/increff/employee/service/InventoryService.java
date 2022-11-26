package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Inventory;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private ProductDao productDao;


    public void add(Inventory inventory) throws ApiException{

        // Check if productId exist
        if(!productExist(inventory.getProductId())){
            throw new ApiException("Product with id : " + inventory.getProductId() + " does not exist");
        }

        inventoryDao.add(inventory);
    }

    public List<InventoryPojo> showInventory(){
        return inventoryDao.showInventory();
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

}
