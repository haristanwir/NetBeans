/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esb.utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author Haris Tanwir
 */
public class ErrorHandling {

    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    public static String getCustomStackTrace(Throwable aThrowable, String msg) {
        final StringBuilder result = new StringBuilder(msg);
        result.append(aThrowable.toString());
        for (StackTraceElement element : aThrowable.getStackTrace()) {
            result.append(element);
        }
        return result.toString();
    }
}
