package com.razerdp.widget.animatedpieview;

import android.content.Context;
import android.view.View;

import com.razerdp.widget.animatedpieview.manager.PieManager;

/**
 * Created by 大灯泡 on 2018/2/1.
 * <p>
 * <h3>CN:</h3>pieview的实现类
 * <p>
 * <h3>EN:</h3>Interface definition for the pieview
 */
public interface IPieView {

    PieManager getManager();

    Context getViewContext();

    public AnimatedPieViewConfig getConfig();

    View getPieView();

    void onCallInvalidate();
}
