package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDao extends  AbstractDao{

    private static String delete_id = "delete from InventoryPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p";
    private static String select_id = "select p from InventoryPojo p where id=:id";
    private static String select_order_id = "select p from OrderItemPojo p where orderId=:orderId";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createOrder(ZonedDateTime zonedDateTime){
        OrderPojo orderPojo = new OrderPojo();
         orderPojo.setDateTime(zonedDateTime);
         em.persist(orderPojo);
    }


    @Transactional
    public List<OrderPojo> showOrders(){
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    @Transactional
    public List<OrderItemPojo> orderReciept(int id){
        TypedQuery<OrderItemPojo> query = getQuery(select_order_id, OrderItemPojo.class);
        query.setParameter("orderId", id);
        return query.getResultList();
    }
}
