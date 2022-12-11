package com.increff.employee.dto;

import com.increff.employee.model.OrderItemDataList;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.util.InvoiceHelper;
import org.apache.fop.apps.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class InvoiceDto {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    private final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    public ResponseEntity<byte[]> getOrderInvoice( @PathVariable int id) throws ApiException, IOException, TransformerException {

        List<OrderItemPojo> orderItemPojoList = orderItemService.getReceipt(id);
        OrderPojo orderPojo = orderService.getOrder(id);

        ZonedDateTime time = orderPojo.getDateTime();


        return InvoiceHelper.invoiceHelper(orderItemPojoList,orderPojo);
    }

}
