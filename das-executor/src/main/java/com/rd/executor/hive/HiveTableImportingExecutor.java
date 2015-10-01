package com.rd.executor.hive;


import com.rd.domain.hive.Table;
import com.rd.executor.repository.DataBaseMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class HiveTableImportingExecutor extends TableImportExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiveTableImportingExecutor.class);

    private DataBaseMetaDataService dataBaseMetaDataService;
    private DataSource hiveDataSource;
    private DataSource relationalDataSource;
    private ExecutorService executorService;

    public void execute() throws Exception {
        LOGGER.info("=======================================================================");
        LOGGER.info("#####           Hive Table Importer Starting...                   #####");
        LOGGER.info("=======================================================================");
        Set<String> tablesToSync = new HashSet<String>();
        tablesToSync.addAll(getAllTableNames());

        CountDownLatch countDownLatch = new CountDownLatch(tablesToSync.size());

        for (String tableName : tablesToSync) {
            TableImporterCallable importerCallable = new TableImporterCallable(
                    dataBaseMetaDataService, tableName,
                    hiveDataSource, relationalDataSource, countDownLatch);
            executorService.submit(importerCallable);
        }
        countDownLatch.await();
        LOGGER.info("=======================================================================");
        LOGGER.info("#####           Hive Table Importer Done                          #####");
        LOGGER.info("=======================================================================");
    }

    private List<String> getAllTableNames() {
        List<Table> allTables = dataBaseMetaDataService.getAllTablesInDatabase();
        List<String> tableNames = new ArrayList<String>(allTables.size());
        for (Table table : allTables) {
            tableNames.add(table.getName());
        }
        return tableNames;
    }

    public void setDataBaseMetaDataService(DataBaseMetaDataService dataBaseMetaDataService) {
        this.dataBaseMetaDataService = dataBaseMetaDataService;
    }

    public void setHiveDataSource(DataSource hiveDataSource) {
        this.hiveDataSource = hiveDataSource;
    }

    public void setRelationalDataSource(DataSource relationalDataSource) {
        this.relationalDataSource = relationalDataSource;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
