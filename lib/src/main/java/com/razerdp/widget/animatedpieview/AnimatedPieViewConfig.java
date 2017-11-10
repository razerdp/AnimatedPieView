package com.razerdp.widget.animatedpieview;

import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.DegreeUtil;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class AnimatedPieViewConfig implements Serializable {
    private static final int DEFAULT_STROKE_WIDTH = 80;
    private static final float DEFAULT_START_ANGLE = -90.0f;
    private static final float DEFAULT_SCALE_SIZE_WHEN_TOUCH = 15;
    private static final long DEFAULT_ANIMATION_DURATION = 2500;
    private static final long DEFAULT_TOUCH_SCALE_UP_DURATION = 500;
    private static final long DEFAULT_TOUCH_SCALE_DOWN_DURATION = 800;
    private static final float DEFAULT_SHADOW_BLUR_RADIUS = 18;
    private static final float DEFAULT_TOUCH_EXPAND_ANGLE = 8;


    private static final Interpolator DEFAULT_ANIMATION_INTERPOLATOR = new DecelerateInterpolator(1.2f);

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startAngle = DEFAULT_START_ANGLE;
    private long duration = DEFAULT_ANIMATION_DURATION;
    private long touchScaleUpDuration = DEFAULT_TOUCH_SCALE_UP_DURATION;
    private long touchScaleDownDuration = DEFAULT_TOUCH_SCALE_DOWN_DURATION;
    private float touchShadowRadius = DEFAULT_SHADOW_BLUR_RADIUS;
    private float touchExpandAngle = DEFAULT_TOUCH_EXPAND_ANGLE;

    private volatile boolean reApply;
    private List<PieInfoImpl> mDatas;
    private AnimatedPieViewHelper mPieViewHelper;
    private Interpolator mInterpolator = DEFAULT_ANIMATION_INTERPOLATOR;
    private boolean isStroke = true;
    private float touchScaleSize = DEFAULT_SCALE_SIZE_WHEN_TOUCH;
    private boolean touchAnimation = true;
    private OnPieSelectListener mOnPieSelectListener;

    public AnimatedPieViewConfig() {
        mPieViewHelper = new AnimatedPieViewHelper();
        mDatas = new ArrayList<>();
    }

    public boolean isDrawStrokeOnly() {
        return isStroke;
    }

    public AnimatedPieViewConfig setDrawStrokeOnly(boolean stroke) {
        isStroke = stroke;
        return setReApply(true);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }


    public AnimatedPieViewConfig setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return setReApply(true);
    }

    public float getStartAngle() {
        return startAngle;
    }

    float getStartAngleAfterLimited() {
        return DegreeUtil.limitDegreeInTo360(startAngle);
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    public AnimatedPieViewConfig setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return setReApply(true);
    }

    public long getDuration() {
        return duration;
    }

    public AnimatedPieViewConfig setDuration(long duration) {
        this.duration = duration;
        return setReApply(true);
    }

    /**
     * startAngle有点儿问题，暂时不允许设置
     *
     * @param startAngle
     * @return
     */
    public AnimatedPieViewConfig setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        return setReApply(true);
    }

    public AnimatedPieViewConfig addData(@NonNull IPieInfo info) {
        assert info != null : "不能添加空数据";
        mDatas.add(PieInfoImpl.create(info).setStrokeWidth(strokeWidth).setDrawStrokeOnly(isStroke));
        mPieViewHelper.prepare();
        return this;
    }

    void updateDatas() {
        if (!ToolUtil.isListEmpty(mDatas)) {
            for (PieInfoImpl data : mDatas) {
                data.setStrokeWidth(strokeWidth);
                data.setDrawStrokeOnly(isStroke);
            }
        }
    }

    protected boolean isReApply() {
        return reApply;
    }

    protected AnimatedPieViewConfig setReApply(boolean reApply) {
        this.reApply = reApply;
        return this;
    }

    public List<IPieInfo> getDatas() {
        List<IPieInfo> result = new ArrayList<>();
        for (PieInfoImpl data : mDatas) {
            result.add(data.getPieInfo());
        }
        return result;
    }

    public float getTouchScaleSize() {
        return touchScaleSize;
    }

    public AnimatedPieViewConfig setTouchScaleSize(float touchScaleSize) {
        this.touchScaleSize = touchScaleSize;
        return this;
    }

    public long getTouchScaleUpDuration() {
        return touchScaleUpDuration;
    }

    public AnimatedPieViewConfig setTouchScaleUpDuration(long touchScaleUpDuration) {
        this.touchScaleUpDuration = touchScaleUpDuration;
        return this;
    }

    public long getTouchScaleDownDuration() {
        return touchScaleDownDuration;
    }

    public AnimatedPieViewConfig setTouchScaleDownDuration(long touchScaleDownDuration) {
        this.touchScaleDownDuration = touchScaleDownDuration;
        return this;
    }

    public float getTouchShadowRadius() {
        return touchShadowRadius;
    }

    public AnimatedPieViewConfig setTouchShadowRadius(float touchShadowRadius) {
        this.touchShadowRadius = touchShadowRadius;
        return this;
    }

    public float getTouchExpandAngle() {
        return touchExpandAngle;
    }

    public AnimatedPieViewConfig setTouchExpandAngle(float touchExpandAngle) {
        this.touchExpandAngle = touchExpandAngle;
        return this;
    }

    public boolean isTouchAnimation() {
        return touchAnimation;
    }

    public AnimatedPieViewConfig setTouchAnimation(boolean touchAnimation) {
        this.touchAnimation = touchAnimation;
        return this;
    }

    public <T extends IPieInfo> OnPieSelectListener<T> getOnPieSelectListener() {
        return mOnPieSelectListener;
    }

    public <T extends IPieInfo> AnimatedPieViewConfig setOnPieSelectListener(OnPieSelectListener<T> onPieSelectListener) {
        mOnPieSelectListener = onPieSelectListener;
        return this;
    }

    protected List<PieInfoImpl> getImplDatas() {
        return new ArrayList<>(mDatas);
    }

    protected boolean isReady() {
        return !ToolUtil.isListEmpty(mDatas);
    }


    protected AnimatedPieViewHelper getHelper() {
        return mPieViewHelper;
    }

    public AnimatedPieViewConfig setConfig(AnimatedPieViewConfig config) {
        if (config != null) {
            setStrokeWidth(config.getStrokeWidth())
                    .setDuration(config.getDuration())
                    .setInterpolator(config.getInterpolator())
                    .setStartAngle(config.getStartAngle())
                    .setDrawStrokeOnly(config.isDrawStrokeOnly())
                    .setTouchScaleSize(config.getTouchScaleSize())
                    .setTouchScaleUpDuration(config.getTouchScaleUpDuration())
                    .setTouchScaleDownDuration(config.getTouchScaleDownDuration())
                    .setTouchShadowRadius(config.getTouchShadowRadius())
                    .setTouchExpandAngle(config.getTouchExpandAngle())
                    .setTouchAnimation(config.isTouchAnimation())
                    .setOnPieSelectListener(config.getOnPieSelectListener());
            List<IPieInfo> infos = config.getDatas();
            mDatas.clear();
            for (IPieInfo info : infos) {
                addData(info);
            }
        }
        return this;
    }

    protected final class AnimatedPieViewHelper {
        private double sumValue;

        private void prepare() {
            if (ToolUtil.isListEmpty(mDatas)) return;
            sumValue = 0;
            //算总和
            for (PieInfoImpl dataImpl : mDatas) {
                IPieInfo info = dataImpl.getPieInfo();
                sumValue += info.getValue();
            }
            //算每部分的角度
            float start = startAngle;
            for (PieInfoImpl data : mDatas) {
                data.setStartAngle(start);
                float angle = (float) (360.0 * (data.getPieInfo().getValue() / sumValue));
                angle = Math.max(1.0f, angle);
                float endAngle = start + angle;
                data.setEndAngle(endAngle);
                start = endAngle;
            }
        }

        public PieInfoImpl findPieinfoWithAngle(float angle) {
            if (ToolUtil.isListEmpty(mDatas)) return null;
            for (PieInfoImpl data : mDatas) {
                if (data.isInAngleRange(angle)) return data;
            }
            return null;
        }

        public double getSumValue() {
            return sumValue;
        }

    }
}
