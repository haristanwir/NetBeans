/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudhopperclient;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.commons.util.HexUtil;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSession;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.tlv.Tlv;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppBindException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.cloudhopper.smpp.util.DeliveryReceipt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Haris Tanwir
 */
public class CloudHopperClient {

    /**
     * @param args the command line arguments
     */
    private static final Logger logger = LoggerFactory.getLogger(CloudHopperClient.class);

    public static void main(String[] args) throws SmppTimeoutException, SmppChannelException, UnrecoverablePduException, SmppBindException, InterruptedException, SmppInvalidArgumentException, RecoverablePduException, IOException {
        // TODO code application logic here

        DefaultSmppClient smppClient = new DefaultSmppClient(Executors.newCachedThreadPool(), 1);
        SmppSessionConfiguration config = new SmppSessionConfiguration();
        config.setWindowSize(5);
        config.setName("Tester.Session");
        config.setType(SmppBindType.TRANSMITTER);

//        config.setHost("10.227.16.66");
//        config.setPort(15019);
//        config.setConnectTimeout(10000);
//        config.setSystemId("IBM_CMS5");
//        config.setPassword("Ibm@123");
//        
        String messagid_as_long = "";
        //System.out.println(DeliveryReceipt.toMessageIdAsLong("01b4453e"));
        //System.out.println(DeliveryReceipt.toMessageIdAsHexString(28591422));

        //HexUtil.
        String ip = "127.0.0.1";
        String port = "2775";
        String systemID = "smppclient1";
        String password = "password";
        String from = "923000513022";
        String to = "8558";
        String smsText = "CHKIDENTITY_RESP 0 99 9";
        
        String codingValueStr = "2";
        String payload = "false";

        config.setHost(ip);
        config.setPort(Integer.parseInt(port));
        config.setConnectTimeout(10000);
        config.setSystemId(systemID);
        config.setPassword(password);

        config.getLoggingOptions().setLogBytes(false);
        // to enable monitoring (request expiration)
        config.setRequestExpiryTimeout(10000);
        config.setWindowMonitorInterval(5000);
        config.setCountersEnabled(false);

        ClientSmppSessionHandler sessionHandler = new ClientSmppSessionHandler();

//        Thread.sleep(5000);
        DefaultSmppSession session = (DefaultSmppSession) smppClient.bind(config, sessionHandler);

        System.in.read();
        
//        session.unbind(1000);
//        
//        session.close();
//        
//        smppClient.destroy();
//        
//        if(true){
//            System.out.println("returning");
//            return;
//        }
        byte[] textBytes = CharsetUtil.encode(smsText, CharsetUtil.CHARSET_GSM);

        SubmitSm submit0 = new SubmitSm();

        // add delivery receipt
        submit0.setDestAddress(new Address(SmppConstants.TON_ALPHANUMERIC, SmppConstants.NPI_ISDN, to));
        submit0.setSourceAddress(new Address(SmppConstants.TON_UNKNOWN, SmppConstants.NPI_UNKNOWN, from));
        submit0.setReplaceIfPresent((byte) 0x0);
        submit0.setServiceType("VMA");
        submit0.setValidityPeriod(null);
        submit0.setEsmClass((byte) 0x0);
        submit0.setProtocolId((byte) 0x0);
        submit0.setPriority((byte) 0x01);
        submit0.setRegisteredDelivery(SmppConstants.REGISTERED_DELIVERY_SMSC_RECEIPT_NOT_REQUESTED);
        submit0.setDefaultMsgId((byte) 0x0);
        submit0.setSequenceNumber(123456);

        boolean isPayload = (payload.equals("true"));
        int codingValue = Integer.parseInt(codingValueStr);
        byte generalDataCoding = 0;
        if (codingValue == 1) {
            generalDataCoding = (byte) 0x00;
        } else if (codingValue == 2) {
            //smsText = "پاکستان کے مختلف شہروں میں انگریزوں کے دور کے گھڑیالوں کی تصاویر۔";
            //smsText = "CHECKDATA";
            generalDataCoding = (byte) 0x08;
            textBytes = CharsetUtil.encode(smsText, CharsetUtil.CHARSET_UCS_2);
        } else if (codingValue == 3) {
            generalDataCoding = (byte) 0x10;
            textBytes = CharsetUtil.encode(smsText, CharsetUtil.CHARSET_GSM);
        } else if (codingValue == 4) {
            smsText = "پاکستان کے مختلف شہروں میں انگریزوں کے دور کے گھڑیالوں کی تصاویر۔";
            generalDataCoding = (byte) 0x18;
            textBytes = CharsetUtil.encode(smsText, CharsetUtil.CHARSET_UCS_2);
        }
        submit0.setDataCoding(generalDataCoding);

        if (isPayload) {
            submit0.setShortMessage((new byte[0]));
            submit0.setOptionalParameter(new Tlv(SmppConstants.TAG_MESSAGE_PAYLOAD, textBytes));
        } else {
            submit0.setShortMessage(textBytes);
        }
        System.out.println("submit0:" + submit0.toString());

        EnquireLinkResp enquireLinkResp1 = session.enquireLink(new EnquireLink(), 10000);
        System.out.println("enquireLinkResp1:" + enquireLinkResp1.toString());

        long start = System.currentTimeMillis();
        //async submitsmresp
        WindowFuture<Integer, PduRequest, PduResponse> sendRequestPdu = session.sendRequestPdu(submit0, 10000, false);
        System.out.println((System.currentTimeMillis() - start) + " msec|" + sendRequestPdu.toString());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String submit0Json = gson.toJson(submit0);
        System.out.println(submit0Json);
        SubmitSm fromJson = gson.fromJson(submit0Json, SubmitSm.class);
        //System.out.println(new String(fromJson.getOptionalParameter(SmppConstants.TAG_MESSAGE_PAYLOAD).getValue()));
        System.out.println(submit0.toString());
        System.out.println(fromJson.toString());
        System.out.println(submit0.hashCode());
        System.out.println(fromJson.hashCode());
        System.out.println(((PduRequest) submit0).toString());
        System.out.println(((PduRequest) fromJson).toString());

        String responsePayload = null;
        if (fromJson.getShortMessage() != null && fromJson.getShortMessage().length > 0) {
            if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_DEFAULT) {
                responsePayload = CharsetUtil.decode(fromJson.getShortMessage(), CharsetUtil.CHARSET_GSM7);
            } else if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_LATIN1) {
                responsePayload = CharsetUtil.decode(fromJson.getShortMessage(), CharsetUtil.CHARSET_ISO_8859_1);
            } else if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_UCS2) {
                responsePayload = CharsetUtil.decode(fromJson.getShortMessage(), CharsetUtil.CHARSET_UCS_2);
            } else {
                responsePayload = new String(fromJson.getShortMessage());
            }
        } else {
            Tlv payloadTlv = fromJson.getOptionalParameter(SmppConstants.TAG_MESSAGE_PAYLOAD);
            if (payloadTlv != null && payloadTlv.getValue() != null) {
                if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_DEFAULT) {
                    responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_GSM7);
                } else if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_LATIN1) {
                    responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_ISO_8859_1);
                } else if (fromJson.getDataCoding() == SmppConstants.DATA_CODING_UCS2) {
                    responsePayload = CharsetUtil.decode(payloadTlv.getValue(), CharsetUtil.CHARSET_UCS_2);
                } else {
                    responsePayload = new String(payloadTlv.getValue());
                }
            }
        }
        System.out.println(responsePayload);
