package com.razerdp.widget.animatedpieview;

import android.util.Log;

import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class DebugLogUtil {
    private static final String TAG = "DebugLogUtil";
    public static boolean LOG_SHOW = true;

    public static void logAngles(List<PieInfoImpl> pieInfos) {
        if (!LOG_SHOW) return;
        if (!ToolUtil.isListEmpty(pieInfos)) {
            for (PieInfoImpl pieInfo : pieInfos) {
                logAngles(pieInfo);
            }
        }
    }

    public static void logAngles(PieInfoImpl pieInfo) {
        logAngles("", pieInfo);
    }

    public static void logAngles(String pre, PieInfoImpl pieInfo) {
        if (!LOG_SHOW) return;
        if (pieInfo != null) {
            Log.i(TAG, pre + "值： " + pieInfo.getPieInfo().getValue() + "    开始角度: " + pieInfo.getStartAngle() + "    结束角度： " + pieInfo.getEndAngle());
        }
    }

    public static void logTouchInRing(boolean in) {
        if (!LOG_SHOW) return;
        Log.i(TAG, "是否点在圆环内: " + in);
    }

    public static void logTouchAngle(double angle) {
        if (!LOG_SHOW) return;
        logTouchAngle("点击角度： ",angle);
    }

    public static void logTouchAngle(String pre, double angle) {
        if (!LOG_SHOW) return;
        Log.i(TAG, pre + angle);
    }
}
