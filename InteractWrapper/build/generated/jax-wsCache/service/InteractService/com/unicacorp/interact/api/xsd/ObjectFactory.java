
package com.unicacorp.interact.api.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.unicacorp.interact.api.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetOfferRequestIpName_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "ipName");
    private final static QName _GetOfferRequestOfferAttributes_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "offerAttributes");
    private final static QName _OfferListInteractionPointName_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "interactionPointName");
    private final static QName _OfferListDefaultString_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "defaultString");
    private final static QName _CommandImplAudienceLevel_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "audienceLevel");
    private final static QName _CommandImplEvent_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "event");
    private final static QName _CommandImplInteractiveChannel_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "interactiveChannel");
    private final static QName _CommandImplMethodIdentifier_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "methodIdentifier");
    private final static QName _CommandImplInteractionPoint_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "interactionPoint");
    private final static QName _AdvisoryMessageMessage_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "message");
    private final static QName _AdvisoryMessageDetailMessage_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "detailMessage");
    private final static QName _ResponseOfferList_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "offerList");
    private final static QName _ResponseSessionID_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "sessionID");
    private final static QName _ResponseApiVersion_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "apiVersion");
    private final static QName _OfferTreatmentCode_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "treatmentCode");
    private final static QName _OfferDescription_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "description");
    private final static QName _OfferOfferName_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "offerName");
    private final static QName _NameValuePairImplValueAsNumeric_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "valueAsNumeric");
    private final static QName _NameValuePairImplName_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "name");
    private final static QName _NameValuePairImplValueDataType_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "valueDataType");
    private final static QName _NameValuePairImplValueAsDate_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "valueAsDate");
    private final static QName _NameValuePairImplValueAsString_QNAME = new QName("http://api.interact.unicacorp.com/xsd", "valueAsString");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.unicacorp.interact.api.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link CommandImpl }
     * 
     */
    public CommandImpl createCommandImpl() {
        return new CommandImpl();
    }

    /**
     * Create an instance of {@link BatchResponse }
     * 
     */
    public BatchResponse createBatchResponse() {
        return new BatchResponse();
    }

    /**
     * Create an instance of {@link NameValuePairImpl }
     * 
     */
    public NameValuePairImpl createNameValuePairImpl() {
        return new NameValuePairImpl();
    }

    /**
     * Create an instance of {@link GetOfferRequest }
     * 
     */
    public GetOfferRequest createGetOfferRequest() {
        return new GetOfferRequest();
    }

    /**
     * Create an instance of {@link AdvisoryMessage }
     * 
     */
    public AdvisoryMessage createAdvisoryMessage() {
        return new AdvisoryMessage();
    }

    /**
     * Create an instance of {@link Offer }
     * 
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * Create an instance of {@link OfferAttributeRequirements }
     * 
     */
    public OfferAttributeRequirements createOfferAttributeRequirements() {
        return new OfferAttributeRequirements();
    }

    /**
     * Create an instance of {@link OfferList }
     * 
     */
    public OfferList createOfferList() {
        return new OfferList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "ipName", scope = GetOfferRequest.class)
    public JAXBElement<String> createGetOfferRequestIpName(String value) {
        return new JAXBElement<String>(_GetOfferRequestIpName_QNAME, String.class, GetOfferRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfferAttributeRequirements }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "offerAttributes", scope = GetOfferRequest.class)
    public JAXBElement<OfferAttributeRequirements> createGetOfferRequestOfferAttributes(OfferAttributeRequirements value) {
        return new JAXBElement<OfferAttributeRequirements>(_GetOfferRequestOfferAttributes_QNAME, OfferAttributeRequirements.class, GetOfferRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "interactionPointName", scope = OfferList.class)
    public JAXBElement<String> createOfferListInteractionPointName(String value) {
        return new JAXBElement<String>(_OfferListInteractionPointName_QNAME, String.class, OfferList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "defaultString", scope = OfferList.class)
    public JAXBElement<String> createOfferListDefaultString(String value) {
        return new JAXBElement<String>(_OfferListDefaultString_QNAME, String.class, OfferList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "audienceLevel", scope = CommandImpl.class)
    public JAXBElement<String> createCommandImplAudienceLevel(String value) {
        return new JAXBElement<String>(_CommandImplAudienceLevel_QNAME, String.class, CommandImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "event", scope = CommandImpl.class)
    public JAXBElement<String> createCommandImplEvent(String value) {
        return new JAXBElement<String>(_CommandImplEvent_QNAME, String.class, CommandImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "interactiveChannel", scope = CommandImpl.class)
    public JAXBElement<String> createCommandImplInteractiveChannel(String value) {
        return new JAXBElement<String>(_CommandImplInteractiveChannel_QNAME, String.class, CommandImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "methodIdentifier", scope = CommandImpl.class)
    public JAXBElement<String> createCommandImplMethodIdentifier(String value) {
        return new JAXBElement<String>(_CommandImplMethodIdentifier_QNAME, String.class, CommandImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "interactionPoint", scope = CommandImpl.class)
    public JAXBElement<String> createCommandImplInteractionPoint(String value) {
        return new JAXBElement<String>(_CommandImplInteractionPoint_QNAME, String.class, CommandImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "message", scope = AdvisoryMessage.class)
    public JAXBElement<String> createAdvisoryMessageMessage(String value) {
        return new JAXBElement<String>(_AdvisoryMessageMessage_QNAME, String.class, AdvisoryMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "detailMessage", scope = AdvisoryMessage.class)
    public JAXBElement<String> createAdvisoryMessageDetailMessage(String value) {
        return new JAXBElement<String>(_AdvisoryMessageDetailMessage_QNAME, String.class, AdvisoryMessage.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfferList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "offerList", scope = Response.class)
    public JAXBElement<OfferList> createResponseOfferList(OfferList value) {
        return new JAXBElement<OfferList>(_ResponseOfferList_QNAME, OfferList.class, Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "sessionID", scope = Response.class)
    public JAXBElement<String> createResponseSessionID(String value) {
        return new JAXBElement<String>(_ResponseSessionID_QNAME, String.class, Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "apiVersion", scope = Response.class)
    public JAXBElement<String> createResponseApiVersion(String value) {
        return new JAXBElement<String>(_ResponseApiVersion_QNAME, String.class, Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "treatmentCode", scope = Offer.class)
    public JAXBElement<String> createOfferTreatmentCode(String value) {
        return new JAXBElement<String>(_OfferTreatmentCode_QNAME, String.class, Offer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "description", scope = Offer.class)
    public JAXBElement<String> createOfferDescription(String value) {
        return new JAXBElement<String>(_OfferDescription_QNAME, String.class, Offer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "offerName", scope = Offer.class)
    public JAXBElement<String> createOfferOfferName(String value) {
        return new JAXBElement<String>(_OfferOfferName_QNAME, String.class, Offer.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "valueAsNumeric", scope = NameValuePairImpl.class)
    public JAXBElement<Double> createNameValuePairImplValueAsNumeric(Double value) {
        return new JAXBElement<Double>(_NameValuePairImplValueAsNumeric_QNAME, Double.class, NameValuePairImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "name", scope = NameValuePairImpl.class)
    public JAXBElement<String> createNameValuePairImplName(String value) {
        return new JAXBElement<String>(_NameValuePairImplName_QNAME, String.class, NameValuePairImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "valueDataType", scope = NameValuePairImpl.class)
    public JAXBElement<String> createNameValuePairImplValueDataType(String value) {
        return new JAXBElement<String>(_NameValuePairImplValueDataType_QNAME, String.class, NameValuePairImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "valueAsDate", scope = NameValuePairImpl.class)
    public JAXBElement<XMLGregorianCalendar> createNameValuePairImplValueAsDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_NameValuePairImplValueAsDate_QNAME, XMLGregorianCalendar.class, NameValuePairImpl.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://api.interact.unicacorp.com/xsd", name = "valueAsString", scope = NameValuePairImpl.class)
    public JAXBElement<String> createNameValuePairImplValueAsString(String value) {
        return new JAXBElement<String>(_NameValuePairImplValueAsString_QNAME, String.class, NameValuePairImpl.class, value);
    }

}
