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
public class UserDAO {
    
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
    
}
