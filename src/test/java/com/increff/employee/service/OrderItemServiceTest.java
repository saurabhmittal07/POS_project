package com.increff.employee.service;

import com.increff.employee.pojo.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemServiceTest extends AbstractUnitTest{
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderItemService orderItemService;


    public BrandCategoryPojo createBrand() throws ApiException {
        return createBrand("nike","shoes");
    }

    public BrandCategoryPojo createBrand(String brand, String category) throws ApiException{
        BrandCategoryPojo brandCategory = new BrandCategoryPojo(brand,category);
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

    private int createOrderItem() throws ApiException {
        OrderPojo orderPojo = createOrder();
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setPrice(100);
        orderItemPojo.setOrderId(orderPojo.getId());
        orderItemPojo.setQuantity(12);
        orderItemService.addOrderItem(orderItemPojo);
        return orderItemPojo.getOrderId();
    }

    @Test
    public void testAddOrderItem() throws ApiException {
        createOrderItem();
    }

    @Test
    public void testGetReceipt() throws ApiException{
       int orderId = createOrderItem();
        List<OrderItemPojo> list = orderItemService.getOrderItems(orderId);
        assertEquals(1,list.size());
        assertEquals(12,list.get(0).getQuantity());
    }
}
