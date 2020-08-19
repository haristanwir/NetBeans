
package com.unicacorp.interact.api.soap;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.unicacorp.interact.api.xsd.BatchResponse;
import com.unicacorp.interact.api.xsd.CommandImpl;
import com.unicacorp.interact.api.xsd.GetOfferRequest;
import com.unicacorp.interact.api.xsd.NameValuePairImpl;
import com.unicacorp.interact.api.xsd.Response;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "InteractSoapServicePortType", targetNamespace = "http://soap.api.interact.unicacorp.com")
@XmlSeeAlso({
    com.unicacorp.interact.api.soap.ObjectFactory.class,
    com.unicacorp.interact.api.xsd.ObjectFactory.class
})
public interface InteractSoapServicePortType {


    /**
     * 
     * @param sessionID
     * @param requests
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:getOffersForMultipleInteractionPoints")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "getOffersForMultipleInteractionPoints", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetOffersForMultipleInteractionPoints")
    @ResponseWrapper(localName = "getOffersForMultipleInteractionPointsResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetOffersForMultipleInteractionPointsResponse")
    public Response getOffersForMultipleInteractionPoints(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "requests", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<GetOfferRequest> requests);

    /**
     * 
     * @param sessionID
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:getProfile")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "getProfile", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetProfile")
    @ResponseWrapper(localName = "getProfileResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetProfileResponse")
    public Response getProfile(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID);

    /**
     * 
     * @return
     *     returns com.unicacorp.interact.api.soap.GetVersionResponse
     */
    @WebMethod(action = "urn:getVersion")
    @WebResult(name = "getVersionResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", partName = "parameters")
    @SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
    public GetVersionResponse getVersion();

    /**
     * 
     * @param eventName
     * @param eventParameters
     * @param sessionID
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:postEvent")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "postEvent", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.PostEvent")
    @ResponseWrapper(localName = "postEventResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.PostEventResponse")
    public Response postEvent(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "eventName", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String eventName,
        @WebParam(name = "eventParameters", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<NameValuePairImpl> eventParameters);

    /**
     * 
     * @param sessionID
     * @param commands
     * @return
     *     returns com.unicacorp.interact.api.xsd.BatchResponse
     */
    @WebMethod(action = "urn:executeBatch")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "executeBatch", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.ExecuteBatch")
    @ResponseWrapper(localName = "executeBatchResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.ExecuteBatchResponse")
    public BatchResponse executeBatch(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "commands", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<CommandImpl> commands);

    /**
     * 
     * @param numberRequested
     * @param sessionID
     * @param iPoint
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:getOffers")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "getOffers", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetOffers")
    @ResponseWrapper(localName = "getOffersResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.GetOffersResponse")
    public Response getOffers(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "iPoint", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String iPoint,
        @WebParam(name = "numberRequested", targetNamespace = "http://soap.api.interact.unicacorp.com")
        int numberRequested);

    /**
     * 
     * @param debug
     * @param sessionID
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:setDebug")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "setDebug", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.SetDebug")
    @ResponseWrapper(localName = "setDebugResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.SetDebugResponse")
    public Response setDebug(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "debug", targetNamespace = "http://soap.api.interact.unicacorp.com")
        boolean debug);

    /**
     * 
     * @param debug
     * @param relyOnExistingSession
     * @param sessionID
     * @param audienceID
     * @param audienceLevel
     * @param parameters
     * @param interactiveChannel
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:startSession")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "startSession", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.StartSession")
    @ResponseWrapper(localName = "startSessionResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.StartSessionResponse")
    public Response startSession(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "relyOnExistingSession", targetNamespace = "http://soap.api.interact.unicacorp.com")
        boolean relyOnExistingSession,
        @WebParam(name = "debug", targetNamespace = "http://soap.api.interact.unicacorp.com")
        boolean debug,
        @WebParam(name = "interactiveChannel", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String interactiveChannel,
        @WebParam(name = "audienceID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<NameValuePairImpl> audienceID,
        @WebParam(name = "audienceLevel", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String audienceLevel,
        @WebParam(name = "parameters", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<NameValuePairImpl> parameters);

    /**
     * 
     * @param sessionID
     * @param audienceID
     * @param audienceLevel
     * @param parameters
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:setAudience")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "setAudience", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.SetAudience")
    @ResponseWrapper(localName = "setAudienceResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.SetAudienceResponse")
    public Response setAudience(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID,
        @WebParam(name = "audienceID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<NameValuePairImpl> audienceID,
        @WebParam(name = "audienceLevel", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String audienceLevel,
        @WebParam(name = "parameters", targetNamespace = "http://soap.api.interact.unicacorp.com")
        List<NameValuePairImpl> parameters);

    /**
     * 
     * @param sessionID
     * @return
     *     returns com.unicacorp.interact.api.xsd.Response
     */
    @WebMethod(action = "urn:endSession")
    @WebResult(targetNamespace = "http://soap.api.interact.unicacorp.com")
    @RequestWrapper(localName = "endSession", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.EndSession")
    @ResponseWrapper(localName = "endSessionResponse", targetNamespace = "http://soap.api.interact.unicacorp.com", className = "com.unicacorp.interact.api.soap.EndSessionResponse")
    public Response endSession(
        @WebParam(name = "sessionID", targetNamespace = "http://soap.api.interact.unicacorp.com")
        String sessionID);

}
