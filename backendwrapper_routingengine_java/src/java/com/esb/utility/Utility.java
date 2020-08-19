/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.utility;

import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class Utility {

    private static final Logger logger = ServiceLogger.getLogger(Utility.class.getName());

    public static String getProperty(String propertyName) {
        String propertyValue = null;
        try {
            propertyValue = System.getenv(propertyName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (propertyValue == null) {
            logger.error("Prop: " + propertyName + " not found");
        }
        return propertyValue;
    }
}
