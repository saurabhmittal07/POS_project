package com.increff.employee.service;


import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.Order;
import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private OrderItemDao orderItemDao;

    public void createOrder(ZonedDateTime zonedDateTime, List<OrderItem> orderItems){
        orderDao.createOrder(zonedDateTime);

         // Get Order Id;
        int orderId = getOrderId();

        for(OrderItem orderItem : orderItems){
            // Get Product Id
           ProductPojo product = getProduct(orderItem.getBarcode());

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

    // Return ProductId by barcode
    public ProductPojo getProduct(String barcode){
        List<ProductPojo> products = productDao.getAllProducts();
        for(ProductPojo product : products) {
            if (product.getBarcode().equals(barcode)) {
                return product;
            }
        }
        return new ProductPojo();
    }


}
