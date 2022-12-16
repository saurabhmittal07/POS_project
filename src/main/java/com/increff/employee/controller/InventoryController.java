package com.increff.employee.controller;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds a product to Inventory")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
    public void addInventory(@RequestBody InventoryForm inventory) throws ApiException {
        inventoryDto.addInventory(inventory);
    }

    @ApiOperation(value = "Show Inventory" )
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAllProducts() throws ApiException {
        return inventoryDto.showInventory();
    }


    @ApiOperation(value = "Updates a inventory detail")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm inventory) throws ApiException {
        inventoryDto.updateInventory(id, inventory);
    }

    @ApiOperation(value = "Gets an inventory by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData getProduct(@PathVariable int id) throws ApiException {
        return inventoryDto.getInventory(id);
    }

    @ApiOperation(value = "updates quantity of a product")
    @RequestMapping(path = "/api/order/updateInventory", method = RequestMethod.PUT)
    public void checkIfInventoryAvailable(@RequestBody InventoryForm inventoryForm) throws ApiException{
         inventoryDto.isInventoryAvailable(inventoryForm);
    }
}
