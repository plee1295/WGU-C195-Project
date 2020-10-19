/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.Appointment;
import com.parkerlee.model.AppointmentDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class for schedule
 *
 * @author parkerlee
 */
public class ScheduleController implements Initializable {
    
    /**
     * The radio button to show schedule for this week
     */
    @FXML
    private RadioButton thisWeek;

    /**
     * The radio button to show schedule for this month
     */
    @FXML
    private RadioButton thisMonth;

    /**
     * The radio button to show all appointments schedule
     */
    @FXML
    private RadioButton all;
    
    /**
     * The table view to display appointment data
     */
    @FXML
    private TableView<Appointment> appointmentTableView;
    
    /**
     * The column to display appointment id
     */
    @FXML
    private TableColumn<Appointment, Integer> apptIdColumn;

    /**
     * The column to display appointment title
     */
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    /**
     * The column to display appointment description
     */
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    /**
     * The column to display appointment location
     */
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    /**
     * The column to display appointment contact id
     */
    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;

    /**
     * The column to display appointment type
     */
    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /**
     * The column to display appointment start date/time
     */
    @FXML
    private TableColumn<Appointment, String> startColumn;

    /**
     * The column to display appointment end date/time
     */
    @FXML
    private TableColumn<Appointment, String> endColumn;

    /**
     * The column to display appointment customer id
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
    
    /**
     * The label to display the current user id
     */
    @FXML
    private Label userIdText;

    /**
     * Initializes the controller class. Sets up toggle group and radio buttons.
     * Sets up table view and columns. Gets appointment data to display. Handles
     * selection of each radio button.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // setup radio buttons
        final ToggleGroup group = new ToggleGroup();
        all.setToggleGroup(group);
        all.setSelected(true);
        thisWeek.setToggleGroup(group);
        thisMonth.setToggleGroup(group);
        
        apptIdColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitleProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        locationColumn.setCellValueFactory(cellData -> cellData.getValue().getLocationProperty());
        contactIdColumn.setCellValueFactory(cellData -> cellData.getValue().getContactIdProperty().asObject());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().getTypeProperty());
        startColumn.setCellValueFactory(cellData -> cellData.getValue().getStartTimeProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().getEndTimeProperty());
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerIdProperty().asObject());
        
        ObservableList<Appointment> appointmentList;
        try {
            appointmentList = AppointmentDAO.getAllRecords();
            populateTable(appointmentList);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        thisWeek.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { 
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecordsInNext7Days();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        thisMonth.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { 
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecordsInNextMonth();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        all.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { 
                    ObservableList<Appointment> appointmentList;
                    try {
                        appointmentList = AppointmentDAO.getAllRecords();
                        populateTable(appointmentList);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    /**
     * Displays appointment data in the table view
     * 
     * @param appointmentList
     */
    private void populateTable(ObservableList<Appointment> appointmentList) {
        appointmentTableView.setItems(appointmentList);
    }
    
    /**
     * Receives user id data from the customer controller.
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
