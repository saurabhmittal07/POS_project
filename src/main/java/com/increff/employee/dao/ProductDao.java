package com.increff.employee.dao;
import com.increff.employee.model.Product;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDao extends AbstractDao{

    private static String delete_id = "delete from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";
    private static String select_id = "select p from ProductPojo p where id=:id";

    private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";



    private static String update_id = "update ProductPojo set barcode=:barcode, " +
            "mrp=:mrp, name=:name, " +
            "brandCategory=:brandCategory " +
            "where id=:id";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void add(Product product) throws ApiException {
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

    @Transactional
    public void deleteProduct(int id){
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Transactional
    public ProductPojo getProduct(int id){
        TypedQuery<ProductPojo > query = getQuery(select_id, ProductPojo .class);
        query.setParameter("id", id);
        return getSingle(query);
    }


    @Transactional
    public ProductPojo getProductByBarcode(String barcode){
        TypedQuery<ProductPojo > query = getQuery(select_barcode, ProductPojo .class);
        query.setParameter("barcode", barcode);
        return query.getResultList().stream().findFirst().orElse(null);

    }


    @Transactional
    public void updateProduct(int id, Product product){
        Query query = em.createQuery(update_id);
        query.setParameter("id", id);
        query.setParameter("brandCategory", product.getBrandCategory());
        query.setParameter("barcode", product.getBarcode());
        query.setParameter("mrp", product.getMrp());
        query.setParameter("name", product.getName());
        query.executeUpdate();
    }
}
