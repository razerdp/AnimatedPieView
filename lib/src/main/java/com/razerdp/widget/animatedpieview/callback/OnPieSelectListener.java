package com.razerdp.widget.animatedpieview.callback;

import android.support.annotation.NonNull;

import com.razerdp.widget.animatedpieview.data.IPieInfo;

/**
 * Created by 大灯泡 on 2017/11/10.
 */

public interface OnPieSelectListener<T extends IPieInfo> {
    void onSelectPie(@NonNull T pieInfo);
}
