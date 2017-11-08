package com.razerdp.widget.animatedpieview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import com.razerdp.widget.animatedpieview.exception.NoViewConfigException;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 * <p>
 * 好吃的圆饼？请问要什么口味呢
 */

public class AnimatedPieView extends View implements PieViewAnimation.AnimationHandler {
    private final String TAG = this.getClass().getSimpleName();

    private AnimatedPieViewConfig mConfig;
    private PieViewAnimation mPieViewAnimation;

    private volatile float angle;
    private PieInfoImpl mCurrentInfo;
    private RectF mDrawRectf;
    private List<PieInfoImpl> mDrawedPieInfo;

    private volatile boolean isInAnimated;


    public AnimatedPieView(Context context) {
        this(context, null);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimatedPieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        if (mConfig == null) mConfig = new AnimatedPieViewConfig();
        mDrawRectf = new RectF();
        mDrawedPieInfo = new ArrayList<>();
        applyConfigInternal(mConfig);
    }

    private void applyConfigInternal(AnimatedPieViewConfig config) {
        if (config == null) throw new NoViewConfigException("请使用config进行配置");
        if (mConfig != config) {
            mConfig.setConfig(config);
        }

        //anim
        if (mPieViewAnimation == null) mPieViewAnimation = new PieViewAnimation(config);
        mPieViewAnimation.setDuration(config.getDuration());
        mPieViewAnimation.setInterpolator(config.getInterpolator());
        mPieViewAnimation.bindAnimationHandler(this);
        mPieViewAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isInAnimated = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        if (config.isReApply()) config.setReApply(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mConfig.isReApply()) {
            applyConfigInternal(mConfig);
        }
        super.onDraw(canvas);
        if (!mConfig.isReady()) return;

        final float width = getWidth() - getPaddingLeft() - getPaddingRight();
        final float height = getHeight() - getPaddingTop() - getPaddingBottom();

        canvas.translate(width / 2, height / 2);
        //半径
        final float radius = (float) (Math.min(width, height) / 2 * 0.85);
        mDrawRectf.set(-radius, -radius, radius, radius);

        if (mCurrentInfo != null) {
            if (!ToolUtil.isListEmpty(mDrawedPieInfo)) {
                for (PieInfoImpl pieInfo : mDrawedPieInfo) {
                    canvas.drawArc(mDrawRectf, pieInfo.getStartAngle(), pieInfo.getSweepAngle(), false, pieInfo.getPaint());
                }
            }
            canvas.drawArc(mDrawRectf, mCurrentInfo.getStartAngle(), angle - mCurrentInfo.getStartAngle(), false, mCurrentInfo.getPaint());
        }

    }

    @Override
    public void onAnimationProcessing(float angle, @NonNull PieInfoImpl infoImpl) {
        if (mCurrentInfo != null) {
            if (angle > mCurrentInfo.getEndAngle()) {
                DebugLogUtil.logAngles("超出角度", mCurrentInfo);
                mDrawedPieInfo.add(mCurrentInfo);
            }
        }
        this.angle = angle;
        this.mCurrentInfo = infoImpl;
        invalidate();
    }

    public AnimatedPieViewConfig getConfig() {
        return mConfig;
    }

    public void applyConfig(AnimatedPieViewConfig config) {
        applyConfigInternal(config);
    }


    public void start() {
        if (isInAnimated) {
            return;
        }
        mDrawedPieInfo.clear();
        clearAnimation();
        isInAnimated = true;
        startAnimation(mPieViewAnimation);
    }
}
