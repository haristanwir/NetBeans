
package com.unicacorp.interact.api.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetOfferRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetOfferRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="duplicationPolicy" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ipName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numberRequested" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="offerAttributes" type="{http://api.interact.unicacorp.com/xsd}OfferAttributeRequirements" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetOfferRequest", propOrder = {
    "duplicationPolicy",
    "ipName",
    "numberRequested",
    "offerAttributes"
})
public class GetOfferRequest {

    protected Integer duplicationPolicy;
    @XmlElementRef(name = "ipName", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> ipName;
    protected Integer numberRequested;
    @XmlElementRef(name = "offerAttributes", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<OfferAttributeRequirements> offerAttributes;

    /**
     * Gets the value of the duplicationPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDuplicationPolicy() {
        return duplicationPolicy;
    }

    /**
     * Sets the value of the duplicationPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDuplicationPolicy(Integer value) {
        this.duplicationPolicy = value;
    }

    /**
     * Gets the value of the ipName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIpName() {
        return ipName;
    }

    /**
     * Sets the value of the ipName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIpName(JAXBElement<String> value) {
        this.ipName = value;
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
     * Gets the value of the offerAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link OfferAttributeRequirements }{@code >}
     *     
     */
    public JAXBElement<OfferAttributeRequirements> getOfferAttributes() {
        return offerAttributes;
    }

    /**
     * Sets the value of the offerAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link OfferAttributeRequirements }{@code >}
     *     
     */
    public void setOfferAttributes(JAXBElement<OfferAttributeRequirements> value) {
        this.offerAttributes = value;
    }

}
