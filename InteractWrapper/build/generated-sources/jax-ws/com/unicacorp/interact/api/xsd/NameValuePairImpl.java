
package com.unicacorp.interact.api.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for NameValuePairImpl complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NameValuePairImpl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueAsDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="valueAsNumeric" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="valueAsString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueDataType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NameValuePairImpl", propOrder = {
    "name",
    "valueAsDate",
    "valueAsNumeric",
    "valueAsString",
    "valueDataType"
})
public class NameValuePairImpl {

    @XmlElementRef(name = "name", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> name;
    @XmlElementRef(name = "valueAsDate", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> valueAsDate;
    @XmlElementRef(name = "valueAsNumeric", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<Double> valueAsNumeric;
    @XmlElementRef(name = "valueAsString", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> valueAsString;
    @XmlElementRef(name = "valueDataType", namespace = "http://api.interact.unicacorp.com/xsd", type = JAXBElement.class, required = false)
    protected JAXBElement<String> valueDataType;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setName(JAXBElement<String> value) {
        this.name = value;
    }

    /**
     * Gets the value of the valueAsDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getValueAsDate() {
        return valueAsDate;
    }

    /**
     * Sets the value of the valueAsDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setValueAsDate(JAXBElement<XMLGregorianCalendar> value) {
        this.valueAsDate = value;
    }

    /**
     * Gets the value of the valueAsNumeric property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public JAXBElement<Double> getValueAsNumeric() {
        return valueAsNumeric;
    }

    /**
     * Sets the value of the valueAsNumeric property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Double }{@code >}
     *     
     */
    public void setValueAsNumeric(JAXBElement<Double> value) {
        this.valueAsNumeric = value;
    }

    /**
     * Gets the value of the valueAsString property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getValueAsString() {
        return valueAsString;
    }

    /**
     * Sets the value of the valueAsString property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setValueAsString(JAXBElement<String> value) {
        this.valueAsString = value;
    }

    /**
     * Gets the value of the valueDataType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getValueDataType() {
        return valueDataType;
    }

    /**
     * Sets the value of the valueDataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setValueDataType(JAXBElement<String> value) {
        this.valueDataType = value;
    }

}
