/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.model;

/**
 *
 * @author Haris Tanwir
 */
public class ServiceResponse {

    private Boolean success = null;
    private Integer reasonCode = null;
    private String resultDesc = null;
    private Object resultObj = null;

    public ServiceResponse() {
    }

    public ServiceResponse(Integer reasonCode, String resultDesc, Object resultObj) {
        this.reasonCode = reasonCode;
        this.success = false;
        if (this.reasonCode == 0 || this.reasonCode == 1) {
            this.success = true;
        }
        this.resultDesc = resultDesc;
        this.resultObj = resultObj;
    }

}
