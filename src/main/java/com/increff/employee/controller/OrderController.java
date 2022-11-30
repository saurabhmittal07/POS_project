package com.increff.employee.controller;

import com.increff.employee.model.Order;
import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Creates an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void createOrder(@RequestBody List<OrderItem> items) throws ApiException {
        System.out.println(items);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderService.createOrder(zonedDateTime, items);
    }

    @ApiOperation(value = "Shows all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<Order> showOrders(){
        return orderService.showOrders();
    }

    @ApiOperation(value = "Check if invenotry exist or not")
    @RequestMapping(path = "/api/order/inventoryExist", method = RequestMethod.POST)
    public double inventoryExist(@RequestBody OrderItem orderItem) throws ApiException{
        return orderService.inventoryExist(orderItem);
    }

    @ApiOperation(value = "Receipt of order of given  id")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public List<OrderItemPojo> orderReciept(@PathVariable int id) throws ApiException{
        return orderService.orderReciept(id);
    }

}
