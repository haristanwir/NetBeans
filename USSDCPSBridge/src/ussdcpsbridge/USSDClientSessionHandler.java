/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDClientSessionHandler extends DefaultSmppSessionHandler {

    private static final Logger logger = USSDLogger.getLogger(USSDClientSessionHandler.class.getName());

    public USSDClientSessionHandler() {
    }

    @Override
    public void firePduRequestExpired(PduRequest pduRequest) {
        logger.info("firePduRequestExpired|pduRequest:" + pduRequest.toString());
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        int commandId = pduRequest.getCommandId();
        if (commandId == SmppConstants.CMD_ID_DELIVER_SM) {
            DeliverSm deliverSm = (DeliverSm) pduRequest;
            logger.info("firePduRequestReceived|deliverSm:" + deliverSm.toString());
            new Thread() {
                public void run() {
                    USSDServerManager ussdServerManager = USSDServerManager.getManager();
                    if (!ussdServerManager.isInitialized()) {
                        synchronized (ussdServerManager) {
                            if (!ussdServerManager.isInitialized()) {
                                ussdServerManager.initialize("smppclient1", "password");
                            }
                        }
                    }
                    USSDServerHandler ussdServerHandler = ussdServerManager.getUssdServerHandler();
                    SmppServerSession ussdServerSession = ussdServerHandler.getSession();
                    try {
                        ussdServerSession.sendRequestPdu(pduRequest, 10000, false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    ussdServerHandler.releaseSession(ussdServerSession);
                }
            }.start();
        } else {
            logger.debug("firePduRequestReceived|pduRequest:" + pduRequest.toString());
        }
        PduResponse response = pduRequest.createResponse();
        return response;
    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
        int commandId = pduAsyncResponse.getResponse().getCommandId();
        if (commandId == SmppConstants.CMD_ID_SUBMIT_SM_RESP) {
            SubmitSmResp submitResp = (SubmitSmResp) pduAsyncResponse.getResponse();
            logger.info("fireExpectedPduResponseReceived|submitResp:" + submitResp.toString());
        } else {
            logger.debug("fireExpectedPduResponseReceived|pduAsyncResponse:" + pduAsyncResponse.toString());
        }

    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {
        logger.info("fireUnexpectedPduResponseReceived|pduResponse:" + pduResponse.toString());
    }

}
