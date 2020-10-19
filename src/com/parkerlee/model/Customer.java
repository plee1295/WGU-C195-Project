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
 * Represents a customer object
 * @author parkerlee
 */
public class Customer {
    
    /**
     * the customer id property
     */
    private IntegerProperty idProperty;

    /**
     * the customer first level division id property
     */
    private IntegerProperty divisionIdProperty;

    /**
     * the customer name property
     */
    private StringProperty nameProperty;

    /**
     * the customer address property
     */
    private StringProperty addressProperty;

    /**
     * the customer postal code property
     */
    private StringProperty postalCodeProperty;

    /**
     * the customer phone number property
     */
    private StringProperty phoneNumberProperty;
    
    /**
     * Sets up a customer object
     */
    public Customer() {
        this.idProperty = new SimpleIntegerProperty();
        this.divisionIdProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.addressProperty = new SimpleStringProperty();
        this.postalCodeProperty = new SimpleStringProperty();
        this.phoneNumberProperty = new SimpleStringProperty();
    }
    
    /**
     * Gets the customer id property
     * @return  the customer id
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     *
     * @param id
     */
    public void setCustomerId(int id) {
        this.idProperty.set(id);
    }

    /**
     *
     * @param idProperty
     */
    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    /**
     * Gets the customer first level division id property
     * @return  the division id
     */
    public IntegerProperty getDivisionIdProperty() {
        return divisionIdProperty;
    }
    
    /**
     *
     * @param id
     */
    public void setCustomerDivisionId(int id) {
        this.divisionIdProperty.set(id);
    }

    /**
     *
     * @param divisionIdProperty
     */
    public void setDivisionIdProperty(IntegerProperty divisionIdProperty) {
        this.divisionIdProperty = divisionIdProperty;
    }
    
    /**
     * Gets the customer name property
     * @return  the customer name
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }
    
    /**
     *
     * @param name
     */
    public void setCustomerName(String name) {
        this.nameProperty.set(name);
    }

    /**
     *
     * @param nameProperty
     */
    public void setNameProperty(StringProperty nameProperty) {
        this.nameProperty = nameProperty;
    }
    
    /**
     * Gets the customer address property
     * @return  the customer address
     */
    public StringProperty getAddressProperty() {
        return addressProperty;
    }
    
    /**
     *
     * @param address
     */
    public void setCustomerAddress(String address) {
        this.addressProperty.set(address);
    }

    /**
     *
     * @param addressProperty
     */
    public void setAddressProperty(StringProperty addressProperty) {
        this.addressProperty = addressProperty;
    }
    
    /**
     * Gets the customer postal code property
     * @return  the customer postal code
     */
    public StringProperty getPostalCodeProperty() {
        return postalCodeProperty;
    }
    
    /**
     *
     * @param postalCode
     */
    public void setCustomerPostalCode(String postalCode) {
        this.postalCodeProperty.set(postalCode);
    }

    /**
     *
     * @param postalCodeProperty
     */
    public void setPostalCodeProperty(StringProperty postalCodeProperty) {
        this.postalCodeProperty = postalCodeProperty;
    }
    
    /**
     * Gets the customer phone number property
     * @return  the customer phone number
     */
    public StringProperty getPhoneNumberProperty() {
        return phoneNumberProperty;
    }
    
    /**
     *
     * @param phoneNumber
     */
    public void setCustomerPhoneNumber(String phoneNumber) {
        this.phoneNumberProperty.set(phoneNumber);
    }

    /**
     *
     * @param phoneNumberProperty
     */
    public void setPhoneNumberProperty(StringProperty phoneNumberProperty) {
        this.phoneNumberProperty = phoneNumberProperty;
    }
    
}
