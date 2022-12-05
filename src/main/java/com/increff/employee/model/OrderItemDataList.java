package com.increff.employee.model;



import com.increff.employee.pojo.OrderItemPojo;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@XmlRootElement
public class OrderItemDataList {
    private Integer orderId;
    private Double total;
    private String time;
    private List<OrderItemPojo> orderItems;
    private String invoiceTime;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<OrderItemPojo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemPojo> orderItems) {
        this.orderItems = orderItems;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public OrderItemDataList(){
    }

    public OrderItemDataList(List<OrderItemPojo> orderItemPojoList, ZonedDateTime time, Double total, Integer orderId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z");
        this.time=time.format(formatter);
        this.total=total;
        this.orderId=orderId;
        this.invoiceTime=ZonedDateTime.now().format(formatter);
        orderItems =orderItemPojoList;

    }
}