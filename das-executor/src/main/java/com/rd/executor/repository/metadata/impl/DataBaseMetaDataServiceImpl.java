package com.rd.executor.repository.metadata.impl;


import com.rd.domain.hive.Column;
import com.rd.domain.hive.Table;
import com.rd.executor.repository.DataBaseMetaDataService;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.jvm.JdbcDatabaseSnapshotGenerator;
import liquibase.snapshot.jvm.StandardJdbcDatabaseSnapshotGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("dataBaseMetaDataService")
public class DataBaseMetaDataServiceImpl implements DataBaseMetaDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseMetaDataServiceImpl.class);

    @Autowired
    private SpringLiquibase liquibase;


    @Override
    public List<Table> getAllTablesInDatabase() {
        List<Table> tableList = new ArrayList<Table>();

        try {
            Set<liquibase.database.structure.Table> liqubaseAllTables = getDatabaseSnapshot().getTables();
            for (liquibase.database.structure.Table liqubaseAllTable : liqubaseAllTables) {
                liquibase.database.structure.Table apiTable = liqubaseAllTable;
                Table table = getTableFromApiTable(apiTable);
                tableList.add(table);
            }

        } catch (Exception e) {

        }
        return tableList;
    }

    @Override
    public List<Column> getColumns(String tableName) {
        List<Column> columns = null;
        if (tableName == null) {
            return null;
        }
        try {
            liquibase.database.structure.Table apiTable = getDatabaseSnapshot().getTable(tableName);
            if (apiTable == null) {
                LOGGER.info("No table found for the given table name [{}]", tableName);
            }
            List<liquibase.database.structure.Column> apiColumns = apiTable.getColumns();
            columns = getColumnListFromApiColumns(apiColumns);
        } catch (Exception e) {
            LOGGER.error("Error occurred in retrieving a columns in [" + tableName + "]", e);
        }
        return columns;
    }

    private DatabaseSnapshot getDatabaseSnapshot() {
        Database db = null;
        DatabaseSnapshot databaseSnapshot = null;
        try {
            JdbcDatabaseSnapshotGenerator jdbcDatabaseSnapshotGenerator = new StandardJdbcDatabaseSnapshotGenerator();
            db = getDatabaseConnection();

            databaseSnapshot = jdbcDatabaseSnapshotGenerator.createSnapshot(db, null, null);
        } catch (DatabaseException e) {
            LOGGER.error("Error in getting database snapshot, ", e);
        } finally {
            if(db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    LOGGER.error("Error in closing Liquibase database. Connection could not be recovered.");
                }
            }
        }
        return databaseSnapshot;
    }

    private Database getDatabaseConnection() {
        Database database = null;
        try {
            DatabaseConnection databaseConnection=new JdbcConnection(liquibase.getDataSource().getConnection());
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection);
        } catch (Exception e) {

        }

        return database;
    }


    private Table getTableFromApiTable(liquibase.database.structure.Table apiTable) {
        if (apiTable == null) {
            LOGGER.info("Table doesn't exists to get a table form metadata service....");
            return null;
        }
        Table table = new Table(apiTable.getName().toLowerCase(), apiTable.getRemarks(), apiTable.getSchema());
        List<Column> columnList = getColumnListFromApiColumns(apiTable.getColumns());
        table.setColumns(columnList);
        return table;
    }

    private List<Column> getColumnListFromApiColumns(List<liquibase.database.structure.Column> apiColumnList) {
        List<Column> columns = new ArrayList<Column>();
        for (liquibase.database.structure.Column apiColumn : apiColumnList) {
            columns.add(getColumnFromApiColumn(apiColumn));
        }
        return columns;
    }

    private Column getColumnFromApiColumn(liquibase.database.structure.Column apiColumn) {
        Column column = new Column(apiColumn.getName().toLowerCase(), apiColumn.getDataType(), apiColumn.getColumnSize(), apiColumn.getDecimalDigits(),
                apiColumn.isNullable(), apiColumn.getTypeName(), apiColumn.getDefaultValue(), apiColumn.isAutoIncrement(),
                apiColumn.isPrimaryKey(), apiColumn.isUnique(), apiColumn.isCertainDataType(), apiColumn.getRemarks());

        return column;
    }
}
