package com.increff.employee.dto;

import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItem;
import com.increff.employee.model.UpdateOrderForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.TrimLower;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryService inventoryService;



    @Transactional
    public void createOrder(ZonedDateTime zonedDateTime,List<OrderItem> items) throws ApiException {

        //Create Order
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDateTime(zonedDateTime);
        orderService.createOrder(orderPojo);

        //Change OrderItems to OrderItemPojos

        for(OrderItem orderItem: items){
            TrimLower.trimLower(orderItem);
            ProductPojo productPojo = productService.getProductByBarcode(orderItem.getBarcode());

            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setQuantity(orderItem.getQuantity());
            orderItemPojo.setOrderId(orderPojo.getId());
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setPrice(productPojo.getMrp());

            // Reduce Inventory
            InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());
            inventoryPojo.setCount(inventoryPojo.getCount() - orderItem.getQuantity());


            orderItemService.addOrderItem(orderItemPojo);
        }
        // Add order items

    }


    public List<OrderForm> showOrders(){
        List<OrderPojo> orderPojos = orderService.showOrders();
        List<OrderForm> orders =  new ArrayList();

        for(OrderPojo orderPojo : orderPojos){
            OrderForm order = new OrderForm();
            order.setId(orderPojo.getId());

            ZonedDateTime zonedDateTime = orderPojo.getDateTime();
            String formattedZdt = zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            order.setDate(formattedZdt.substring(0,10) + " " + formattedZdt.substring(11,19));

            orders.add(order);
        }
        return orders;
    }
}
