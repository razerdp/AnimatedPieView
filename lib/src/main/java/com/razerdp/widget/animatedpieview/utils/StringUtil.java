package com.razerdp.widget.animatedpieview.utils;

import android.text.TextUtils;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class StringUtil {

    public static boolean noEmpty(String source) {
        return !TextUtils.isEmpty(source);
    }

    public static boolean isEmptyWithNull(String source) {
        return TextUtils.isEmpty(source) || TextUtils.equals(source, "null") || TextUtils.equals(source, "Null") || TextUtils.equals(source, "NULL");
    }

    public static String trim(String source) {
        if (noEmpty(source)) {
            return source.trim();
        }
        return source;

    }
}
