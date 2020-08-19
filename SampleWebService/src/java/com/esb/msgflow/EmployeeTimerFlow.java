/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.msgflow;

import com.esb.core.EmployeeTimerWorker;
import com.esb.utility.Constant;
import com.esb.utility.ErrorHandling;
import com.esb.utility.FlowLogger;
import com.esb.utility.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Haris Tanwir
 */
@WebListener
@WebServlet(name = "EmployeeTimerFlow", urlPatterns = "/EmployeeTimerFlow")
public class EmployeeTimerFlow extends HttpServlet implements ServletContextListener {

    private static final Logger logger = FlowLogger.getLogger(EmployeeTimerWorker.class.getName());
    private static final Logger Errorlogger = FlowLogger.getLogger(ErrorHandling.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String function = request.getParameter("function");
        if (function.equals("getFlowState")) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("status", (isInitialized) ? 1 : 0);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonObj.toJSONString());
            }
            jsonObj.clear();
            jsonObj = null;
        } else if (function.equals("startFlow")) {
            initialize();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("status", 1);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonObj.toJSONString());
            }
            jsonObj.clear();
            jsonObj = null;
        } else if (function.equals("stopFlow")) {
            delete();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("status", 1);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonObj.toJSONString());
            }
            jsonObj.clear();
            jsonObj = null;
        } else if (function.equals("reloadFlow")) {
            delete();
            try {
                Thread.sleep(5000);
            } catch (Exception ex) {
            }
            initialize();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("status", 1);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonObj.toJSONString());
            }
            jsonObj.clear();
            jsonObj = null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private static String timerID = "timer_1";
    private static String QUEUE_NAME = "EMPLOYEE";
    private static Integer timeoutSec = 1;
    private static ScheduledExecutorService scheduler = null;
    private static Boolean isInitialized = false;
    private static Boolean startOnDeploy = false;

    public EmployeeTimerFlow() {
    }

    public synchronized void initialize() {
        if (!isInitialized) {
            try {
                EmployeeTimerWorker.init(Utility.getProperty(Constant.RABBITMQ_HOST_NAME),
                        Integer.parseInt(Utility.getProperty(Constant.RABBITMQ_PORT)));
                timeoutSec = Integer.parseInt(Utility.getProperty(Constant.EmployeeTimerFlow_FLOW_TIMEOUT_SEC));//get from property
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                Errorlogger.error(ErrorHandling.getStackTrace(ex));
            }
            scheduler = Executors.newSingleThreadScheduledExecutor();
            EmployeeTimerWorker timerThread = new EmployeeTimerWorker(timerID, QUEUE_NAME);
            scheduler.scheduleAtFixedRate(timerThread, 0, timeoutSec, TimeUnit.SECONDS);
            isInitialized = true;
        }
    }

    public synchronized void delete() {
        if (isInitialized) {
            scheduler.shutdown();
            try {
                scheduler.awaitTermination(30, TimeUnit.MINUTES);
            } catch (Exception ex) {
            }
            isInitialized = false;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            startOnDeploy = Boolean.parseBoolean(Utility.getProperty(Constant.EmployeeTimerFlow_START_ON_DEPLOY));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            Errorlogger.error(ErrorHandling.getStackTrace(ex));
        }
        if (startOnDeploy) {
            initialize();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        delete();
        EmployeeTimerWorker.shutdownWorker();
    }
}
