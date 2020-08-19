/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.unica;

import com.sun.xml.ws.client.BindingProviderProperties;
import com.unicacorp.interact.api.xsd.Response;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Haris Tanwir
 */
public class InteractHandler {

    public Response startsession(java.lang.String sessionID, boolean relyOnExistingSession, boolean debug, java.lang.String interactiveChannel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> audienceID, java.lang.String audienceLevel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> parameters) {
        return InteractHandler.startSession(sessionID, relyOnExistingSession, debug, interactiveChannel, audienceID, audienceLevel, parameters);
    }

    private static Response startSession(java.lang.String sessionID, boolean relyOnExistingSession, boolean debug, java.lang.String interactiveChannel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> audienceID, java.lang.String audienceLevel, java.util.List<com.unicacorp.interact.api.xsd.NameValuePairImpl> parameters) {
        com.unicacorp.interact.api.soap.InteractService service = new com.unicacorp.interact.api.soap.InteractService();
        com.unicacorp.interact.api.soap.InteractSoapServicePortType port = service.getInteractSoapServiceHttpSoap11Endpoint();
        BindingProvider bindingprovider = (BindingProvider) port;
        bindingprovider.getRequestContext().put(BindingProviderProperties.REQUEST_TIMEOUT, 5000);
        bindingprovider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8090/interact/services/InteractService");
        return port.startSession(sessionID, relyOnExistingSession, debug, interactiveChannel, audienceID, audienceLevel, parameters);
    }
}
