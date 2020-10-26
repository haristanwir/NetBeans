/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.tlv.Tlv;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import java.io.IOException;

/**
 *
 * @author Haris Tanwir
 */
public class USSDServer {

    static public void main(String[] args) throws SmppInvalidArgumentException, IOException {

        USSDServerManager ussdServerManager = USSDServerManager.getManager();
        if (!ussdServerManager.isInitialized()) {
            synchronized (ussdServerManager) {
                if (!ussdServerManager.isInitialized()) {
                    ussdServerManager.initialize("smppclient1", "password");
                }
            }
        }
        
        System.in.read();

        DeliverSm deliverSm = new DeliverSm();
        deliverSm.setDataCoding((byte)0x00);
        deliverSm.setDefaultMsgId((byte)0x00);
        deliverSm.setDestAddress(new Address((byte)0x01, (byte)0x01, "8558"));
        deliverSm.setOptionalParameter(new Tlv(SmppConstants.TAG_PAYLOAD_TYPE, new byte[]{0x01}));
        deliverSm.setPriority((byte)0x00);
        deliverSm.setProtocolId((byte)0x34);
        deliverSm.setRegisteredDelivery((byte)0x00);
        deliverSm.setSequenceNumber(10342);
        deliverSm.setShortMessage(CharsetUtil.encode("CHKIDENTITY", CharsetUtil.CHARSET_GSM));
        deliverSm.setSourceAddress(new Address((byte)0x01, (byte)0x01, "923000513022"));
        
        System.out.println("Sending: CHKIDENTITY");
        
        USSDServerHandler ussdServerHandler = ussdServerManager.getUssdServerHandler();
        SmppServerSession ussdServerSession = ussdServerHandler.getSession();
        if (ussdServerSession != null && ussdServerSession.isBound()) {
            try {
                ussdServerSession.sendRequestPdu(deliverSm, 10000, false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Requeue deliverSm:" + deliverSm.hashCode());            
        }
        ussdServerHandler.releaseSession(ussdServerSession);
        /*
        USSDClientManager ussdClientManager = USSDClientManager.getManager();
        if (!ussdClientManager.isInitialized()) {
        synchronized (ussdClientManager) {
        if (!ussdClientManager.isInitialized()) {
        ussdClientManager.initialize();
        }
        }
        }
         */
    }
}
