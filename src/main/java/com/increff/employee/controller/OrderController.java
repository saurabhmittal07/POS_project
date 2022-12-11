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



    @ApiOperation(value = "updates quantity of a product")
    @RequestMapping(path = "/api/order/updateInventory", method = RequestMethod.PUT)
    public void updateInventory(@RequestBody UpdateOrderForm updateOrderForm) throws ApiException{
            orderDto.updateInventory(updateOrderForm);
    }


    @ApiOperation(value = "Check if required inventory available or not")
    @RequestMapping(path = "/api/order/inventoryExist/{barcode}/{cur}", method = RequestMethod.GET)
    public double getMrp(@PathVariable String barcode ,@PathVariable String cur) throws ApiException{
        return orderDto.getMrp(barcode , cur);
    }


}