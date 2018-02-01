package com.razerdp.widget.animatedpieview;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2018/2/1.
 * <p>
 * <h3>CN:</h3>pieview配置类，pieview会根据你配置的参数进行绘制
 * <p>
 * <h3>EN:</h3>Config for pieview.Pieview will draw as what you set.
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


    //=============================================================default

    public static DecimalFormat sFormateRate = new DecimalFormat("0.##");
    private static final String DEFAULT_AUTO_DESC_FORMAT = "%1$s%%";
    private static final int DEFAULT_STROKE_WIDTH = 80;
    private static final float DEFAULT_START_DEGREE = -90.0f;
    private static final long DEFAULT_ANIMATION_DURATION = 2500;
    private static final long DEFAULT_TOUCH_FLOATUP_DURATION = 500;
    private static final long DEFAULT_TOUCH_FLOATDOWN_DURATION = 800;
    private static final float DEFAULT_SHADOW_BLUR_RADIUS = 18;
    private static final float DEFAULT_FLOAT_EXPAND_ANGLE = 8;


    //=============================================================option

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startDegree = DEFAULT_START_DEGREE;
    private long duration = DEFAULT_ANIMATION_DURATION;
    private long floatUpDuration = DEFAULT_TOUCH_FLOATUP_DURATION;
    private long floatDownDuration = DEFAULT_TOUCH_FLOATDOWN_DURATION;
    private float floatShadowRadius = DEFAULT_SHADOW_BLUR_RADIUS;
    private float floatExpandDegree = DEFAULT_FLOAT_EXPAND_ANGLE;
    private String autoDescStringFormat = DEFAULT_AUTO_DESC_FORMAT;


    private boolean strokeMode = true;


    private List<Pair<IPieInfo, Boolean>> mDatas;


    public AnimatedPieViewConfig strokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public AnimatedPieViewConfig startDegree(float startDegree) {
        this.startDegree = startDegree;
        return this;
    }

    public AnimatedPieViewConfig duration(long duration) {
        this.duration = duration;
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

    public AnimatedPieViewConfig floatExpandDegree(float floatExpandDegree) {
        this.floatExpandDegree = floatExpandDegree;
        return this;
    }

    public AnimatedPieViewConfig autoDescStringFormat(String autoDescStringFormat) {
        this.autoDescStringFormat = autoDescStringFormat;
        return this;
    }

    public AnimatedPieViewConfig strokeMode(boolean strokeMode) {
        this.strokeMode = strokeMode;
        return this;
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
        if (!ToolUtil.isListEmpty(mDatas)) {
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

    public float getStartDegree() {
        return startDegree;
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

    public float getFloatExpandDegree() {
        return floatExpandDegree;
    }

    public String getAutoDescStringFormat() {
        return autoDescStringFormat;
    }

    public boolean isStrokeMode() {
        return strokeMode;
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
     * @deprecated Use {@link #getStartDegree()} instead..
     */
    @Deprecated
    public float getStartAngle() {
        return getStartDegree();
    }

    /**
     * @deprecated Use {@link #startDegree(float)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setStartAngle(float startAngle) {
        return startDegree(startAngle);
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
     * @deprecated Use {@link #getFloatExpandDegree()} instead..
     */
    @Deprecated
    public float getTouchExpandAngle() {
        return getFloatExpandDegree();
    }

    /**
     * @deprecated Use {@link #floatExpandDegree(float)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setTouchExpandAngle(float touchExpandAngle) {
        return floatExpandDegree(touchExpandAngle);
    }

    /**
     * @deprecated Use {@link #isStrokeMode()} instead..
     */
    @Deprecated
    public boolean isDrawStrokeOnly() {
        return isStrokeMode();
    }

    /**
     * @deprecated Use {@link #strokeMode(boolean)} instead..
     */
    @Deprecated
    public AnimatedPieViewConfig setDrawStrokeOnly(boolean stroke) {
        return strokeMode(stroke);
    }
}
