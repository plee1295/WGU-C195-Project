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
import com.parkerlee.model.CustomerDAO;
import com.parkerlee.model.DivisionDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class for reports
 *
 * @author parkerlee
 */
public class ReportController implements Initializable {
    
    /**
     * The label to display the current user id
     */
    @FXML
    private Label userIdText;
    
    /**
     * The radio button to get appointments by type
     */
    @FXML
    private RadioButton apptsByType;

    /**
     * The radio button to get appointments by month
     */
    @FXML
    private RadioButton apptsByMonth;

    /**
     * The radio button to get schedule for contacts
     */
    @FXML
    private RadioButton contactSchedules;

    /**
     * The radio button to get customers recently added
     */
    @FXML
    private RadioButton customersAdded;
    
    /**
     * The text area to show report data
     */
    @FXML
    private TextArea textArea;

    /**
     * Initializes the controller class. Sets up the toggle group and radio buttons. 
     * Handles the selection of radio buttons. 
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        final ToggleGroup group = new ToggleGroup();
        apptsByType.setToggleGroup(group);
        apptsByType.setSelected(true);
        apptsByMonth.setToggleGroup(group);
        contactSchedules.setToggleGroup(group);
        customersAdded.setToggleGroup(group);
        
        // Initialze with default radio button selected
        handleApptsByType();
        
        // Handle appointment by type radio button selected
        apptsByType.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { 
                    textArea.setText("");
                    handleApptsByType();
                }
            }
        });
        
        // Handle appointment by month radio button selected
        apptsByMonth.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    textArea.setText("");
                    try {
                        ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();
                        String month;
                        
                        HashMap<String, Integer> monthCountMap = new HashMap<String, Integer>(); 
                        
                        for (int i = 0; i < apptList.size(); i++) {
                            String date = apptList.get(i).getStartTimeProperty().getValue();
                            month = date.split(" ")[0].split("-")[1];

                            if (monthCountMap.containsKey(month)) {
                                // If month is present in monthCountMap, 
                                // incrementing it's count by 1 
                                monthCountMap.put(month, monthCountMap.get(month) + 1);
                            } else {
                                // If month is not present in monthCountMap, 
                                // putting this month to monthCountMap with 1 as it's value 
                                monthCountMap.put(month, 1);
                            }
                        }
                        
                        String result = "";

                        for (Map.Entry entry : monthCountMap.entrySet()) {
                            String monthStr = convertNumToMonth(entry.getKey().toString());
                            result += monthStr + ": " + entry.getValue() + "\n";
                        }

                        textArea.setText(result);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        // Handle contact schedules radio button selected
        contactSchedules.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { 
                    textArea.setText("");
                    
                    try {
                        ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();
                        ObservableList<Contact> contactList = ContactDAO.getAllRecords();
                        
                        String contactName;
                        int id;
                        int contactId;
                        
                        int apptId;
                        int customerId;
                        String title;
                        String type;
                        String description;
                        String start;
                        String end;
                        
                        String result = "";

                        for (int i = 0; i < contactList.size(); i++) {
                            contactName = contactList.get(i).getNameProperty().getValue();
                            id = contactList.get(i).getIdProperty().getValue();
                            
                            for (int j = 0; j < apptList.size(); j++) {
                                contactId = apptList.get(j).getContactIdProperty().getValue();
                                
                                if (contactId == id) {
                                    apptId = apptList.get(j).getIdProperty().getValue();
                                    customerId = apptList.get(j).getContactIdProperty().getValue();
                                    title = apptList.get(j).getTitleProperty().getValue();
                                    type = apptList.get(j).getTypeProperty().getValue();
                                    description = apptList.get(j).getDescriptionProperty().getValue();
                                    start = apptList.get(j).getStartTimeProperty().getValue();
                                    end = apptList.get(j).getEndTimeProperty().getValue();

                                    result += "Contact: " + contactName + "\n";
                                    result += "ApptID: " + apptId;
                                    result += " CustomerID: " + customerId;
                                    result += " Title: " + title;
                                    result += " Type: " + type;
                                    result += " Desc: " + description;
                                    result += " Start: " + start;
                                    result += " End: " + end + "\n";
                                }
                            }
                        }

                        textArea.setText(result);

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        // Handle customers added radio button selected
        customersAdded.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    textArea.setText("");

                    try {
                        ObservableList<Customer> customerList = CustomerDAO.getAllRecordsAddedThisWeek();
                        
                        System.out.println(customerList.size());

                        int count = customerList.size();

                        textArea.setText(Integer.toString(count));

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    } 
    
    /**
     * Helper function to convert a number to the appropriate month.
     * 
     * @param month the number to be converted to a month name
     * @return      the month name
     */
    private String convertNumToMonth(String month) {
        switch (month) {
            case "01":
                return "January";
            case "02":
                return "February";
            case "03":
                return "March";
            case "04":
                return "April";
            case "05":
                return "May";
            case "06":
                return "June";
            case "07":
                return "July";
            case "08":
                return "August";
            case "09":
                return "September";
            case "10":
                return "October";
            case "11":
                return "November";
            case "12":
                return "December";
            default:
                return "";
        }
    }
    
    /**
     * Gets the report to display appointments by type.
     */
    private void handleApptsByType() {
        try {
            ObservableList<Appointment> apptList = AppointmentDAO.getAllRecords();

            int onboardCount = 0;
            int updateCount = 0;

            for (int i = 0; i < apptList.size(); i++) {
                if (apptList.get(i).getTypeProperty().getValue().equals("onboard")) {
                    onboardCount++;
                } else if (apptList.get(i).getTypeProperty().getValue().equals("update")) {
                    updateCount++;
                } else {
                    continue;
                }
            }
            
            String result = "";
            
            result += "Onboarding appointments: " + onboardCount + "\n";
            result += "Update appointments: " + updateCount + "\n";
            
            textArea.setText(result);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Receives user id data from the customer controller to display in a label.
     * 
     * @param id
     */
    public void getData(int id) {
        int userId = id;
        userIdText.setText("User ID: " + userId);
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
