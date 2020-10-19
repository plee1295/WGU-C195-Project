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
 * Represents  first level division object
 * @author parkerlee
 */
public class Division {
    
    /**
     * the first level division id property
     */
    private IntegerProperty idProperty;

    /**
     * the first level division country id property
     */
    private IntegerProperty countryIdProperty;

    /**
     * the first level division name property
     */
    private StringProperty divisionNameProperty;
    
    /**
     * Sets up a first level division object
     */
    public Division() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryIdProperty = new SimpleIntegerProperty();
        this.divisionNameProperty = new SimpleStringProperty();
    }
    
    /**
     * Gets the first level division id property
     */
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    /**
     * Sets the first level division id property
     * @param id
     */
    public void setDivisionId(int id) {
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
     * Gets the first level division country id property
     */
    public IntegerProperty getCountryIdProperty() {
        return countryIdProperty;
    }
    
    /**
     * Sets the first level division country id property
     * @param id
     */
    public void setDivisionCountryId(int id) {
        this.countryIdProperty.set(id);
    }

    /**
     *
     * @param countryIdProperty
     */
    public void setCountryIdProperty(IntegerProperty countryIdProperty) {
        this.countryIdProperty = countryIdProperty;
    }
    
    /**
     * Gets the first level division name property
     */
    public StringProperty getDivisionNameProperty() {
        return divisionNameProperty;
    }
    
    /**
     * Sets the first level division name property
     * @param name
     */
    public void setDivisionDivisionName(String name) {
        this.divisionNameProperty.set(name);
    }

    /**
     *
     * @param divisionNameProperty
     */
    public void setDivisionNameProperty(StringProperty divisionNameProperty) {
        this.divisionNameProperty = divisionNameProperty;
    }
    
}
