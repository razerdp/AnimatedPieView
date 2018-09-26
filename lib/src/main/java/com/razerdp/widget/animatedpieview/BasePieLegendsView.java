package com.razerdp.widget.animatedpieview;

import android.content.Context;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.razerdp.widget.animatedpieview.data.IPieInfo;

/**
 * Created by 大灯泡 on 2018/9/26.
 * <p>
 * 图例
 */
public abstract class BasePieLegendsView<T extends IPieInfo> extends FrameLayout {

    public BasePieLegendsView(Context context) {
        super(context);
    }

    public BasePieLegendsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePieLegendsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContentView(View customContentView) {
        final int child = getChildCount();
        if (child <= 0) {
            addView(customContentView);
        } else {
            removeAllViewsInLayout();
            addView(customContentView);
        }
    }

    public void setContentView(@LayoutRes int layoutId) {
        setContentView(View.inflate(getContext(), layoutId, null));
    }

    public abstract void onPieDrawStart(@NonNull T pie);

    public abstract void onPieDrawing(@NonNull T pie, @FloatRange(from = 0, to = 1) float progress);

    public abstract void onPieDrawFinish(@NonNull T pie);

    public void onPieFloatUp(@NonNull T pie, @FloatRange(from = 0, to = 1) float timeSet) {

    }

    public void onPieFloatDown(@NonNull T pie, @FloatRange(from = 0, to = 1) float timeSet) {

    }

}
