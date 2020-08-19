/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.unica;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.jws.WebService;

/**
 *
 * @author Haris Tanwir
 */
@WebService(serviceName = "InteractService", portName = "InteractSoapServiceHttpSoap11Endpoint", endpointInterface = "com.unicacorp.interact.api.soap.InteractSoapServicePortType", targetNamespace = "http://soap.api.interact.unicacorp.com", wsdlLocation = "WEB-INF/wsdl/InteractService/InteractService.wsdl")
public class InteractService {

    public com.unicacorp.interact.api.xsd.Response getOffersForMultipleInteractionPoints(java.lang.String sessionID, java.util.List<com.unicacorp.interact.api.xsd.GetOfferRequest> requests) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response getProfile(java.lang.String sessionID) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.soap.GetVersionResponse getVersion() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response postEvent(java.lang.String sessionID, java.lang.String eventName, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> eventParameters) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.BatchResponse executeBatch(java.lang.String sessionID, java.util.List<com.unicacorp.interact.api.xsd.CommandImpl> commands) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response getOffers(java.lang.String sessionID, java.lang.String iPoint, int numberRequested) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response setDebug(java.lang.String sessionID, boolean debug) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response startSession(java.lang.String sessionID, boolean relyOnExistingSession, boolean debug, java.lang.String interactiveChannel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> audienceID, java.lang.String audienceLevel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> parameters) throws Exception {
        //TODO implement this method
        try {
            InteractHandler interactHandler = new InteractHandler();
            return interactHandler.startsession(sessionID, relyOnExistingSession, debug, interactiveChannel, audienceID, audienceLevel, parameters);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            throw new Exception(exceptionAsString, ex);
        }
    }

    public com.unicacorp.interact.api.xsd.Response setAudience(java.lang.String sessionID, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> audienceID, java.lang.String audienceLevel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> parameters) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public com.unicacorp.interact.api.xsd.Response endSession(java.lang.String sessionID) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
