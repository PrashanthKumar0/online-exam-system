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

    private static Connection connection = null;

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static void connect() throws Exception {
        connection = DriverManager.getConnection(cnxnURL, cnxnUser, cnxnPassword);
    }

    public static void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}