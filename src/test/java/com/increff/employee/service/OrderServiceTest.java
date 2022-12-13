package com.increff.employee.service;

import com.increff.employee.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest{

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;


    @Test
    public void testCreateOrder() throws ApiException {
        createOrder();
    }

    @Test
    public void testShowOrders() throws ApiException {
        createOrder();
        List<OrderPojo> orderPojos = orderService.showOrders();
        assertEquals(1,orderPojos.size());
    }

    @Test
    public void testGetOrder() throws ApiException{
        OrderPojo orderPojo =  createOrder();
        OrderPojo newOrderPojo = orderService.getOrder(orderPojo.getId());
        assertEquals(orderPojo.getDateTime(),newOrderPojo.getDateTime());
    }

    @Test
    public void testGetOrderByDate() throws ApiException{

        createOrder();
        String preTime = "2021-12-12";
        String endTime = "2080-12-12";

        List<OrderPojo> orderPojos = orderService.getOrdersByDate(preTime, endTime);
        assertEquals(1,orderPojos.size());
    }

}
