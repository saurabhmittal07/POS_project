package com.increff.employee.dao;


import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Stack;

@Repository
public class OrderDao extends  AbstractDao{

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_order_id = "select p from OrderItemPojo p where orderId=:orderId";
    private static String select_by_date = "select p from OrderPojo p where dateTime >=:startDate AND dateTime <=:endDate";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void createOrder(ZonedDateTime zonedDateTime){
        OrderPojo orderPojo = new OrderPojo();
         orderPojo.setDateTime(zonedDateTime);
         em.persist(orderPojo);
    }

    @Transactional
    public OrderPojo getOrder(int id){
        TypedQuery<OrderPojo > query = getQuery(select_id, OrderPojo .class);
        query.setParameter("id", id);
        return getSingle(query);
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

    @Transactional
    public List<OrderPojo> ordersByDate(String startDate, String endDate){
        TypedQuery<OrderPojo> query = getQuery(select_by_date, OrderPojo.class);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        ZonedDateTime start = ZonedDateTime.parse(startDate + "T00:00:00.000+05:30[Asia/Calcutta]", formatter);
        ZonedDateTime end = ZonedDateTime.parse(endDate + "T00:00:00.000+05:30[Asia/Calcutta]", formatter);
        query.setParameter("startDate", start);
        query.setParameter("endDate", end);


        return query.getResultList();
    }
}
