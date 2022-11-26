package com.increff.employee.dao;

import com.increff.employee.model.Inventory;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao {

    private static String delete_id = "delete from InventoryPojo p where id=:id";
    private static String select_all = "select p from InventoryPojo p";
    private static String select_id = "select p from InventoryPojo p where id=:id";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(Inventory inventory) throws ApiException {
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setCount(inventory.getCount());
        inventoryPojo.setProductId(inventory.getProductId());
        em.persist(inventoryPojo);
    }

    @Transactional
    public List<InventoryPojo> showInventory(){
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }
}
