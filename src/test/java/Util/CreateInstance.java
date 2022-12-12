package Util;

import com.increff.employee.pojo.*;
import com.increff.employee.service.*;
import org.hibernate.annotations.Tuplizers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestConstructor;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateInstance extends AbstractUnitTest {

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



}
