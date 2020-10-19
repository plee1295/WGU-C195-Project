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
 * The customer data access object
 * @author parkerlee
 */
public class CustomerDAO {
    
    /**
     * Helper function to execute SQL queries and increase code readability
     * @param query
     * @param errMessage
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void execute(String query, String errMessage) throws SQLException, ClassNotFoundException {
        try {
            DBUtil.dbExecuteQuery(query);
        } catch (SQLException e) {
            System.out.println(errMessage + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Creates an observable array list of customers that will be used to execute queries
     * @param rs
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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
    
    /**
     * Inserts a customer into the database
     * @param name      the customer name to be added
     * @param address   the customer address to be added
     * @param division  the customer division to be added
     * @param zip       the customer zip to be added
     * @param phone     the customer phone to be added
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertCustomer(String name, String address, int division, String zip, String phone) throws SQLException, ClassNotFoundException {
        String query = "insert into customers"
                     + "(Customer_Name, Address, Division_ID, Postal_Code, Phone) "
                     + "values('"+name+"', '"+address+"', '"+division+"', '"+zip+"', '"+phone+"')";
        execute(query, "Error inserting customer in database");
    }
    
    /**
     * Updates a customer that already exists inside of the database
     * @param id        the customer id to be updated
     * @param name      the customer name to be updated
     * @param address   the customer address to be updated
     * @param division  the customer division to be updated
     * @param zip       the customer zip to be updated
     * @param phone     the customer phone to be updated
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void updateCustomer(int id, String name, String address, int division, String zip, String phone) throws ClassNotFoundException, SQLException {
        String query = "update customers set Customer_Name = '"+name+"', "
                     + "Address = '"+address+"', Postal_Code = '"+zip+"', Phone = '"+phone+"', "
                     + "Division_ID = '"+division+"' "
                     + "where Customer_ID = '"+id+"' ";
        execute(query, "Error updating customer in database");
    }
    
    /**
     * Deletes a customer from the database
     * @param id    the id of the customer to be deleted
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void deleteCustomer(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from customers where Customer_ID = '"+id+"' ";
        execute(query, "Error deleting customer from database");
    }
    
    /**
     * Gets all customer records from the customer table in the database
     * @return  an observable list of all customer records
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets a list of customer records that have been added to the database this week
     * @return  an observable list of customers
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllRecordsAddedThisWeek() throws ClassNotFoundException, SQLException {
        String query = "select * from customers WHERE Create_Date >= DATE_ADD(NOW(), INTERVAL -7 DAY)";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Customer> customerList = getCustomerObjects(rs);
            
            return customerList;
            
        } catch (SQLException e) {
            System.out.println("Error getting customers added in last 7 days data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
