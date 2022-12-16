package com.increff.employee.dto;

import com.increff.employee.model.*;
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

    @Autowired
    private OrderItemService orderItemService;

    public List<InventoryReport> showInventoryReport() throws ApiException {
        List<InventoryReport> inventoryReports = new ArrayList<>();

        List<InventoryPojo> inventoryPojos = inventoryService.showInventory();
        int i=0;

        // Key Brand Id, Value index of brand Id in reportList
        HashMap<Integer, Integer> brandIdToTableIndexMap = new HashMap<>();
        for(InventoryPojo inventoryPojo : inventoryPojos){
            ProductPojo productPojo = productService.getProduct(inventoryPojo.getProductId());
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(productPojo.getBrandCategory());

            //Check if brand-category page already exists in map
            if(brandIdToTableIndexMap.containsKey(brandCategoryPojo.getId())){
                int index = brandIdToTableIndexMap.get(brandCategoryPojo.getId());
                InventoryReport temp =  inventoryReports.get(index);
                temp.setQuantity(temp.getQuantity() + inventoryPojo.getCount());
                inventoryReports.set(index, temp);
                continue;
            }
            brandIdToTableIndexMap.put(brandCategoryPojo.getId(), i);
            InventoryReport inventoryReport = new InventoryReport();
            inventoryReport.setBrand(brandCategoryPojo.getBrand());
            inventoryReport.setCategory(brandCategoryPojo.getCategory());
            inventoryReport.setQuantity(inventoryPojo.getCount());
            inventoryReport.setSerialNo(++i);

            inventoryReports.add(inventoryReport);
        }
        return inventoryReports;
    }


    public List<BrandCategoryData> showBrandReport() {
        List<BrandCategoryPojo> brands=  brandCategoryService.getAllBrands();
        List<BrandCategoryData> brandDatas = new ArrayList<>();

        int i=1;
        for(BrandCategoryPojo brand : brands){
            BrandCategoryData brandData = new BrandCategoryData();
            brandData.setBrand(brand.getBrand());
            brandData.setCategory(brand.getCategory());
            brandData.setId(i++);
            brandDatas.add(brandData);

        }
        return brandDatas;
    }


    public List<ReportItem> showRevenueReport( FilterForm filterForm) throws ApiException {

        HashMap<Integer, Pair<Integer,Double>> brandIdToRevenueMap = new HashMap<>();

        // Get Order in date range
        List<OrderPojo> orders = orderService.getOrdersByDate(filterForm.getStartDate(), filterForm.getEndDate());

        // Go through all orders
        for(OrderPojo orderPojo : orders){

            // Get orderItems of orders
            List<OrderItemPojo> orderItems = orderItemService.getOrderItems(orderPojo.getId());

            // Go through all orderItems
            for(OrderItemPojo orderItemPojo : orderItems){
                addOrderItemIntoRevenueReport(orderItemPojo,brandIdToRevenueMap,filterForm.getBrand(),filterForm.getCategory());
            }
        }
        return populateList(brandIdToRevenueMap);
    }

    void addOrderItemIntoRevenueReport(OrderItemPojo orderItemPojo, HashMap<Integer, Pair<Integer,Double>> brandIdToRevenueMap, String brand, String category ) throws ApiException {
        // Get Product with product Id
        ProductPojo productPojo = productService.getProduct(orderItemPojo.getProductId());
        int brandId = productPojo.getBrandCategory();

        Pair<Integer, Double> pair = new Pair<>(orderItemPojo.getQuantity(),
                orderItemPojo.getQuantity()*orderItemPojo.getPrice());

        // Get Brand & Category by brandId
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.getBrand(brandId);

        //Check if brand category match with value in filter form
        if((brandCategoryPojo.getBrand().equals(brand)  || brand.equals("")) &&
                (brandCategoryPojo.getCategory().equals(category)   || category.equals("") )){
            if(brandIdToRevenueMap.containsKey(brandId)){
                int newQuantity = brandIdToRevenueMap.get(brandId).getKey() + pair.getKey();
                Double newRevenue = brandIdToRevenueMap.get(brandId).getValue() + pair.getValue();

                brandIdToRevenueMap.put(brandId , new Pair<>(newQuantity,newRevenue));
            }else{
                brandIdToRevenueMap.put(brandId, pair);
            }
        }
    }


    private List<ReportItem> populateList(HashMap<Integer, Pair<Integer,Double>> brandIdToRevenueMap) throws ApiException {
        List<ReportItem> items = new ArrayList<>();
        for (Map.Entry<Integer,Pair<Integer,Double>> mapElement : brandIdToRevenueMap.entrySet()) {
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
