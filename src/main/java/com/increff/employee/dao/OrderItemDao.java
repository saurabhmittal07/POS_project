package com.increff.employee.dao;


import com.increff.employee.pojo.OrderItemPojo;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

    private static String select_order_id = "select p from OrderItemPojo p where orderId=:orderId";


    @Transactional
    public void addOrderItem(OrderItemPojo orderItemPojo){
        em().persist(orderItemPojo);
    }

    @Transactional
    public List<OrderItemPojo> orderReciept(int id){
        TypedQuery<OrderItemPojo> query = getQuery(select_order_id, OrderItemPojo.class);
        query.setParameter("orderId", id);
        return query.getResultList();
    }

}
