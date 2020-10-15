/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.Appointment;
import com.parkerlee.model.AppointmentDAO;
import com.parkerlee.model.Contact;
import com.parkerlee.model.ContactDAO;
import com.parkerlee.model.Country;
import com.parkerlee.model.CountryDAO;
import com.parkerlee.model.Customer;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author parkerlee
 */

public class AppointmentController implements Initializable {
    
    @FXML
    private Label titleText;
    
    @FXML
    private Label customerIdText;
    
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TextField appointmentIdTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private TextField typeTextField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endTimeTextField;
    
    @FXML
    private Label userIdText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Initialize contact names combo box
        ObservableList<Contact> contactList;
        try {
            contactList = ContactDAO.getAllRecords();
            
            ObservableList<String> contactNames = FXCollections.observableArrayList();
          
            contactList.forEach((contact) -> {
                String contactToAdd = contact.getNameProperty().getValue();
                contactNames.add(contactToAdd);
            });
            
            contactComboBox.setItems(contactNames);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void populateTable(ObservableList<Appointment> appointmentList) {
        appointmentTableView.setItems(appointmentList);
    }
    
    public void getData(Customer customer, int userId) {
        String customerName = customer.getNameProperty().getValue();
        int customerId = customer.getIdProperty().getValue();
        
        titleText.setText("Appointments for " + customerName);
        customerIdText.setText("Customer ID: " + customerId);
        userIdText.setText("User ID: " + userId);
    }
    
    public LocalDateTime convertToTimestamp(String date, String time) {

        // get date
        String[] dateArr = date.split("-"); 
        int year = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1]);
        int day = Integer.parseInt(dateArr[2]);
        
        // get time
        int hour = 0;
        int minutes = 0;
        String[] timeArr = time.split(" ");
        String[] hourMinArr = timeArr[0].split(":");
        
        if (timeArr[1].equals("AM") && Integer.parseInt(hourMinArr[0]) == 12) {
            hour = 0;
            minutes = Integer.parseInt(hourMinArr[1]);
        } else if (timeArr[1].equals("AM")) {
            hour = Integer.parseInt(hourMinArr[0]);
            minutes = Integer.parseInt(hourMinArr[1]);
        } else if (timeArr[1].equals("PM")) {
            hour = Integer.parseInt(hourMinArr[0]) + 12;
            minutes = Integer.parseInt(hourMinArr[1]);
        }
         
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minutes);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime timeToAdd = zonedDateTime.toLocalDateTime();
        
        return timeToAdd;

    }
    
    @FXML
    void addAppointmentButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = contactComboBox.getValue();
            String type = typeTextField.getText();
            String startDate = startDatePicker.getValue().toString();
            String startTime = startTimeTextField.getText();
            String endDate = endDatePicker.getValue().toString();
            String endTime = endTimeTextField.getText();
            
            String customerIdStr = customerIdText.getText();
            int customerId = Integer.parseInt(customerIdStr.split(" ")[2]);
            
            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            
            int contactId = ContactDAO.getContactIdFromName(contact);
            
            LocalDateTime startTimestamp = convertToTimestamp(startDate, startTime);
            LocalDateTime endTimestamp = convertToTimestamp(endDate, endTime);
        
            AppointmentDAO.insertAppointment(title, description, location, type, startTimestamp, endTimestamp, customerId, userId, contactId);
            
//            ObservableList<Appointment> appointmentList = AppointmentDAO.getAllRecords();
//            populateTable(appointmentList);
//            
//            clearTextFields();
            
        } catch (SQLException e) {
            System.out.println("Error adding appointment to database: " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
}
