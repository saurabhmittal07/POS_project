package com.increff.employee.service;

import javax.transaction.Transactional;

import com.increff.employee.pojo.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.ZonedDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QaConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration("src/test/webapp")
@Transactional
public abstract class AbstractUnitTest {
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
        BrandCategoryPojo brandCategory = new BrandCategoryPojo();
        brandCategory.setBrand(brand);
        brandCategory.setCategory(category);
        return brandCategoryService.addBrand(brandCategory);
    }

    public ProductPojo createProduct() throws ApiException{
        createBrand();
        return createProduct("milton", 1, "qqq", 43);
    }

    ProductPojo createProduct(String name, int brandId, String barcode, int mrp) throws ApiException{

        ProductPojo product = new ProductPojo();
        product.setName(name);
        product.setBarcode(barcode);
        product.setMrp(mrp);
        product.setBrandCategory(brandId);

        return productService.addProduct(product);
    }

    @Transactional
    InventoryPojo addInventory() throws ApiException {
        ProductPojo productPojo = createProduct();
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setProductId(productPojo.getId());
        inventoryPojo.setCount(5);

        return inventoryService.addInventory(inventoryPojo);
    }

    OrderPojo createOrder() throws ApiException {
        addInventory();
        OrderPojo orderPojo = new OrderPojo();
        orderPojo.setDateTime(ZonedDateTime.now());
        orderService.createOrder(orderPojo);
        return orderPojo;
    }

    int createOrderItem() throws ApiException {
        OrderPojo orderPojo = createOrder();
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setPrice(100);
        orderItemPojo.setOrderId(orderPojo.getId());
        orderItemPojo.setQuantity(12);
        orderItemService.addOrderItem(orderItemPojo);
        return orderItemPojo.getOrderId();
    }
}
