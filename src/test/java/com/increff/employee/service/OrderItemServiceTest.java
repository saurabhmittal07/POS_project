package com.increff.employee.service;

import com.increff.employee.pojo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemServiceTest extends AbstractUnitTest{

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderItemService orderItemService;
    @Test
    public void testAddOrderItem() throws ApiException {
        createOrderItem();
    }

    @Test
    public void testGetReceipt() throws ApiException{
       int orderId = createOrderItem();
        List<OrderItemPojo> list = orderItemService.getOrderItems(orderId);
        assertEquals(1,list.size());
        assertEquals(12,list.get(0).getQuantity());
    }
}
