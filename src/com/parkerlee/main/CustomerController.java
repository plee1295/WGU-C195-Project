/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.AppointmentDAO;
import com.parkerlee.model.Country;
import com.parkerlee.model.CountryDAO;
import com.parkerlee.model.Customer;
import com.parkerlee.model.CustomerDAO;
import com.parkerlee.model.Division;
import com.parkerlee.model.DivisionDAO;
import com.parkerlee.util.CustomerSingleton;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for customers
 * 
 * @author parkerlee
 */
public class CustomerController {

    /**
     * The table view that display customer data
     */
    @FXML
    private TableView<Customer> customerTableView;

    /**
     * The column that displays customer id data
     */
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    /**
     * The column that displays customer name data
     */
    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    /**
     * The column that displays customer address data
     */
    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    /**
     * The column that displays customer postal code data
     */
    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;
    
    /**
     * The column that displays customer first level division data
     */
    @FXML
    private TableColumn<Customer, Integer> customerDivisionColumn;

    /**
     * The column that displays customer phone number data
     */
    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    /**
     * The text field that collects and displays customer name data
     */
    @FXML
    private TextField customerNameTextField;

    /**
     * The text field that collects and displays customer address data
     */
    @FXML
    private TextField customerAddressTextField;

    /**
     * The combo box that collects and displays customer country data
     */
    @FXML
    private ComboBox<String> countryComboBox;

    /**
     * The combo box that collects and displays customer first level division data
     */
    @FXML
    private ComboBox<String> firstLevelDivisionComboBox;

    /**
     * The text field that collects and displays customer postal code data
     */
    @FXML
    private TextField postalCodeTextField;

    /**
     * The text field that collects and displays customer phone number data
     */
    @FXML
    private TextField phoneNumberTextField;

    /**
     * The label that displays the current function of the window
     */
    @FXML
    private Label functionTitleText;
    
    /**
     * The label that displays the current user id
     */
    @FXML
    private Label userIdText;
    
    /**
     * The button that handles the addition of a new customer to the database
     */
    @FXML
    private Button addCustomerButton;

    /**
     * The button that handles the updating of a customer in the database
     */
    @FXML
    private Button updateCustomerButton;

    /**
     * The button that handles deleting a customer from the database
     */
    @FXML
    private Button deleteCustomerButton;

    /**
     * The button that handles getting the selected customer data and navigating to the appointment view
     */
    @FXML
    private Button getAppointmentsButton;
    
