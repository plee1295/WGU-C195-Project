/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.util;

import javax.sql.rowset.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Handles database connectivity
 * @author parkerlee
 */
public class DBUtil {
    
    /**
     * the JDBC driver string
     */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * the initial connection state
     */
    private static Connection connection = null;
    
    /**
     * the JDBC driver connection string with time zone arguments
     */
    private static final String connStr = "jdbc:mysql://wgudb.ucertify.com/WJ06NB5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    /**
     * Connects to the database using the JDBC driver
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
           Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            throw e;
        }
        
//        System.out.println("JDBC Driver has been registered...");
        
        try {
            connection = DriverManager.getConnection(connStr, "U06NB5", "53688812293");
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console: " + e);
            throw e;
        }
    }
    
    /**
     * Disconnects from the database
     * @throws SQLException
     */
    public static void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    /**
     * Handles inserting, updating, and deleting data from the database
     * @param sqlStatement  the SQL query to be executed
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void dbExecuteQuery(String sqlStatement) throws SQLException, ClassNotFoundException {
        Statement st = null;
        try {
            dbConnect();
            st = connection.createStatement();
            st.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            System.out.println("Problem occurred in dbExecuteQuery operation: " + e);
            throw e;
        } finally {
            if (st != null) {
                st.close();
            }
            dbDisconnect();
        }
    }

    /**
     * Handles retrieving data from the database
     * @param sqlQuery  the SQL query to be executed
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ResultSet dbExecute(String sqlQuery) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        CachedRowSet crs = null;
        
        try {
            dbConnect();
            st = connection.createStatement();
            rs = st.executeQuery(sqlQuery);
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
        } catch (SQLException e) {
            System.out.println("Problem occurred in dbExecute operation: " + e);
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            } if (st != null) {
                st.close();
            }
            dbDisconnect();
        }
        return crs;
    }
    
}
