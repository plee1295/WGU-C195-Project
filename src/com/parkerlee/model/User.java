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
 * Represents a user object
 * @author parkerlee
 */
public class User {
    
    /**
     * the user id property
     */
    private IntegerProperty idProperty;

    /**
     * the user username property
     */
    private StringProperty usernameProperty;

    /**
     * the user password property
     */
    private StringProperty passwordProperty;
    
    /**
     * Sets up a user object
     */
    public User() {
        this.idProperty = new SimpleIntegerProperty();
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
    }
    
    /**
     * Gets the user id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     *
     * @param id
     */
    public void setUserId(int id) {
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
     * Gets the user username property
     */
    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }
    
    /**
     *
     * @param username
     */
    public void setUserUsername(String username) {
        this.usernameProperty.set(username);
    }

    /**
     *
     * @param usernameProperty
     */
    public void setUsernameProperty(StringProperty usernameProperty) {
        this.usernameProperty = usernameProperty;
    }
    
    /**
     * Gets the user password property
     */
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }
    
    /**
     *
     * @param password
     */
    public void setUserPassword(String password) {
        this.passwordProperty.set(password);
    }

    /**
     *
     * @param passwordProperty
     */
    public void setPasswordProperty(StringProperty passwordProperty) {
        this.passwordProperty = passwordProperty;
    }
    
}
