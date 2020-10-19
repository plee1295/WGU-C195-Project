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
 * The user data access object
 * @author parkerlee
 */
public class UserDAO {
    
    /**
     * Creates an observable array list of users that will be used to execute queries
     * @param rs    the result set
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static ObservableList<User> getUserObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<User> userList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("User_ID"));
                user.setUserUsername(rs.getString("User_Name"));
                user.setUserPassword(rs.getString("Password"));
                
                userList.add(user);
            }
            
            return userList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting user objects: " + e);
            e.printStackTrace();
            throw e; 
        }
    }
    
    /**
     * Gets all user records in the database
     * @return  an observable list of all users in the user table
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<User> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from users";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<User> userList = getUserObjects(rs);
            
            return userList;
            
        } catch (SQLException e) {
            System.out.println("Error getting user data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Get the user id of the currently logged in user
     * @param username  the username of the current user
     * @return          the id of the current user
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getCurrentUserId(String username) throws ClassNotFoundException, SQLException {
        String query = "select User_ID from users where User_Name = '"+username+"'";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                return id;
            }
            // TODO: THROW EXCEPTION/ERROR
            return 0;
            
        } catch (SQLException e) {
            System.out.println("Error getting user id from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
