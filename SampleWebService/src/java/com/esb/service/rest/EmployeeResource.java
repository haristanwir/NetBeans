/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.service.rest;

import com.esb.model.Employee;
import com.esb.model.ServiceResponse;
import com.esb.utility.ActiveMQProdConnectionPool;
import com.esb.utility.Constant;
import com.esb.utility.ErrorHandling;
import com.esb.utility.FlowLogger;
import com.esb.utility.IBMMQProdConnectionPool;
import com.esb.utility.RabbitMQProdConnectionPool;
import com.esb.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;

/**
 * REST Web Service
 *
 * @author Haris Tanwir
 */
@Path("employee")
@Singleton
public class EmployeeResource {

    private static final Logger logger = FlowLogger.getLogger(EmployeeResource.class.getName());
    private static final Logger Errorlogger = FlowLogger.getLogger(ErrorHandling.class.getName());

    private static RabbitMQProdConnectionPool mqProducerPool = null;
    //private static ActiveMQProdConnectionPool mqProducerPool = null;
    //private static IBMMQProdConnectionPool mqProducerPool = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EmployeeResource
     */
    public EmployeeResource() {
    }

    public synchronized static void init(String mq_ip, Integer mq_port) {
        try {
            EmployeeResource.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }
        mqProducerPool = new RabbitMQProdConnectionPool(mq_ip, mq_port, EmployeeResource.class.getName());
        //mqProducerPool = new ActiveMQProdConnectionPool("localhost", 61616);
        //mqProducerPool = new IBMMQProdConnectionPool("localhost", 1415, "IIB10QMGR", "SYSTEM.ADMIN.SVRCONN");
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getEmployeeById(@PathParam("id") String id, @Suspended final AsyncResponse asyncResponse) {
        //TODO return proper representation object
        asyncResponse.setTimeout(5000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Employee emp = new Employee();
        emp.setEmpID(id);
        emp.setEmpName("Haris");
        String empJson = gson.toJson(emp);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(empJson).build());
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeByName(@PathParam("name") String name) {
        //TODO return proper representation object
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Employee emp = new Employee();
        emp.setEmpID("112");
        emp.setEmpName(name);
        String empJson = gson.toJson(emp);
        return Response.status(200).entity(empJson).build();
    }

    @POST
    @Path("setInfo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void setInfo(String content, @Suspended final AsyncResponse asyncResponse) {
        asyncResponse.setTimeout(5000, TimeUnit.MILLISECONDS);
        asyncResponse.setTimeoutHandler(new AsyncTimeoutHandler());
        String QUEUE_NAME = "EMPLOYEE";
        ServiceResponse serviceResponse = new ServiceResponse();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            if (mqProducerPool == null) {
                EmployeeResource.init(Utility.getProperty(Constant.RABBITMQ_HOST_NAME),
                        Integer.parseInt(Utility.getProperty(Constant.RABBITMQ_PORT)));
            }
            EmployeeResource.mqProducerPool.enqueue(content, QUEUE_NAME, null);
            //EmployeeResource.mqProducerPool.enqueue(content, QUEUE_NAME, 4);
            serviceResponse.setReasonCode(0);
            serviceResponse.setResultDesc("SUCCESS");
        } catch (Exception _ex) {
            logger.error(_ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(_ex));
            serviceResponse.setReasonCode(1);
            serviceResponse.setResultDesc(_ex.getMessage());
        }
        String serviceResponseJson = gson.toJson(serviceResponse);
        asyncResponse.resume(Response.status(HttpURLConnection.HTTP_OK).entity(serviceResponseJson).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response setEmployee(String content) {
        String QUEUE_NAME = "EMPLOYEE";
        ServiceResponse serviceResponse = new ServiceResponse();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            if (mqProducerPool == null) {
                EmployeeResource.init(Utility.getProperty(Constant.RABBITMQ_HOST_NAME),
                        Integer.parseInt(Utility.getProperty(Constant.RABBITMQ_PORT)));
            }
            EmployeeResource.mqProducerPool.enqueue(content, QUEUE_NAME, null);
            //EmployeeResource.mqProducerPool.enqueue(content, QUEUE_NAME, 4);
            serviceResponse.setReasonCode(0);
            serviceResponse.setResultDesc("SUCCESS");
        } catch (Exception _ex) {
            logger.error(_ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(_ex));
            serviceResponse.setReasonCode(1);
            serviceResponse.setResultDesc(_ex.getMessage());
        }
        String serviceResponseJson = gson.toJson(serviceResponse);
        return Response.status(HttpURLConnection.HTTP_OK).entity(serviceResponseJson).build();
    }

    @PreDestroy
    private void shutdown() {
        try {
            EmployeeResource.mqProducerPool.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
    }
}
