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
    
    private static ObservableList<Customer> getCustomerObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("Customer_ID"));
                customer.setCustomerDivisionId(rs.getInt("Division_ID"));
                customer.setCustomerName(rs.getString("Customer_Name"));
                customer.setCustomerAddress(rs.getString("Address"));
                customer.setCustomerPostalCode(rs.getString("Postal_Code"));
                customer.setCustomerPhoneNumber(rs.getString("Phone"));
                
                customerList.add(customer);
            }
            
            return customerList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting customer objects: " + e);
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
    
    public static void updateCustomer(int id, String name, String address, int division, String zip, String phone) throws ClassNotFoundException, SQLException {
        String query = "update customers set Customer_Name = '"+name+"', "
                     + "Address = '"+address+"', Postal_Code = '"+zip+"', Phone = '"+phone+"', "
                     + "Division_ID = '"+division+"' "
                     + "where Customer_ID = '"+id+"' ";
        execute(query, "Error updating customer in database");
    }
    
    public static void deleteCustomer(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from customers where Customer_ID = '"+id+"' ";
        execute(query, "Error deleting customer from database");
    }
    
    public static ObservableList<Customer> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from customers";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Customer> customerList = getCustomerObjects(rs);
            
            return customerList;
            
        } catch (SQLException e) {
            System.out.println("Error getting customer data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
