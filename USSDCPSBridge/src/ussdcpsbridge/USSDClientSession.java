/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSession;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDClientSession {

    private static final Logger logger = USSDLogger.getLogger(USSDClientSession.class.getName());
    private SmppSessionConfiguration configuration = null;
    private DefaultSmppClient ussdClient = null;
    private DefaultSmppSession session = null;

    public USSDClientSession() {
        configuration = new SmppSessionConfiguration();
        configuration.setHost("127.0.0.1");
        configuration.setPort(2775);
        configuration.setSystemId("smppclient1");
        configuration.setPassword("password");
        configuration.setType(SmppBindType.TRANSCEIVER);
        configuration.setBindTimeout(10000);
        configuration.setConnectTimeout(10000);
        configuration.setWindowSize(10);
        configuration.setRequestExpiryTimeout(30000);
        configuration.setWindowMonitorInterval(15000);
        configuration.setWindowWaitTimeout(configuration.getRequestExpiryTimeout());
        configuration.getLoggingOptions().setLogBytes(false);
        configuration.setCountersEnabled(false);
        ussdClient = new DefaultSmppClient(Executors.newCachedThreadPool(), 1);
        try {
            session = (DefaultSmppSession) ussdClient.bind(configuration, new USSDClientSessionHandler());
            new EnquireLinkThread(session, 10000L).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DefaultSmppSession getSession() {
        return session;
    }

}
