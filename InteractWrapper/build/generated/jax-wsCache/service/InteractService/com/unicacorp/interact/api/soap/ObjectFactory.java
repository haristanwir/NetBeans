
package com.unicacorp.interact.api.soap;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.unicacorp.interact.api.xsd.BatchResponse;
import com.unicacorp.interact.api.xsd.Response;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.unicacorp.interact.api.soap package. 
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

    private final static QName _SetDebugResponseReturn_QNAME = new QName("http://soap.api.interact.unicacorp.com", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.unicacorp.interact.api.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetDebugResponse }
     * 
     */
    public SetDebugResponse createSetDebugResponse() {
        return new SetDebugResponse();
    }

    /**
     * Create an instance of {@link StartSessionResponse }
     * 
     */
    public StartSessionResponse createStartSessionResponse() {
        return new StartSessionResponse();
    }

    /**
     * Create an instance of {@link GetProfileResponse }
     * 
     */
    public GetProfileResponse createGetProfileResponse() {
        return new GetProfileResponse();
    }

    /**
     * Create an instance of {@link GetOffers }
     * 
     */
    public GetOffers createGetOffers() {
        return new GetOffers();
    }

    /**
     * Create an instance of {@link EndSession }
     * 
     */
    public EndSession createEndSession() {
        return new EndSession();
    }

    /**
     * Create an instance of {@link ExecuteBatch }
     * 
     */
    public ExecuteBatch createExecuteBatch() {
        return new ExecuteBatch();
    }

    /**
     * Create an instance of {@link GetProfile }
     * 
     */
    public GetProfile createGetProfile() {
        return new GetProfile();
    }

    /**
     * Create an instance of {@link GetVersionResponse }
     * 
     */
    public GetVersionResponse createGetVersionResponse() {
        return new GetVersionResponse();
    }

    /**
     * Create an instance of {@link PostEventResponse }
     * 
     */
    public PostEventResponse createPostEventResponse() {
        return new PostEventResponse();
    }

    /**
     * Create an instance of {@link SetDebug }
     * 
     */
    public SetDebug createSetDebug() {
        return new SetDebug();
    }

    /**
     * Create an instance of {@link ExecuteBatchResponse }
     * 
     */
    public ExecuteBatchResponse createExecuteBatchResponse() {
        return new ExecuteBatchResponse();
    }

    /**
     * Create an instance of {@link EndSessionResponse }
     * 
     */
    public EndSessionResponse createEndSessionResponse() {
        return new EndSessionResponse();
    }

    /**
     * Create an instance of {@link PostEvent }
     * 
     */
    public PostEvent createPostEvent() {
        return new PostEvent();
    }

    /**
     * Create an instance of {@link GetOffersForMultipleInteractionPoints }
     * 
     */
    public GetOffersForMultipleInteractionPoints createGetOffersForMultipleInteractionPoints() {
        return new GetOffersForMultipleInteractionPoints();
    }

    /**
     * Create an instance of {@link SetAudienceResponse }
     * 
     */
    public SetAudienceResponse createSetAudienceResponse() {
        return new SetAudienceResponse();
    }

    /**
     * Create an instance of {@link GetOffersResponse }
     * 
     */
    public GetOffersResponse createGetOffersResponse() {
        return new GetOffersResponse();
    }

    /**
     * Create an instance of {@link GetOffersForMultipleInteractionPointsResponse }
     * 
     */
    public GetOffersForMultipleInteractionPointsResponse createGetOffersForMultipleInteractionPointsResponse() {
        return new GetOffersForMultipleInteractionPointsResponse();
    }

    /**
     * Create an instance of {@link StartSession }
     * 
     */
    public StartSession createStartSession() {
        return new StartSession();
    }

    /**
     * Create an instance of {@link SetAudience }
     * 
     */
    public SetAudience createSetAudience() {
        return new SetAudience();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = SetDebugResponse.class)
    public JAXBElement<Response> createSetDebugResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, SetDebugResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BatchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = ExecuteBatchResponse.class)
    public JAXBElement<BatchResponse> createExecuteBatchResponseReturn(BatchResponse value) {
        return new JAXBElement<BatchResponse>(_SetDebugResponseReturn_QNAME, BatchResponse.class, ExecuteBatchResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = EndSessionResponse.class)
    public JAXBElement<Response> createEndSessionResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, EndSessionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = GetVersionResponse.class)
    public JAXBElement<Response> createGetVersionResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, GetVersionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = GetOffersResponse.class)
    public JAXBElement<Response> createGetOffersResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, GetOffersResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = GetOffersForMultipleInteractionPointsResponse.class)
    public JAXBElement<Response> createGetOffersForMultipleInteractionPointsResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, GetOffersForMultipleInteractionPointsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = GetProfileResponse.class)
    public JAXBElement<Response> createGetProfileResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, GetProfileResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = PostEventResponse.class)
    public JAXBElement<Response> createPostEventResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, PostEventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = StartSessionResponse.class)
    public JAXBElement<Response> createStartSessionResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, StartSessionResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap.api.interact.unicacorp.com", name = "return", scope = SetAudienceResponse.class)
    public JAXBElement<Response> createSetAudienceResponseReturn(Response value) {
        return new JAXBElement<Response>(_SetDebugResponseReturn_QNAME, Response.class, SetAudienceResponse.class, value);
    }

}
