package com.razerdp.widget.animatedpieview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

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
    private ValueAnimator mTouchScaleUpAnimator;
    private ValueAnimator mTouchScaleDownAnimator;

    private TouchHelper mTouchHelper;

    private volatile float angle;
    private volatile float mScaleUpTime;
    private volatile float mScaleDownTime;
    private PieInfoImpl mCurrentInfo;
    private PieInfoImpl mCurrentTouchInfo;
    private PieInfoImpl mLastTouchInfo;
    private RectF mDrawRectf;
    private RectF mTouchRectf;
    private List<PieInfoImpl> mDrawedCachePieInfo;

    private volatile boolean isInAnimating;

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
        mDrawedCachePieInfo = new ArrayList<>();
        mTouchHelper = new TouchHelper(mConfig);
        applyConfigInternal(mConfig);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    private void applyConfigInternal(AnimatedPieViewConfig config) {
        if (config == null) throw new NoViewConfigException("请使用config进行配置");
        mConfig.setConfig(config);
        buildAnima(mConfig);
        if (mConfig.isReApply()) config.setReApply(false);
    }

    private void buildAnima(AnimatedPieViewConfig config) {
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
                isInAnimating = false;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //scale up
        if (mTouchScaleUpAnimator == null)
            mTouchScaleUpAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mTouchScaleUpAnimator.setDuration(config.getTouchScaleUpDuration());
        mTouchScaleUpAnimator.setInterpolator(new DecelerateInterpolator());
        mTouchScaleUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleUpTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        //scaleDown
        if (mTouchScaleDownAnimator == null)
            mTouchScaleDownAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        mTouchScaleDownAnimator.setDuration(config.getTouchScaleDownDuration());
        mTouchScaleDownAnimator.setInterpolator(new DecelerateInterpolator());
        mTouchScaleDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScaleDownTime = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
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
            drawCachedInfos(canvas, null);
            canvas.drawArc(mDrawRectf, mCurrentInfo.getStartAngle(), angle - mCurrentInfo.getStartAngle(), !mConfig.isDrawStrokeOnly(), mCurrentInfo.getPaint());
        }
    }

    private void onTouchModeHandle(Canvas canvas) {
        if (mCurrentTouchInfo != null) {
            drawCachedInfos(canvas, mCurrentTouchInfo);
            //先完成上一个点击的动画
            if (mLastTouchInfo != null && !mLastTouchInfo.equalsWith(mCurrentInfo)) {
                mTouchRectf.set(mDrawRectf);
                mTouchEventPaint.set(mLastTouchInfo.getPaint());
                BlurMaskFilter scaleDownMaskFilter = new BlurMaskFilter(Math.max(1, mConfig.getTouchShadowRadius() * mScaleDownTime), BlurMaskFilter.Blur.SOLID);
                mTouchEventPaint.setMaskFilter(mScaleDownTime > 0 ? scaleDownMaskFilter : null);
                mTouchEventPaint.setStrokeWidth(mLastTouchInfo.getPaint().getStrokeWidth() + (10 * mScaleDownTime));
                canvas.drawArc(mTouchRectf,
                        mLastTouchInfo.getStartAngle() - (mConfig.getTouchExpandAngle() * mScaleDownTime),
                        mLastTouchInfo.getSweepAngle() + (mConfig.getTouchExpandAngle() * 2 * mScaleDownTime),
                        !mConfig.isDrawStrokeOnly(),
                        mTouchEventPaint);
            }
            //再完成当前点击的动画
            mTouchEventPaint.set(mCurrentTouchInfo.getPaint());
            BlurMaskFilter maskFilter = new BlurMaskFilter(Math.max(1, mConfig.getTouchShadowRadius() * mScaleUpTime), BlurMaskFilter.Blur.SOLID);
            mTouchEventPaint.setMaskFilter(maskFilter);
            final float scaleSizeInTouch = mConfig.getTouchScaleSize();
            if (!mConfig.isDrawStrokeOnly()) {
                mTouchRectf.set(mDrawRectf.left - scaleSizeInTouch,
                        mDrawRectf.top - scaleSizeInTouch,
                        mDrawRectf.right + scaleSizeInTouch,
                        mDrawRectf.bottom + scaleSizeInTouch);
            } else {
                mTouchRectf.set(mDrawRectf);
                mTouchEventPaint.setStrokeWidth(mCurrentTouchInfo.getPaint().getStrokeWidth() + (10 * mScaleUpTime));
            }
            canvas.drawArc(mTouchRectf,
                    mCurrentTouchInfo.getStartAngle() - (mConfig.getTouchExpandAngle() * mScaleUpTime),
                    mCurrentTouchInfo.getSweepAngle() + (mConfig.getTouchExpandAngle() * 2 * mScaleUpTime),
                    !mConfig.isDrawStrokeOnly(),
                    mTouchEventPaint);
        }
    }

    /**
     * 绘制缓存的圆环
     *
     * @param canvas
     * @param excluded 排除excluded
     */
    private void drawCachedInfos(Canvas canvas, PieInfoImpl excluded) {
        if (!ToolUtil.isListEmpty(mDrawedCachePieInfo)) {
            for (PieInfoImpl pieInfo : mDrawedCachePieInfo) {
                if (excluded != null && excluded.equalsWith(pieInfo)) {
                    continue;
                }
                canvas.drawArc(mDrawRectf, pieInfo.getStartAngle(), pieInfo.getSweepAngle(), !mConfig.isDrawStrokeOnly(), pieInfo.getPaint());
            }
        }
    }

    @Override
    public void onAnimationProcessing(float angle, @NonNull PieInfoImpl infoImpl) {
        if (mCurrentInfo != null) {
            //角度切换时就把画过的添加到缓存，因为角度切换只有很少的几次，所以这里允许循环，并不会造成大量的循环
            if (angle >= mCurrentInfo.getEndAngle()) {
                boolean hasAdded = false;
                for (PieInfoImpl pieInfo : mDrawedCachePieInfo) {
                    if (pieInfo.equalsWith(mCurrentInfo)) {
                        hasAdded = true;
                        break;
                    }
                }
                if (!hasAdded) {
                    DebugLogUtil.logAngles("超出角度", mCurrentInfo);
                    mDrawedCachePieInfo.add(mCurrentInfo);
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
        if (isInAnimating) {
            return;
        }
        setMode(Mode.DRAW);
        mDrawedCachePieInfo.clear();
        clearAnimation();
        isInAnimating = true;
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
                    Toast.makeText(getContext(), clickedInfo.getPieInfo().getDesc(), Toast.LENGTH_SHORT).show();
                    if (!isInAnimating) {
                        mLastTouchInfo = mCurrentTouchInfo;
                        mCurrentTouchInfo = clickedInfo;
                        setMode(Mode.TOUCH);
                        if (mConfig.isTouchAnimation()) {
                            mTouchScaleDownAnimator.start();
                            mTouchScaleUpAnimator.start();
                        } else {
                            invalidate();
                        }
                    }
                    return true;
                }
                break;

        }
        return super.onTouchEvent(event);
    }


    //-----------------------------------------Tools-----------------------------------------

    private void setMode(Mode mode) {
        if (mode == Mode.DRAW) {
            mCurrentTouchInfo = null;
            mLastTouchInfo = null;
        }
        this.mode = mode;
    }
}
