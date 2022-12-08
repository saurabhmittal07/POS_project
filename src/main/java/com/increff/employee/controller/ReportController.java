package com.increff.employee.controller;

import com.increff.employee.dto.ReportDto;
import com.increff.employee.model.*;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportController {

    @Autowired
    private ReportDto reportDto;

    @ApiOperation(value = "Shows Inventory Report")
    @RequestMapping(path = "/api/report/inventory", method = RequestMethod.GET)
    public List<InventoryReport> showInventoryReport() {
        return reportDto.showInventoryReport();
    }

    @ApiOperation(value = "Shows Brand Report")
    @RequestMapping(path = "/api/report/brand", method = RequestMethod.GET)
    public List<BrandCategory> showBrandReport() {
        return reportDto.showBrandReport();
    }

    @ApiOperation(value = "Shows Filtered Report")
    @RequestMapping(path = "/api/report", method = RequestMethod.POST)
    public List<ReportItem> showReport(@RequestBody FilterForm filterForm) throws ApiException{
        return reportDto.showReport(filterForm);
    }
}
