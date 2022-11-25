package com.increff.employee.dao;

import com.increff.employee.model.Product;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{


    private static String delete_id = "delete from BrandCategoryPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";
    private static String select_id = "select p from BrandCategoryPojo p where id=:id";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(Product product){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(product.getName());
        productPojo.setBarcode(product.getBarcode());
        productPojo.setBrandCategory(product.getBrandCategory());
        productPojo.setMrp(product.getMrp());

        em.persist(productPojo);
    }

    @Transactional
    public List<ProductPojo> getAllProducts(){
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }
}
