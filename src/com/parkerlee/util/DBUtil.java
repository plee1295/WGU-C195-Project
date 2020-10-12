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
 *
 * @author parkerlee
 */
public class DBUtil {
    
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static Connection connection = null;
    
    private static final String connStr = "jdbc:mysql://wgudb.ucertify.com/WJ06NB5?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
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
    
    public static void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw e;
        }
    }
    
    // this is for insert/delete/update database operations
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
    
    // this is for retrieving records from the database
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
