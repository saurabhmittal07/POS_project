package com.increff.employee.dto;

import com.increff.employee.model.Inventory;
import com.increff.employee.model.Order;
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
        List<OrderItemPojo> orderItemPojos = new ArrayList<>();
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

            orderItemPojos.add(orderItemPojo);
        }
        // Add order items
        orderItemService.addOrderItem(orderItemPojos);
    }


    public List<Order> showOrders(){
        List<OrderPojo> orderPojos = orderService.showOrders();
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


    public void updateInventory(UpdateOrderForm updateOrderForm) throws ApiException{
        ProductPojo productPojo = productService.getProductByBarcode(updateOrderForm.getBarcode());
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());

        if (inventoryPojo.getCount() < updateOrderForm.getQuantity()) {
            throw new ApiException(inventoryPojo.getCount()+" Unit/units available in inventory");
        }
    }



    public double getMrp(String barcode , String quantity) throws ApiException{
        int cur = Integer.parseInt(quantity);
        validate(barcode, cur);

        ProductPojo productPojo = productService.getProductByBarcode(barcode);
        // Check if required quantity available
        InventoryPojo inventoryPojo = inventoryService.getInventoryByProductId(productPojo.getId());


        if(inventoryPojo == null){
            throw new ApiException(0 + " Unit/units available in inventory");
        } else if(inventoryPojo.getCount() < cur){
            throw new ApiException(inventoryPojo.getCount() + " Unit/units available in inventory");
        }

        return  productPojo.getMrp();
    }

    private void validate(String barcode , int quantity) throws ApiException{
        if( barcode.equals("")){
            throw new ApiException("Please enter barcode");
        }
        if(quantity <= 0){
            throw new ApiException("Quantity should be more than 0");
        }
        ProductPojo productPojo = productService.getProductByBarcode(barcode);

        if(productPojo == null){
            throw new ApiException("Product with barcode:" + barcode +" does not exist");
        }
    }

}
