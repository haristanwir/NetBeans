
package com.unicacorp.interact.api.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="iPoint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numberRequested" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sessionID",
    "iPoint",
    "numberRequested"
})
@XmlRootElement(name = "getOffers")
public class GetOffers {

    @XmlElement(required = true, nillable = true)
    protected String sessionID;
    @XmlElement(required = true, nillable = true)
    protected String iPoint;
    protected int numberRequested;

    /**
     * Gets the value of the sessionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionID(String value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the iPoint property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIPoint() {
        return iPoint;
    }

    /**
     * Sets the value of the iPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIPoint(String value) {
        this.iPoint = value;
    }

    /**
     * Gets the value of the numberRequested property.
     * 
     */
    public int getNumberRequested() {
        return numberRequested;
    }

    /**
     * Sets the value of the numberRequested property.
     * 
     */
    public void setNumberRequested(int value) {
        this.numberRequested = value;
    }

}
