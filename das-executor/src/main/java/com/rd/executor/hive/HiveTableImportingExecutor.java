package com.rd.executor.hive;


import com.rd.domain.hive.Table;
import com.rd.executor.repository.DataBaseMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Service
public class HiveTableImportingExecutor extends TableImportExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(HiveTableImportingExecutor.class);

    @Autowired
    private DataBaseMetaDataService dataBaseMetaDataService;

    @Autowired
    @Qualifier("hiveDataSource")
    private DataSource hiveDataSource;

    @Autowired
    @Qualifier("dataSource")
    private DataSource relationalDataSource;

    @Autowired
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

}
