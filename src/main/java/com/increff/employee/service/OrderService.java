package com.increff.employee.service;


import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Order;
import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.sun.org.apache.xpath.internal.operations.Or;
import io.swagger.models.auth.In;
import org.hibernate.dialect.identity.Oracle12cGetGeneratedKeysDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private OrderItemDao orderItemDao;


    @Transactional
    public void createOrder(ZonedDateTime zonedDateTime, List<OrderItem> orderItems){
        orderDao.createOrder(zonedDateTime);

         // Get Order Id;
        int orderId = getOrderId();

        for(OrderItem orderItem : orderItems){
            // Get Product
           ProductPojo product = productDao.getProductByBarcode(orderItem.getBarcode());

           // Reduce inventory
            InventoryPojo inventoryPojo = inventoryDao.getInventoryByProductId(product.getId());
            inventoryPojo.setCount(inventoryPojo.getCount() - orderItem.getQuantity());

            OrderItemPojo orderItemPojo = new OrderItemPojo();
            orderItemPojo.setOrderId(orderId);
            orderItemPojo.setQuantity(orderItem.getQuantity());
            orderItemPojo.setProductId(product.getId());
            orderItemPojo.setPrice(product.getMrp());
            orderItemDao.addOrderItem(orderItemPojo);


        }

    }

    public List<Order> showOrders(){
        List<OrderPojo> orderPojos =  orderDao.showOrders();

        List<Order> orders =  new ArrayList();

        for(OrderPojo orderPojo : orderPojos){
            Order order = new Order();
            order.setId(orderPojo.getId());

            ZonedDateTime zonedDateTime = orderPojo.getDateTime();
            String formattedZdt = zonedDateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            order.setDate(formattedZdt.substring(0,10) + " " + formattedZdt.substring(11,19));

            orders.add(order);
        }
        return orders;
    }

    @Transactional
    public double inventoryExist(OrderItem orderItem) throws ApiException{

        // Check if barcode exist

        ProductPojo productPojo = productDao.getProductByBarcode(orderItem.getBarcode());

        if(productPojo == null){
            throw new ApiException("Product with barcode:" + orderItem.getBarcode() +" does not exist");
        }
        // Check if required quantity available
        InventoryPojo inventoryPojo = inventoryDao.getInventoryByProductId(productPojo.getId());

        if(inventoryPojo == null){
            throw new ApiException(0 + " Unit/units available in inventory");
        } else if(inventoryPojo.getCount() < orderItem.getQuantity()){
            throw new ApiException(inventoryPojo.getCount() + " Unit/units available in inventory");
        }

        // Reduce invenotory
        inventoryPojo.setCount(inventoryPojo.getCount() - orderItem.getQuantity());

        return  productPojo.getMrp();
    }

    // Return Order Id of current order
    public int getOrderId(){
        List<Order> orders = showOrders();
        int maxId = 1;
        for(Order order : orders){
            if(order.getId() > maxId){
                maxId = order.getId();
            }
        }
        return maxId;
    }



}
