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
 * Represents a country object
 * @author parkerlee
 */
public class Country {
    
    /**
     * The country id property
     */
    private IntegerProperty idProperty;

    /**
     * The country name property
     */
    private StringProperty countryNameProperty;
    
    /**
     * Sets up a country object
     */
    public Country() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryNameProperty = new SimpleStringProperty();
    }
    
    /**
     * Gets the country id property
     * @return
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     * Sets the country id
     * @param id
     */
    public void setCountryId(int id) {
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
     * Gets the country name property
     * @return
     */
    public StringProperty getCountryNameProperty() {
        return countryNameProperty;
    }
    
    /**
     * Sets the country name
     * @param name
     */
    public void setCountryCountryName(String name) {
        this.countryNameProperty.set(name);
    }

    /**
     *
     * @param countryNameProperty
     */
    public void setCountryNameProperty(StringProperty countryNameProperty) {
        this.countryNameProperty = countryNameProperty;
    }
    
}
