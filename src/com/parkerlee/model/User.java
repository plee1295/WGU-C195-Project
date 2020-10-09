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
public class User {
    
    private IntegerProperty idProperty;
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    
    public User() {
        this.idProperty = new SimpleIntegerProperty();
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setUserId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }
    
    public void setUserUsername(String username) {
        this.usernameProperty.set(username);
    }

    public void setUsernameProperty(StringProperty usernameProperty) {
        this.usernameProperty = usernameProperty;
    }
    
    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }
    
    public void setUserPassword(String password) {
        this.passwordProperty.set(password);
    }

    public void setPasswordProperty(StringProperty passwordProperty) {
        this.passwordProperty = passwordProperty;
    }
    
}
