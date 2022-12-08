package com.increff.employee.dto;

import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InvoiceService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Service
public class InvoiceDto {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;


    public HttpServletResponse getOrderInvoice(@PathVariable int id) throws ApiException, IOException, TransformerException {
        List<OrderItemPojo> orderItemPojos = orderService.getReceipt(id);
        OrderPojo orderPojo = orderService.getOrder(id);
        return invoiceService.getOrderInvoice(orderItemPojos, orderPojo);
    }
}
