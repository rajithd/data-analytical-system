package com.rd.executor.hive;

import com.rd.executor.repository.DataBaseMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


public class TableImportExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TableImportExecutor.class);

    protected static class TableImporterCallable implements Callable {
        private CountDownLatch countDownLatch;
        private String tableName;
        private DataBaseMetaDataService dataBaseMetaDataService;
        private DataSource hiveDataSource;
        private DataSource relationalDataSource;

        protected TableImporterCallable(DataBaseMetaDataService dataBaseMetaDataService, String tableName,
                                        DataSource hiveDataSource, DataSource relationalDataSource,
                                        CountDownLatch countDownLatch) {
            this.dataBaseMetaDataService = dataBaseMetaDataService;
            this.tableName = tableName;
            this.hiveDataSource = hiveDataSource;
            this.relationalDataSource = relationalDataSource;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Object call() throws Exception {
            HiveTableImporter hiveTableImporter = new HiveTableImporter();
            Connection hiveConnection = null;
            try {
                hiveConnection = hiveDataSource.getConnection();
                hiveTableImporter.importTable(dataBaseMetaDataService,tableName,hiveConnection,relationalDataSource);

            } catch (Exception e){
                logger.error("Error occur while getting hive connection [{}]" , e.getMessage());
            } finally {
                if (hiveConnection != null) {
                    try {
                        hiveConnection.close();
                    } catch (SQLException e) {
                        logger.error("Error closing hive connection", e);
                    }
                }
                this.countDownLatch.countDown();
            }
            return null;
        }
    }
}
