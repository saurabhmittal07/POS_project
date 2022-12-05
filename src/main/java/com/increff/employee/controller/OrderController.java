package com.increff.employee.controller;

import com.increff.employee.model.Order;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.UpdateOrderForm;
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
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderService.createOrder(zonedDateTime, items);
    }

    @ApiOperation(value = "Shows all orders")
    @RequestMapping(path = "/api/order", method = RequestMethod.GET)
    public List<Order> showOrders(){
        return orderService.showOrders();
    }



    @ApiOperation(value = "Receipt of order of given  id")
    @RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
    public List<OrderItemPojo> orderReciept(@PathVariable int id) throws ApiException{
        return orderService.orderReciept(id);
    }

    @ApiOperation(value = "updates quantity of a product")
    @RequestMapping(path = "/api/order/updateInventory", method = RequestMethod.PUT)
    public void updateInventory(@RequestBody UpdateOrderForm updateOrderForm) throws ApiException{
            orderService.updateInventory(updateOrderForm);
    }


    @ApiOperation(value = "Check if required invenotry available or not")
    @RequestMapping(path = "/api/order/inventoryExist/{barcode}/{cur}", method = RequestMethod.GET)
    public double inventoryExist(@PathVariable String barcode ,@PathVariable String cur) throws ApiException{
        return orderService.inventoryExist(barcode , cur);
    }


}