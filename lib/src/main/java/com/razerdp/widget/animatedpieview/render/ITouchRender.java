package com.razerdp.widget.animatedpieview.render;

import android.view.MotionEvent;

/**
 * Created by 大灯泡 on 2018/1/31.
 */
public interface ITouchRender {

    boolean onTouchEvent(MotionEvent event);

    void forceAbortTouch();
}
