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
 * Represents a contact object
 * 
 * @author parkerlee
 */
public class Contact {
    
    /**
     * The contact id property
     */
    private IntegerProperty idProperty;

    /**
     * The contact name property
     */
    private StringProperty nameProperty;

    /**
     * The contact email property
     */
    private StringProperty emailProperty;
    
    /**
     * Sets up a contact object
     */
    public Contact() {
        this.idProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
    }
    
    /**
     * Gets the contact id property
     * @return
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     * Sets the contact id property
     * @param id
     */
    public void setContactId(int id) {
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
     * Gets the contact name property
     * @return
     */
    public StringProperty getNameProperty() {
        return nameProperty;
    }
    
    /**
     * Sets the contact name property
     * @param name
     */
    public void setContactName(String name) {
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
     * Gets the contact email property
     * @return
     */
    public StringProperty getEmailProperty() {
        return emailProperty;
    }
    
    /**
     * Sets the contact email property
     * @param email
     */
    public void setContactEmail(String email) {
        this.emailProperty.set(email);
    }

    /**
     *
     * @param emailProperty
     */
    public void setEmailProperty(StringProperty emailProperty) {
        this.emailProperty = emailProperty;
    }
    
}
