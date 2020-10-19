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
import java.time.OffsetDateTime;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

/**
 * FXML Controller class for appointments
 *
 * @author parkerlee
 */

public class AppointmentController implements Initializable {
    
    /**
     * The label to generate the current customer name
     */
    @FXML
    private Label titleText;
    
    /**
     * The label to display the current customer id
     */
    @FXML
    private Label customerIdText;
    
    /**
     * The table to display the appointment data
     */
    @FXML
    private TableView<Appointment> appointmentTableView;

    /**
     * The column to display appointment id data
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIdColumn;

    /**
     * The column to display appointment title data
     */
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    /**
     * The column to display appointment description data
     */
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    /**
     * The column to display appointment location data
     */
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    /**
     * The column to display appointment contact id data
     */
    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;

    /**
     * The column to display appointment type data
     */
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /**
     * The column to display appointment start time data
     */
    @FXML
    private TableColumn<Appointment, String> startColumn;

    /**
     * The column to display appointment end time data
     */
    @FXML
    private TableColumn<Appointment, String> endColumn;

    /**
     * The column to display appointment customer id data
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;

    /**
     * The text field to display appointment id data
     */
    @FXML
    private TextField appointmentIdTextField;

    /**
     * The text field to collect and display appointment title data
     */
    @FXML
    private TextField titleTextField;

    /**
     * The text field to collect and display appointment description data
     */
    @FXML
    private TextField descriptionTextField;

    /**
     * The text field to collect and display appointment location data
     */
    @FXML
    private TextField locationTextField;

    /**
     * The combo box to collect and display appointment contact data
     */
    @FXML
    private ComboBox<String> contactComboBox;

    /**
     * The text field to collect and display appointment type data
     */
    @FXML
    private TextField typeTextField;

    /**
     * The date picker to collect and display appointment start date data
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * The text field to collect and display appointment start time data
     */
    @FXML
    private TextField startTimeTextField;

    /**
     * The date picker to collect and display appointment end date data
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * The text field to collect and display appointment end time data
     */
    @FXML
    private TextField endTimeTextField;
    
    /**
     * The label to display the current user id
     */
    @FXML
    private Label userIdText;
    
    /**
     * The label to display the current function of the window
     */
    @FXML
    private Label functionTitleText;
    
    /**
     * The button to add an appointment to the database
     */
    @FXML
    private Button addAppointmentButton;

    /**
     * The button to update an appointment in the database
     */
    @FXML
    private Button updateAppointmentButton;

    /**
     * The button to delete an appointment from the database
     */
    @FXML
    private Button deleteAppointmentButton;

