/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.core;

import com.esb.model.ServiceResponse;
import com.esb.utility.ErrorHandling;
import com.esb.utility.ServiceLogger;
import com.esb.utility.Utility;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Haris Tanwir
 */
public class GetInteractionQueueSize {

    private static final Logger logger = ServiceLogger.getLogger(GetInteractionQueueSize.class.getName());

    public ServiceResponse getCROInteractionQueueSize(JSONObject req, String queueName) {
        ServiceResponse respObj = new ServiceResponse();
        logger.info("Incoming getCROInteractionQueueSize request message|" + req.toJSONString());
        Long value = 0l;
        Client client = ClientBuilder.newClient();
        try {
//            String amq_user = "admin";
//            String amq_pass = "admin";
//            String jolokia_host = "localhost";
//            String jolokia_port = "8161";
//            String jolokia_broker = "localhost";
            String amq_user = Utility.getProperty("AMQ_USER");
            String amq_pass = Utility.getProperty("AMQ_PASSWORD");
            String jolokia_host = Utility.getProperty("JOLOKIA_HOST");
            String jolokia_port = Utility.getProperty("JOLOKIA_PORT");
            String jolokia_broker = Utility.getProperty("JOLOKIA_BROKER");
            String usernameAndPassword = amq_user + ":" + amq_pass;
            String authorizationHeaderValue = "Basic " + java.util.Base64.getEncoder().encodeToString(usernameAndPassword.getBytes());
//            String url = "http://" + jolokia_host + ":" + jolokia_port + "/api/jolokia/read/org.apache.activemq:type=Broker,brokerName=" + jolokia_broker + ",destinationType=Queue,destinationName=" + queueName;
            String url = "http://" + jolokia_host + ":" + jolokia_port + "/console/jolokia/read/org.apache.activemq.artemis:broker=\"" + jolokia_broker + "\",component=addresses,address=\"" + queueName + "\"";
            try {
                Response getResponse = client.target(url).request(MediaType.APPLICATION_JSON).header("Authorization", authorizationHeaderValue).get();
                if (getResponse.getStatus() == 200) {
                    String rawBody = getResponse.readEntity(String.class);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jolokiaObj = (JSONObject) jsonParser.parse(rawBody);
//                    value = (Long) ((JSONObject) jolokiaObj.get("value")).get("QueueSize");
                    value = (Long) ((JSONObject) jolokiaObj.get("value")).get("MessageCount");
                }
                client.close();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
            JSONObject queueObj = new JSONObject();
            queueObj.put("queue", queueName);
            queueObj.put("messageCount", value);
            respObj = new ServiceResponse(0, "SUCCESS", queueObj);
        } catch (Exception ex) {
            respObj = new ServiceResponse(-10, ex.getClass().getCanonicalName(), ErrorHandling.getStackTrace(ex));
            logger.error("Error Occured in getCROInteractionQueueSize|" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        try {
            client.close();
        } catch (Exception ex) {
        }
        return respObj;
    }

}
