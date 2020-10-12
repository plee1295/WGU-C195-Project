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
 *
 * @author parkerlee
 */
public class CountryDAO {
    
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
    
}
