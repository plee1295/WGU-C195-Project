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
import com.parkerlee.model.Customer;
import com.parkerlee.util.CustomerSingleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

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
    private TableColumn<Appointment, Integer> apptIdColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

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
    
    @FXML
    private Label functionTitleText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        functionTitleText.setText("Add");

        apptIdColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        contactIdColumn.setCellValueFactory(cellData -> cellData.getValue().getContactIdProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerIdProperty().asObject());
        
        int customerId = CustomerSingleton.id;
        System.out.println("Customer ID: " + customerId);
        
        ObservableList<Appointment> appointmentList;
        try {
            appointmentList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTable(appointmentList);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        // get selected appointment data
        appointmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                functionTitleText.setText("Update/Delete");
                
                Appointment appt = appointmentTableView.getSelectionModel().getSelectedItem();
                
                int apptId = appt.getIdProperty().getValue();
                String title = appt.getTitleProperty().getValue();
                String description = appt.getDescriptionProperty().getValue();
                String location = appt.getLocationProperty().getValue();
                int contactId = appt.getContactIdProperty().getValue();
                String type = appt.getTypeProperty().getValue();
                String start = appt.getStartTimeProperty().getValue();
                String end = appt.getEndTimeProperty().getValue();

                try {
                    String contact = ContactDAO.getContactNameFromId(contactId);

                    String[] startDateAndTime = convertToDateAndTime(start);
                    String[] endDateAndTime = convertToDateAndTime(end);
                    LocalDate startDate = convertStringToLocalDate(startDateAndTime[0]);
                    String startTime = startDateAndTime[1];
                    LocalDate endDate = convertStringToLocalDate(endDateAndTime[0]);
                    String endTime = endDateAndTime[1];
                    
                    appointmentIdTextField.setText(Integer.toString(apptId));
                    titleTextField.setText(title);
                    descriptionTextField.setText(description);
                    locationTextField.setText(location);
                    contactComboBox.setValue(contact);
                    typeTextField.setText(type);
                    startDatePicker.setValue(startDate);
                    startTimeTextField.setText(startTime);
                    endDatePicker.setValue(endDate);
                    endTimeTextField.setText(endTime);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        return localDate;
    }
    
    private String[] convertToDateAndTime(String str) { // 10/21/2020
        String[] ans = new String[2];
        
        String[] getDateAndTime = str.split(" ");
        String date = getDateAndTime[0]; // 2020-10-21
        String time = getDateAndTime[1]; // 15:30:00.0
        
        // format date
        String[] dateArr = date.split("-");
        String year = dateArr[0];
        String month = dateArr[1];
        String day = dateArr[2];
        
        ans[0] = day + "/" + month + "/" + year;
        
        // format time
        String[] timeArr = time.split(":");
        int hour = Integer.parseInt(timeArr[0]);
        int minute = Integer.parseInt(timeArr[1]);
        
        if (hour == 0) {
            hour = 12;
            ans[1] = Integer.toString(hour) + ":" + minute + " AM";
        } else if (hour < 13) {
            ans[1] = Integer.toString(hour) + ":" + minute + " PM";
        } else if (hour >= 13) {
            hour -= 12;
            ans[1] = Integer.toString(hour) + ":" + minute + " PM";
        }
        
        return ans;
    }
    
    private void populateTable(ObservableList<Appointment> appointmentList) {
        appointmentTableView.setItems(appointmentList);
    }
    
    public void getData(Customer customer, int userId) {
        titleText.setText("Appointments for " + customer.getNameProperty().getValue());
        userIdText.setText("User ID: " + userId);
        customerIdText.setText("Customer ID: " + customer.getIdProperty().getValue());
    }
    
    public void clearTextFields() {
        appointmentIdTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        contactComboBox.getSelectionModel().clearSelection();
        typeTextField.clear();
        startDatePicker.getEditor().clear();
        startTimeTextField.clear();
        endDatePicker.getEditor().clear();
        endTimeTextField.clear();
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
            
            ObservableList<Appointment> appointmentList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTable(appointmentList);
            
            clearTextFields();
            
        } catch (SQLException e) {
            System.out.println("Error adding appointment to database: " + e);
            e.printStackTrace();
            throw e;
        }
        
        clearTextFields();
    }
    
    @FXML
    void updateAppointmentButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
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
            
            Appointment appt = appointmentTableView.getSelectionModel().getSelectedItem();
            int apptId = appt.getIdProperty().getValue();
            
            AppointmentDAO.updateAppointment(apptId, title, description, location, type, startTimestamp, endTimestamp, customerId, userId, contactId);
            
            ObservableList<Appointment> apptList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTable(apptList);
            
        } catch (SQLException e) {
            System.out.println("Error occurred while updating customer data: " + e);
            e.printStackTrace();
            throw e;
        }
        
        clearTextFields();
        functionTitleText.setText("Add");
    }
    
    @FXML
    void deleteAppointmentButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        Appointment appt = appointmentTableView.getSelectionModel().getSelectedItem();
        int id = appt.getIdProperty().getValue();
        
        try {
            AppointmentDAO.deleteAppointment(id);
            String customerIdStr = customerIdText.getText();
            int customerId = Integer.parseInt(customerIdStr.split(" ")[2]);
            ObservableList<Appointment> apptList = AppointmentDAO.getAllRecordsForCustomer(customerId);
            populateTable(apptList);
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting appointment from database: " + e);
            e.printStackTrace();
            throw e;
        }
  
        clearTextFields();
        
        Alert alert = new Alert(AlertType.INFORMATION, "Appointment has been successfully deleted!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            
        functionTitleText.setText("Add");
        
    }
    
    @FXML
    void backButtonPressed (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            CustomerController controller = loader.getController();
            
            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            controller.getData(userId);
            
            stage.setScene(scene);
            stage.show();       
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
