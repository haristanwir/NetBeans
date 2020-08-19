/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.SmppServerHandler;
import com.cloudhopper.smpp.SmppServerSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.type.SmppProcessingException;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDServerHandler implements SmppServerHandler {

    private static final Logger logger = USSDLogger.getLogger(USSDServerHandler.class.getName());
    private ArrayList<SmppServerSession> sessions = new ArrayList<>();
    private String systemId = null;
    private String password = null;

    public USSDServerHandler(String systemId, String password) {
        super();
        this.systemId = systemId;
        this.password = password;
    }

    @Override
    public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration, final BaseBind bindRequest) throws SmppProcessingException {
        // test name change of sessions
        // this name actually shows up as thread context....
        //sessionConfiguration.setName("JazzCash.SMPP." + sessionConfiguration.getSystemId());
        
        logger.info("Incoming bindRequest: " + bindRequest.toString());

        if (!systemId.equals(bindRequest.getSystemId())) {
            throw new SmppProcessingException(SmppConstants.STATUS_INVSYSID);
        }
        if (!password.equals(bindRequest.getPassword())) {
            throw new SmppProcessingException(SmppConstants.STATUS_INVPASWD);
        }
    }

    @Override
    public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp preparedBindResponse) throws SmppProcessingException {
        logger.info("Session created: " + session.hashCode());
        // need to do something it now (flag we're ready)
        new EnquireLinkThread(session, 10000L).start();
        synchronized (sessions) {
            sessions.add(session);
        }
        session.serverReady(new USSDServerSessionHandler(session));
    }

    @Override
    public void sessionDestroyed(Long sessionId, SmppServerSession session) {
        logger.info("Session destroyed: " + session.hashCode());
        // print out final stats
        /*if (session.hasCounters()) {
            logger.info("final session RxSubmitSM: " + session.getCounters().getRxDataSM().toString());
            logger.info("final session RxDeliverSM: " + session.getCounters().getRxDeliverSM().toString());
            logger.info("final session RxEnquireLink: " + session.getCounters().getRxEnquireLink().toString());
            logger.info("final session RxSubmitSM: " + session.getCounters().getRxSubmitSM().toString());
            logger.info("final session TxDataSM: " + session.getCounters().getTxDataSM().toString());
            logger.info("final session TxDeliverSM: " + session.getCounters().getTxDeliverSM().toString());
            logger.info("final session TxEnquireLink: " + session.getCounters().getTxEnquireLink().toString());
            logger.info("final session TxSubmitSM: " + session.getCounters().getTxSubmitSM().toString());
        }*/
        // make sure it's really shutdown
        session.destroy();
        synchronized (sessions) {
            sessions.remove(session);
        }
    }

    public SmppServerSession getSession() {
        SmppServerSession session = null;
        try {
            synchronized (sessions) {
                session = sessions.remove(0);
                while (!session.isBound()) {
					session = null;
                    session = sessions.remove(0);
                }
            }
        } catch (Exception ex) {
        }
        return session;
    }

    public void releaseSession(SmppServerSession session) {
        if (session != null) {
            if (session.isBound()) {
                synchronized (sessions) {
                    sessions.add(session);
                }
            }
        }
    }

}
