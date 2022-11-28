package com.increff.employee.service;


import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public void createOrder(ZonedDateTime zonedDateTime){
         orderDao.createOrder(zonedDateTime);
    }

    public List<OrderPojo> showorders(){
        return orderDao.showOrders();
    }

}
