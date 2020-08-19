/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.impl.DefaultSmppSession;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDClientManager {

    private static final Logger logger = USSDLogger.getLogger(USSDClientManager.class.getName());
    private static USSDClientManager manager = new USSDClientManager();
    private Integer maxSession = 5;
    private boolean initialized = false;
    private ArrayList<DefaultSmppSession> sessions = new ArrayList<>();

    public static USSDClientManager getManager() {
        return manager;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public synchronized void initialize() {
        if (this.initialized) {
            return;
        }
        for (int i = 0; i < maxSession; i++) {
            try {
                USSDClientSession ussdClientSession = new USSDClientSession();
                sessions.add(ussdClientSession.getSession());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        initialized = true;
    }

    public DefaultSmppSession getSession() {
        DefaultSmppSession session = null;
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
        if (session == null) {
            USSDClientSession ussdClientSession = new USSDClientSession();
            session = ussdClientSession.getSession();
        }
        return session;
    }

    public void releaseSession(DefaultSmppSession session) {
        if (session != null) {
            if (session.isBound()) {
                synchronized (sessions) {
                    sessions.add(session);
                }
            }
        }
    }
}
