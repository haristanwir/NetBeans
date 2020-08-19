
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
 * <p>Java class for OfferList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfferList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="interactionPointName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recommendedOffers" type="{http://api.interact.unicacorp.com/xsd}Offer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferList", propOrder = {
    "defaultString",
    "interactionPointName",
    "recommendedOffers"
})
public class OfferList {

    @XmlElementRef(name = "defaultString", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> defaultString;
    @XmlElementRef(name = "interactionPointName", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> interactionPointName;
    @XmlElement(nillable = true)
    protected List<Offer> recommendedOffers;

    /**
     * Gets the value of the defaultString property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDefaultString() {
        return defaultString;
    }

    /**
     * Sets the value of the defaultString property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDefaultString(JAXBElement<String> value) {
        this.defaultString = value;
    }

    /**
     * Gets the value of the interactionPointName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getInteractionPointName() {
        return interactionPointName;
    }

    /**
     * Sets the value of the interactionPointName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setInteractionPointName(JAXBElement<String> value) {
        this.interactionPointName = value;
    }

    /**
     * Gets the value of the recommendedOffers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recommendedOffers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecommendedOffers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Offer }
     * 
     * 
     */
    public List<Offer> getRecommendedOffers() {
        if (recommendedOffers == null) {
            recommendedOffers = new ArrayList<Offer>();
        }
        return this.recommendedOffers;
    }

}
