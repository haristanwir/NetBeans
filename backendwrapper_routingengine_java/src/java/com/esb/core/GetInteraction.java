/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.core;

import com.esb.model.ServiceResponse;
import com.esb.utility.ActiveMQConsConnectionPool;
import com.esb.utility.ActiveMQProdConnectionPool;
import com.esb.utility.ErrorHandling;
import com.esb.utility.ServiceLogger;
import java.util.Date;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Haris Tanwir
 */
public class GetInteraction {

    private static final Logger logger = ServiceLogger.getLogger(GetInteraction.class.getName());
    private static ActiveMQProdConnectionPool mqProducerPool = null;
    private static ActiveMQConsConnectionPool mqConsumerPool = null;

    private synchronized static void init(String mq_ip, Integer mq_port) {
        if (GetInteraction.mqConsumerPool == null || GetInteraction.mqProducerPool == null) {
            try {
                GetInteraction.mqConsumerPool.shutdown();
            } catch (Exception ex) {
            }
            try {
                GetInteraction.mqProducerPool.shutdown();
            } catch (Exception ex) {
            }
            GetInteraction.mqConsumerPool = new ActiveMQConsConnectionPool(mq_ip, mq_port);
            GetInteraction.mqProducerPool = new ActiveMQProdConnectionPool(mq_ip, mq_port);
        }
    }

    public ServiceResponse getCROInteraction(JSONObject req, String queueName, String mq_ip, int mq_port) {
        ServiceResponse respObj = new ServiceResponse();
        JSONParser jsonParser = new JSONParser();
        logger.info("Incoming getCROInteraction request message|" + req.toJSONString());
        try {
            if (GetInteraction.mqConsumerPool == null || GetInteraction.mqProducerPool == null) {
                GetInteraction.init(mq_ip, mq_port);
            }
            String croID = (String) req.get("croID");
            Message mqMessage = GetInteraction.mqConsumerPool.dequeue(queueName);
            if (mqMessage != null) {
                String message = new String(((TextMessage) mqMessage).getText());
                JSONObject croInteractionObj = (JSONObject) jsonParser.parse(message);
                if (croID.equals(croInteractionObj.get("croID"))) {
                    croInteractionObj.put("requeueTime", new Date().getTime());
                    GetInteraction.mqProducerPool.enqueue(croInteractionObj.toJSONString(), queueName, 4);
                    respObj = new ServiceResponse(-2, "REQUEUED", croInteractionObj);
                    logger.info("CRO interaction requeued, having same croID: " + croID);
                    logger.debug("CRO interaction requeued, having same croID: " + croID + ", Interaction: " + croInteractionObj.toJSONString());
                } else if (croInteractionObj.get("transferCROID") != null && !croID.equals(croInteractionObj.get("transferCROID"))) {
                    croInteractionObj.put("requeueTime", new Date().getTime());
                    GetInteraction.mqProducerPool.enqueue(croInteractionObj.toJSONString(), queueName, 4);
                    respObj = new ServiceResponse(-2, "REQUEUED", croInteractionObj);
                    logger.info("CRO interaction requeued, transferCROID not matched, croID: " + croID + ", transferCROID: " + croInteractionObj.get("transferCROID"));
                    logger.debug("CRO interaction requeued, transferCROID not matched, croID: " + croID + ", transferCROID: " + croInteractionObj.get("transferCROID") + ", Interaction: " + croInteractionObj.toJSONString());
                } else {
                    respObj = new ServiceResponse(0, "SUCCESS", croInteractionObj);
                    logger.info("CRO interaction dequeued, croID: " + croID);
                    logger.debug("CRO interaction dequeued, croID: " + croID + ", Interaction: " + croInteractionObj.toJSONString());
                }
            } else {
                respObj = new ServiceResponse(-1, "FAILURE", mqMessage);
            }
        } catch (Exception ex) {
            respObj = new ServiceResponse(-10, ex.getClass().getCanonicalName(), ErrorHandling.getStackTrace(ex));
            logger.error("Error Occured in getCROInteraction|" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return respObj;
    }

    public synchronized static void shutdown() {
        try {
            GetInteraction.mqConsumerPool.shutdown();
        } catch (Exception ex) {
        }
        try {
            GetInteraction.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }
    }

}
