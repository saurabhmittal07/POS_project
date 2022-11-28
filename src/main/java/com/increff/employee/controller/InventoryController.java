package com.increff.employee.controller;
import com.increff.employee.model.Inventory;
import com.increff.employee.model.Product;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation(value = "Adds a product to Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void addProduct(@RequestBody Inventory inventory) throws ApiException {
        inventoryService.add(inventory);
    }

    @ApiOperation(value = "Show Inventory" )
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryPojo> getAllProducts(){
        return inventoryService.showInventory();
    }


    @ApiOperation(value = "Updates a inventory detail")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody Inventory inventory) throws ApiException {
        inventoryService.updateInventory(id, inventory);
    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryPojo getProduct(@PathVariable int id) throws ApiException {
        return inventoryService.getInventory(id);
    }

}
