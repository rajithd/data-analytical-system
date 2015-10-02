package com.rd.executor.rule;

import com.rd.domain.analytics.VisitedProductAnalytic;
import com.rd.executor.repository.HiveDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class RuleExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutor.class);

    @Autowired
    private HiveDao hiveDao;

    public void executeRule(String input) {
        try {
            switch (input) {
                case "1":
                    recommendedProductsByVisited();
                    break;
                case "2":
                    recommendedProductsBySearch();
                    break;
                case "3":
                    advertiseProductsByBrand();
                    break;
                case "4":
                    advertiseProductsByPurchase();
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported rule type");
            }
        } catch (Exception e) {
            LOGGER.error("Error occur while processing rule", e);
        }
    }

    private void advertiseProductsByPurchase() throws SQLException {
        List<VisitedProductAnalytic> visitedProducts = hiveDao.getProductsByPurchase();
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("------------   Advertise products by Purchase -----------------------");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Long customerId = null;
        String categoryName = null;
        for (VisitedProductAnalytic analytic : visitedProducts) {
            if (analytic.getCustomerId().equals(customerId) && analytic.getCategoryName().equals(categoryName)) {
            } else {
                LOGGER.info("-------------------------------------------------------------");
                LOGGER.info("^^^^^  Advertise result for Customer id : {} and name : {} ^^^^^^^", analytic.getCustomerId(), analytic.getCustomerName());
                LOGGER.info("-------------------------------------------------------------");
                customerId = analytic.getCustomerId();
                categoryName = analytic.getCategoryName();
                LOGGER.info("----->>  More Advertisements on brand new {} ", analytic.getCategoryName());
            }
        }
    }

    private void advertiseProductsByBrand() throws SQLException {
        List<VisitedProductAnalytic> visitedProducts = hiveDao.getProductsByCategory();
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("------------   Advertise products by Brand -------------------------");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Long customerId = null;
        String categoryName = null;
        for (VisitedProductAnalytic analytic : visitedProducts) {
            if (analytic.getCustomerId().equals(customerId) && analytic.getCategoryName().equals(categoryName)) {
            } else {
                LOGGER.info("-------------------------------------------------------------");
                LOGGER.info("^^^^^  Advertise result for Customer id : {} and name : {} ^^^^^^^", analytic.getCustomerId(), analytic.getCustomerName());
                LOGGER.info("-------------------------------------------------------------");
                customerId = analytic.getCustomerId();
                categoryName = analytic.getCategoryName();
                LOGGER.info("----->>  More Advertisements on {} ", analytic.getCategoryName());
            }
        }
    }

    private void recommendedProductsBySearch() throws SQLException {
        List<VisitedProductAnalytic> visitedProducts = hiveDao.getSearchProducts();
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("------------   Recommendation results by searched products ---------");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Long customerId = null;
        for (VisitedProductAnalytic analytic : visitedProducts) {
            if (analytic.getCustomerId().equals(customerId)) {
                LOGGER.info("----->>  Product id is {} and name is {}", analytic.getProductId(), analytic.getProductName());
            } else {
                LOGGER.info("-------------------------------------------------------------");
                LOGGER.info("^^^^^  Recommendation result for Customer id : {} and name : {} ^^^^^^^", analytic.getCustomerId(), analytic.getCustomerName());
                LOGGER.info("-------------------------------------------------------------");
                customerId = analytic.getCustomerId();
                LOGGER.info("----->>  Product id is {} and name is {}", analytic.getProductId(), analytic.getProductName());
            }
        }

    }

    private void recommendedProductsByVisited() throws SQLException {
        List<VisitedProductAnalytic> visitedProducts = hiveDao.getVisitedProducts();
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        LOGGER.info("------------   Recommendation results by visited products ----------");
        LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Long customerId = null;
        for (VisitedProductAnalytic analytic : visitedProducts) {
            if (analytic.getCustomerId().equals(customerId)) {
                LOGGER.info("----->>  Product id is {} and name is {}", analytic.getProductId(), analytic.getProductName());
            } else {
                LOGGER.info("-------------------------------------------------------------");
                LOGGER.info("^^^^^  Recommendation result for Customer id : {} and name : {} ^^^^^^^", analytic.getCustomerId(), analytic.getCustomerName());
                LOGGER.info("-------------------------------------------------------------");
                customerId = analytic.getCustomerId();
                LOGGER.info("----->>  Product id is {} and name is {}", analytic.getProductId(), analytic.getProductName());
            }
        }
    }
}
