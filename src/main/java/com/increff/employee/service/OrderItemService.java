package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public void addOrderItem(List<OrderItemPojo> orderItemPojos){
        for(OrderItemPojo orderItemPojo : orderItemPojos){
            orderItemDao.addOrderItem(orderItemPojo);
        }
    }

    public List<OrderItemPojo> getReceipt(int id){
        return orderItemDao.orderReciept(id);
    }

}
