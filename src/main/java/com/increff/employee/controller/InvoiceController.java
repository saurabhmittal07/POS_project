package com.increff.employee.controller;

import com.increff.employee.service.ApiException;
import com.increff.employee.service.InvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.IOException;


@Api
@RestController
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    @ApiOperation(value = "Downloads Invoice")
    @RequestMapping(path = "/api/order/downloadInvoice/{id}", method = RequestMethod.GET)
    public HttpServletResponse createOrder(@PathVariable int id) throws ApiException, IOException, TransformerException {
        return invoiceService.getOrderInvoice(id);
    }
}
