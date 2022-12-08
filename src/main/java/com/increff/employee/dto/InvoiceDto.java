package com.increff.employee.dto;

import com.increff.employee.model.OrderItem;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InvoiceService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.List;

@Service
public class InvoiceDto {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;


    public ResponseEntity<byte[]> getOrderInvoice( @PathVariable int id) throws ApiException, IOException, TransformerException {

        List<OrderItemPojo> orderItemPojos = orderService.getReceipt(id);
        OrderPojo orderPojo = orderService.getOrder(id);
        invoiceService.getOrderInvoice( orderItemPojos, orderPojo);

        byte[] contents = loadFile("src/main/webapp/Invoices/invoice"+id+".pdf");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "invoice"+id+".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;

    }
    public static byte[] readFully(InputStream stream) throws IOException
    {
        byte[] buffer = new byte[8192];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1)
        {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public static byte[] loadFile(String sourcePath) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(sourcePath);
            return readFully(inputStream);
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }


}
