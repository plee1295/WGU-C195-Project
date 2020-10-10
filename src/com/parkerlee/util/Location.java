/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.util;

import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author parkerlee
 */
public class Location {
    
    public static String getUserLocationInfo() {
        
        Locale currentLocale = Locale.getDefault();
        String country = currentLocale.getDisplayCountry();
        
        TimeZone timeZone = TimeZone.getDefault();
        String zone = timeZone.getDisplayName(true, 0);
        
        String location = country + " (" + zone + ")";
        
        return location;
        
    }
    
}
