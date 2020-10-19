/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkerlee.util;

import java.util.Locale;

/**
 *
 * @author parkerlee
 */
public class Translator {
    
    /**
     * Gets the default language of the system user
     * @return  a string containing the default system language
     */
    public static String getUserLanguage() {
        String currentLocale = Locale.getDefault().getLanguage();
        
        return currentLocale;
    }
    
}
