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
public class TransferInteraction {

    private static final Logger logger = ServiceLogger.getLogger(TransferInteraction.class.getName());
    private static ActiveMQProdConnectionPool mqProducerPool = null;

    private synchronized static void init(String mq_ip, Integer mq_port) {
        if (TransferInteraction.mqProducerPool == null) {
            try {
                TransferInteraction.mqProducerPool.shutdown();
            } catch (Exception ex) {
            }
            TransferInteraction.mqProducerPool = new ActiveMQProdConnectionPool(mq_ip, mq_port);
        }
    }

    public ServiceResponse transferCROInteraction(JSONObject req, String queueName, String mq_ip, int mq_port) {
        ServiceResponse respObj = new ServiceResponse();
        logger.info("Incoming transferCROInteraction request message|" + req.toJSONString());
        try {
            if (TransferInteraction.mqProducerPool == null) {
                TransferInteraction.init(mq_ip, mq_port);
            }
            String croID = (String) req.get("croID");
            String transferCROID = (String) req.get("transferCROID");
            String sessionID = (String) req.get("sessionID");
            String msisdn = (String) req.get("msisdn");
            if (!croID.equals(transferCROID)) {
                JSONObject croInteractionObj = new JSONObject();
                croInteractionObj.put("croID", croID);
                croInteractionObj.put("sessionID", sessionID);
                croInteractionObj.put("msisdn", msisdn);
                croInteractionObj.put("transferCROID", transferCROID);
                croInteractionObj.put("enqueueTime", new Date().getTime());
                TransferInteraction.mqProducerPool.enqueue(croInteractionObj.toJSONString(), queueName, 4);
                respObj = new ServiceResponse(0, "SUCCESS", croInteractionObj);
                logger.info("CRO interaction enqueued, croID: " + croID + ", transferCROID: " + transferCROID);
                logger.debug("CRO interaction enqueued, croID: " + croID + ", transferCROID: " + transferCROID + ", Interaction: " + croInteractionObj.toJSONString());
            } else {
                respObj = new ServiceResponse(-1, "FAILURE", new JSONObject());
                logger.info("CRO interaction enqueue failed, Cannot have same croID and transferCROID, croID: " + croID + ", transferCROID: " + transferCROID);
            }
        } catch (Exception ex) {
            respObj = new ServiceResponse(-10, ex.getClass().getCanonicalName(), ErrorHandling.getStackTrace(ex));
            logger.error("Error Occured in transferCROInteraction|" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return respObj;
    }

    public synchronized static void shutdown() {
        try {
            TransferInteraction.mqProducerPool.shutdown();
        } catch (Exception ex) {
        }
    }
}
