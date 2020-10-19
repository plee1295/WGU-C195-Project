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
 * The contact data access object
 * @author parkerlee
 */
public class ContactDAO {
    
    /**
     * Creates an observable array list of contacts that will be used to execute queries
     * @param rs    the result set
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<Contact> getContactObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Contact> contactList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("Contact_ID"));
                contact.setContactName(rs.getString("Contact_Name"));
                contact.setContactEmail(rs.getString("Email"));
                
                contactList.add(contact);
            }
            
            return contactList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting contact objects: " + e);
            e.printStackTrace();
            throw e; 
        }
    }
    
    /**
     * Gets all contact records
     * @return  an observable list of contacts
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Contact> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from contacts";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Contact> contactList = getContactObjects(rs);
            
            return contactList;
            
        } catch (SQLException e) {
            System.out.println("Error getting contact data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the contact id from the contact name
     * @param name  the contact name 
     * @return      the contact id
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getContactIdFromName(String name) throws ClassNotFoundException, SQLException {
        String query = "select Contact_ID from contacts where Contact_Name = '"+name+"'";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;
            }
            // TODO: THROW EXCEPTION/ERROR
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Error getting contact id from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Gets the contact name from the id
     * @param id    the contact id
     * @return      the contact name
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getContactNameFromId(int id) throws ClassNotFoundException, SQLException {
        String query = "select Contact_Name from contacts where Contact_ID = "+id+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                String name = rs.getString(1);
                return name;
            }
            // TODO: THROW EXCEPTION/ERROR
            return "";
            
        } catch (SQLException e) {
            System.out.println("Error getting contact name from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
