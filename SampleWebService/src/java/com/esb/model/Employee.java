/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Haris Tanwir
 */
@XmlRootElement
public class Employee {

    private String empID = null;
    private String empName = null;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

}
