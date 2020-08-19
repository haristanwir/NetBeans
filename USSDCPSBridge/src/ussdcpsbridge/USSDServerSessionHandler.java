/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSession;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSmResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.tlv.Tlv;
import java.lang.ref.WeakReference;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDServerSessionHandler extends DefaultSmppSessionHandler {

    private static final Logger logger = USSDLogger.getLogger(USSDServerSessionHandler.class.getName());
    private WeakReference<SmppSession> sessionRef = null;

    public USSDServerSessionHandler(SmppSession session) {
        this.sessionRef = new WeakReference<SmppSession>(session);
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        SmppSession session = sessionRef.get();
        int commandId = pduRequest.getCommandId();
        if (commandId == SmppConstants.CMD_ID_SUBMIT_SM) {
            logger.info("firePduRequestReceived|session:" + session.hashCode() + "|SubmitSm:" + ((SubmitSm) pduRequest).toString());
            SubmitSm submitSm = (SubmitSm) pduRequest;
            String responsePayload = null;
            if (submitSm.getShortMessage() != null && submitSm.getShortMessage().length > 0) {
                if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_DEFAULT) {
                    responsePayload = CharsetUtil.decode(submitSm.getShortMessage(), CharsetUtil.CHARSET_GSM7);
                } else if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_LATIN1) {
                    responsePayload = CharsetUtil.decode(submitSm.getShortMessage(), CharsetUtil.CHARSET_ISO_8859_1);
                } else if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_UCS2) {
                    responsePayload = CharsetUtil.decode(submitSm.getShortMessage(), CharsetUtil.CHARSET_UCS_2);
                } else {
                    responsePayload = new String(submitSm.getShortMessage());
                }
            } else {
                Tlv payloadTlv = submitSm.getOptionalParameter(SmppConstants.TAG_MESSAGE_PAYLOAD);
                if (payloadTlv != null && payloadTlv.getValue() != null) {
                    if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_DEFAULT) {
                        responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_GSM7);
                    } else if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_LATIN1) {
                        responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_ISO_8859_1);
                    } else if (submitSm.getDataCoding() == SmppConstants.DATA_CODING_UCS2) {
                        responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_UCS_2);
                    } else {
                        responsePayload = new String(payloadTlv.getValue());
                    }
                }
            }
            System.out.println("Submitsm data:"+responsePayload);

            /*new Thread() {
                public void run() {
                    USSDClientManager ussdClientManager = USSDClientManager.getManager();
                    if (!ussdClientManager.isInitialized()) {
                        synchronized (ussdClientManager) {
                            if (!ussdClientManager.isInitialized()) {
                                ussdClientManager.initialize();
                            }
                        }
                    }
                    DefaultSmppSession ussdClientSession = ussdClientManager.getSession();
                    try {
                        ussdClientSession.sendRequestPdu(pduRequest, 10000, false);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    ussdClientManager.releaseSession(ussdClientSession);
                }
            }.start();*/
        } else {
            logger.debug("firePduRequestReceived|session:" + session.hashCode() + "|pduRequest:" + pduRequest.toString());
        }
        return pduRequest.createResponse();
    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
        SmppSession session = sessionRef.get();
        int commandId = pduAsyncResponse.getResponse().getCommandId();
        if (commandId == SmppConstants.CMD_ID_DELIVER_SM_RESP) {
            logger.info("fireExpectedPduResponseReceived|session:" + session.hashCode() + "|DeliverSmResp:" + ((DeliverSmResp) pduAsyncResponse).toString());
        } else {
            logger.debug("fireExpectedPduResponseReceived|session:" + session.hashCode() + "|pduAsyncResponse:" + pduAsyncResponse.toString());
        }
    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {
        SmppSession session = sessionRef.get();
        logger.info("fireUnexpectedPduResponseReceived|session:" + session.hashCode() + "|pduResponse:" + pduResponse.toString());
    }
}
