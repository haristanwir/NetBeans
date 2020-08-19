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
public class ServiceResponse {

    private Integer reasonCode = null;
    private String resultDesc = null;

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

}
