package com.increff.employee.dao;


import com.increff.employee.model.BrandCategory;
import com.increff.employee.pojo.BrandCategoryPojo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class BrandCategoryDao {


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(BrandCategory p) {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand(p.getBrand());
        brandCategoryPojo.setCategory(p.getCategory());
        System.out.println (brandCategoryPojo);
        em.persist(brandCategoryPojo);
    }


}
