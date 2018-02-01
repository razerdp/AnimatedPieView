package com.razerdp.widget.animatedpieview.utils;

import android.util.Log;

/**
 * Created by 大灯泡 on 2018/1/31.
 */
public class PLog {

    public static boolean DEBUG;

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private PLog() {

    }

    public static boolean isDebuggable() {
        return DEBUG;
    }

    public static void setDebuggable(boolean debug) {
        DEBUG = debug;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }


    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.e(className, createLog(message));
    }


    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(Thread.currentThread().getStackTrace());
        Log.wtf(className, createLog(message));
    }
}
