
package com.unicacorp.interact.api.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advisoryMessages" type="{http://api.interact.unicacorp.com/xsd}AdvisoryMessage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allOfferLists" type="{http://api.interact.unicacorp.com/xsd}OfferList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="apiVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="offerList" type="{http://api.interact.unicacorp.com/xsd}OfferList" minOccurs="0"/>
 *         &lt;element name="profileRecord" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sessionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", propOrder = {
    "advisoryMessages",
    "allOfferLists",
    "apiVersion",
    "offerList",
    "profileRecord",
    "sessionID",
    "statusCode"
})
public class Response {

    @XmlElement(nillable = true)
    protected List<AdvisoryMessage> advisoryMessages;
    @XmlElement(nillable = true)
    protected List<OfferList> allOfferLists;
    @XmlElementRef(name = "apiVersion", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> apiVersion;
    @XmlElementRef(name = "offerList", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<OfferList> offerList;
    @XmlElement(nillable = true)
    protected List<NameValuePairImpl> profileRecord;
    @XmlElementRef(name = "sessionID", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sessionID;
    protected Integer statusCode;

    /**
     * Gets the value of the advisoryMessages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the advisoryMessages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdvisoryMessages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdvisoryMessage }
     * 
     * 
     */
    public List<AdvisoryMessage> getAdvisoryMessages() {
        if (advisoryMessages == null) {
            advisoryMessages = new ArrayList<AdvisoryMessage>();
        }
        return this.advisoryMessages;
    }

    /**
     * Gets the value of the allOfferLists property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allOfferLists property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllOfferLists().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OfferList }
     * 
     * 
     */
    public List<OfferList> getAllOfferLists() {
        if (allOfferLists == null) {
            allOfferLists = new ArrayList<OfferList>();
        }
        return this.allOfferLists;
    }

    /**
     * Gets the value of the apiVersion property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the value of the apiVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setApiVersion(JAXBElement<String> value) {
        this.apiVersion = value;
    }

    /**
     * Gets the value of the offerList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OfferList }{@code >}
     *     
     */
    public JAXBElement<OfferList> getOfferList() {
        return offerList;
    }

    /**
     * Sets the value of the offerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OfferList }{@code >}
     *     
     */
    public void setOfferList(JAXBElement<OfferList> value) {
        this.offerList = value;
    }

    /**
     * Gets the value of the profileRecord property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the profileRecord property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProfileRecord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePairImpl }
     * 
     * 
     */
    public List<NameValuePairImpl> getProfileRecord() {
        if (profileRecord == null) {
            profileRecord = new ArrayList<NameValuePairImpl>();
        }
        return this.profileRecord;
    }

    /**
     * Gets the value of the sessionID property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSessionID(JAXBElement<String> value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStatusCode(Integer value) {
        this.statusCode = value;
    }

}
