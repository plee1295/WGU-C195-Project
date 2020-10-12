/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.model;

import com.parkerlee.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author parkerlee
 */
public class CustomerDAO {
    
    private static void execute(String query, String errMessage) throws SQLException, ClassNotFoundException {
        try {
            DBUtil.dbExecuteQuery(query);
        } catch (SQLException e) {
            System.out.println(errMessage + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void insertCustomer(String name, String address, int division, String zip, String phone) throws SQLException, ClassNotFoundException {
        String query = "insert into customers"
                     + "(Customer_Name, Address, Division_ID, Postal_Code, Phone) "
                     + "values('"+name+"', '"+address+"', '"+division+"', '"+zip+"', '"+phone+"')";
        execute(query, "Error inserting customer in database");
    }
    
}
