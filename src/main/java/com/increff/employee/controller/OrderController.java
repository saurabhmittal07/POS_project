package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.UpdateOrderForm;
import com.increff.employee.service.ApiException;
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
    private OrderDto orderDto;

    @ApiOperation(value = "Creates an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void createOrder(@RequestBody List<OrderItem> items) throws ApiException {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderDto.createOrder(zonedDateTime, items);
    }

    @ApiOperation(value = "Shows all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderForm> showOrders(){
        return orderDto.showOrders();
    }








}