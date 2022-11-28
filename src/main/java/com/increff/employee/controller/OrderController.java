package com.increff.employee.controller;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.type.ZonedDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;

@Api
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Creates an order")
    @RequestMapping(path = "/api/order", method = RequestMethod.POST)
    public void createOrder() throws ApiException {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderService.createOrder(zonedDateTime);
    }

    @ApiOperation(value = "Shows all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<OrderPojo> showOrders(){
        return orderService.showorders();
    }

}
