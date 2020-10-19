/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.model;

import com.parkerlee.util.DBUtil;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.collections.FXCollections;

/**
 * The country data access object
 * @author parkerlee
 */
public class CountryDAO {
    
    /**
     * Creates an observable array list of countries that will be used to execute queries
     * @param rs    the result set
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Country> getCountryObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Country> countryList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Country country = new Country();
                country.setCountryId(rs.getInt("Country_ID"));
                country.setCountryCountryName(rs.getString("Country"));
                
                countryList.add(country);
            }
            
            return countryList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting country objects: " + e);
            e.printStackTrace();
            throw e; 
        }
    }
    
    /**
     * Gets all country records in the database
     * @return  an observable list of country objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Country> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from countries";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Country> countryList = getCountryObjects(rs);
            
            return countryList;
            
        } catch (SQLException e) {
            System.out.println("Error getting country data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the country id from the country name
     * @param countryName   the country name
     * @return              the country id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getSelectedCountryId(String countryName) throws ClassNotFoundException, SQLException {
        String query = "select Country_ID from countries where Country = '"+countryName+"' ";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;
            }
            // TODO: THROW EXCEPTION/ERROR
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Error getting country id from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the country id from the first level division id from the first level division table
     * @param id    the first level division id
     * @return      the country id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCountryIdFromDivisionId(int id) throws ClassNotFoundException, SQLException {
        String query = "select COUNTRY_ID from first_level_divisions where Division_ID = "+id+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                int countryId = Integer.parseInt(rs.getString(1));
                return countryId;
            }
            // TODO: THROW EXCEPTION/ERROR
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Error getting country id from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the country name from the country id
     * @param id    the country id
     * @return      the country name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getCountryName(int id) throws ClassNotFoundException, SQLException {
        String query = "select Country from countries where Country_ID = "+id+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                String countryName = rs.getString(1);
                return countryName;
            }
            // TODO: THROW EXCEPTION/ERROR
            return "";
            
        } catch (SQLException e) {
            System.out.println("Error getting country name from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
