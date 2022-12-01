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


    private static String delete_id = "delete from BrandCategoryPojo p where id=:id";
    private static String select_all = "select p from BrandCategoryPojo p";
    private static String select_id = "select p from BrandCategoryPojo p where id=:id";
    private static String update_id = "update BrandCategoryPojo set brand=:brand, category=:category where id=:id";




    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(BrandCategory p)  {

        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand(p.getBrand());
        brandCategoryPojo.setCategory(p.getCategory());

        em.persist(brandCategoryPojo);
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
        return getSingle(query);
    }

    @Transactional
    public int deleteBrand(int id){
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Transactional
    public void updateBrand(int id, BrandCategory brandCategory){
        Query query = em.createQuery(update_id);
        query.setParameter("id", id);
        query.setParameter("brand", brandCategory.getBrand());
        query.setParameter("category", brandCategory.getCategory());
        query.executeUpdate();
    }

}
