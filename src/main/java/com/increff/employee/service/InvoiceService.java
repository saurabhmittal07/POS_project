package com.increff.employee.service;


import com.increff.employee.model.OrderItemDataList;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.apache.fop.apps.FopFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.fop.apps.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;

@Service
public class InvoiceService {


    private final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
    @Transactional(rollbackOn = ApiException.class)
    public String getOrderInvoice( List<OrderItemPojo> orderItemPojoList, OrderPojo orderPojo) throws ApiException, IOException, TransformerException {

        ZonedDateTime time = orderPojo.getDateTime();
        double total = 0.;

        for (OrderItemPojo itemPojo : orderItemPojoList) {
            total += itemPojo.getQuantity() * itemPojo.getPrice();
        }
        OrderItemDataList orderItemDataList = new OrderItemDataList(orderItemPojoList, time, total, orderPojo.getId());
        String invoice="main/webapp/Invoices/invoice"+orderPojo.getId()+".pdf";
        String xml = javaObjectToXml(orderItemDataList);
        File xsltFile = new File("src", "main/webapp/invoice.xml");
        File pdfFile = new File("src", invoice);
        convertToPDF(orderItemDataList, xsltFile, pdfFile, xml);
        return "http://localhost:9000/pos/Invoices/invoice"+orderPojo.getId()+".pdf";
//        File file=new File("src/main/resources/Invoices/invoice"+orderPojo.getId()+".pdf");
//        if (file.exists()) {
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//            response.setContentLength((int) file.length());
//            InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()));
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//        }
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
