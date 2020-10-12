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
public class Country {
    
    private IntegerProperty idProperty;
    private StringProperty countryNameProperty;
    
    public Country() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryNameProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setCountryId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    public StringProperty getCountryNameProperty() {
        return countryNameProperty;
    }
    
    public void setCountryCountryName(String name) {
        this.countryNameProperty.set(name);
    }

    public void setCountryNameProperty(StringProperty countryNameProperty) {
        this.countryNameProperty = countryNameProperty;
    }
    
}
