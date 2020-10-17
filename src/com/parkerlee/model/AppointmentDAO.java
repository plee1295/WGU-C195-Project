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
    
    private static ObservableList<Appointment> getAppointmentObjects(ResultSet rs) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setAppointmentId(rs.getInt("Appointment_ID"));
                appt.setAppointmentTitle(rs.getString("Title"));
                appt.setAppointmentDescription(rs.getString("Description"));
                appt.setAppointmentLocation(rs.getString("Location"));
                appt.setContactId(rs.getInt("Contact_ID"));
                appt.setAppointmentType(rs.getString("Type"));
                appt.setAppointmentStartTime(rs.getString("Start"));
                appt.setAppointmentEndTime(rs.getString("End"));
                appt.setCustomerId(rs.getInt("Customer_ID"));
                
                appointmentList.add(appt);
            }
            
            return appointmentList;
                    
        } catch (SQLException e) {
           System.out.println("Error getting appointment objects: " + e);
            e.printStackTrace();
            throw e; 
        }
    }
    
    public static boolean hasAppointments(int customerId) throws SQLException, ClassNotFoundException {
        String query = "select * from appointments where Customer_ID = "+customerId+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> apptList = getAppointmentObjects(rs);
            
            if (apptList.isEmpty()) {
                return false;
            }
            
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error getting country data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static boolean isDuplicateAppointmentTime(int customerId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) as Duplicate_Count FROM appointments " +
                        "WHERE Customer_ID = "+customerId+" " +
                        "AND (('"+startTime+"' BETWEEN Start AND End) " +
                        "OR ('"+endTime+"' BETWEEN Start AND End) " +
                        "OR (Start BETWEEN '"+startTime+"' AND '"+endTime+"') " +
                        "OR (End BETWEEN '"+startTime+"' AND '"+endTime+"')) ";
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            if(rs.next()) {
                if (Integer.parseInt(rs.getString(1)) >= 1) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error getting appointment duplication data from database" + e);
            e.printStackTrace();
            throw e;
        }
    } 
    
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        String query = "insert into appointments"
                     + "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                     + "values('"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', "+customerId+", "+userId+", "+contactId+")";
        execute(query, "Error inserting appointment in database");
    }
    
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws ClassNotFoundException, SQLException {
        String query = "update appointments set Title = '"+title+"', "
                     + "Description = '"+description+"', Location = '"+location+"', Type = '"+type+"', "
                     + "Start = '"+start+"', End = '"+end+"', Customer_ID = "+customerId+", User_ID = "+userId+", Contact_ID = '"+contactId+"' "
                     + "where Appointment_ID = '"+id+"' ";
        execute(query, "Error updating appointment in database");
    }
    
    public static void deleteAppointment(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from appointments where Appointment_ID = '"+id+"' ";
        execute(query, "Error deleting appointment from database");
    }
    
    public static ObservableList<Appointment> getAllRecordsForCustomer(int customerId) throws ClassNotFoundException, SQLException {
        String query = "select * from appointments where Customer_ID = "+customerId+"";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointment data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Appointment> getAllRecords() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointment data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Appointment> getAllRecordsInNext7Days() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointments for next 7 days data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Appointment> getAllRecordsInNextMonth() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 MONTH)";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointments for next 7 days data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Appointment> getAllRecordsInNext15Minutes() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointments for next 15 minutes data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    public static ObservableList<Appointment> getAppointmentsByType() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments";
        
        try {
            ResultSet rs = DBUtil.dbExecute(query);
            ObservableList<Appointment> appointmentList = getAppointmentObjects(rs);
            
            return appointmentList;
            
        } catch (SQLException e) {
            System.out.println("Error getting appointment data from database" + e);
            e.printStackTrace();
            throw e;
        }
    }
            
}
