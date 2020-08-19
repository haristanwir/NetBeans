/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class EnquireLinkThread extends Thread {

    private static final Logger logger = USSDLogger.getLogger(EnquireLinkThread.class.getName());
    private final AtomicBoolean sendingEnquireLink = new AtomicBoolean(true);
    private SmppSession session = null;
    private Long timeout = null;

    public EnquireLinkThread(SmppSession session, Long timeout) {
        this.session = session;
        this.timeout = timeout;
    }

    public void stopThread() {
        sendingEnquireLink.set(false);
    }

    @Override
    public void run() {
        while (sendingEnquireLink.get()) {
            try {
                EnquireLinkResp enquireLinkResp = session.enquireLink(new EnquireLink(), 10000);
                logger.debug("enquireLinkResp:" + enquireLinkResp.toString());
            } catch (RecoverablePduException e1) {
                closeSMPPSession();
            } catch (UnrecoverablePduException e1) {
                closeSMPPSession();
            } catch (SmppTimeoutException e1) {
                closeSMPPSession();
            } catch (SmppChannelException e1) {
                closeSMPPSession();
            } catch (InterruptedException e1) {
                closeSMPPSession();
            } catch (Exception e1) {
                closeSMPPSession();
            }
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                closeSMPPSession();
            }
        }
    }

    private void closeSMPPSession() {
        try {
            if (session != null) {
                stopThread();
                session.unbind(timeout);
                session.close();
                session.destroy();
                session = null;
            }
        } catch (Exception ex) {
            session = null;
            stopThread();
        }
    }
}
