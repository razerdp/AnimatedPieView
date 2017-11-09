package com.razerdp.widget.animatedpieview.utils;

/**
 * Created by 大灯泡 on 2017/11/9.
 */

public class DegreeUtil {

    public static float limitDegreeInTo360(double inputAngle) {
        float result;
        double tInputAngle = inputAngle - (int) inputAngle;//取小数
        result = (float) ((int) inputAngle % 360.0f + tInputAngle);
        return result < 0 ? 360.0f + result : result;
    }
}
