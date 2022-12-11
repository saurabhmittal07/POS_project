package com.increff.employee.dao;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao {

    private static String delete_id = "delete from InventoryPojo p where id=:id";
    private static String select_all = "select p from InventoryPojo p";
    private static String select_id = "select p from InventoryPojo p where id=:id";

    private static String select_product_id = "select p from InventoryPojo p where productId=:productId";




    @Transactional
    public InventoryPojo add(int productId, int count) throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setCount(count);
        inventoryPojo.setProductId(productId);
        System.out.println(count);
        em().persist(inventoryPojo);
        return inventoryPojo;
    }

    @Transactional
    public List<InventoryPojo> showInventory(){
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    @Transactional
    public InventoryPojo getInventoryByProductId(int productId){
        TypedQuery<InventoryPojo > query = getQuery(select_product_id, InventoryPojo.class);
        query.setParameter("productId", productId);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Transactional
    public void deleteInventory(int id){
        Query query = em().createQuery(delete_id);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public InventoryPojo getInventory(int id){
        TypedQuery<InventoryPojo > query = getQuery(select_id, InventoryPojo .class);
        query.setParameter("id", id);
        return getSingle(query);
    }


    @Transactional
    public void updateInventory(int id, InventoryForm inventory){
    }


}
