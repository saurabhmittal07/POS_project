package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public void addOrderItem(OrderItemPojo orderItemPojo){
        orderItemDao.addOrderItem(orderItemPojo);
    }

    public List<OrderItemPojo> getOrderItems(int id){
        return orderItemDao.getOrderItems(id);
    }

}
