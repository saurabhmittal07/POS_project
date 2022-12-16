package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public void addOrderItem(OrderItemPojo orderItemPojo) throws ApiException {
        validateOrderItem(orderItemPojo);
        orderItemDao.addOrderItem(orderItemPojo);
    }

    public List<OrderItemPojo> getOrderItems(int id){
        return orderItemDao.getOrderItems(id);
    }

    void validateOrderItem(OrderItemPojo orderItemPojo) throws ApiException{
        if(orderItemPojo.getQuantity() <= 0){
            throw new ApiException("Quantity should be more than 0");
        }
    }
}