    /**
     * Initializes the customer controller class.
     * 
     * @throws Exception
     */
    @FXML
    public void initialize() throws Exception {
        
        addCustomerButton.setDisable(false);
        updateCustomerButton.setDisable(true);
        deleteCustomerButton.setDisable(true);
        getAppointmentsButton.setDisable(true);
        
        // Initialize starting values for customerTableView
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().getIdProperty().asObject());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        customerAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getAddressProperty());
        customerPostalCodeColumn.setCellValueFactory(cellData -> cellData.getValue().getPostalCodeProperty());
        customerDivisionColumn.setCellValueFactory(cellData -> cellData.getValue().getDivisionIdProperty().asObject());
        customerPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getPhoneNumberProperty());
        
        ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
        populateTable(customerList);

        // Initialize country names combo box
        ObservableList<Country> countryList = CountryDAO.getAllRecords();
        ObservableList<String> countryNames = FXCollections.observableArrayList();
          
        countryList.forEach((country) -> {
            String countryToAdd = country.getCountryNameProperty().getValue();
            countryNames.add(countryToAdd);
        });
          
        countryComboBox.setItems(countryNames);
        
        // get selected customer data
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateCustomerButton.setDisable(false);
                deleteCustomerButton.setDisable(false);
                getAppointmentsButton.setDisable(false);
                addCustomerButton.setDisable(true);
                
                functionTitleText.setText("Update/Delete");
                
                Customer customer = customerTableView.getSelectionModel().getSelectedItem();
                
                String name = customer.getNameProperty().getValue();
                String address = customer.getAddressProperty().getValue();
                String zip = customer.getPostalCodeProperty().getValue();
                String phone = customer.getPhoneNumberProperty().getValue();
                
                // TODO
                // get division value where division_id = id AND SET
                int divisionId = customer.getDivisionIdProperty().getValue();
                try {
                    String divisionName = DivisionDAO.getDivisionName(divisionId);
                    int countryId = CountryDAO.getCountryIdFromDivisionId(divisionId);
                    String countryName = CountryDAO.getCountryName(countryId);
                    
                    firstLevelDivisionComboBox.setValue(divisionName);
                    countryComboBox.setValue(countryName);
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                customerNameTextField.setText(name);
                customerAddressTextField.setText(address);
                postalCodeTextField.setText(zip);
                phoneNumberTextField.setText(phone);
            }
        });
        
    }
    
    /**
     * Displays customer data in the table view
     * 
     * @param customerList  the list of customers used to populate the table view
     */
    private void populateTable(ObservableList<Customer> customerList) {
        customerTableView.setItems(customerList);
    }
    
    /**
     * Clears all of the text fields in the window
     */
    private void clearTextFields() {
        customerNameTextField.clear();
        customerAddressTextField.clear();
        countryComboBox.getSelectionModel().clearSelection();
        countryComboBox.setPromptText("Select Country");
        firstLevelDivisionComboBox.getSelectionModel().clearSelection();
        firstLevelDivisionComboBox.setPromptText("Select Division");
        postalCodeTextField.clear();
        phoneNumberTextField.clear();
    }
    
    /**
     * Receives data from the login controller to display in the window
     * 
     * @param id    the current user id sent from the login controller
     */
    public void getData(int id) {
        int userId = id;
        userIdText.setText("User ID: " + id);
    }
    
    /**
     * Validates customer data from the text fields
     * 
     * @param name      the customer name to be validated
     * @param address   the customer address to be validated
     * @param country   the customer country to be validated
     * @param zip       the customer postal code to be validated
     * @param phone     the customer phone number to be validated
     * @param fld       the customer first level division to be validated
     * @return          whether or not the customer data is valid
     */
    private boolean isCustomerValid(String name, String address, String country, String zip, String phone, String fld) {
        if (name.isBlank() || address.isBlank() || country.isBlank() || zip.isBlank() || phone.isBlank() || fld.isBlank()) {
            return false;
        }
        return true;
    }
    
    /**
     * Handles the add customer button press. Validates the input data and updates the database with
     * the new customer.
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void addCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String customerName = customerNameTextField.getText();
            String customerAddress = customerAddressTextField.getText();
            String customerCountry = countryComboBox.getValue();
            String customerPostalCode = postalCodeTextField.getText();
            String customerPhoneNumber = phoneNumberTextField.getText();
            
            String customerFLD = firstLevelDivisionComboBox.getValue();
            int selectedDivisionId = DivisionDAO.getSelectedDivisionId(customerFLD);
            
            boolean isValidCustomer = isCustomerValid(customerName, customerAddress, customerCountry, customerPostalCode, customerPhoneNumber, customerFLD);
        
            if (isValidCustomer) {
                CustomerDAO.insertCustomer(customerName, customerAddress, selectedDivisionId, customerPostalCode, customerPhoneNumber);

                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTable(customerList);

                clearTextFields();
                
                addCustomerButton.setDisable(false);
                updateCustomerButton.setDisable(true);
                deleteCustomerButton.setDisable(true);
                getAppointmentsButton.setDisable(true);
                
            } else {
                Alert alert = new Alert(AlertType.WARNING, "No field can be left blank.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            
            
        } catch (SQLException e) {
            System.out.println("Error adding employee to database: " + e);
            e.printStackTrace();
            throw e;
        } 
    }
    
    /**
     * Handles the update customer button press. Validates the input data and updates the database
     * with the selected customer. 
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void updateCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String name = customerNameTextField.getText();
            String address = customerAddressTextField.getText();
            String zip = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            String country = countryComboBox.getValue();
            
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            int customerId = customer.getIdProperty().getValue();
            
            String customerFLD = firstLevelDivisionComboBox.getValue();
            int divisionId = DivisionDAO.getSelectedDivisionId(customerFLD);
            
            boolean isValidCustomer = isCustomerValid(name, address, country, zip, phone, customerFLD);
            
            if (isValidCustomer) {
               CustomerDAO.updateCustomer(customerId, name, address, divisionId, zip, phone);
            
                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTable(customerList);
            
                clearTextFields(); 
                
                addCustomerButton.setDisable(false);
                updateCustomerButton.setDisable(true);
                deleteCustomerButton.setDisable(true);
                getAppointmentsButton.setDisable(true);
                
            } else {
                Alert alert = new Alert(AlertType.WARNING, "Unable to update customer.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            
            
            
        } catch (SQLException e) {
            System.out.println("Error occurred while updating customer data: " + e);
            e.printStackTrace();
            throw e;
        }
        
        functionTitleText.setText("Add");
    }
    
    /**
     * Handles the delete customer button press. Removes the selected customer from the database.
     * 
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @FXML
    void deleteCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        int id = customer.getIdProperty().getValue();
        
        if (!AppointmentDAO.hasAppointments(id)) {
            try {
                CustomerDAO.deleteCustomer(id);
                ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
                populateTable(customerList);
            } catch (SQLException e) {
                System.out.println("Error occurred while deleting employee from database: " + e);
                e.printStackTrace();
                throw e;
            }
        
            clearTextFields();
            
            addCustomerButton.setDisable(false);
            updateCustomerButton.setDisable(true);
            deleteCustomerButton.setDisable(true);
            getAppointmentsButton.setDisable(true);
        
            Alert alert = new Alert(AlertType.INFORMATION, "customer has been successfully deleted!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            
            functionTitleText.setText("Add");
        } else {
            Alert alert = new Alert(AlertType.WARNING, "Cannot delete customer with upcoming appointments.", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }  
    }
    
    /**
     * Handles the selection of the country combo box. Populates the first level
     * division combo box with the correct data. 
     * 
     * @param event
     * @throws Exception
     */
    @FXML
    void countrySelected(ActionEvent event) throws Exception {
        String selectedCountry = countryComboBox.getValue();
        System.out.println(countryComboBox.getValue());
        
        int selectedId = CountryDAO.getSelectedCountryId(selectedCountry);
        
        // get all first level divisions where country_id = id (from above)
        ObservableList<Division> divisionList = DivisionDAO.getAllRecords();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        
        divisionList.forEach((division) -> {
            if (selectedId == division.getCountryIdProperty().getValue()) {
                String divisionToAdd = division.getDivisionNameProperty().getValue();
                divisionNames.add(divisionToAdd);
            }
        });
        
        firstLevelDivisionComboBox.setItems(divisionNames);
    }
    
    /**
     * Handles the get appointments button pressed. Navigates the user to the appointment view
     * and sends the necessary data associated with the selected customer and current user. 
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    void getAppointmentsButtonPressed (ActionEvent event) throws IOException {
        try {
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            CustomerSingleton.id = customer.getIdProperty().getValue();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AppointmentView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            AppointmentController controller = loader.getController();
            
            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            
            controller.getData(customer, userId);
            
            stage.setScene(scene);
            stage.show();       
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the schedule button pressed. Navigates the user to the schedule view 
     * and sends the necessary data associated with the current user. 
     * 
     * @param event
     */
    @FXML
    void scheduleButtonPressed (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScheduleView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ScheduleController controller = loader.getController();
            
            String userIdStr = userIdText.getText();
            int userId = Integer.parseInt(userIdStr.split(" ")[2]);
            controller.getData(userId);
            
            stage.setScene(scene);
            stage.show();       
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the logout button pressed. Navigates the user back to the login view
     * and effectively logs out the current user. 
     *  
     * @param event
     */
    @FXML
    void logoutButtonPressed (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            LoginController controller = loader.getController();
            stage.setScene(scene);
            stage.show();       
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the get reports button pressed. Navigates the user to the report view
     * and sends the necessary data associated with the current user. 
     * 
     * @param event
     */
    @FXML
    void getReportsButtonPressed (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ReportController controller = loader.getController();
            
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

