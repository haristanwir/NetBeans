
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
 * <p>Java class for CommandImpl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommandImpl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="audienceID" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="audienceLevel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="event" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="eventParameters" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="getOfferRequests" type="{http://api.interact.unicacorp.com/xsd}GetOfferRequest" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="interactionPoint" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactiveChannel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="methodIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberRequested" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="relyOnExistingSession" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommandImpl", propOrder = {
    "audienceID",
    "audienceLevel",
    "debug",
    "event",
    "eventParameters",
    "getOfferRequests",
    "interactionPoint",
    "interactiveChannel",
    "methodIdentifier",
    "numberRequested",
    "relyOnExistingSession"
})
public class CommandImpl {

    @XmlElement(nillable = true)
    protected List<NameValuePairImpl> audienceID;
    @XmlElementRef(name = "audienceLevel", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> audienceLevel;
    protected Boolean debug;
    @XmlElementRef(name = "event", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> event;
    @XmlElement(nillable = true)
    protected List<NameValuePairImpl> eventParameters;
    @XmlElement(nillable = true)
    protected List<GetOfferRequest> getOfferRequests;
    @XmlElementRef(name = "interactionPoint", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> interactionPoint;
    @XmlElementRef(name = "interactiveChannel", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> interactiveChannel;
    @XmlElementRef(name = "methodIdentifier", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> methodIdentifier;
    protected Integer numberRequested;
    protected Boolean relyOnExistingSession;

    /**
     * Gets the value of the audienceID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the audienceID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAudienceID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePairImpl }
     * 
     * 
     */
    public List<NameValuePairImpl> getAudienceID() {
        if (audienceID == null) {
            audienceID = new ArrayList<NameValuePairImpl>();
        }
        return this.audienceID;
    }

    /**
     * Gets the value of the audienceLevel property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getAudienceLevel() {
        return audienceLevel;
    }

    /**
     * Sets the value of the audienceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setAudienceLevel(JAXBElement<String> value) {
        this.audienceLevel = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDebug() {
        return debug;
    }

    /**
     * Sets the value of the debug property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDebug(Boolean value) {
        this.debug = value;
    }

    /**
     * Gets the value of the event property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEvent() {
        return event;
    }

    /**
     * Sets the value of the event property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEvent(JAXBElement<String> value) {
        this.event = value;
    }

    /**
     * Gets the value of the eventParameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eventParameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEventParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePairImpl }
     * 
     * 
     */
    public List<NameValuePairImpl> getEventParameters() {
        if (eventParameters == null) {
            eventParameters = new ArrayList<NameValuePairImpl>();
        }
        return this.eventParameters;
    }

    /**
     * Gets the value of the getOfferRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getOfferRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetOfferRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GetOfferRequest }
     * 
     * 
     */
    public List<GetOfferRequest> getGetOfferRequests() {
        if (getOfferRequests == null) {
            getOfferRequests = new ArrayList<GetOfferRequest>();
        }
        return this.getOfferRequests;
    }

    /**
     * Gets the value of the interactionPoint property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInteractionPoint() {
        return interactionPoint;
    }

    /**
     * Sets the value of the interactionPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInteractionPoint(JAXBElement<String> value) {
        this.interactionPoint = value;
    }

    /**
     * Gets the value of the interactiveChannel property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInteractiveChannel() {
        return interactiveChannel;
    }

    /**
     * Sets the value of the interactiveChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInteractiveChannel(JAXBElement<String> value) {
        this.interactiveChannel = value;
    }

    /**
     * Gets the value of the methodIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMethodIdentifier() {
        return methodIdentifier;
    }

    /**
     * Sets the value of the methodIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMethodIdentifier(JAXBElement<String> value) {
        this.methodIdentifier = value;
    }

    /**
     * Gets the value of the numberRequested property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberRequested() {
        return numberRequested;
    }

    /**
     * Sets the value of the numberRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberRequested(Integer value) {
        this.numberRequested = value;
    }

    /**
     * Gets the value of the relyOnExistingSession property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRelyOnExistingSession() {
        return relyOnExistingSession;
    }

    /**
     * Sets the value of the relyOnExistingSession property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRelyOnExistingSession(Boolean value) {
        this.relyOnExistingSession = value;
    }

}
