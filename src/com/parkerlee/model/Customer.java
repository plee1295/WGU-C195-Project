/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author parkerlee
 */
public class Customer {
    
    private IntegerProperty idProperty;
    private IntegerProperty divisionIdProperty;
    private StringProperty nameProperty;
    private StringProperty addressProperty;
    private StringProperty postalCodeProperty;
    private StringProperty phoneNumberProperty;
    
    public Customer() {
        this.idProperty = new SimpleIntegerProperty();
        this.divisionIdProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.addressProperty = new SimpleStringProperty();
        this.postalCodeProperty = new SimpleStringProperty();
        this.phoneNumberProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setCustomerId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    public IntegerProperty getDivisionIdProperty() {
        return divisionIdProperty;
    }
    
    public void setCustomerDivisionId(int id) {
        this.divisionIdProperty.set(id);
    }

    public void setDivisionIdProperty(IntegerProperty divisionIdProperty) {
        this.divisionIdProperty = divisionIdProperty;
    }
    
    public StringProperty getNameProperty() {
        return nameProperty;
    }
    
    public void setCustomerName(String name) {
        this.nameProperty.set(name);
    }

    public void setNameProperty(StringProperty nameProperty) {
        this.nameProperty = nameProperty;
    }
    
    public StringProperty getAddressProperty() {
        return addressProperty;
    }
    
    public void setCustomerAddress(String address) {
        this.addressProperty.set(address);
    }

    public void setAddressProperty(StringProperty addressProperty) {
        this.addressProperty = addressProperty;
    }
    
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }
    
    public void setCustomerPostalCode(String postalCode) {
        this.postalCodeProperty.set(postalCode);
    }

    public void setPostalCodeProperty(StringProperty postalCodeProperty) {
        this.postalCodeProperty = postalCodeProperty;
    }
    
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }
    
    public void setCustomerPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty.set(phoneNumber);
    }

    public void setPhoneNumberProperty(StringProperty phoneNumberProperty) {
        this.phoneNumberProperty = phoneNumberProperty;
    }
    
}
