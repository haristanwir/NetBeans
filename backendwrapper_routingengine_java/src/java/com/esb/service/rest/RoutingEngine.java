/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.service.rest;

import com.esb.core.GetInteraction;
import com.esb.core.GetInteractionQueueSize;
import com.esb.core.PutInteraction;
import com.esb.core.TransferInteraction;
import com.esb.model.ServiceResponse;
import com.esb.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ManagedAsync;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * REST Web Service
 *
 * @author Haris Tanwir
 */
@Path("routingengine")
@Singleton
public class RoutingEngine {

//    private static final String queueName = "cro.interaction.queue";
//    private static final String mq_ip = "localhost";
//    private static final String mq_port = "61616";
    private static final String queueName = Utility.getProperty("CRO_QUEUE");
    private static final String mq_ip = Utility.getProperty("AMQ_TCP_HOST");
    private static final String mq_port = Utility.getProperty("AMQ_TCP_PORT");
    private GetInteraction getInteractionObj = new GetInteraction();
    private GetInteractionQueueSize getCROInteractionQueueSize = new GetInteractionQueueSize();
    private PutInteraction putInteractionObj = new PutInteraction();
    private TransferInteraction transferInteractionObj = new TransferInteraction();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RoutingEngine
     */
    public RoutingEngine() {
    }

    @GET
    @Path("/getCROInteraction/{croID}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getCROInteraction(@PathParam("croID") String croID, @Suspended final AsyncResponse asyncResponse) {
        //public Response getCROInteraction(@PathParam("croID") String croID) {
        asyncResponse.setTimeout(10000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        JSONObject reqObj = new JSONObject();
        reqObj.put("croID", croID);
        ServiceResponse serviceResp = getInteractionObj.getCROInteraction(reqObj, queueName, mq_ip, Integer.parseInt(mq_port));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String serviceRespJson = gson.toJson(serviceResp);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build());
        //return Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build();
    }

    @GET
    @Path("/getCROInteractionQueueSize")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getCROInteractionQueueSize(@Suspended final AsyncResponse asyncResponse) {
        //public Response getCROInteractionQueueSize() {
        asyncResponse.setTimeout(10000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        JSONObject reqObj = new JSONObject();
        ServiceResponse serviceResp = getCROInteractionQueueSize.getCROInteractionQueueSize(reqObj, queueName);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String serviceRespJson = gson.toJson(serviceResp);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build());
        //return Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build();
    }

    @POST
    @Path("/putCROInteraction")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void putCROInteraction(String content, @Suspended final AsyncResponse asyncResponse) throws ParseException {
        //public Response putCROInteraction(String content) throws ParseException {
        asyncResponse.setTimeout(10000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        JSONParser jsonParser = new JSONParser();
        JSONObject reqObj = (JSONObject) jsonParser.parse(content);
        ServiceResponse serviceResp = putInteractionObj.putCROInteraction(reqObj, queueName, mq_ip, Integer.parseInt(mq_port));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String serviceRespJson = gson.toJson(serviceResp);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build());
        //return Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build();
    }

    @POST
    @Path("/transferCROInteraction")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void transferCROInteraction(String content, @Suspended final AsyncResponse asyncResponse) throws ParseException {
        //public Response transferCROInteraction(String content) throws ParseException {
        asyncResponse.setTimeout(10000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        JSONParser jsonParser = new JSONParser();
        JSONObject reqObj = (JSONObject) jsonParser.parse(content);
        ServiceResponse serviceResp = transferInteractionObj.transferCROInteraction(reqObj, queueName, mq_ip, Integer.parseInt(mq_port));
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String serviceRespJson = gson.toJson(serviceResp);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build());
        //return Response.status(HttpURLConnection.HTTP_OK).entity(serviceRespJson).build();
    }

    @PreDestroy
    private synchronized void shutdown() {
        try {
            GetInteraction.shutdown();
        } catch (Exception ex) {
        }
        try {
            PutInteraction.shutdown();
        } catch (Exception ex) {
        }
        try {
            TransferInteraction.shutdown();
        } catch (Exception ex) {
        }
    }

}
