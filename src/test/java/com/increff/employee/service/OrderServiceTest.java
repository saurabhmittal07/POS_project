package com.increff.employee.service;

import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest{

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;


    public BrandCategoryPojo createBrand() throws ApiException {
        return createBrand("nike","shoes");
    }

    public BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo();
        brandCategory.setBrand(brand);
        brandCategory.setCategory(category);
        return brandCategoryService.addBrand(brandCategory);
    }

    private ProductPojo createProduct() throws ApiException{
        return createProduct("milton", 1, "qqq", 43);
    }

    private ProductPojo createProduct  (String name, int brandId, String barcode, int mrp) throws ApiException{
        createBrand();
        ProductPojo product = new ProductPojo();
        product.setName(name);
        product.setBarcode(barcode);
        product.setMrp(mrp);
        product.setBrandCategory(brandId);

        return productService.addProduct(product);
    }

    @Transactional
    private InventoryPojo addInventory() throws ApiException {
        ProductPojo productPojo = createProduct();
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setCount(5);

        return inventoryService.addInventory(inventoryPojo);
    }

    private OrderPojo createOrder() throws ApiException {
        addInventory();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDateTime(ZonedDateTime.now());
        orderService.createOrder(orderPojo);
        return orderPojo;
    }

    @Test
    public void testCreateOrder() throws ApiException {
        createOrder();
    }

    @Test
    public void testShowOrders() throws ApiException {
        createOrder();
        List<OrderPojo> orderPojos = orderService.showOrders();
        assertEquals(1,orderPojos.size());
    }

    @Test
    public void testGetOrder() throws ApiException{
        OrderPojo orderPojo =  createOrder();
        OrderPojo newOrderPojo = orderService.getOrder(orderPojo.getId());
        assertEquals(orderPojo.getDateTime(),newOrderPojo.getDateTime());
    }

    @Test
    public void testGetOrderByDate() throws ApiException{

        createOrder();
        String preTime = "2021-12-12";
        String endTime = "2080-12-12";

        List<OrderPojo> orderPojos = orderService.getOrdersByDate(preTime, endTime);
        assertEquals(1,orderPojos.size());
    }

}
