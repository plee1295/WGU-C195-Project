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
public class Appointment {
    
    private IntegerProperty idProperty;
    private IntegerProperty customerIdProperty;
    private IntegerProperty userIdProperty;
    private IntegerProperty contactIdProperty;
    
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;
    private StringProperty locationProperty;
    private StringProperty typeProperty;
    private StringProperty startTimeProperty;
    private StringProperty endTimeProperty;
    
    public Appointment() {
        this.idProperty = new SimpleIntegerProperty();
        this.customerIdProperty = new SimpleIntegerProperty();
        this.userIdProperty = new SimpleIntegerProperty();
        this.contactIdProperty = new SimpleIntegerProperty();
        
        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.locationProperty = new SimpleStringProperty();
        this.typeProperty = new SimpleStringProperty();
        this.startTimeProperty = new SimpleStringProperty();
        this.endTimeProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setAppointmentId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    public IntegerProperty getCustomerIdProperty() {
        return customerIdProperty;
    }
    
    public void setCustomerId(int id) {
        this.customerIdProperty.set(id);
    }

    public void setCustomerIdProperty(IntegerProperty idProperty) {
        this.customerIdProperty = idProperty;
    }
    
    public IntegerProperty getUserIdProperty() {
        return userIdProperty;
    }
    
    public void setUserId(int id) {
        this.userIdProperty.set(id);
    }

    public void setUserIdProperty(IntegerProperty idProperty) {
        this.userIdProperty = idProperty;
    }
    
    public IntegerProperty getContactIdProperty() {
        return contactIdProperty;
    }
    
    public void setContactId(int id) {
        this.contactIdProperty.set(id);
    }

    public void setContactIdProperty(IntegerProperty idProperty) {
        this.contactIdProperty = idProperty;
    }
    
    public StringProperty getTitleProperty() {
        return titleProperty;
    }
    
    public void setAppointmentTitle(String title) {
        this.titleProperty.set(title);
    }

    public void setTitleProperty(StringProperty titleProperty) {
        this.titleProperty = titleProperty;
    }
    
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }
    
    public void setAppointmentDescription(String desc) {
        this.descriptionProperty.set(desc);
    }

    public void setDescriptionProperty(StringProperty descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }
    
    public StringProperty getLocationProperty() {
        return locationProperty;
    }
    
    public void setAppointmentLocation(String location) {
        this.locationProperty.set(location);
    }

    public void setLocationProperty(StringProperty locationProperty) {
        this.locationProperty = locationProperty;
    }
    
    public StringProperty getTypeProperty() {
        return typeProperty;
    }
    
    public void setAppointmentType(String type) {
        this.typeProperty.set(type);
    }

    public void setTypeProperty(StringProperty typeProperty) {
        this.typeProperty = typeProperty;
    }
    
    public StringProperty getStartTimeProperty() {
        return startTimeProperty;
    }
    
    public void setAppointmentStartTime(String time) {
        this.startTimeProperty.set(time);
    }

    public void setStartTimeProperty(StringProperty startTimeProperty) {
        this.startTimeProperty = startTimeProperty;
    }
    
    public StringProperty getEndTimeProperty() {
        return endTimeProperty;
    }
    
    public void setAppointmentEndTime(String time) {
        this.endTimeProperty.set(time);
    }

    public void setEndTimeProperty(StringProperty endTimeProperty) {
        this.endTimeProperty = endTimeProperty;
    }
    
}
