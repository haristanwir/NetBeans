/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.service.rest;

import java.net.HttpURLConnection;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Haris Tanwir
 */
public class AsyncTimeoutHandler implements TimeoutHandler {

    @Override
    public void handleTimeout(AsyncResponse asyncResponse) {
        Response timeoutResponse = Response.status(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
                                            .type(MediaType.TEXT_PLAIN)
                                            .entity("Operation timed out")
                                            .build();
        asyncResponse.resume(timeoutResponse);
    }
}
