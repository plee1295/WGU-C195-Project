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
    private TableView<?> customerTableView;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> customerAddressColumn;

    @FXML
    private TableColumn<?, ?> customerPostalCodeColumn;

    @FXML
    private TableColumn<?, ?> customerPhoneNumberColumn;

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

          // Initialize country names combo box
          ObservableList<Country> countryList = CountryDAO.getAllRecords();
          ObservableList<String> countryNames = FXCollections.observableArrayList();
          
          countryList.forEach((country) -> {
              String countryToAdd = country.getCountryNameProperty().getValue();
              countryNames.add(countryToAdd);
          });
          
          countryComboBox.setItems(countryNames);
        
    }
    
    // TODO: show customers in tableview
    // TODO: add validation
    // TODO: clear values after successful insertion
    @FXML
    void addCustomerButtonPressed(ActionEvent event) throws ClassNotFoundException, SQLException {
        try {
            String customerName = customerNameTextField.getText();
            String customerAddress = customerAddressColumn.getText();
            String customerCountry = countryComboBox.getValue();
            String customerPostalCode = postalCodeTextField.getText();
            String customerPhoneNumber = phoneNumberTextField.getText();
            
            String customerFLD = firstLevelDivisionComboBox.getValue();
            int selectedDivisionId = DivisionDAO.getSelectedDivisionId(customerFLD);
        
            CustomerDAO.insertCustomer(customerName, customerAddress, selectedDivisionId, customerPostalCode, customerPhoneNumber);
            
//            ObservableList<Customer> customerList = CustomerDAO.getAllRecords();
//            populateTable(customerList);
            
        } catch (SQLException e) {
            System.out.println("Error adding employee to database: " + e);
            e.printStackTrace();
            throw e;
        }
        
        
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

