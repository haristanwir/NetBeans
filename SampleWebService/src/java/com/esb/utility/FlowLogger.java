/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.utility;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 *
 * @author Haris Tanwir
 */
public class FlowLogger {

    private static Logger logger = null;
    private static boolean isInitialized = false;

    private static synchronized void initialize() {
        try {
            Configurator.initialize(null, Constant.APP_ROOT_DIR + File.separator + Constant.APP_DIR + File.separator + Constant.CONFIG_DIR + File.separator + Constant.LOG_XML_FILE_NAME);
            isInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String msg, String loggerName) {
        Logger logger = LogManager.getLogger(loggerName);
        if (!isInitialized) {
            FlowLogger.initialize();
        }
        logger.info(msg);
    }

    public static void logDebug(String msg, String loggerName) {
        Logger logger = LogManager.getLogger(loggerName);
        if (!isInitialized) {
            FlowLogger.initialize();
        }
        logger.debug(msg);
    }

    public static Logger getLogger(String className) {
        logger = LogManager.getLogger(className);
        if (!isInitialized) {
            FlowLogger.initialize();
        }
        return logger;
    }
}
