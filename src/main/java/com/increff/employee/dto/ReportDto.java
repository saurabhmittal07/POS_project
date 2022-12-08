package com.increff.employee.dto;

import com.increff.employee.model.BrandCategory;
import com.increff.employee.model.FilterForm;
import com.increff.employee.model.InventoryReport;
import com.increff.employee.model.ReportItem;
import com.increff.employee.pojo.*;
import com.increff.employee.service.*;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportDto {


    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private ProductService productService;

    public List<InventoryReport> showInventoryReport() {
        List<InventoryReport> inventoryReports = new ArrayList<>();

        List<InventoryPojo> inventoryPojos = inventoryService.showInventory();
        int i=0;

        HashMap<Integer, Integer> map = new HashMap<>();
        for(InventoryPojo inventoryPojo : inventoryPojos){
            ProductPojo productPojo = productService.getProduct(inventoryPojo.getProductId());
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());

            //Check if brand-category page already exists in map
            if(map.containsKey(brandCategoryPojo.getId())){
                int index = map.get(brandCategoryPojo.getId());
                InventoryReport temp =  inventoryReports.get(index);
                temp.setQuantity(temp.getQuantity() + inventoryPojo.getCount());
                inventoryReports.set(index, temp);
                continue;
            }
            map.put(brandCategoryPojo.getId(), i);
            InventoryReport inventoryReport = new InventoryReport();
            inventoryReport.setBrand(brandCategoryPojo.getBrand());
            inventoryReport.setCategory(brandCategoryPojo.getCategory());
            inventoryReport.setQuantity(inventoryPojo.getCount());
            inventoryReport.setSerialNo(++i);

            inventoryReports.add(inventoryReport);
        }
        return inventoryReports;
    }


    public List<BrandCategory> showBrandReport() {
        List<BrandCategoryPojo> brands=  brandCategoryService.getAllBrands();
        List<BrandCategory> brandDatas = new ArrayList<>();

        for(BrandCategoryPojo brand : brands){
            BrandCategory brandData = new BrandCategory();
            brandData.setBrand(brand.getBrand());
            brandData.setCategory(brand.getCategory());
            brandDatas.add(brandData);
        }
        return brandDatas;
    }


    public List<ReportItem> showReport(@RequestBody FilterForm filterForm) throws ApiException {
        HashMap<Integer, Pair<Integer,Double>> map = new HashMap<>();
        // Get Order in date range

        List<OrderPojo> orders = orderService.getOrdersByDate(filterForm.getStartDate(), filterForm.getEndDate());

        // Go through all orders
        for(OrderPojo orderPojo : orders){

            // Get orderItems of orders
            List<OrderItemPojo> orderItems = orderService.getReceipt(orderPojo.getId());

            // Go through all orderItems
            for(OrderItemPojo orderItemPojo : orderItems){

                // Get Product with product Id
                ProductPojo productPojo = productService.getProduct(orderItemPojo.getProductId());
                int brandId = productPojo.getBrandCategory();

                Pair<Integer, Double> pair = new Pair<>(orderItemPojo.getQuantity(),
                        orderItemPojo.getQuantity()*orderItemPojo.getPrice());

                // Get Brand & Category by brandId
                BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(brandId);
                if((brandCategoryPojo.getBrand().equals(filterForm.getBrand())  || filterForm.getBrand().equals("")) &&
                        (brandCategoryPojo.getCategory().equals(filterForm.getCategory())   || filterForm.getCategory().equals("") )){
                    if(map.containsKey(brandId)){
                        int newQuantity = map.get(brandId).getKey() + pair.getKey();
                        Double newRevenue = map.get(brandId).getValue() + pair.getValue();

                        map.put(brandId , new Pair<>(newQuantity,newRevenue));
                    }else{
                        map.put(brandId, pair);
                    }
                }
            }
        }
        return populateList(map);
    }

    private List<ReportItem> populateList(HashMap<Integer, Pair<Integer,Double>> map){
        List<ReportItem> items = new ArrayList<>();
        for (Map.Entry<Integer,Pair<Integer,Double>> mapElement : map.entrySet()) {
            int brandId = mapElement.getKey();
            Pair<Integer, Double> pair= mapElement.getValue();
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(brandId);
            ReportItem reportItem = new ReportItem();
            reportItem.setBrand(brandCategoryPojo.getBrand());
            reportItem.setCategory(brandCategoryPojo.getCategory());
            reportItem.setQuantity(pair.getKey());
            reportItem.setRevenue(pair.getValue());
            items.add(reportItem);
        }
        return items;
    }
}
