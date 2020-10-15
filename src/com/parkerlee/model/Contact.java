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
public class Contact {
    
    private IntegerProperty idProperty;
    private StringProperty nameProperty;
    private StringProperty emailProperty;
    
    public Contact() {
        this.idProperty = new SimpleIntegerProperty();
        this.nameProperty = new SimpleStringProperty();
        this.emailProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setContactId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    public StringProperty getNameProperty() {
        return nameProperty;
    }
    
    public void setContactName(String name) {
        this.nameProperty.set(name);
    }

    public void setNameProperty(StringProperty nameProperty) {
        this.nameProperty = nameProperty;
    }
    
    public StringProperty getEmailProperty() {
        return emailProperty;
    }
    
    public void setContactEmail(String email) {
        this.emailProperty.set(email);
    }

    public void setEmailProperty(StringProperty emailProperty) {
        this.emailProperty = emailProperty;
    }
    
}
