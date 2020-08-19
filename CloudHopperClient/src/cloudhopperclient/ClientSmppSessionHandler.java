/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudhopperclient;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.DeliverSmResp;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.util.DeliveryReceipt;
import com.cloudhopper.smpp.util.DeliveryReceiptException;
import org.joda.time.DateTimeZone;

/**
 *
 * @author Haris Tanwir
 */
public class ClientSmppSessionHandler extends DefaultSmppSessionHandler {

    public ClientSmppSessionHandler() {
    }

    @Override
    public void firePduRequestExpired(PduRequest pduRequest) {
        System.out.println("PDU request expired: " + pduRequest.toString());
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        System.out.println("firePduRequestReceived ---------------");
        if (pduRequest.getCommandId() == SmppConstants.CMD_ID_DELIVER_SM) {
            System.out.println(pduRequest.toString());
            DeliverSm deliverSm = (DeliverSm) pduRequest;
            System.out.println("deliverSm.getName():" + deliverSm.getName());
            System.out.println("deliverSm.getCommandId():" + deliverSm.getCommandId());
            System.out.println("deliverSm.getCommandStatus():" + deliverSm.getCommandStatus());
            System.out.println("deliverSm.getSequenceNumber():" + deliverSm.getSequenceNumber());
            System.out.println("deliverSm.toString():" + deliverSm.toString());
            System.out.println("---------------");
            String requestPayload = null;
            if (deliverSm.getShortMessage() != null) {
                if (deliverSm.getDataCoding() == SmppConstants.DATA_CODING_DEFAULT) {
                    requestPayload = CharsetUtil.decode(deliverSm.getShortMessage(), CharsetUtil.CHARSET_GSM7);
                } else if (deliverSm.getDataCoding() == SmppConstants.DATA_CODING_LATIN1) {
                    requestPayload = CharsetUtil.decode(deliverSm.getShortMessage(), CharsetUtil.CHARSET_ISO_8859_1);
                } else if (deliverSm.getDataCoding() == SmppConstants.DATA_CODING_UCS2) {
                    requestPayload = CharsetUtil.decode(deliverSm.getShortMessage(), CharsetUtil.CHARSET_UCS_2);
                } else {
                    requestPayload = new String(deliverSm.getShortMessage());
                }
            }
            System.out.println("deliverSm.getShortMessage():" + requestPayload);
            

            //DeliverSmResp response = (DeliverSmResp) pduRequest.createResponse();
            //return response;
        } else if (pduRequest.getCommandId() == SmppConstants.CMD_ID_ENQUIRE_LINK) {
            System.out.println(pduRequest.toString());
            System.out.println("---------------");
            //EnquireLinkResp response = (EnquireLinkResp) pduRequest.createResponse();
            //return response;
        }
        PduResponse response = pduRequest.createResponse();
        return response;
    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
        System.out.println("fireExpectedPduResponseReceived------------------");
        int commandId = pduAsyncResponse.getResponse().getCommandId();
        if (SmppConstants.CMD_ID_SUBMIT_SM_RESP == commandId) {
            SubmitSmResp submitResp = (SubmitSmResp) pduAsyncResponse.getResponse();
            System.out.println("submitResp.getMessageId:  " + submitResp.getMessageId());
            System.out.println("submitResp.getCommandId:  " + submitResp.getCommandId());
            System.out.println("submitResp.getCommandStatus:  " + submitResp.getCommandStatus());
            System.out.println("submitResp.getSequenceNumber:  " + submitResp.getSequenceNumber());
            System.out.println("submitResp.getResultMessage:  " + submitResp.getResultMessage());
            System.out.println("submitResp.tostr:  " + submitResp.toString());
            System.out.println("submitResp.getName:  " + submitResp.getName());
            System.out.println("------------------");
        } else {
            System.out.println(pduAsyncResponse.toString());
            System.out.println("------------------");
        }
    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {
        System.out.println("fireUnexpectedPduResponseReceived------------------");
        System.out.println(pduResponse.toString());
        System.out.println("------------------");
    }

}
