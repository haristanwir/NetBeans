/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.core;

import com.esb.model.ServiceResponse;
import com.esb.utility.ActiveMQProdConnectionPool;
import com.esb.utility.ErrorHandling;
import com.esb.utility.ServiceLogger;
import java.util.Date;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Haris Tanwir
 */
public class PutInteraction {

    private static final Logger logger = ServiceLogger.getLogger(PutInteraction.class.getName());
    private static ActiveMQProdConnectionPool mqProducerPool = null;

    private synchronized static void init(String mq_ip, Integer mq_port) {
        if (PutInteraction.mqProducerPool == null) {
            try {
                PutInteraction.mqProducerPool.shutdown();
            } catch (Exception ex) {
            }
            PutInteraction.mqProducerPool = new ActiveMQProdConnectionPool(mq_ip, mq_port);
        }
    }

    public ServiceResponse putCROInteraction(JSONObject req, String queueName, String mq_ip, int mq_port) {
        ServiceResponse respObj = new ServiceResponse();
        logger.info("Incoming putCROInteraction request message|" + req.toJSONString());
        try {
            if (PutInteraction.mqProducerPool == null) {
                PutInteraction.init(mq_ip, mq_port);
            }
            String croID = (req.get("croID") == null ? null : (String) req.get("croID"));
            String sessionID = (String) req.get("sessionID");
            String msisdn = (String) req.get("msisdn");
            JSONObject croInteractionObj = new JSONObject();
            croInteractionObj.put("croID", croID);
            croInteractionObj.put("sessionID", sessionID);
            croInteractionObj.put("msisdn", msisdn);
            croInteractionObj.put("transferCROID", null);
            croInteractionObj.put("enqueueTime", new Date().getTime());
            PutInteraction.mqProducerPool.enqueue(croInteractionObj.toJSONString(), queueName, 4);
            respObj = new ServiceResponse(0, "SUCCESS", croInteractionObj);
            logger.info("CRO interaction enqueued, croID: " + croID);
            logger.debug("CRO interaction enqueued, croID: " + croID + ", Interaction: " + croInteractionObj.toJSONString());
        } catch (Exception ex) {
            respObj = new ServiceResponse(-10, ex.getClass().getCanonicalName(), ErrorHandling.getStackTrace(ex));
            logger.error("Error Occured in putCROInteraction|" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return respObj;
    }

    public synchronized static void shutdown() {
        try {
            PutInteraction.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }
    }
}
