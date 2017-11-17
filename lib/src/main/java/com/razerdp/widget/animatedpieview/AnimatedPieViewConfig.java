package com.razerdp.widget.animatedpieview;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.razerdp.widget.animatedpieview.utils.DegreeUtil;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class AnimatedPieViewConfig implements Serializable {
    private static final DecimalFormat sFormateRate = new DecimalFormat("#.##");
    private static final int DEFAULT_STROKE_WIDTH = 80;
    private static final float DEFAULT_START_ANGLE = -90.0f;
    private static final float DEFAULT_SCALE_SIZE_WHEN_TOUCH = 15;
    private static final long DEFAULT_ANIMATION_DURATION = 2500;
    private static final long DEFAULT_TOUCH_SCALE_UP_DURATION = 500;
    private static final long DEFAULT_TOUCH_SCALE_DOWN_DURATION = 800;
    private static final float DEFAULT_SHADOW_BLUR_RADIUS = 18;
    private static final float DEFAULT_TOUCH_EXPAND_ANGLE = 8;
    private static final float DEFAULT_PIE_RADIUS_SCALE = 0.85f;
    private static final float DEFAULT_TEXT_MARGIN_BOTTOM = 8;
    private static final int DEFAULT_TEXT_SIZE = 10;


    private static final Interpolator DEFAULT_ANIMATION_INTERPOLATOR = new DecelerateInterpolator(1.2f);

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startAngle = DEFAULT_START_ANGLE;
    private long duration = DEFAULT_ANIMATION_DURATION;
    private long touchScaleUpDuration = DEFAULT_TOUCH_SCALE_UP_DURATION;
    private long touchScaleDownDuration = DEFAULT_TOUCH_SCALE_DOWN_DURATION;
    private float touchShadowRadius = DEFAULT_SHADOW_BLUR_RADIUS;
    private float touchExpandAngle = DEFAULT_TOUCH_EXPAND_ANGLE;
    private float pieRadiusScale = DEFAULT_PIE_RADIUS_SCALE;
    private float textMarginBottom = DEFAULT_TEXT_MARGIN_BOTTOM;
    private int textSize = DEFAULT_TEXT_SIZE;

    private volatile boolean reApply;
    private List<PieInfoImpl> mDatas;
    private AnimatedPieViewHelper mPieViewHelper;
    private Interpolator mInterpolator = DEFAULT_ANIMATION_INTERPOLATOR;
    private boolean isStroke = true;
    private float touchScaleSize = DEFAULT_SCALE_SIZE_WHEN_TOUCH;
    private boolean touchAnimation = true;
    private OnPieSelectListener mOnPieSelectListener;
    private boolean drawText = true;

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
        return addData(info, false);
    }

    public AnimatedPieViewConfig addData(@NonNull IPieInfo info, boolean autoDesc) {
        assert info != null : "不能添加空数据";
        mDatas.add(PieInfoImpl.create(info).setStrokeWidth(strokeWidth).setDrawStrokeOnly(isStroke));
        mPieViewHelper.prepare(autoDesc);
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

    public float getPieRadiusScale() {
        return drawText ? 0.55f : pieRadiusScale;
    }

    public AnimatedPieViewConfig setPieRadiusScale(@FloatRange(from = 0.0f, to = 1.0f) float pieRadiusScale) {
        if (pieRadiusScale <= 0.0f) pieRadiusScale = 0.0f;
        if (pieRadiusScale > 1.0f) pieRadiusScale = 1.0f;
        this.pieRadiusScale = pieRadiusScale;
        return this;
    }

    public boolean isDrawText() {
        return drawText;
    }

    public AnimatedPieViewConfig setDrawText(boolean drawText) {
        this.drawText = drawText;
        return setReApply(true);
    }

    public <T extends IPieInfo> OnPieSelectListener<T> getOnPieSelectListener() {
        return mOnPieSelectListener;
    }

    public <T extends IPieInfo> AnimatedPieViewConfig setOnPieSelectListener(OnPieSelectListener<T> onPieSelectListener) {
        mOnPieSelectListener = onPieSelectListener;
        return this;
    }

    public float getTextMarginBottom() {
        return textMarginBottom;
    }

    public AnimatedPieViewConfig setTextMarginBottom(float textMarginBottom) {
        this.textMarginBottom = textMarginBottom;
        return setReApply(true);
    }

    public int getTextSize() {
        return textSize;
    }

    public AnimatedPieViewConfig setTextSize(int textSize) {
        this.textSize = textSize;
        return setReApply(true);
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
                    .setOnPieSelectListener(config.getOnPieSelectListener())
                    .setPieRadiusScale(config.getPieRadiusScale())
                    .setDrawText(config.isDrawText())
                    .setTextMarginBottom(config.getTextMarginBottom())
                    .setTextSize(config.getTextSize());
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

        private void prepare(boolean autoDesc) {
            if (ToolUtil.isListEmpty(mDatas)) return;
            sumValue = 0;
            //算总和
            for (PieInfoImpl dataImpl : mDatas) {
                IPieInfo info = dataImpl.getPieInfo();
                sumValue += info.getValue();
            }
            //自动填充auto
            if (autoDesc) {
                for (PieInfoImpl data : mDatas) {
                    if (data.getPieInfo() instanceof SimplePieInfo) {
                        SimplePieInfo info = (SimplePieInfo) data.getPieInfo();
                        info.setDesc(sFormateRate.format((info.getValue() / sumValue) * 100) + "%");
                    }
                }
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
