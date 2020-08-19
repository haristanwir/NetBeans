
package com.unicacorp.interact.api.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.unicacorp.interact.api.xsd.NameValuePairImpl;


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
 *         &lt;element name="relyOnExistingSession" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="debug" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="interactiveChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="audienceID" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded"/>
 *         &lt;element name="audienceLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="parameters" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded" minOccurs="0"/>
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
    "relyOnExistingSession",
    "debug",
    "interactiveChannel",
    "audienceID",
    "audienceLevel",
    "parameters"
})
@XmlRootElement(name = "startSession")
public class StartSession {

    @XmlElement(required = true, nillable = true)
    protected String sessionID;
    protected boolean relyOnExistingSession;
    protected boolean debug;
    @XmlElement(required = true, nillable = true)
    protected String interactiveChannel;
    @XmlElement(required = true, nillable = true)
    protected List<NameValuePairImpl> audienceID;
    @XmlElement(required = true, nillable = true)
    protected String audienceLevel;
    @XmlElement(nillable = true)
    protected List<NameValuePairImpl> parameters;

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
     * Gets the value of the relyOnExistingSession property.
     * 
     */
    public boolean isRelyOnExistingSession() {
        return relyOnExistingSession;
    }

    /**
     * Sets the value of the relyOnExistingSession property.
     * 
     */
    public void setRelyOnExistingSession(boolean value) {
        this.relyOnExistingSession = value;
    }

    /**
     * Gets the value of the debug property.
     * 
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Sets the value of the debug property.
     * 
     */
    public void setDebug(boolean value) {
        this.debug = value;
    }

    /**
     * Gets the value of the interactiveChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInteractiveChannel() {
        return interactiveChannel;
    }

    /**
     * Sets the value of the interactiveChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInteractiveChannel(String value) {
        this.interactiveChannel = value;
    }

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
     *     {@link String }
     *     
     */
    public String getAudienceLevel() {
        return audienceLevel;
    }

    /**
     * Sets the value of the audienceLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAudienceLevel(String value) {
        this.audienceLevel = value;
    }

    /**
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePairImpl }
     * 
     * 
     */
    public List<NameValuePairImpl> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<NameValuePairImpl>();
        }
        return this.parameters;
    }

}
