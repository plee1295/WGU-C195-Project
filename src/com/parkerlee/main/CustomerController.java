/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.main;

import com.parkerlee.model.Country;
import com.parkerlee.model.CountryDAO;
import com.parkerlee.model.Customer;
import com.parkerlee.model.CustomerDAO;
import com.parkerlee.model.Division;
import com.parkerlee.model.DivisionDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CustomerController {

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerAddressColumn;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeColumn;
    
    @FXML
    private TableColumn<Customer, Integer> customerDivisionColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumberColumn;

    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerAddressTextField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> firstLevelDivisionComboBox;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Label functionTitleText;
    
    @FXML
    public void initialize() throws Exception {
        
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
    
    private void populateTable(ObservableList<Customer> customerList) {
        customerTableView.setItems(customerList);
    }
    
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
    
    // TODO: add validation
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
        
            CustomerDAO.insertCustomer(customerName, customerAddress, selectedDivisionId, customerPostalCode, customerPhoneNumber);
            
            ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
            populateTable(customerList);
            
            clearTextFields();
            
        } catch (SQLException e) {
            System.out.println("Error adding employee to database: " + e);
            e.printStackTrace();
            throw e;
        }
        
        
    }
    
    @FXML
    void updateCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String name = customerNameTextField.getText();
            String address = customerAddressTextField.getText();
            String zip = postalCodeTextField.getText();
            String phone = phoneNumberTextField.getText();
            
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            int customerId = customer.getIdProperty().getValue();
            
            String customerFLD = firstLevelDivisionComboBox.getValue();
            int divisionId = DivisionDAO.getSelectedDivisionId(customerFLD);
            
            CustomerDAO.updateCustomer(customerId, name, address, divisionId, zip, phone);
            
            ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
            populateTable(customerList);
            
        } catch (SQLException e) {
            System.out.println("Error occurred while updating customer data: " + e);
            e.printStackTrace();
            throw e;
        }
    }
    
    @FXML
    void deleteCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            int id = customer.getIdProperty().getValue();
            
            CustomerDAO.deleteCustomer(id);
            
            ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
            populateTable(customerList);
            
        } catch (SQLException e) {
            System.out.println("Error occurred while deleting employee from database: " + e);
            e.printStackTrace();
            throw e;
        }
        
        clearTextFields();
    }
    
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

}

