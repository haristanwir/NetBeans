/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ussdcpsbridge;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

/**
 *
 * @author Haris Tanwir
 */
public class USSDLogger {

    private static Logger logger = null;
    private static boolean isInitialized = false;

    private static synchronized void initialize() {
        try {
            ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
            AppenderComponentBuilder console = builder.newAppender("console", "Console");
            LayoutComponentBuilder standardPattern = builder.newLayout("PatternLayout");
            standardPattern.addAttribute("pattern", "%d{dd MMM yyyy HH:mm:ss,SSS}|[%p]|{%t}|%c{1}:%m%n");
            console.add(standardPattern);
            builder.add(console);
            RootLoggerComponentBuilder asynclogger = asynclogger = builder.newAsyncRootLogger(Level.INFO);
            //RootLoggerComponentBuilder asynclogger = asynclogger = builder.newAsyncRootLogger(Level.DEBUG);
            /*
            try {
                if (Utility.getProperty("LOG_LEVEL").equalsIgnoreCase("debug")) {
                    asynclogger = builder.newAsyncRootLogger(Level.DEBUG);
                } else {
                    asynclogger = builder.newAsyncRootLogger(Level.INFO);
                }
            } catch (Exception ex1) {
            }
            */
            asynclogger.addAttribute("additivity", false);
            asynclogger.add(builder.newAppenderRef("console"));
            builder.add(asynclogger);
            Configurator.initialize(builder.build());
            isInitialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logInfo(String msg, String loggerName) {
        if (!isInitialized) {
            USSDLogger.initialize();
        }
        Logger logger = LogManager.getLogger(loggerName);
        logger.info(msg);
    }

    public static void logDebug(String msg, String loggerName) {
        if (!isInitialized) {
            USSDLogger.initialize();
        }
        Logger logger = LogManager.getLogger(loggerName);
        logger.debug(msg);
    }

    public static Logger getLogger(String className) {
        if (!isInitialized) {
            USSDLogger.initialize();
        }
        logger = LogManager.getLogger(className);
        return logger;
    }
}
