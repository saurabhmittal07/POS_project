package com.increff.employee.controller;

import com.increff.employee.model.FilterForm;
import com.increff.employee.model.InventoryReport;
import com.increff.employee.model.Product;
import com.increff.employee.model.ReportItem;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "Shows Inventory Report")
    @RequestMapping(path = "/api/report/inventory", method = RequestMethod.GET)
    public List<InventoryReport> showInventoryReport() {
        return reportService.showInventoryReport();
    }

    @ApiOperation(value = "Shows Brand Report")
    @RequestMapping(path = "/api/report/brand", method = RequestMethod.GET)
    public List<BrandCategoryPojo> showBrandReport() {
        return reportService.showBrandReport();
    }

    @ApiOperation(value = "Shows Filtered Report")
    @RequestMapping(path = "/api/report", method = RequestMethod.POST)
    public List<ReportItem> showReport(@RequestBody FilterForm filterForm) throws ApiException{
        return reportService.showReport(filterForm);
    }
}
