/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.core;

import com.esb.utility.ActiveMQConsConnectionPool;
import com.esb.utility.ActiveMQProdConnectionPool;
import com.esb.utility.Constant;
import com.esb.utility.ErrorHandling;
import com.esb.utility.FlowLogger;
import com.esb.utility.IBMMQConsConnectionPool;
import com.esb.utility.IBMMQProdConnectionPool;
import com.esb.utility.ThroughputController;
import com.esb.utility.JDBCConnectionPool;
import com.esb.utility.RabbitMQConsConnectionPool;
import com.esb.utility.RabbitMQProdConnectionPool;
import com.esb.utility.Utility;
import com.rabbitmq.client.GetResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class EmployeeInputWorker extends Thread {

    private static final Logger logger = FlowLogger.getLogger(EmployeeInputWorker.class.getName());
    private static final Logger Errorlogger = FlowLogger.getLogger(ErrorHandling.class.getName());

    private static Boolean isRunning = false;

    private String message = null;
    private String queue = null;
    private String backoutQueue = null;
    private static ThroughputController tpsController = null;
    private static JDBCConnectionPool dbConnectionPool = null;
    private static RabbitMQConsConnectionPool mqConsumerPool = null;
    private static RabbitMQProdConnectionPool mqProducerPool = null;
    //private static ActiveMQConsConnectionPool mqConsumerPool = null;
    //private static ActiveMQProdConnectionPool mqProducerPool = null;
    //private static IBMMQProdConnectionPool mqProducerPool = null;
    //private static IBMMQConsConnectionPool mqConsumerPool = null;

    public static void init(Integer tps, String db_drivername, String db_url, String db_user, String db_password, String mq_ip, Integer mq_port) {
        try {
            EmployeeInputWorker.mqConsumerPool.shutdown();
        } catch (Exception ex) {
        }
        try {
            EmployeeInputWorker.dbConnectionPool.shutdown();
        } catch (Exception ex) {
        }
        try {
            EmployeeInputWorker.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }

        tpsController = new ThroughputController(tps);
        //dbConnectionPool = new JDBCConnectionPool(db_drivername, db_url, db_user, db_password);
        mqConsumerPool = new RabbitMQConsConnectionPool(mq_ip, mq_port, EmployeeInputWorker.class.getName());
        mqProducerPool = new RabbitMQProdConnectionPool(mq_ip, mq_port, EmployeeInputWorker.class.getName());

        //mqConsumerPool = new ActiveMQConsConnectionPool("localhost", 61616);
        //mqProducerPool = new ActiveMQProdConnectionPool("localhost", 61616);
        //mqProducerPool = new IBMMQProdConnectionPool("localhost", 1415, "IIB10QMGR", "SYSTEM.ADMIN.SVRCONN");
        //mqConsumerPool = new IBMMQConsConnectionPool("localhost", 1415, "IIB10QMGR", "SYSTEM.ADMIN.SVRCONN");
    }

    public EmployeeInputWorker(String queue, String backoutQueue) {
        this.queue = queue;
        this.backoutQueue = backoutQueue;
    }

    public static void setIsRunning(Boolean isInitialized) {
        synchronized (EmployeeInputWorker.isRunning) {
            EmployeeInputWorker.isRunning = isInitialized;
        }
    }

    public synchronized static void shutdownWorker() {
        try {
            EmployeeInputWorker.mqConsumerPool.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
        try {
            EmployeeInputWorker.dbConnectionPool.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
        try {
            EmployeeInputWorker.mqProducerPool.shutdown();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
    }

    @Override
    public void run() {
        while (EmployeeInputWorker.isRunning) {
            try {
                message = null;
                EmployeeInputWorker.tpsController.evaluateTPS();
                long dequeueStart = System.currentTimeMillis();
                GetResponse mqMessage = EmployeeInputWorker.mqConsumerPool.dequeue(queue);
                //Message mqMessage = EmployeeInputWorker.mqConsumerPool.dequeue(queue);
                if (mqMessage != null) {
                    long threadStart = System.currentTimeMillis();
                    message = new String(mqMessage.getBody(), "UTF-8");
                    //message = new String(((TextMessage)mqMessage).getText());
                    logger.info("Message Received from queue:" + queue + ", message:" + message);

//                    Connection connection = EmployeeInputWorker.dbConnectionPool.getConnection();
//                    String sql = "SELECT * FROM CX_SMS_NTFY_TMP WHERE rownum < 10";
//                    PreparedStatement statement = connection.prepareStatement(sql);
//                    ResultSet rs = statement.executeQuery();
//                    String msisdn = null;
//                    while (rs.next()) {
//                        msisdn = rs.getString("MSISDN");
//                    }
//                    logger.info(" msisdn: " + msisdn);
//                    try {
//                        rs.close();
//                    } catch (Exception ex) {
//                    }
//                    try {
//                        statement.close();
//                    } catch (Exception ex) {
//                    }
//                    EmployeeInputWorker.dbConnectionPool.releaseConnection(connection);

                    logger.info("dequeue time is " + (threadStart - dequeueStart) + " DB time is " + (System.currentTimeMillis() - threadStart));
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                Errorlogger.error(ErrorHandling.getStackTrace(ex));
                try {
                    EmployeeInputWorker.mqProducerPool.enqueue(message, backoutQueue, null);
                    //EmployeeInputWorker.mqProducerPool.enqueue(message, backoutQueue, 4);
                } catch (Exception _ex) {
                    logger.error(_ex.getMessage());
                    Errorlogger.error(ErrorHandling.getStackTrace(_ex));
                }
            }
        }
    }
}
