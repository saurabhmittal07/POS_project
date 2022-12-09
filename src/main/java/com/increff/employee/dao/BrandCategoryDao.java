package com.increff.employee.dao;


import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandCategoryDao extends AbstractDao{



    private static String select_all = "select p from BrandCategoryPojo p";
    private static String select_id = "select p from BrandCategoryPojo p where id=:id";
    private static String select_by_name = "select p from BrandCategoryPojo p where brand=:brand AND category=:category";




    @Transactional
    public BrandCategoryPojo addBrand(BrandCategoryPojo brandCategoryPojo)  {
        em().persist(brandCategoryPojo);
        return brandCategoryPojo;
    }

    @Transactional
    public List<BrandCategoryPojo> getAllBrands(){
        TypedQuery<BrandCategoryPojo> query = getQuery(select_all, BrandCategoryPojo.class);
        return query.getResultList();
    }

    @Transactional
    public BrandCategoryPojo getBrand(int id){
        TypedQuery<BrandCategoryPojo> query = getQuery(select_id, BrandCategoryPojo.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findFirst().orElse(null);
    }


    @Transactional
    public BrandCategoryPojo getBrandByName(String brand, String category){
        TypedQuery<BrandCategoryPojo> query = getQuery(select_by_name, BrandCategoryPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return query.getResultList().stream().findFirst().orElse(null);
    }

}
