package com.increff.employee.dto;

import com.increff.employee.model.OrderItemDataList;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
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
        double total = 0.;

        for (OrderItemPojo itemPojo : orderItemPojoList) {
            total += itemPojo.getQuantity() * itemPojo.getPrice();
        }

        // Convert XML to PDF
        OrderItemDataList orderItemDataList = new OrderItemDataList(orderItemPojoList, time, total, orderPojo.getId());
        String invoice="main/webapp/Invoices/invoice"+orderPojo.getId()+".pdf";
        String xml = javaObjectToXml(orderItemDataList);
        File xsltFile = new File("src", "main/webapp/invoice.xml");
        File pdfFile = new File("src", invoice);
        convertToPDF(orderItemDataList, xsltFile, pdfFile, xml);


        // Change PDF to byte Array
        byte[] contents = loadFile("src/main/webapp/Invoices/invoice"+id+".pdf");

        // Send byte Array in response
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

    private static String javaObjectToXml(OrderItemDataList orderItemList) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderItemDataList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(orderItemList, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void convertToPDF(OrderItemDataList team, File xslt, File pdf, String xml)
            throws IOException, TransformerException {
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = Files.newOutputStream(pdf.toPath());
        out = new java.io.BufferedOutputStream(out);
        try {
            Fop fop = null;
            try {
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            } catch (FOPException e) {
                throw new RuntimeException(e);
            }
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));
            Source src = new StreamSource(new StringReader(xml));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (FOPException e) {
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
    }


}
