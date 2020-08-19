/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import com.cloudhopper.smpp.SmppServerConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Haris Tanwir
 */
public class USSDServerManager {

    /**
     * @param args the command line arguments
     */
    private static final Logger logger = USSDLogger.getLogger(USSDServerManager.class.getName());
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, new ThreadFactory() {
        private AtomicInteger sequence = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("SmppServerSessionWindowMonitorPool-" + sequence.getAndIncrement());
            return thread;
        }
    });
    private SmppServerConfiguration configuration = null;
    private DefaultSmppServer ussdServer = null;
    private USSDServerHandler ussdServerHandler = null;
    private boolean initialized = false;
    private static USSDServerManager manager = new USSDServerManager();

    public static USSDServerManager getManager() {
        return manager;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public USSDServerHandler getUssdServerHandler() {
        return ussdServerHandler;
    }

    public synchronized void initialize(String systemID, String password) {
        if (this.initialized) {
            return;
        }
        try {
            configuration = new SmppServerConfiguration();
            configuration.setPort(12775);
            configuration.setBindTimeout(10000);
            configuration.setConnectTimeout(10000);
            configuration.setDefaultWindowSize(10);
            configuration.setMaxConnectionSize(10);
            configuration.setDefaultRequestExpiryTimeout(30000);
            configuration.setDefaultWindowMonitorInterval(15000);
            configuration.setDefaultWindowWaitTimeout(configuration.getDefaultRequestExpiryTimeout());
            configuration.setDefaultSessionCountersEnabled(false);
            configuration.setNonBlockingSocketsEnabled(true);
            configuration.setJmxEnabled(true);
            ussdServerHandler = new USSDServerHandler(systemID, password);
            ussdServer = new DefaultSmppServer(configuration, ussdServerHandler, executor, monitorExecutor);
            ussdServer.start();
            initialized = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopServer() {
        if (!this.initialized) {
            return;
        }
        try {
            ussdServer.stop();
        } catch (Exception ex) {
        }
        try {
            ussdServer.destroy();
        } catch (Exception ex) {
        }
    }
}
