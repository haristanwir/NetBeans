
package com.unicacorp.interact.api.xsd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OfferAttributeRequirements complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfferAttributeRequirements">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="attributes" type="{http://api.interact.unicacorp.com/xsd}NameValuePairImpl" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="childRequirements" type="{http://api.interact.unicacorp.com/xsd}OfferAttributeRequirements" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="numberRequested" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferAttributeRequirements", propOrder = {
    "attributes",
    "childRequirements",
    "numberRequested"
})
public class OfferAttributeRequirements {

    @XmlElement(nillable = true)
    protected List<NameValuePairImpl> attributes;
    @XmlElement(nillable = true)
    protected List<OfferAttributeRequirements> childRequirements;
    protected Integer numberRequested;

    /**
     * Gets the value of the attributes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NameValuePairImpl }
     * 
     * 
     */
    public List<NameValuePairImpl> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<NameValuePairImpl>();
        }
        return this.attributes;
    }

    /**
     * Gets the value of the childRequirements property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childRequirements property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildRequirements().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OfferAttributeRequirements }
     * 
     * 
     */
    public List<OfferAttributeRequirements> getChildRequirements() {
        if (childRequirements == null) {
            childRequirements = new ArrayList<OfferAttributeRequirements>();
        }
        return this.childRequirements;
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

}
