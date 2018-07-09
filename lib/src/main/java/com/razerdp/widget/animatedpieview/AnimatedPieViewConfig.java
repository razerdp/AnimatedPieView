package com.razerdp.widget.animatedpieview;

import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2018/2/1.
 * <p>
 * <h3>CN:</h3>pieview配置类，pieview会根据你配置的参数进行绘制
 * <h3>EN:</h3>Config for pieview.PieView will draw as what you set.
 */
public class AnimatedPieViewConfig {
    private static final long serialVersionUID = -2285434281608092357L;
    private static final String TAG = "AnimatedPieViewConfig";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FOCUS_WITH_ALPHA, FOCUS_WITH_ALPHA_REV, FOCUS_WITHOUT_ALPHA})
    public @interface FocusAlpha {
    }

    public static final int FOCUS_WITH_ALPHA = 0x10;
    public static final int FOCUS_WITH_ALPHA_REV = 0x11;
    public static final int FOCUS_WITHOUT_ALPHA = 0x12;


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ABOVE, BELOW, ALIGN, ECTOPIC})
    public @interface TextGravity {
    }

    public static final int ABOVE = 0x20;
    public static final int BELOW = 0x21;
    public static final int ALIGN = 0x22;
    public static final int ECTOPIC = 0x23;


    //=============================================================default

    public static DecimalFormat sFormateRate = new DecimalFormat("0.##");
    private static final String DEFAULT_AUTO_DESC_FORMAT = "%1$s%%";
    private static final int DEFAULT_STROKE_WIDTH = 80;
    private static final float DEFAULT_START_DEGREE = -90.0f;
    private static final long DEFAULT_ANIMATION_DURATION = 3000;
    private static final long DEFAULT_TOUCH_FLOATUP_DURATION = 500;
    private static final long DEFAULT_TOUCH_FLOATDOWN_DURATION = 800;
    private static final float DEFAULT_SHADOW_BLUR_RADIUS = 18;
    private static final float DEFAULT_FLOAT_EXPAND_ANGLE = 5;
    private static final float DEFAULT_DESC_TEXT_SIZE = 14;
    private static final float DEFAULT_SPLIT_ANGLE = 0;
    private static final float DEFAULT_FLOAT_EXPAND_SIZE = 15;
    private static final int DEFAULT_FOCUS_ALPHA_TYPE = FOCUS_WITH_ALPHA_REV;
    private static int DEFAULT_FOCUS_ALPHA = 150;
    private static final int DEFAULT_TEXT_GRAVITY = ECTOPIC;
    private static final int DEFAULT_GUIDE_POINT_RADIUS = 4;
    private static final int DEFAULT_GUIDE_MARGIN_START = 10;
    private static final int DEFAULT_GUIDE_LINE_WIDTH = 2;
    private static final int DEFAULT_TEXT_MARGIN = 6;
    private static final Interpolator DEFAULT_ANIMATION_INTERPOLATOR = new LinearInterpolator();

    //=============================================================option

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startAngle = DEFAULT_START_DEGREE;
    private long duration = DEFAULT_ANIMATION_DURATION;
    private long floatUpDuration = DEFAULT_TOUCH_FLOATUP_DURATION;
    private long floatDownDuration = DEFAULT_TOUCH_FLOATDOWN_DURATION;
    private float floatShadowRadius = DEFAULT_SHADOW_BLUR_RADIUS;
    private float floatExpandAngle = DEFAULT_FLOAT_EXPAND_ANGLE;
    private float floatExpandSize = DEFAULT_FLOAT_EXPAND_SIZE;
    private String autoDescStringFormat = DEFAULT_AUTO_DESC_FORMAT;
    private boolean autoSize = true;
    private float pieRadius = 0;
    private float pieRadiusRatio = 0;
    private float textSize = DEFAULT_DESC_TEXT_SIZE;
    private boolean drawText = false;
    private float splitAngle = DEFAULT_SPLIT_ANGLE;
    private boolean animatePie = true;
    private boolean canTouch = true;
    private boolean animTouch = true;
    private OnPieSelectListener mSelectListener;
    @FocusAlpha
    private int focusAlphaType = DEFAULT_FOCUS_ALPHA_TYPE;
    private int focusAlpha = DEFAULT_FOCUS_ALPHA;
    @TextGravity
    private int textGravity = DEFAULT_TEXT_GRAVITY;
    private int guidePointRadius = DEFAULT_GUIDE_POINT_RADIUS;
    private int guideLineMarginStart = DEFAULT_GUIDE_MARGIN_START;
    private int guideLineWidth = DEFAULT_GUIDE_LINE_WIDTH;
    private boolean cubicGuide = false;
    private int textMargin = DEFAULT_TEXT_MARGIN;
    private Interpolator animationInterpolator = DEFAULT_ANIMATION_INTERPOLATOR;


    private boolean strokeMode = true;


    private List<Pair<IPieInfo, Boolean>> mDatas;

    public AnimatedPieViewConfig() {
        this(null);
    }

    public AnimatedPieViewConfig(AnimatedPieViewConfig config) {
        mDatas = new ArrayList<>();
        if (config != null) {
            copyFrom(config);
        }
    }

    public AnimatedPieViewConfig strokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public AnimatedPieViewConfig startAngle(float startAngle) {
        this.startAngle = startAngle;
        return this;
    }

    /**
     * <h3>CN:</h3>甜甜圈的动画时间，过短的话可能会造成部分甜甜圈无法绘制,建议高于500ms
     * <h3>EN:</h3>How long this piechart should draw. The duration cannot be negative.Recommended above 500ms
     *
     * @param duration Duration in milliseconds(Recommended above 500ms)
     */
    public AnimatedPieViewConfig duration(long duration) {
        this.duration = Math.max(500, duration);
        return this;
    }

    public AnimatedPieViewConfig floatUpDuration(long popupDuration) {
        this.floatUpDuration = popupDuration;
        return this;
    }

    public AnimatedPieViewConfig floatDownDuration(long floatDownDuration) {
        this.floatDownDuration = floatDownDuration;
        return this;
    }

    public AnimatedPieViewConfig floatShadowRadius(float floatShadowRadius) {
        this.floatShadowRadius = floatShadowRadius;
        return this;
    }

    public AnimatedPieViewConfig floatExpandAngle(float floatExpandAngle) {
        this.floatExpandAngle = floatExpandAngle;
        return this;
    }

    public AnimatedPieViewConfig autoDescStringFormat(String autoDescStringFormat) {
        this.autoDescStringFormat = autoDescStringFormat;
        return this;
    }

    public AnimatedPieViewConfig autoSize(boolean autoSize) {
        this.autoSize = autoSize;
        return this;
    }

    public AnimatedPieViewConfig pieRadius(float pieRadius) {
        this.pieRadius = pieRadius;
        return autoSize(pieRadius <= 0);
    }

    public AnimatedPieViewConfig pieRadiusRatio(@FloatRange(from = 0f, to = 1f) float pieRadiusRatio) {
        this.pieRadiusRatio = pieRadiusRatio;
        return autoSize(pieRadiusRatio <= 0);
    }


    public AnimatedPieViewConfig textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public AnimatedPieViewConfig drawText(boolean drawText) {
        this.drawText = drawText;
        return this;
    }

    public AnimatedPieViewConfig splitAngle(float splitAngle) {
        this.splitAngle = splitAngle;
        return this;
    }

    public AnimatedPieViewConfig animatePie(boolean animatePie) {
        this.animatePie = animatePie;
        return this;
    }

    public AnimatedPieViewConfig strokeMode(boolean strokeMode) {
        this.strokeMode = strokeMode;
        return this;
    }

    public AnimatedPieViewConfig canTouch(boolean canTouch) {
        this.canTouch = canTouch;
        return this;
    }

    public AnimatedPieViewConfig animOnTouch(boolean animTouch) {
        this.animTouch = animTouch;
        return this;
    }

    public <T extends IPieInfo> AnimatedPieViewConfig selectListener(OnPieSelectListener<T> selectListener) {
        mSelectListener = selectListener;
        return this;
    }

    public AnimatedPieViewConfig floatExpandSize(float floatExpandSize) {
        this.floatExpandSize = floatExpandSize;
        return this;
    }

    public AnimatedPieViewConfig focusAlphaType(@FocusAlpha int focusAlphaType) {
        this.focusAlphaType = focusAlphaType;
        return this;
    }

    public AnimatedPieViewConfig focusAlpha(int focusAlpha) {
        this.focusAlpha = focusAlpha;
        return this;
    }

    public AnimatedPieViewConfig textGravity(@TextGravity int textGravity) {
        this.textGravity = textGravity;
        return this;
    }

    public AnimatedPieViewConfig guidePointRadius(int guidePointRadius) {
        this.guidePointRadius = guidePointRadius;
        return this;
    }

    public AnimatedPieViewConfig guideLineMarginStart(int guideLineMarginStart) {
        this.guideLineMarginStart = guideLineMarginStart;
        return this;
    }

    public AnimatedPieViewConfig cubicGuide(boolean cubicGuide) {
        this.cubicGuide = cubicGuide;
        if (cubicGuide) {
            return textGravity(ALIGN);
        }
        return this;
    }

    public AnimatedPieViewConfig guideLineWidth(int guideLineWidth) {
        this.guideLineWidth = guideLineWidth;
        return this;
    }

    public AnimatedPieViewConfig textMargin(int textMargin) {
        this.textMargin = textMargin;
        return this;
    }

    public AnimatedPieViewConfig interpolator(Interpolator interpolator) {
        this.animationInterpolator = interpolator;
        return this;
    }

    public AnimatedPieViewConfig copyFrom(AnimatedPieViewConfig config) {
        if (config == null) return this;
        this.mDatas.clear();
        this.mDatas.addAll(config.mDatas);
        return strokeWidth(config.strokeWidth)
                .startAngle(config.startAngle)
                .duration(config.duration)
                .floatUpDuration(config.floatUpDuration)
                .floatDownDuration(config.floatDownDuration)
                .floatShadowRadius(config.floatShadowRadius)
                .floatExpandAngle(config.floatExpandAngle)
                .autoDescStringFormat(config.autoDescStringFormat)
                .autoSize(config.autoSize)
                .pieRadius(config.pieRadius)
                .pieRadiusRatio(config.pieRadiusRatio)
                .textSize(config.textSize)
                .drawText(config.drawText)
                .splitAngle(config.splitAngle)
                .animatePie(config.animatePie)
                .strokeMode(config.strokeMode)
                .canTouch(config.canTouch)
                .animOnTouch(config.animTouch)
                .selectListener(config.mSelectListener)
                .floatExpandSize(config.floatExpandSize)
                .focusAlphaType(config.focusAlphaType)
                .focusAlpha(config.focusAlpha)
                .textGravity(config.textGravity)
                .guidePointRadius(config.guidePointRadius)
                .guideLineMarginStart(config.guideLineMarginStart)
                .cubicGuide(config.cubicGuide)
                .guideLineWidth(config.guideLineWidth)
                .textMargin(config.textMargin)
                .interpolator(config.animationInterpolator);
    }

    //=============================================================data
    public AnimatedPieViewConfig addData(@NonNull IPieInfo info) {
        return addData(info, false);
    }

    public AnimatedPieViewConfig addData(@NonNull IPieInfo info, boolean autoDesc) {
        if (info == null) {
            Log.e(TAG, "addData: pieinfo is null,abort add data");
            return this;
        }
        mDatas.add(Pair.create(info, autoDesc));
        return this;
    }

    public List<Pair<IPieInfo, Boolean>> getDatas() {
        return mDatas;
    }

    public List<IPieInfo> getRawDatas() {
        List<IPieInfo> result = new ArrayList<>();
        if (!Util.isListEmpty(mDatas)) {
            for (Pair<IPieInfo, Boolean> data : mDatas) {
                result.add(data.first);
            }
        }
        return result;

    }

    //=============================================================getter
    public int getStrokeWidth() {
        return strokeWidth;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public long getDuration() {
        return duration;
    }

    public long getFloatUpDuration() {
        return floatUpDuration;
    }

    public long getFloatDownDuration() {
        return floatDownDuration;
    }

    public float getFloatShadowRadius() {
        return floatShadowRadius;
    }

    public float getFloatExpandAngle() {
        return floatExpandAngle;
    }

    public String getAutoDescStringFormat() {
        return autoDescStringFormat;
    }

    public boolean isAutoSize() {
        return autoSize;
    }

    public float getPieRadius() {
        return pieRadius;
    }

    public float getPieRadiusRatio() {
        return pieRadiusRatio;
    }

    public float getTextSize() {
        return textSize;
    }

    public boolean isDrawText() {
        return drawText;
    }

    public float getSplitAngle() {
        return splitAngle;
    }

    public boolean isAnimatePie() {
        return animatePie;
    }

    public boolean isStrokeMode() {
        return strokeMode;
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public boolean isAnimTouch() {
        return animTouch;
    }

    public OnPieSelectListener getSelectListener() {
        return mSelectListener;
    }

    public float getFloatExpandSize() {
        return floatExpandSize;
    }

    @FocusAlpha
    public int getFocusAlphaType() {
        return focusAlphaType;
    }

    public int getFocusAlpha() {
        return focusAlpha;
    }

    @TextGravity
    public int getTextGravity() {
        return textGravity;
    }

    public int getGuidePointRadius() {
        return guidePointRadius;
    }

    public int getGuideLineMarginStart() {
        return guideLineMarginStart;
    }

    public boolean isCubicGuide() {
        return cubicGuide;
    }

    public int getGuideLineWidth() {
        return guideLineWidth;
    }

    public int getTextMargin() {
        return textMargin;
    }

    public Interpolator getAnimationInterpolator() {
        return animationInterpolator;
    }

    //=============================================================Deprecated methods
    //from version 1.1.5,most of methods' name has been changed

    /**
     * @deprecated Use {@link #strokeWidth(int)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setStrokeWidth(int strokeWidth) {
        return strokeWidth(strokeWidth);
    }

    /**
     * @deprecated Use {@link #startAngle(float)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setStartAngle(float startAngle) {
        return startAngle(startAngle);
    }


    /**
     * @deprecated Use {@link #duration(long)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setDuration(long duration) {
        return duration(duration);
    }


    /**
     * @deprecated Use {@link #getFloatUpDuration()} instead..
     */
    @Deprecated
    public long getTouchScaleUpDuration() {
        return getFloatUpDuration();
    }

    /**
     * @deprecated Use {@link #floatUpDuration(long)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setTouchScaleUpDuration(long touchScaleUpDuration) {
        return floatUpDuration(touchScaleUpDuration);
    }

    /**
     * @deprecated Use {@link #getFloatDownDuration()} instead..
     */
    @Deprecated
    public long getTouchScaleDownDuration() {
        return getFloatDownDuration();
    }

    /**
     * @deprecated Use {@link #floatDownDuration(long)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setTouchScaleDownDuration(long touchScaleDownDuration) {
        return floatDownDuration(touchScaleDownDuration);
    }

    /**
     * @deprecated Use {@link #getFloatShadowRadius()} instead..
     */
    @Deprecated
    public float getTouchShadowRadius() {
        return getFloatShadowRadius();
    }

    /**
     * @deprecated Use {@link #floatShadowRadius(float)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setTouchShadowRadius(float touchShadowRadius) {
        return floatShadowRadius(touchShadowRadius);
    }


    /**
     * @deprecated Use {@link #getFloatExpandAngle()} instead..
     */
    @Deprecated
    public float getTouchExpandAngle() {
        return getFloatExpandAngle();
    }

    /**
     * @deprecated Use {@link #floatExpandAngle(float)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setTouchExpandAngle(float touchExpandAngle) {
        return floatExpandAngle(touchExpandAngle);
    }

    /**
     * @deprecated Use {@link #isStrokeMode()} instead.
     */
    @Deprecated
    public boolean isDrawStrokeOnly() {
        return isStrokeMode();
    }

    /**
     * @deprecated Use {@link #strokeMode(boolean)} instead.
     */
    @Deprecated
    public AnimatedPieViewConfig setDrawStrokeOnly(boolean stroke) {
        return strokeMode(stroke);
    }


    /**
     * @deprecated Use {@link #selectListener(OnPieSelectListener)} instead.
     */
    public <T extends IPieInfo> AnimatedPieViewConfig setOnPieSelectListener(OnPieSelectListener<T> listener) {
        return selectListener(listener);
    }

    /**
     * @deprecated Use {@link #drawText(boolean)} instead.
     */
    public AnimatedPieViewConfig setDrawText(boolean drawText) {
        return drawText(drawText);
    }

    /**
     * @deprecated Use {@link #textSize(float)} instead.
     */
    public AnimatedPieViewConfig setTextSize(float textSize) {
        return textSize(textSize);
    }

    /**
     * @deprecated Use {@link #textMargin(int)} instead.
     */
    public AnimatedPieViewConfig setTextMarginLine(int textMargin) {
        return textMargin(textMargin);
    }

    /**
     * @deprecated Use {@link #pieRadiusRatio(float)} instead.
     */
    public AnimatedPieViewConfig setPieRadiusScale(@FloatRange(from = 0f, to = 1f) float pieRadiusRatio) {
        return pieRadiusRatio(pieRadiusRatio);
    }

    /**
     * @deprecated Use {@link #guidePointRadius(int)} instead.
     */
    public AnimatedPieViewConfig setTextPointRadius(int textPointRadius) {
        return guidePointRadius(textPointRadius);
    }

    /**
     * @deprecated Use {@link #guideLineWidth(int)} instead.
     */
    public AnimatedPieViewConfig setTextLineStrokeWidth(int strokeWidth) {
        return guideLineWidth(strokeWidth);
    }

    /**
     * @deprecated we dont't need this length because it will calculate automaticed
     */
    public AnimatedPieViewConfig setTextLineTransitionLength(int length) {
        return this;
    }

    /**
     * @deprecated Use {@link #guideLineMarginStart(int)} instead.
     */
    public AnimatedPieViewConfig setTextLineStartMargin(int margin) {
        return guideLineMarginStart(margin);
    }

    /**
     * @deprecated Use {@link #textGravity(int)} instead.
     */
    public AnimatedPieViewConfig setDirectText(boolean directed) {
        return textGravity(directed ? ABOVE : ECTOPIC);
    }

    /**
     * @deprecated Use {@link #canTouch(boolean)} instead.
     */
    public AnimatedPieViewConfig setCanTouch(boolean canTouch) {
        return canTouch(canTouch);
    }

    /**
     * @deprecated Use {@link #splitAngle(float)} instead.
     */
    public AnimatedPieViewConfig setSplitAngle(float splitAngle) {
        return splitAngle(splitAngle);
    }

    /**
     * @deprecated Use {@link #focusAlpha(int)} instead.
     */
    public AnimatedPieViewConfig setFocusAlphaType(@FocusAlpha int focusAlphaType) {
        return focusAlphaType(focusAlphaType);
    }

    /**
     * @deprecated we dont't need to set paintCap.
     */
    public AnimatedPieViewConfig setStrokePaintCap(Paint.Cap paintCap) {
        return this;
    }

    /**
     * @deprecated Use {@link #animOnTouch(boolean)} instead.
     */
    public AnimatedPieViewConfig setTouchAnimation(boolean animTouch) {
        return animOnTouch(animTouch);
    }

    /**
     * @deprecated Use {@link #floatExpandSize(float)} instead.
     */
    public AnimatedPieViewConfig setTouchScaleSize(float touchScaleSize) {
        return floatExpandSize(touchScaleSize);
    }
}
