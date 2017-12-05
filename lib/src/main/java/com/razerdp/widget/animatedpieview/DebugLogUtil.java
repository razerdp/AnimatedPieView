package com.razerdp.widget.animatedpieview;

import android.util.Log;

import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class DebugLogUtil {
    private static final String TAG = "DebugLogUtil";
    public static boolean LOG_SHOW = false;

    public static void logAngles(String pre, List<InternalPieInfo> pieInfos) {
        if (!LOG_SHOW) return;
        if (!ToolUtil.isListEmpty(pieInfos)) {
            for (InternalPieInfo pieInfo : pieInfos) {
                logAngles(pre, pieInfo);
            }
        }
    }

    public static void logAngles(List<InternalPieInfo> pieInfos) {
        if (!LOG_SHOW) return;
        if (!ToolUtil.isListEmpty(pieInfos)) {
            for (InternalPieInfo pieInfo : pieInfos) {
                logAngles(pieInfo);
            }
        }
    }

    public static void logAngles(InternalPieInfo pieInfo) {
        logAngles("", pieInfo);
    }

    public static void logAngles(String pre, InternalPieInfo pieInfo) {
        if (!LOG_SHOW) return;
        if (pieInfo != null) {
            Log.i(TAG, pre + pieInfo.toString());
        }
    }

    public static void logTouchInRing(boolean in) {
        if (!LOG_SHOW) return;
        Log.i(TAG, "是否点在圆环内: " + in);
    }

    public static void logTouchAngle(double angle) {
        if (!LOG_SHOW) return;
        logTouchAngle("点击角度： ", angle);
    }

    public static void findTouchInfo(InternalPieInfo info) {
        if (!LOG_SHOW || info == null) return;
        Log.i(TAG, "找到点击的info: " + info.toString());
    }

    public static void logTouchAngle(String pre, double angle) {
        if (!LOG_SHOW) return;
        Log.i(TAG, pre + angle);
    }
}
