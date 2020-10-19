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
 * Represents an appointment object
 * 
 * @author parkerlee
 */
public class Appointment {
    
    /**
     * The id property of an appointment
     */
    private IntegerProperty idProperty;

    /**
     * The customer id property of an appointment
     */
    private IntegerProperty customerIdProperty;

    /**
     * The user id property of an appointment
     */
    private IntegerProperty userIdProperty;

    /**
     * The contact id property of an appointment
     */
    private IntegerProperty contactIdProperty;
    
    /**
     * The title property of an appointment
     */
    private StringProperty titleProperty;

    /**
     * The description property of an appointment
     */
    private StringProperty descriptionProperty;

    /**
     * The location property of an appointment
     */
    private StringProperty locationProperty;

    /**
     * The type property of an appointment
     */
    private StringProperty typeProperty;

    /**
     * The start time property of an appointment
     */
    private StringProperty startTimeProperty;

    /**
     * The end time property of an appointment
     */
    private StringProperty endTimeProperty;
    
    /**
     * Sets up an appointment object
     */
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
    
    /**
     * Gets the appointment id property
     * 
     * @return  the appointment id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     * Sets the appointment id
     * 
     * @param id    the appointment id
     */
    public void setAppointmentId(int id) {
        this.idProperty.set(id);
    }

    /**
     * Sets the appointment id property
     * 
     * @param idProperty    the appointment id property
     */
    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    /**
     * Gets the customer id
     * 
     * @return  the customer id property
     */
    public IntegerProperty getCustomerIdProperty() {
        return customerIdProperty;
    }
    
    /**
     * Sets the customer id
     * 
     * @param id    the customer id
     */
    public void setCustomerId(int id) {
        this.customerIdProperty.set(id);
    }

    /**
     * Sets the customer id property
     * 
     * @param idProperty    the customer id property
     */
    public void setCustomerIdProperty(IntegerProperty idProperty) {
        this.customerIdProperty = idProperty;
    }
    
    /**
     * Gets the user id property
     * 
     * @return  the user id property
     */
    public IntegerProperty getUserIdProperty() {
        return userIdProperty;
    }
    
    /**
     * Sets the user id
     * 
     * @param id    the user id
     */
    public void setUserId(int id) {
        this.userIdProperty.set(id);
    }

    /**
     * Sets the user id property
     * 
     * @param idProperty    the user id property
     */
    public void setUserIdProperty(IntegerProperty idProperty) {
        this.userIdProperty = idProperty;
    }
    
    /**
     * Gets the contact id property
     * 
     * @return  the contact id property
     */
    public IntegerProperty getContactIdProperty() {
        return contactIdProperty;
    }
    
    /** 
     * Sets the contact id
     * 
     * @param id    the contact id
     */
    public void setContactId(int id) {
        this.contactIdProperty.set(id);
    }

    /**
     * Sets the contact id property
     * 
     * @param idProperty    the contact id property
     */
    public void setContactIdProperty(IntegerProperty idProperty) {
        this.contactIdProperty = idProperty;
    }
    
    /**
     * Gets the title property
     * 
     * @return  the title property
     */
    public StringProperty getTitleProperty() {
        return titleProperty;
    }
    
    /**
     * Sets the appointment title
     * 
     * @param title
     */
    public void setAppointmentTitle(String title) {
        this.titleProperty.set(title);
    }

    /**
     * Sets the appointment title property
     * 
     * @param titleProperty
     */
    public void setTitleProperty(StringProperty titleProperty) {
        this.titleProperty = titleProperty;
    }
    
    /**
     * Gets the description property
     * 
     * @return  the description property
     */
    public StringProperty getDescriptionProperty() {
        return descriptionProperty;
    }
    
    /**
     * Sets the appointment description
     * 
     * @param desc  the appointment description
     */
    public void setAppointmentDescription(String desc) {
        this.descriptionProperty.set(desc);
    }

    /**
     * Sets the description property
     * 
     * @param descriptionProperty   the description property
     */
    public void setDescriptionProperty(StringProperty descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }
    
    /**
     * Gets the location property
     * 
     * @return  the location property
     */
    public StringProperty getLocationProperty() {
        return locationProperty;
    }
    
    /**
     * Sets the appointment location
     * 
     * @param location  the appointment location
     */
    public void setAppointmentLocation(String location) {
        this.locationProperty.set(location);
    }

    /**
     * Sets the location property
     * 
     * @param locationProperty  the location property
     */
    public void setLocationProperty(StringProperty locationProperty) {
        this.locationProperty = locationProperty;
    }
    
    /**
     *
     * @return
     */
    public StringProperty getTypeProperty() {
        return typeProperty;
    }
    
    /**
     *
     * @param type
     */
    public void setAppointmentType(String type) {
        this.typeProperty.set(type);
    }

    /**
     *
     * @param typeProperty
     */
    public void setTypeProperty(StringProperty typeProperty) {
        this.typeProperty = typeProperty;
    }
    
    /**
     *
     * @return
     */
    public StringProperty getStartTimeProperty() {
        return startTimeProperty;
    }
    
    /**
     *
     * @param time
     */
    public void setAppointmentStartTime(String time) {
        this.startTimeProperty.set(time);
    }

    /**
     *
     * @param startTimeProperty
     */
    public void setStartTimeProperty(StringProperty startTimeProperty) {
        this.startTimeProperty = startTimeProperty;
    }
    
    /**
     *
     * @return
     */
    public StringProperty getEndTimeProperty() {
        return endTimeProperty;
    }
    
    /**
     *
     * @param time
     */
    public void setAppointmentEndTime(String time) {
        this.endTimeProperty.set(time);
    }

    /**
     *
     * @param endTimeProperty
     */
    public void setEndTimeProperty(StringProperty endTimeProperty) {
        this.endTimeProperty = endTimeProperty;
    }
    
}
