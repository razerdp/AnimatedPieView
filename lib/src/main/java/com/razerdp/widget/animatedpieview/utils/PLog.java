package com.razerdp.widget.animatedpieview.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * Created by 大灯泡 on 2018/1/31.
 */
public class PLog {

    public static boolean DEBUG = false;

    static String tag;//类名

    private PLog() {

    }

    public static boolean isDebuggable() {
        return DEBUG;
    }

    public static void setDebuggable(boolean debug) {
        DEBUG = debug;
    }


    private static StackTraceElement getTag() {
        StackTraceElement element = getCurrentStackTrace();
        tag = element == null ? "Unknow" : element.getFileName().replace(".java", "");
        return element;
    }


    public static void e(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.e(tag, wrapLogWithMethodLocation(element, message));
    }


    public static void i(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.i(tag, wrapLogWithMethodLocation(element, message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.d(tag, wrapLogWithMethodLocation(element, message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.v(tag, wrapLogWithMethodLocation(element, message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.w(tag, wrapLogWithMethodLocation(element, message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        StackTraceElement element = getTag();
        Log.wtf(tag, wrapLogWithMethodLocation(element, message));
    }

    //-----------------------------------------tool-----------------------------------------


    /**
     * 代码定位
     */
    private static String wrapLogWithMethodLocation(StackTraceElement element, String msg) {
        if (element == null) {
            element = getCurrentStackTrace();
        }
        String className = "unknow";
        String methodName = "unknow";
        int lineNumber = -1;
        if (element != null) {
            className = element.getFileName();
            methodName = element.getMethodName();
            lineNumber = element.getLineNumber();
        }

        StringBuilder sb = new StringBuilder();
        msg = wrapJson(msg);
        sb.append("  (")
                .append(className)
                .append(":")
                .append(lineNumber)
                .append(") #")
                .append(methodName)
                .append("：")
                .append('\n')
                .append(msg);
        return sb.toString();
    }

    public static String wrapJson(String jsonStr) {
        String message;
        if (TextUtils.isEmpty(jsonStr)) return "json为空";
        try {
            if (jsonStr.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(jsonStr);
                message = jsonObject.toString(2);
                message = "\n================JSON================\n"
                        + message + '\n'
                        + "================JSON================\n";
            } else if (jsonStr.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(jsonStr);
                message = jsonArray.toString(4);
                message = "\n================JSONARRAY================\n"
                        + message + '\n'
                        + "================JSONARRAY================\n";
            } else {
                message = jsonStr;
            }
        } catch (JSONException e) {
            message = jsonStr;
        }

        return message;
    }

    public static String getCrashInfo(Throwable tr) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        tr.printStackTrace(printWriter);
        Throwable cause = tr.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String crashInfo = writer.toString();
        printWriter.close();
        return crashInfo;
    }

    /**
     * 获取当前栈信息
     *
     * @return
     */
    private static StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace, PLog.class);
        if (stackOffset == -1) {
            stackOffset = getStackOffset(trace, Logger.class);
            if (stackOffset == -1) {
                stackOffset = getStackOffset(trace, Log.class);
                if (stackOffset == -1) {
                    return null;
                }
            }
        }
        return trace[stackOffset];
    }

    private static int getStackOffset(StackTraceElement[] trace, Class cla) {
        int logIndex = -1;
        for (int i = 0; i < trace.length; i++) {
            StackTraceElement element = trace[i];
            String tClass = element.getClassName();
            if (TextUtils.equals(tClass, cla.getName())) {
                logIndex = i;
            } else {
                if (logIndex > -1) break;
            }
        }
        logIndex++;
        if (logIndex >= trace.length) {
            logIndex = trace.length - 1;
        }
        return logIndex;
    }
}
