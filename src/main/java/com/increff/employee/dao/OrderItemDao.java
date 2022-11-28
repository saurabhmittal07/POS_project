package com.increff.employee.dao;


import com.increff.employee.pojo.OrderItemPojo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class OrderItemDao extends AbstractDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void addOrderItem(OrderItemPojo orderItemPojo){
        em.persist(orderItemPojo);
    }
}
