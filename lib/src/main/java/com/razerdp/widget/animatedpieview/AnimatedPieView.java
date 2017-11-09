package com.razerdp.widget.animatedpieview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import com.razerdp.widget.animatedpieview.exception.NoViewConfigException;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 * <p>
 * 好吃的甜甜圈？请问要什么口味呢
 */

public class AnimatedPieView extends View implements PieViewAnimation.AnimationHandler {
    protected final String TAG = this.getClass().getSimpleName();

    private enum Mode {
        DRAW, TOUCH
    }

    private Mode mode = Mode.DRAW;

    private AnimatedPieViewConfig mConfig;
    private PieViewAnimation mPieViewAnimation;
    private TouchHelper mTouchHelper;

    private volatile float angle;
    private PieInfoImpl mCurrentInfo;
    private PieInfoImpl mCurrentTouchInfo;
    private RectF mDrawRectf;
    private RectF mTouchRectf;
    private List<PieInfoImpl> mDrawedPieInfo;

    private volatile boolean isInAnimated;

    private Paint mTouchEventPaint;


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
        if (mTouchEventPaint == null) {
            mTouchEventPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            BlurMaskFilter maskFilter = new BlurMaskFilter(18, BlurMaskFilter.Blur.SOLID);
            mTouchEventPaint.setMaskFilter(maskFilter);
        }
        mDrawRectf = new RectF();
        mTouchRectf = new RectF();
        mDrawedPieInfo = new ArrayList<>();
        mTouchHelper = new TouchHelper(mConfig);
        applyConfigInternal(mConfig);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void applyConfigInternal(AnimatedPieViewConfig config) {
        if (config == null) throw new NoViewConfigException("请使用config进行配置");
        mConfig.setConfig(config);
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

        if (mConfig.isReApply()) config.setReApply(false);
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
        mTouchHelper.setPieParam(width / 2, height / 2, radius);
        mDrawRectf.set(-radius, -radius, radius, radius);

        switch (mode) {
            case DRAW:
                onDrawModeHandle(canvas);
                break;
            case TOUCH:
                onTouchModeHandle(canvas);
                break;
        }
    }

    private void onDrawModeHandle(Canvas canvas) {
        if (mCurrentInfo != null) {
            if (!ToolUtil.isListEmpty(mDrawedPieInfo)) {
                for (PieInfoImpl pieInfo : mDrawedPieInfo) {
                    canvas.drawArc(mDrawRectf, pieInfo.getStartAngle(), pieInfo.getSweepAngle(), !mConfig.isDrawStrokeOnly(), pieInfo.getPaint());
                }
            }
            canvas.drawArc(mDrawRectf, mCurrentInfo.getStartAngle(), angle - mCurrentInfo.getStartAngle(), !mConfig.isDrawStrokeOnly(), mCurrentInfo.getPaint());
        }
    }

    private void onTouchModeHandle(Canvas canvas) {
        if (mCurrentTouchInfo != null) {
            if (!ToolUtil.isListEmpty(mDrawedPieInfo)) {
                for (PieInfoImpl pieInfo : mDrawedPieInfo) {
                    if (!mCurrentTouchInfo.equalsWith(pieInfo)) {
                        canvas.drawArc(mDrawRectf, pieInfo.getStartAngle(), pieInfo.getSweepAngle(), !mConfig.isDrawStrokeOnly(), pieInfo.getPaint());
                    }
                }
            }
            mTouchEventPaint.set(mCurrentTouchInfo.getPaint());
            BlurMaskFilter maskFilter = new BlurMaskFilter(18, BlurMaskFilter.Blur.SOLID);
            mTouchEventPaint.setMaskFilter(maskFilter);
            final float scaleSizeInTouch = mConfig.getScaleSizeInTouch();
            mTouchRectf.set(mDrawRectf.left - scaleSizeInTouch,
                            mDrawRectf.top - scaleSizeInTouch,
                            mDrawRectf.right + scaleSizeInTouch,
                            mDrawRectf.bottom + scaleSizeInTouch);
            canvas.drawArc(mTouchRectf, mCurrentTouchInfo.getStartAngle() - 5, mCurrentTouchInfo.getSweepAngle() + 5, !mConfig.isDrawStrokeOnly(), mTouchEventPaint);
        }
    }

    @Override
    public void onAnimationProcessing(float angle, @NonNull PieInfoImpl infoImpl) {
        if (mCurrentInfo != null) {
            if (angle >= mCurrentInfo.getEndAngle()) {
                boolean hasAdded = false;
                for (PieInfoImpl pieInfo : mDrawedPieInfo) {
                    if (pieInfo.equalsWith(mCurrentInfo)) {
                        hasAdded = true;
                        break;
                    }
                }
                if (!hasAdded) {
                    DebugLogUtil.logAngles("超出角度", mCurrentInfo);
                    mDrawedPieInfo.add(mCurrentInfo);
                }
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
        setMode(Mode.DRAW);
        mDrawedPieInfo.clear();
        clearAnimation();
        isInAnimated = true;
        startAnimation(mPieViewAnimation);
    }

    //-----------------------------------------touch-----------------------------------------

    float x = 0;
    float y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                PieInfoImpl clickedInfo = mTouchHelper.pointToInfo(x, y);
                if (clickedInfo != null) {
                    if (!isInAnimated) {
                        this.mCurrentTouchInfo = clickedInfo;
                        setMode(Mode.TOUCH);
                        invalidate();
                    }
                    return true;
                }
                break;

        }
        return super.onTouchEvent(event);
    }


    //-----------------------------------------Tools-----------------------------------------

    private void setMode(Mode mode) {
        this.mode = mode;
    }
}
