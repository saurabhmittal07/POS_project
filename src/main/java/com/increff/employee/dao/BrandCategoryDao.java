package com.increff.employee.dao;


import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import com.increff.employee.pojo.EmployeePojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class BrandCategoryDao extends AbstractDao{


    private static String select_all = "select p from BrandCategoryPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(BrandCategory p)  {

        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand(p.getBrand());
        brandCategoryPojo.setCategory(p.getCategory());
        System.out.println (brandCategoryPojo);
        em.persist(brandCategoryPojo);
    }

    @Transactional
    public List<BrandCategoryPojo> getAllBrands(){
        TypedQuery<BrandCategoryPojo> query = getQuery(select_all, BrandCategoryPojo.class);
        return query.getResultList();
    }





}
