package com.razerdp.widget.animatedpieview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.razerdp.widget.animatedpieview.data.IPieInfo;

/**
 * Created by 大灯泡 on 2018/9/26.
 * <p>
 * 图例
 */
public class DefaultPieLegendsView extends BasePieLegendsView {

    private View viewTag;
    private TextView tvDesc;
    private static final float SCALE_RATIO = 0.4f;
    private static final float SCALE_MAX = 0.8f;

    public static DefaultPieLegendsView newInstance(Context context) {
        return new DefaultPieLegendsView(context);
    }

    private DefaultPieLegendsView(Context context) {
        this(context, null);
    }

    private DefaultPieLegendsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private DefaultPieLegendsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.widget_default_pie_legends);
        viewTag = findViewById(R.id.view_tag);
        tvDesc = findViewById(R.id.tv_desc);

        reset();
    }

    private void reset() {
        viewTag.setScaleX(0f);
        viewTag.setScaleY(0f);
        viewTag.setAlpha(0);
        tvDesc.setAlpha(0);
        tvDesc.setScaleX(0f);
        tvDesc.setScaleY(0f);

        viewTag.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewTag.setPivotX(viewTag.getWidth() / 2);
                viewTag.setPivotY(viewTag.getHeight() / 2);
                viewTag.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        tvDesc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvDesc.setPivotX(tvDesc.getWidth() / 2);
                tvDesc.setPivotY(tvDesc.getHeight() / 2);
                tvDesc.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    @Override
    public void onPieDrawStart(@NonNull IPieInfo pie) {
        viewTag.setBackgroundColor(pie.getColor());
        tvDesc.setText(pie.getDesc());

        reset();
    }

    @Override
    public void onPieDrawing(@NonNull IPieInfo pie, float progress) {
        viewTag.setAlpha(progress);
        viewTag.setScaleX(SCALE_MAX * progress);
        viewTag.setScaleY(SCALE_MAX * progress);

        tvDesc.setAlpha(progress);
        tvDesc.setScaleX(SCALE_MAX * progress);
        tvDesc.setScaleY(SCALE_MAX * progress);
    }

    @Override
    public void onPieDrawFinish(@NonNull IPieInfo pie) {
        viewTag.setAlpha(1f);
        viewTag.setScaleX(SCALE_MAX);
        viewTag.setScaleY(SCALE_MAX);

        tvDesc.setAlpha(1f);
        tvDesc.setScaleX(SCALE_MAX);
        tvDesc.setScaleY(SCALE_MAX);
    }

    public void onPieFloatUp(@NonNull IPieInfo pie, float timeSet) {
        float scale = SCALE_RATIO * timeSet;

        viewTag.setScaleX(SCALE_MAX + scale);
        viewTag.setScaleY(SCALE_MAX + scale);

        tvDesc.setScaleX(SCALE_MAX + scale);
        tvDesc.setScaleY(SCALE_MAX + scale);
    }

    public void onPieFloatDown(@NonNull IPieInfo pie, float timeSet) {
        float scale = SCALE_RATIO * timeSet;

        viewTag.setScaleX(SCALE_MAX + scale);
        viewTag.setScaleY(SCALE_MAX + scale);

        tvDesc.setScaleX(SCALE_MAX + scale);
        tvDesc.setScaleY(SCALE_MAX + scale);
    }
}
