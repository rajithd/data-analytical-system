package com.rd.executor.repository.impl;

import com.rd.domain.analytics.VisitedProductAnalytic;
import com.rd.executor.repository.HiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class HiveDaoImpl implements HiveDao {

    private static final String VISITED_PRODUCTS = "SELECT v.customer_id,c.name as cname,COUNT(v.product_id),p.product_category_id,p.name as pname,p.product_id " +
            "FROM visited_product v,product p,customer c " +
            "WHERE v.product_id=p.product_id AND c.customer_id=v.customer_id " +
            "GROUP by v.customer_id,c.name,p.product_category_id,p.name,p.product_id";

    private static final String SEARCH_STRING = "SELECT DISTINCT p.name as pname, s.customer_id, c.name as cname, p.product_id " +
            "FROM search_criteria s,product p, customer c " +
            "WHERE s.product_category_id=p.product_category_id AND c.customer_id = s.customer_id ORDER BY cname";

    private static final String CATEGORY_CRITERIA = "SELECT c.name as cname,pc.category_name,v.customer_id,p.product_category_id,p.name as pname,p.product_id " +
            "FROM visited_product v,product p,customer c,product_category pc " +
            "WHERE v.product_id=p.product_id AND c.customer_id=v.customer_id AND pc.product_category_id=p.product_category_id " +
            "GROUP by v.customer_id,c.name,p.product_category_id,p.name,p.product_id,pc.category_name";

    private static final String PURCHASE_CRITERIA = "select t.customer_id,p.product_category_id,pc.category_name,c.name\n" +
            "from transaction t, product p,product_category pc, customer c\n" +
            "where t.product_id = p.product_id AND p.brand_new=true AND pc.product_category_id=p.product_category_id AND c.customer_id=t.customer_id";

    @Autowired
    private JdbcTemplate hiveJdbcTemplate;

    @Override
    public List<VisitedProductAnalytic> getVisitedProducts() throws SQLException {
        return hiveJdbcTemplate.query(VISITED_PRODUCTS, (resultSet, i) -> {
            VisitedProductAnalytic vp = new VisitedProductAnalytic();
            vp.setCustomerId(resultSet.getLong("v.customer_id"));
            vp.setProductId(resultSet.getLong("p.product_id"));
            vp.setProductName(resultSet.getString("pname"));
            vp.setCustomerName(resultSet.getString("cname"));
            return vp;
        });

    }

    @Override
    public List<VisitedProductAnalytic> getSearchProducts() throws SQLException {
        return hiveJdbcTemplate.query(SEARCH_STRING, (resultSet, i) -> {
            VisitedProductAnalytic vp = new VisitedProductAnalytic();
            vp.setCustomerId(resultSet.getLong("s.customer_id"));
            vp.setProductId(resultSet.getLong("p.product_id"));
            vp.setProductName(resultSet.getString("pname"));
            vp.setCustomerName(resultSet.getString("cname"));
            return vp;
        });
    }

    @Override
    public List<VisitedProductAnalytic> getProductsByCategory() throws SQLException {
        return hiveJdbcTemplate.query(CATEGORY_CRITERIA, (resultSet, i) -> {
            VisitedProductAnalytic vp = new VisitedProductAnalytic();
            vp.setCustomerId(resultSet.getLong("v.customer_id"));
            vp.setProductId(resultSet.getLong("p.product_id"));
            vp.setProductName(resultSet.getString("pname"));
            vp.setCustomerName(resultSet.getString("cname"));
            vp.setCategoryName(resultSet.getString("pc.category_name"));
            return vp;
        });
    }

    @Override
    public List<VisitedProductAnalytic> getProductsByPurchase() throws SQLException {
        return hiveJdbcTemplate.query(PURCHASE_CRITERIA, (resultSet, i) -> {
            VisitedProductAnalytic vp = new VisitedProductAnalytic();
            vp.setCustomerId(resultSet.getLong("t.customer_id"));
            vp.setCustomerName(resultSet.getString("c.name"));
            vp.setCategoryName(resultSet.getString("pc.category_name"));
            return vp;
        });
    }
}
