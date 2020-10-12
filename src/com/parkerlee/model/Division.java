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
public class Division {
    
    private IntegerProperty idProperty;
    private IntegerProperty countryIdProperty;
    private StringProperty divisionNameProperty;
    
    public Division() {
        this.idProperty = new SimpleIntegerProperty();
        this.countryIdProperty = new SimpleIntegerProperty();
        this.divisionNameProperty = new SimpleStringProperty();
    }
    
    public IntegerProperty getIdProperty() {
        return idProperty;
    }
    
    public void setDivisionId(int id) {
        this.idProperty.set(id);
    }

    public void setIdProperty(IntegerProperty idProperty) {
        this.idProperty = idProperty;
    }
    
    public IntegerProperty getCountryIdProperty() {
        return countryIdProperty;
    }
    
    public void setDivisionCountryId(int id) {
        this.countryIdProperty.set(id);
    }

    public void setCountryIdProperty(IntegerProperty countryIdProperty) {
        this.countryIdProperty = countryIdProperty;
    }
    
    public StringProperty getDivisionNameProperty() {
        return divisionNameProperty;
    }
    
    public void setDivisionDivisionName(String name) {
        this.divisionNameProperty.set(name);
    }

    public void setDivisionNameProperty(StringProperty divisionNameProperty) {
        this.divisionNameProperty = divisionNameProperty;
    }
    
}