//        WindowFuture<Integer, PduRequest, PduResponse> future0 = session.sendRequestPdu(new EnquireLink(), 10000, true);
//        if (!future0.await()) {
//            logger.error("Failed to receive enquire_link_resp within specified time");
//        } else if (future0.isSuccess()) {
//            EnquireLinkResp enquireLinkResp2 = (EnquireLinkResp) future0.getResponse();
//            logger.info("enquire_link_resp #2: commandStatus [" + enquireLinkResp2.getCommandStatus() + "=" + enquireLinkResp2.getResultMessage() + "]");
//        } else {
//            logger.error("Failed to properly receive enquire_link_resp: " + future0.getCause());
//        }
        if (session != null) {
            System.out.println("Cleaning up session... (final counters)");
            if (session.hasCounters()) {
                System.out.println("tx-enquireLink: " + session.getCounters().getTxEnquireLink());
                System.out.println("tx-submitSM: " + session.getCounters().getTxSubmitSM());
                System.out.println("tx-deliverSM: " + session.getCounters().getTxDeliverSM());
                System.out.println("tx-dataSM: " + session.getCounters().getTxDataSM());
                System.out.println("rx-enquireLink: " + session.getCounters().getRxEnquireLink());
                System.out.println("rx-submitSM: " + session.getCounters().getRxSubmitSM());
                System.out.println("rx-deliverSM: " + session.getCounters().getRxDeliverSM());
                System.out.println("rx-dataSM: " + session.getCounters().getRxDataSM());
            }

            System.in.read();
            
            session.unbind(5000);
            System.out.println("unbind called");
            session.close(5000);
            System.out.println("close called");
            session.destroy();
            System.out.println("destroy called");

            // alternatively, could call close(), get outstanding requests from
            // the sendWindow (if we wanted to retry them later), then call shutdown()
        }

        // this is required to not causing server to hang from non-daemon threads
        // this also makes sure all open Channels are closed to I *think*
        //smppClient.destroy();
        System.out.println("Done. Exiting");
    }
}
