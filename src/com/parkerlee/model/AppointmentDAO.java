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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 *
 * @author parkerlee
 */
public class AppointmentDAO {
    
    private static void execute(String query, String errMessage) throws SQLException, ClassNotFoundException {
        try {
            DBUtil.dbExecuteQuery(query);
        } catch (SQLException e) {
            System.out.println(errMessage + e);
            e.printStackTrace();
            throw e;
        }
    }
    
//    private static ObservableList<Customer> getCustomerObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
//        try {
//            ObservableList<Customer> customerList = FXCollections.observableArrayList();
//            
//            while (rs.next()) {
//                Customer customer = new Customer();
//                customer.setCustomerId(rs.getInt("Customer_ID"));
//                customer.setCustomerDivisionId(rs.getInt("Division_ID"));
//                customer.setCustomerName(rs.getString("Customer_Name"));
//                customer.setCustomerAddress(rs.getString("Address"));
//                customer.setCustomerPostalCode(rs.getString("Postal_Code"));
//                customer.setCustomerPhoneNumber(rs.getString("Phone"));
//                
//                customerList.add(customer);
//            }
//            
//            return customerList;
//                    
//        } catch (SQLException e) {
//           System.out.println("Error getting customer objects: " + e);
//            e.printStackTrace();
//            throw e; 
//        }
//    }
    
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        String query = "insert into appointments"
                     + "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                     + "values('"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', "+customerId+", "+userId+", "+contactId+")";
        execute(query, "Error inserting appointment in database");
    }
    
//    public static ObservableList<Customer> getAllRecords() throws ClassNotFoundException, SQLException {
//        String query = "select * from customers";
//        
//        try {
//            ResultSet rs = DBUtil.dbExecute(query);
//            ObservableList<Customer> customerList = getCustomerObjects(rs);
//            
//            return customerList;
//            
//        } catch (SQLException e) {
//            System.out.println("Error getting customer data from database" + e);
//            e.printStackTrace();
//            throw e;
//        }
//    }
    
}
