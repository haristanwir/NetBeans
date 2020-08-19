/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.service.soap;

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
import java.io.File;
import javax.annotation.PreDestroy;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
@WebService(serviceName = "EmployeeService")
public class EmployeeService {

    private static final Logger logger = FlowLogger.getLogger(EmployeeService.class.getName());
    private static final Logger Errorlogger = FlowLogger.getLogger(ErrorHandling.class.getName());

    private static RabbitMQProdConnectionPool mqProducerPool = null;
    //private static ActiveMQProdConnectionPool mqProducerPool = null;
    //private static IBMMQProdConnectionPool mqProducerPool = null;
    
    public synchronized static void init(String mq_ip, Integer mq_port) {
        try {
            EmployeeService.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }
        mqProducerPool = new RabbitMQProdConnectionPool(mq_ip, mq_port, EmployeeService.class.getName());
        //mqProducerPool = new ActiveMQProdConnectionPool("localhost", 61616);
        //mqProducerPool = new IBMMQProdConnectionPool("localhost", 1415, "IIB10QMGR", "SYSTEM.ADMIN.SVRCONN");
    }

    @WebMethod(operationName = "setInfo")
    public ServiceResponse setInfo(@WebParam(name = "employeeInfo") Employee empObj) {
        String QUEUE_NAME = "EMPLOYEE";
        ServiceResponse serviceResponse = new ServiceResponse();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            String empJson = gson.toJson(empObj);
            if (mqProducerPool == null) {
                EmployeeService.init(Utility.getProperty(Constant.RABBITMQ_HOST_NAME),
                        Integer.parseInt(Utility.getProperty(Constant.RABBITMQ_PORT)));
            }
            EmployeeService.mqProducerPool.enqueue(empJson, QUEUE_NAME, null);
            //EmployeeService.mqProducerPool.enqueue(empJson, QUEUE_NAME, 4);
            serviceResponse.setReasonCode(0);
            serviceResponse.setResultDesc("SUCCESS");
        } catch (Exception _ex) {
            logger.error(_ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(_ex));
            serviceResponse.setReasonCode(1);
            serviceResponse.setResultDesc(_ex.getMessage());
        }
        return serviceResponse;
    }

    @PreDestroy
    private void shutdown() {
        try {
            EmployeeService.mqProducerPool.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
    }


}
