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
 * The appointment data access object
 * 
 * @author parkerlee
 */
public class AppointmentDAO {
    
    /**
     * Helper function to execute SQL queries and increase code readability
     * 
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
     * Creates an observable array list of appointments that will be used to execute queries
     * 
     * @param rs    the result set 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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
    
    /**
     * Checks whether or not a customer has any appointments in the database
     * 
     * @param customerId
     * @return  true/false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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
    
    /**
     * Checks whether or not the input appointment time interferes with any 
     * appointments already in the database
     * 
     * @param customerId    the customer id to check for matching appointments
     * @param startTime     the start time of the appointment to be validated
     * @param endTime       the end time of the appointment to be validated
     * @return  true/false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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
    
    /**
     * Inserts an appointment in to the database
     * 
     * @param title         the appointment title to be added
     * @param description   the appointment description to be added
     * @param location      the appointment location to be added
     * @param type          the appointment type to be added
     * @param start         the appointment start date/time to be added
     * @param end           the appointment end date/time to be added
     * @param customerId    the appointment customer id to be added
     * @param userId        the appointment user id to be added
     * @param contactId     the appointment contact id to be added
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        String query = "insert into appointments"
                     + "(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) "
                     + "values('"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', "+customerId+", "+userId+", "+contactId+")";
        execute(query, "Error inserting appointment in database");
    }
    
    /**
     * Updates the appointment data in the database
     * 
     * @param id            the appointment id to be updated
     * @param title         the appointment title to be updated
     * @param description   the appointment description to be updated
     * @param location      the appointment location to be updated
     * @param type          the appointment type to be updated
     * @param start         the appointment start date/time to be updated
     * @param end           the appointment end date/time to be updated
     * @param customerId    the appointment customer id to be updated
     * @param userId        the appointment user id to be updated
     * @param contactId     the appointment contact id to be updated
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws ClassNotFoundException, SQLException {
        String query = "update appointments set Title = '"+title+"', "
                     + "Description = '"+description+"', Location = '"+location+"', Type = '"+type+"', "
                     + "Start = '"+start+"', End = '"+end+"', Customer_ID = "+customerId+", User_ID = "+userId+", Contact_ID = '"+contactId+"' "
                     + "where Appointment_ID = '"+id+"' ";
        execute(query, "Error updating appointment in database");
    }
    
    /**
     * Deletes the appointment from the database
     * @param id    the appointment id of the appointment to be deleted
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void deleteAppointment(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from appointments where Appointment_ID = '"+id+"' ";
        execute(query, "Error deleting appointment from database");
    }
    
    /**
     * Gets all the appointment records for a specific customer
     * 
     * @param customerId    the customer id of the appointments to be returned
     * @return              an observable list of appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets all appointment records in the database
     * 
     * @return an observable list of all appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets all appointment records in the next 7 days
     * 
     * @return an observable list of appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets all appointment records in the next month
     * 
     * @return an observable list of appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets all appointment records in the next 15 minutes
     * @return an observable list of appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
    
    /**
     * Gets all appointment records from the database
     * @return an observable list of all appointments
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