    /**
     * Initializes the appointment controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        
        addAppointmentButton.setDisable(false);
        updateAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
        
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
                addAppointmentButton.setDisable(true);
                updateAppointmentButton.setDisable(false);
                deleteAppointmentButton.setDisable(false);
                
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
    
    /**
     * Converts a string to a local date
     * 
     * @param date  the string to be converted
     * @return      the local date converted from a string
     */
    private LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        
        return localDate;
    }
    
    /**
     * Converts a string to a date and time held in an array
     * 
     * @param str   the string to be converted
     * @return      the array holding the converted date and time
     */
    private String[] convertToDateAndTime(String str) {
        String[] ans = new String[2];
        
        String[] getDateAndTime = str.split(" ");
        String date = getDateAndTime[0];
        String time = getDateAndTime[1];
        
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
    
    /**
     * Displays appointment data in the table view
     * 
     * @param appointmentList   the list of appointments used to populate the table view
     */
    private void populateTable(ObservableList<Appointment> appointmentList) {
        appointmentTableView.setItems(appointmentList);
    }
    
    /**
     * Receives data from the customer controller to display in the window
     * 
     * @param customer  the current customer object sent from the customer controller
     * @param userId    the current user id sent from the customer controller
     */
    public void getData(Customer customer, int userId) {
        titleText.setText("Appointments for " + customer.getNameProperty().getValue());
        userIdText.setText("User ID: " + userId);
        customerIdText.setText("Customer ID: " + customer.getIdProperty().getValue());
    }
    
    /**
     * Clears all of the text fields in the window
     */
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
    
    /**
     * Converts date and time strings to a LocalDateTime object in UTC
     * 
     * @param date  the date string to be converted
     * @param time  the time string to be converted
     * @return      the LocalDateTime object in UTC
     */
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
        } else if (timeArr[1].equals("PM") && Integer.parseInt(hourMinArr[0]) == 12) {       
            hour = 12;
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
    
    /**
     * Converts date and time strings into a LocalDateTime object in EST
     * 
     * @param date  the date string to be converted
     * @param time  the time string to be converted
     * @return      the LocalDateTime object in EST
     */
    public LocalDateTime convertToEST(String date, String time) {
        
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
        } else if (timeArr[1].equals("PM") && Integer.parseInt(hourMinArr[0]) == 12) {       
            hour = 12;
            minutes = Integer.parseInt(hourMinArr[1]);
        } else if (timeArr[1].equals("AM")) {
            hour = Integer.parseInt(hourMinArr[0]);
            minutes = Integer.parseInt(hourMinArr[1]);
        } else if (timeArr[1].equals("PM")) {
            hour = Integer.parseInt(hourMinArr[0]) + 12;
            minutes = Integer.parseInt(hourMinArr[1]);
        }
        
        LocalDateTime ldtInUserZone = LocalDateTime.of(year, month, day, hour, minutes);
        ZonedDateTime zonedDateTime = ldtInUserZone.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime timeToAdd = zonedDateTime.toLocalDateTime();
        
        return timeToAdd;
    }
    
    /**
     * Validates the appointment time to be added or updated
     * 
     * @param start     the start LocalDateTime object
     * @param end       the end LocalDateTime object
     * @return          whether or not the LocalDateTime objects hold valid times
     */
    public boolean validateAppointmentTime(LocalDateTime start, LocalDateTime end) {
        int startHour = start.getHour();
        int startMinute = start.getMinute();
        int endHour = end.getHour();
        int endMinute = end.getMinute();
        
        if (startHour >= 8 && startHour < 22 && endHour >= 8 && endHour <= 22) {
            if (endHour == 22 && endMinute > 0) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    /**
     * Normalizes the appointment type
     * 
     * @param type  the type string input in the type text field
     * @return      the validated type string
     */
    private String getTypeString(String type) {
        type = type.toLowerCase();
        
        if (type.equals("onboard") || type.equals("onboarding")) {
            return "onboard";
        } else if (type.equals("update")) {
            return "update";
        } else {
            return "";
        }
    }
    
    /**
     * Validates the data input in the type text field is correct
     * 
     * @param type  the input string in the type text field
     * @return      whether the type string is valid or not
     */
    private boolean validateType(String type) {
        if (type.equals("onboard") || type.equals("update")) {
            return true;
        }
        return false;
    }
    
    /**
     * Handles the add appointment button press. Validates the input data and updates the database with
     * the new appointment.
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void addAppointmentButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String id = appointmentIdTextField.getText();
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = contactComboBox.getValue();
            String startDate = startDatePicker.getValue().toString();
            String startTime = startTimeTextField.getText();
            String endDate = endDatePicker.getValue().toString();
            String endTime = endTimeTextField.getText();

            String typeStr = typeTextField.getText().toLowerCase();
            String type;
            if (typeStr.equals("onboard") || typeStr.equals("update")) {
                type = typeStr;
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Type text field must either be 'onboard' or 'update'.", ButtonType.OK);
                
                /**
                 * This lambda expression is used to validate the user response before continuing on with
                 * the program execution. The use of the lambda expression here is to simplify the
                 * syntax of the code. 
                 */
                alert.showAndWait().filter(response -> response == ButtonType.OK);
                return;
            }

            String customerIdStr = customerIdText.getText();
            int customerId = Integer.parseInt(customerIdStr.split(" ")[2]);

            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);

            int contactId = ContactDAO.getContactIdFromName(contact);

            LocalDateTime startTimestamp = convertToTimestamp(startDate, startTime);
            LocalDateTime endTimestamp = convertToTimestamp(endDate, endTime);

            LocalDateTime startEST = convertToEST(startDate, startTime);
            LocalDateTime endEST = convertToEST(endDate, endTime);

            boolean isValidAppointmentTime = validateAppointmentTime(startEST, endEST);
            boolean isDuplicateAppointmentTime = AppointmentDAO.isDuplicateAppointmentTime(customerId, startTimestamp, endTimestamp);

            if (isValidAppointmentTime) {
                if (!isDuplicateAppointmentTime) {
                    AppointmentDAO.insertAppointment(title, description, location, type, startTimestamp, endTimestamp, customerId, userId, contactId);

                    ObservableList<Appointment> appointmentList = AppointmentDAO.getAllRecordsForCustomer(customerId);
                    populateTable(appointmentList);

                    clearTextFields();

                    addAppointmentButton.setDisable(false);
                    updateAppointmentButton.setDisable(true);
                    deleteAppointmentButton.setDisable(true);

                    startDatePicker.setValue(LocalDate.now());
                    endDatePicker.setValue(LocalDate.now());

                } else {
                    Alert alert = new Alert(AlertType.WARNING, "Customer already has an appointment scheduled at this time.", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
                }

            } else {
                Alert alert = new Alert(AlertType.WARNING, "Appointment must be made between 8am-10pm EST.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }

        } catch (SQLException e) {
            System.out.println("Error adding appointment to database: " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Handles the update appointment button press. Validates the input data and updates the database
     * with the selected appointment. 
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
            
            addAppointmentButton.setDisable(false);
            updateAppointmentButton.setDisable(true);
            deleteAppointmentButton.setDisable(true);
            
        } catch (SQLException e) {
            System.out.println("Error occurred while updating customer data: " + e);
            e.printStackTrace();
            throw e;
        }
        
        clearTextFields();
        functionTitleText.setText("Add");
    }
    
    /**
     * Handles the delete appointment button press. Removes the selected appointment from the database.
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
        
        addAppointmentButton.setDisable(false);
        updateAppointmentButton.setDisable(true);
        deleteAppointmentButton.setDisable(true);
        
        Alert alert = new Alert(AlertType.INFORMATION, "Appointment has been successfully deleted!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            
        functionTitleText.setText("Add");
        
    }
    
    /**
     * Handles the back button pressed. Returns the user back to the customer view. 
     * 
     * @param event
     */
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
