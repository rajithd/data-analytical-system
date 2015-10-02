package com.rd.executor.repository;


import com.rd.domain.hive.Column;
import com.rd.domain.hive.Table;

import java.util.List;

public interface DataBaseMetaDataService {

    List<Table> getAllTablesInDatabase();

    List<Column> getColumns(String tableName);

}
