package com.increff.employee.service;


import com.increff.employee.dao.*;
import com.increff.employee.model.FilterForm;
import com.increff.employee.model.InventoryReport;
import com.increff.employee.model.ReportItem;
import com.increff.employee.pojo.*;

import io.swagger.models.auth.In;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class ReportService {

    @Autowired
    private InventoryDao inventoryDao ;
    @Autowired
    private ProductDao productDao ;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private BrandCategoryDao brandCategoryDao ;

    public List<InventoryReport> showInventoryReport(){

        List<InventoryReport> inventoryReports = new ArrayList<>();

        List<InventoryPojo> inventoryPojos = inventoryDao.showInventory();
        int i=0;

        HashMap<Integer, Integer> map = new HashMap<>();
        for(InventoryPojo inventoryPojo : inventoryPojos){
            ProductPojo productPojo = productDao.getProduct(inventoryPojo.getProductId());
            BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(productPojo.getBrandCategory());

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

    public List<BrandCategoryPojo> showBrandReport(){
        return brandCategoryDao.getAllBrands();
    }

    public List<ReportItem> showReport(FilterForm filterForm){

        HashMap<Integer, Pair<Integer,Double>> map = new HashMap<>();
        // Get Order in date range

        List<OrderPojo> orders = orderDao.ordersByDate(filterForm.getStartDate(), filterForm.getEndDate());

        // Go through all orders
        for(OrderPojo orderPojo : orders){

            // Get orderItems of orders
            List<OrderItemPojo> orderItems = orderDao.orderReciept(orderPojo.getId());

            // Go through all orderItems
            for(OrderItemPojo orderItemPojo : orderItems){

                // Get Product with product Id
                ProductPojo productPojo = productDao.getProduct(orderItemPojo.getProductId());
                int brandId = productPojo.getBrandCategory();

                Pair<Integer, Double> pair = new Pair<>(orderItemPojo.getQuantity(),
                        orderItemPojo.getQuantity()*orderItemPojo.getPrice());

                // Get Brand & Category by brandId
                BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(brandId);
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
            BrandCategoryPojo brandCategoryPojo = brandCategoryDao.getBrand(brandId);
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
