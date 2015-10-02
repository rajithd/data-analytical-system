package com.rd.executor.repository;

import com.rd.domain.analytics.VisitedProductAnalytic;

import java.sql.SQLException;
import java.util.List;

public interface HiveDao {

    List<VisitedProductAnalytic> getVisitedProducts() throws SQLException;

    List<VisitedProductAnalytic> getSearchProducts() throws SQLException;

}
