/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.msgflow;

import com.esb.core.EmployeeInputWorker;
import com.esb.utility.Constant;
import com.esb.utility.ErrorHandling;
import com.esb.utility.FlowLogger;
import com.esb.utility.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
@WebServlet(name = "EmployeeInputFlow", urlPatterns = "/EmployeeInputFlow")
public class EmployeeInputFlow extends HttpServlet implements ServletContextListener {

    private static final Logger logger = FlowLogger.getLogger(EmployeeInputWorker.class.getName());
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

    private static String QUEUE_NAME = "EMPLOYEE";
    private static String QUEUE_NAME_BOQ = QUEUE_NAME + ".BOQ";
    private static Integer threadPoolSize = 1;
    private static ExecutorService execService = null;
    private static Boolean isInitialized = false;
    private static Boolean startOnDeploy = false;

    public EmployeeInputFlow() {
    }

    public static synchronized void initialize() {
        if (!isInitialized) {
            try {
                EmployeeInputWorker.init(Integer.parseInt(Utility.getProperty(Constant.EmployeeInputFlow_FLOW_TPS)),
                        Utility.getProperty(Constant.JDBC_DRIVER),
                        Utility.getProperty(Constant.JDBC_STRING),
                        Utility.getProperty(Constant.JDBC_USER_NAME),
                        Utility.getProperty(Constant.JDBC_PASSWORD),
                        Utility.getProperty(Constant.RABBITMQ_HOST_NAME),
                        Integer.parseInt(Utility.getProperty(Constant.RABBITMQ_PORT)));
                threadPoolSize = Integer.parseInt(Utility.getProperty(Constant.EmployeeInputFlow_POOL_SIZE));//get from property
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                Errorlogger.error(ErrorHandling.getStackTrace(ex));
            }
            EmployeeInputWorker.setIsRunning(true);
            execService = Executors.newFixedThreadPool(threadPoolSize);
            for (int i = 0; i < threadPoolSize; i++) {
                EmployeeInputWorker mqThread = new EmployeeInputWorker(QUEUE_NAME, QUEUE_NAME_BOQ);
                execService.execute(mqThread);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }
            isInitialized = true;
        }
    }

    public static synchronized void delete() {
        if (isInitialized) {
            EmployeeInputWorker.setIsRunning(false);
            execService.shutdown();
            try {
                execService.awaitTermination(30, TimeUnit.MINUTES);
            } catch (Exception ex) {
            }
            isInitialized = false;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            startOnDeploy = Boolean.parseBoolean(Utility.getProperty(Constant.EmployeeInputFlow_START_ON_DEPLOY));
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
        EmployeeInputWorker.shutdownWorker();
    }
}
