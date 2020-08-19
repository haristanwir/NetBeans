/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbhealthcheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Haris Tanwir
 */
public class DBHealthCheck {

    /**
     * @param args the command line arguments
     */
    final static Logger logger = Logger.getLogger(DBHealthCheck.class);

    public static void main(String[] args) {
        // TODO code application logic here

        String ip = null;
        String username = null;
        String password = null;
        String servicename = null;
        Long sleeptimesec = null;
        Connection connection = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,SSS");

        String log4JPropertyFile = "./log4j.properties";
        Properties p = new Properties();

        try {
            p.load(new FileInputStream(log4JPropertyFile));
            PropertyConfigurator.configure(p);
        } catch (IOException e) {
            System.out.println("Unable to configure log4j");
            System.out.println(e);
            return;
        }

        FileAppender appender = (FileAppender) logger.getAppender("UTIL");
        System.out.println("Logs created on : " + new File(appender.getFile()));

        try {
            ip = args[0];
        } catch (Exception ex) {
            logger.info("ERROR!!!...args[0] should be server IP...Exiting...");
            return;
        }
        try {
            username = (args[1]);
        } catch (Exception ex) {
            logger.info("ERROR!!!...args[1] should be server username...Exiting...");
            return;
        }
        try {
            password = (args[2]);
        } catch (Exception ex) {
            logger.info("ERROR!!!...args[2] should be password...Exiting...");
            return;
        }
        try {
            servicename = (args[3]);
        } catch (Exception ex) {
            logger.info("ERROR!!!...args[3] should be servicename...Exiting...");
            return;
        }
        try {
            sleeptimesec = Long.parseLong(args[4]);
        } catch (Exception ex) {
            logger.info("ERROR!!!...args[4] should be sleep time in sec...Exiting...");
            return;
        }

        while (true) {
            long constart = System.currentTimeMillis();
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException ex) {
                logger.info("Where is your Oracle JDBC Driver?");
                logger.info(getStackTrace(ex));
                return;
            }
            try {
                connection = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":1521/" + servicename, username, password);
            } catch (SQLException ex) {
                logger.info(getStackTrace(ex));
                return;
            }
            long connTime = System.currentTimeMillis() - constart;
            logger.info("DB Connection Time: " + connTime + " msec");
            try {
                long querystart = System.currentTimeMillis();
                String sql = "SELECT sysdate FROM dual";
                logger.info("Executing Query: " + sql);
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();
                long queryTime = System.currentTimeMillis() - querystart;
                String sysdate = "";
                while (rs.next()) {
                    sysdate = rs.getString("sysdate");
                }
                logger.info("SQL Query Time: " + queryTime + " msec");
                logger.info("SQL Result: Sysdate -> " + sysdate);

                try {
                    rs.close();
                } catch (Exception ex) {
                    logger.info(getStackTrace(ex));
                }
                try {
                    statement.close();
                } catch (Exception ex) {
                    logger.info(getStackTrace(ex));
                }
                try {
                    connection.close();
                } catch (Exception ex) {
                    logger.info(getStackTrace(ex));
                }
                logger.info("Connection Closed");
            } catch (Exception ex) {
                logger.info(getStackTrace(ex));
            }

            logger.info("Thread sleeping for " + ((sleeptimesec * 1000)) + " msec");
            try {
                Thread.sleep(sleeptimesec * 1000);
            } catch (InterruptedException ex) {
                logger.info(getStackTrace(ex));
            }
        }
    }

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

}
