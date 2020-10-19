/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.model;

import com.parkerlee.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The first level division data access object
 * @author parkerlee
 */
public class DivisionDAO {
    
    /**
     * Creates an observable array list of first level divisions that will be used to execute queries
     * @param rs
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Division> getDivisionObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Division> divisionList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Division division = new Division();
                division.setDivisionId(rs.getInt("Division_ID"));
                division.setDivisionCountryId(rs.getInt("COUNTRY_ID"));
                division.setDivisionDivisionName(rs.getString("Division"));
                
                divisionList.add(division);
            }
            
            return divisionList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting country objects: " + e);
            e.printStackTrace();
            throw e; 
        }
    }
    
    /**
     * Gets all first level division records in the first level division table of the database
     * @return  an observable list of first level division objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Division> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from first_level_divisions";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Division> divisionList = getDivisionObjects(rs);
            
            return divisionList;
            
        } catch (SQLException e) {
            System.out.println("Error getting division data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the first level division id from the first level division name
     * @param divisionName  the division name
     * @return              the division id associated with the name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getSelectedDivisionId(String divisionName) throws ClassNotFoundException, SQLException {
        String query = "select Division_ID from first_level_divisions where Division = '"+divisionName+"' ";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;
            }
            // TODO: THROW EXCEPTION/ERROR
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Error getting division id from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the first level division name from the first level division id
     * @param id    the first level division id
     * @return      the first level division name associated with the id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getDivisionName(int id) throws ClassNotFoundException, SQLException {
        String query = "select Division from first_level_divisions where Division_ID = "+id+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                String name = rs.getString(1);
                return name;
            }
            // TODO: THROW EXCEPTION/ERROR
            return "";
            
        } catch (SQLException e) {
            System.out.println("Error getting division name from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
