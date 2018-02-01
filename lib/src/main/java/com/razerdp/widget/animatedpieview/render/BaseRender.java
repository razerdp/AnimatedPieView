package com.razerdp.widget.animatedpieview.render;

import android.graphics.Canvas;

import com.razerdp.widget.animatedpieview.IPieView;
import com.razerdp.widget.animatedpieview.manager.PieManager;

/**
 * Created by 大灯泡 on 2018/2/1.
 * <p>
 * <h3>CN:</h3>渲染器基类
 * <p>
 * <h3>EN:</h3>Base render
 */
public abstract class BaseRender {
    protected String TAG = this.getClass().getSimpleName();

    IPieView mIPieView;
    PieManager mPieManager;
    private volatile boolean isPrepared;

    public BaseRender(IPieView iPieView) {
        mIPieView = iPieView;
        mPieManager = iPieView.getManager();
        mPieManager.registerRender(this);
    }

    public void draw(Canvas canvas) {
        if (!isPrepared) return;
        onDraw(canvas);
    }

    public final void prepare() {
        isPrepared = false;
        reset();
        isPrepared = onPrepare();
    }

    public void destroy() {
        onDestroy();
        mPieManager.unRegisterRender(this);
    }

    public abstract void reset();

    public abstract boolean onPrepare();

    public abstract void onSizeChanged(int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);

    public abstract void onDraw(Canvas canvas);

    public abstract void onDestroy();

    public void callInvalidate() {
        mIPieView.onCallInvalidate();
    }

}
