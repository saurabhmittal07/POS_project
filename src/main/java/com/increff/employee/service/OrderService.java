package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Order;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.UpdateOrderForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;


    @Transactional
    public void createOrder(OrderPojo orderPojo){
        orderDao.createOrder(orderPojo);
    }


    public List<OrderPojo> showOrders(){
        List<OrderPojo> orderPojos =  orderDao.showOrders();
        return orderPojos;
    }


    @Transactional
    public void updateInventory(InventoryPojo inventoryPojo, int quantity) throws ApiException {
        if (inventoryPojo.getCount() < quantity) {
            throw new ApiException(inventoryPojo.getCount()+" Unit/units available in inventory");
        }
    }

    public List<OrderItemPojo> getReceipt(int id){
        return orderDao.orderReciept(id);
    }

    public OrderPojo getOrder(int id){
        return orderDao.getOrder(id);
    }


}
