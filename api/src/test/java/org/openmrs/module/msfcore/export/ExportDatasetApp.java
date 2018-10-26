package org.openmrs.module.msfcore.export;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;

/**
 * Small utility to help with dataset creation
 */
public class ExportDatasetApp {

    public static void main(String[] args) throws Exception {
        // database connection
        Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3308/msf", "root", "Admin123");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        // partial database export
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("order_type");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("exported-data-set.xml"));
    }

}
