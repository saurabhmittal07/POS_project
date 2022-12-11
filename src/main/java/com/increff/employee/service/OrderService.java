package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    public OrderPojo getOrder(int id){
        return orderDao.getOrder(id);
    }

    public List<OrderPojo> getOrdersByDate(String startDate, String endDate){
        return orderDao.ordersByDate(startDate,endDate);
    }

}
