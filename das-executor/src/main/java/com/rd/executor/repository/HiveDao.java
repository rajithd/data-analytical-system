package com.rd.executor.repository;

import com.rd.domain.analytics.VisitedProductAnalytic;

import java.sql.SQLException;
import java.util.List;

public interface HiveDao {

    /**
     * Fetch most recent visited products
     *
     * @return {@link com.rd.domain.analytics.VisitedProductAnalytic}
     * @throws SQLException
     */
    List<VisitedProductAnalytic> getVisitedProducts() throws SQLException;

    /**
     * Fetch products by search criteria
     *
     * @return {@link com.rd.domain.analytics.VisitedProductAnalytic}
     * @throws SQLException
     */
    List<VisitedProductAnalytic> getSearchProducts() throws SQLException;

    /**
     * Fetch products by category
     *
     * @return {@link com.rd.domain.analytics.VisitedProductAnalytic}
     * @throws SQLException
     */
    List<VisitedProductAnalytic> getProductsByCategory() throws SQLException;

    /**
     * Fetch products by purchase history
     *
     * @return {@link com.rd.domain.analytics.VisitedProductAnalytic}
     * @throws SQLException
     */
    List<VisitedProductAnalytic> getProductsByPurchase() throws SQLException;

}
