package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * SQLDatabaseWrapper
 */
public class SQLDatabaseWrapper {
    private static final String cnxnURL = "jdbc:sqlite:database.db";
    private static final String cnxnUser = null;
    private static final String cnxnPassword = null;
    
    private static Connection conection = null;

    public static Connection getConnection() throws Exception {
        if (conection == null) {
            connect();
        }
        return conection;
    }

    public static void connect() throws Exception{
        conection = DriverManager.getConnection(cnxnURL, cnxnUser, cnxnPassword);
    }


}