package com.razerdp.widget.animatedpieview;

import android.graphics.Paint;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class AnimatedPieViewConfig implements Serializable {
    private static final long serialVersionUID = -2285434281608092357L;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FOCUS_WITH_ALPHA, FOCUS_WITH_ALPHA_REV, FOCUS_WITHOUT_ALPHA})
    public @interface FocusAlpha {
    }

    public static final int FOCUS_WITH_ALPHA = 0x10;
    public static final int FOCUS_WITH_ALPHA_REV = 0x11;
    public static final int FOCUS_WITHOUT_ALPHA = 0x12;


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
    private static final float DEFAULT_TEXT_MARGIN_LINE = 8;
    private static final int DEFAULT_TEXT_SIZE = 10;
    private static final int DEFAULT_TEXT_POINT_RADIUS = 4;
    private static final int DEFAULT_TEXT_LINE_STROKE_WIDTH = 4;
    private static final int DEFAULT_TEXT_LINE_TRANSITION_LENGTH = 32;
    private static final int DEFAULT_TEXT_LINE_START_MARGIN = 8;
    private static final boolean DEFAULT_DIRECT_TEXT = false;
    private static final float DEFAULT_SPLIT_ANGLE = 0.0f;
    private static final int DEFAULT_FOCUS_ALPHA = FOCUS_WITHOUT_ALPHA;
    private static final float DEFAULT_FOCUS_ALPHA_CUT = 150;


    private static final Interpolator DEFAULT_ANIMATION_INTERPOLATOR = new LinearInterpolator();

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startAngle = DEFAULT_START_ANGLE;
    private long duration = DEFAULT_ANIMATION_DURATION;
    private long touchScaleUpDuration = DEFAULT_TOUCH_SCALE_UP_DURATION;
    private long touchScaleDownDuration = DEFAULT_TOUCH_SCALE_DOWN_DURATION;
    private float touchShadowRadius = DEFAULT_SHADOW_BLUR_RADIUS;
    private float touchExpandAngle = DEFAULT_TOUCH_EXPAND_ANGLE;
    private float pieRadiusScale = DEFAULT_PIE_RADIUS_SCALE;
    private float textMarginLine = DEFAULT_TEXT_MARGIN_LINE;
    private int textSize = DEFAULT_TEXT_SIZE;
    private int textPointRadius = DEFAULT_TEXT_POINT_RADIUS;
    private int textLineStrokeWidth = DEFAULT_TEXT_LINE_STROKE_WIDTH;
    private int textLineTransitionLength = DEFAULT_TEXT_LINE_TRANSITION_LENGTH;
    private int textLineStartMargin = DEFAULT_TEXT_LINE_START_MARGIN;
    private float splitAngle = DEFAULT_SPLIT_ANGLE;
    @FocusAlpha
    int focusAlphaType = DEFAULT_FOCUS_ALPHA;
    float focusAlphaCut = DEFAULT_FOCUS_ALPHA_CUT;

    private volatile boolean reApply;
    private List<InternalPieInfo> mDatas;
    private AnimatedPieViewHelper mPieViewHelper;
    private Interpolator mInterpolator = DEFAULT_ANIMATION_INTERPOLATOR;
    private boolean isStroke = true;
    private float touchScaleSize = DEFAULT_SCALE_SIZE_WHEN_TOUCH;
    private boolean touchAnimation = true;
    private OnPieSelectListener mOnPieSelectListener;
    private boolean drawText = true;
    private boolean directText = DEFAULT_DIRECT_TEXT;
    private boolean canTouch = true;
    private Paint.Cap strokePaintCap = Paint.Cap.BUTT;

    public AnimatedPieViewConfig() {
        mPieViewHelper = new AnimatedPieViewHelper();
        mDatas = new ArrayList<>();
    }

    /**
     * <p>是否只画线（甜甜圈）
     *
     * @return true：甜甜圈<br>false：饼图
     */
    public boolean isStrokeOnly() {
        return isStroke;
    }

    /**
     * <p>是否只画线</p>
     *
     * @param stroke <br>true：甜甜圈<br>false：饼图
     */
    public AnimatedPieViewConfig strokeOnly(boolean stroke) {
        isStroke = stroke;
        return reApply(true);
    }

    /**
     * 获取线宽（甜甜圈的线宽）
     */
    public int getStrokeWidth() {
        return strokeWidth;
    }


    /**
     * <p>设置线宽（只针对甜甜圈，饼图无效）
     * <p>在设置线宽后，请注意，您的甜甜圈外圈半径实际上是您设定的半径加上线宽的一半
     *
     * @param strokeWidth 线宽
     */
    public AnimatedPieViewConfig strokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return reApply(true);
    }

    /**
     * 起始偏移角度
     */
    public float getStartAngle() {
        return startAngle;
    }

    /**
     * 设置起始偏移角度
     * <strong><p>**任意角度**
     *
     * @param startAngle 起始角度（任意）
     */
    public AnimatedPieViewConfig startAngle(float startAngle) {
        this.startAngle = startAngle;
        return reApply(true);
    }

    /**
     * 甜甜圈生长动画的插值器
     */
    Interpolator getAnimationInterpolator() {
        return mInterpolator;
    }

    /**
     * 设置甜甜圈生长动画插值器（暂时关闭，#issue 2，解决了的话再开放）
     *
     * @param interpolator 插值器，不建议用{@link android.view.animation.OvershootInterpolator}，可能会
     *                     出现意料之外的效果
     */
    private AnimatedPieViewConfig animationInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return reApply(true);
    }

    /**
     * 生长动画时间
     */
    public long getAnimationDrawDuration() {
        return duration;
    }

    /**
     * 设置生长动画的时间
     *
     * @param duration 动画时间
     */
    public AnimatedPieViewConfig animationDrawDuration(long duration) {
        this.duration = duration;
        return reApply(true);
    }


    /**
     * 添加一个数据数组
     * <p>see @{@link AnimatedPieViewConfig#addData(IPieInfo)}
     *
     * @param infos 数据实体数组
     */
    public AnimatedPieViewConfig addDatas(@NonNull List<? extends IPieInfo> infos) {
        for (IPieInfo info : infos) {
            addData(info);
        }
        return this;
    }

    /**
     * 添加一个数据实体用于绘制/控制甜甜圈
     * <p>该数据必须实现接口：{@link IPieInfo}
     *
     * @param info 数据实体
     */
    public AnimatedPieViewConfig addData(@NonNull IPieInfo info) {
        return addData(info, false);
    }

    /**
     * 添加一个数据实体用于绘制/控制甜甜圈
     * <p>该数据必须实现接口：{@link IPieInfo}
     *
     * @param info     数据实体
     * @param autoDesc 是否自动描述，当自动描述传值<strong>true</strong>，则不再使用实体返回的
     *                 desc，而是默认显示所占<strong>百分比</strong>
     */
    public AnimatedPieViewConfig addData(@NonNull IPieInfo info, boolean autoDesc) {
        assert info != null : "不能添加空数据";
        if (info == null) return this;
        InternalPieInfo internalPieInfo = InternalPieInfo.create(info)
                .setStrokeWidth(strokeWidth)
                .setDrawStrokeOnly(isStroke)
                .setAutoDesc(autoDesc)
                .setPaintStrokeCap(strokePaintCap);
        mDatas.add(internalPieInfo);
        mPieViewHelper.prepare();
        return this;
    }

    protected boolean isReApply() {
        return reApply;
    }

    protected AnimatedPieViewConfig reApply(boolean reApply) {
        this.reApply = reApply;
        return this;
    }

    /**
     * 获取View绑定的数据（与原对象不一致）
     */
    public List<IPieInfo> getDatas() {
        List<IPieInfo> result = new ArrayList<>();
        for (InternalPieInfo data : mDatas) {
            result.add(data.getPieInfo());
        }
        return result;
    }

    /**
     * 获取点击浮现时甜甜圈/饼图放大值
     */
    public float getTouchScaleSize() {
        return touchScaleSize;
    }

    public AnimatedPieViewConfig touchScaleSize(float touchScaleSize) {
        this.touchScaleSize = touchScaleSize;
        return reApply(true);
    }

    /**
     * 获取点击浮现动画时间
     */
    public long getTouchScaleUpDuration() {
        return touchScaleUpDuration;
    }

    /**
     * 设置点击浮现动画时间
     * <p>缩小归位动画时间{@link AnimatedPieViewConfig#touchScaleDownDuration(long)}
     *
     * @param touchScaleUpDuration 时间
     */
    public AnimatedPieViewConfig touchScaleUpDuration(long touchScaleUpDuration) {
        this.touchScaleUpDuration = touchScaleUpDuration;
        return reApply(true);
    }

    /**
     * 获取缩小归位动画时间
     */
    public long getTouchScaleDownDuration() {
        return touchScaleDownDuration;
    }

    /**
     * 设置缩小归位动画时间
     * <p>点击放大动画时间{@link AnimatedPieViewConfig#touchScaleUpDuration(long)} (long)}
     *
     * @param touchScaleDownDuration 时间
     */
    public AnimatedPieViewConfig touchScaleDownDuration(long touchScaleDownDuration) {
        this.touchScaleDownDuration = touchScaleDownDuration;
        return reApply(true);
    }

    /**
     * 获取点击浮现后的阴影大小
     */
    public float getTouchShadowRadius() {
        return touchShadowRadius;
    }

    /**
     * 设置点击浮现后的阴影大小
     *
     * @param touchShadowRadius 阴影大小
     */
    public AnimatedPieViewConfig touchShadowRadius(float touchShadowRadius) {
        this.touchShadowRadius = touchShadowRadius;
        return reApply(true);
    }

    /**
     * 获取点击浮现后的扩展角度
     */
    public float getTouchExpandAngle() {
        return touchExpandAngle;
    }

    /**
     * 设置点击浮现后的扩展角度
     *
     * @param touchExpandAngle 角度（不建议太大）
     */
    public AnimatedPieViewConfig touchExpandAngle(float touchExpandAngle) {
        this.touchExpandAngle = touchExpandAngle;
        return reApply(true);
    }

    /**
     * 是否播放点击动画（否的情况下会直接绘制而不产生动画过渡）
     */
    public boolean isTouchWithAnimation() {
        return touchAnimation;
    }

    /**
     * 设置是否播放点击动画（否的情况下会直接绘制而不产生动画过渡）
     *
     * @param touchAnimation <br>true：启动点击动画过渡<br>false：关闭点击动画过渡
     * @return
     */
    public AnimatedPieViewConfig touchWithAnimation(boolean touchAnimation) {
        this.touchAnimation = touchAnimation;
        return reApply(true);
    }

    /**
     * 获取甜甜圈半径比例
     * <p>该比例是针对View大小，将会影响到甜甜圈绘制半径（减去padding）
     * <p>当绘制文字的时候，默认固定为0.55f
     * <p>文字绘制开关：{@link AnimatedPieViewConfig#drawDescText(boolean)}
     */
    public float getPieRadiusScale() {
        return drawText ? 0.55f : pieRadiusScale;
    }

    /**
     * 设置甜甜圈半径比例
     * <p>该比例是针对View大小，将会影响到甜甜圈绘制半径（减去padding）
     * <p>当绘制文字的时候，默认固定为0.55f
     * <p>文字绘制开关：{@link AnimatedPieViewConfig#drawDescText(boolean)}
     *
     * @param pieRadiusScale 半径比例，不能超过1
     */
    public AnimatedPieViewConfig pieRadiusScale(@FloatRange(from = 0.0f, to = 1.0f) float pieRadiusScale) {
        if (pieRadiusScale <= 0.0f) pieRadiusScale = 0.0f;
        if (pieRadiusScale > 1.0f) pieRadiusScale = 1.0f;
        this.pieRadiusScale = pieRadiusScale;
        return reApply(true);
    }

    /**
     * 是否绘制文字描述
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为true时，文字描述强制为显示百分比
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为false时，文字描述显示为数据的desc{@link IPieInfo#getDesc()}
     */
    public boolean isDrawDescText() {
        return drawText;
    }

    /**
     * 是否绘制文字描述
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为true时，文字描述强制为显示百分比
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为false时，文字描述显示为数据的desc{@link IPieInfo#getDesc()}
     *
     * @param drawText 是否绘制文字
     */
    public AnimatedPieViewConfig drawDescText(boolean drawText) {
        this.drawText = drawText;
        return reApply(true);
    }

    /**
     * 获取甜甜圈点击事件
     *
     * @param <T> 传入接口{@link IPieInfo}的数据实体类，缺省值默认为Object
     */
    public <T extends IPieInfo> OnPieSelectListener<T> getOnPieSelectListener() {
        return mOnPieSelectListener;
    }

    /**
     * 设置甜甜圈点击监听事件
     *
     * @param onPieSelectListener 监听事件
     * @param <T>                 传入接口{@link IPieInfo}的数据实体类，缺省值默认为Object
     */
    public <T extends IPieInfo> AnimatedPieViewConfig pieSelectListener(OnPieSelectListener<T> onPieSelectListener) {
        mOnPieSelectListener = onPieSelectListener;
        return reApply(true);
    }

    /**
     * 获取文字与指示线的距离
     */
    public float getTextMarginLine() {
        return textMarginLine;
    }

    /**
     * 设置文字与指示线的距离
     *
     * @param textMarginLine 距离
     */
    public AnimatedPieViewConfig textMarginLine(float textMarginLine) {
        this.textMarginLine = textMarginLine;
        return reApply(true);
    }

    /**
     * 获取文字大小(px)
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     * 设置文字大小(px)
     *
     * @param textSize 文字大小(px)
     */
    public AnimatedPieViewConfig textSize(int textSize) {
        this.textSize = textSize;
        return reApply(true);
    }

    /**
     * 获取描述文字的点
     */
    public int getDescGuidePointRadius() {
        return textPointRadius;
    }

    /**
     * 设置描述文字的小点
     *
     * @param textPointRadius 点的半径
     */
    public AnimatedPieViewConfig descGuidePointRadius(int textPointRadius) {
        this.textPointRadius = textPointRadius;
        return reApply(true);
    }

    /**
     * 获取描述文字的指示线宽度
     */
    public int getTextGuideLineStrokeWidth() {
        return textLineStrokeWidth;
    }

    /**
     * 设置描述文字的指示线宽度
     *
     * @param textLineStrokeWidth 宽度（px）
     */
    public AnimatedPieViewConfig textGuideLineStrokeWidth(int textLineStrokeWidth) {
        this.textLineStrokeWidth = textLineStrokeWidth;
        return reApply(true);
    }

    /**
     * 获取描述文字的指示线转折线长度
     */
    public int getTextLineTransitionLength() {
        return textLineTransitionLength;
    }

    /**
     * 设置描述文字的指示线转折线长度
     *
     * @param textLineTransitionLength 长度（px）
     */
    public AnimatedPieViewConfig setTextLineTransitionLength(int textLineTransitionLength) {
        this.textLineTransitionLength = textLineTransitionLength;
        return reApply(true);
    }

    /**
     * 获取描述文字的指示线开始距离外圆半径的大小
     */
    public int getTextLineStartMargin() {
        return textLineStartMargin;
    }

    /**
     * 设置描述文字的指示线开始距离外圆半径的大小
     *
     * @param textLineStartMargin 距离(px)
     */
    public AnimatedPieViewConfig setTextLineStartMargin(int textLineStartMargin) {
        this.textLineStartMargin = textLineStartMargin;
        return reApply(true);
    }

    /**
     * 描述文字是否同一个方向
     */
    public boolean isDirectText() {
        return directText;
    }

    /**
     * 设置描述文字是否统一方向
     *
     * @param directText <p>true：文字将会在指示线上绘制<p>false：文字在1、2象限部分绘制在线的上方，在3、4象限绘制在线的下方
     */
    public AnimatedPieViewConfig directText(boolean directText) {
        this.directText = directText;
        return reApply(true);
    }

    /**
     * 是否允许甜甜圈可触碰放大
     */
    public boolean isCanTouch() {
        return canTouch;
    }

    /**
     * 设置甜甜圈是否允许触碰放大
     */
    public AnimatedPieViewConfig canTouch(boolean canTouch) {
        this.canTouch = canTouch;
        return reApply(true);
    }

    /**
     * 甜甜圈间隙的角度
     */
    public float getSplitAngle() {
        return splitAngle;
    }

    /**
     * 设置甜甜圈间隙角度
     *
     * @param splitAngle
     */
    public AnimatedPieViewConfig splitAngle(float splitAngle) {
        this.splitAngle = Math.abs(splitAngle);
        return reApply(true);
    }

    /**
     * 焦点甜甜圈alpha模式
     */
    @FocusAlpha
    public int getFocusAlphaType() {
        return focusAlphaType;
    }

    /**
     * 设置焦点甜甜圈alpha模式
     *
     * @param focusAlphaType One of {@link #FOCUS_WITH_ALPHA}, {@link #FOCUS_WITH_ALPHA_REV}, or {@link #FOCUS_WITHOUT_ALPHA}.
     */
    public AnimatedPieViewConfig focusAlphaType(@FocusAlpha int focusAlphaType) {
        return focusAlphaType(focusAlphaType, focusAlphaCut);
    }

    /**
     * 设置焦点甜甜圈alpha模式
     *
     * @param focusAlphaType One of {@link #FOCUS_WITH_ALPHA}, {@link #FOCUS_WITH_ALPHA_REV}, or {@link #FOCUS_WITHOUT_ALPHA}
     * @param alphaCutDown   cut down alpha when focus touch
     */
    public AnimatedPieViewConfig focusAlphaType(@FocusAlpha int focusAlphaType, @FloatRange(from = 0, to = 255) float alphaCutDown) {
        this.focusAlphaType = focusAlphaType;
        this.focusAlphaCut = Math.min(255, Math.abs(alphaCutDown));
        return reApply(true);
    }

    /**
     * 获取焦点甜甜圈的alpha削减范围
     */
    public float getFocusAlphaCut() {
        return focusAlphaCut;
    }

    /**
     * 笔刷样式
     */
    public Paint.Cap getStrokePaintCap() {
        return strokePaintCap;
    }

    /**
     * 设置笔刷样式
     *
     * @param strokePaintCap 设置笔刷样式
     */
    public AnimatedPieViewConfig strokePaintCap(@NonNull Paint.Cap strokePaintCap) {
        if (strokePaintCap == null) strokePaintCap = Paint.Cap.BUTT;
        this.strokePaintCap = strokePaintCap;
        return reApply(true);
    }

    protected List<InternalPieInfo> getImplDatas() {
        return new ArrayList<>(mDatas);
    }

    protected boolean isReady() {
        return !ToolUtil.isListEmpty(mDatas);
    }


    protected AnimatedPieViewHelper getHelper() {
        return mPieViewHelper;
    }

    /**
     * 将新config应用到当前config
     *
     * @param config 新config
     */
    public AnimatedPieViewConfig setConfig(AnimatedPieViewConfig config) {
        if (config != null) {
            strokeWidth(config.getStrokeWidth())
                    .animationDrawDuration(config.getAnimationDrawDuration())
                    .animationInterpolator(config.getAnimationInterpolator())
                    .startAngle(config.getStartAngle())
                    .strokeOnly(config.isStrokeOnly())
                    .touchScaleSize(config.getTouchScaleSize())
                    .touchScaleUpDuration(config.getTouchScaleUpDuration())
                    .touchScaleDownDuration(config.getTouchScaleDownDuration())
                    .touchShadowRadius(config.getTouchShadowRadius())
                    .touchExpandAngle(config.getTouchExpandAngle())
                    .touchWithAnimation(config.isTouchWithAnimation())
                    .pieSelectListener(config.getOnPieSelectListener())
                    .pieRadiusScale(config.getPieRadiusScale())
                    .drawDescText(config.isDrawDescText())
                    .textMarginLine(config.getTextMarginLine())
                    .textSize(config.getTextSize())
                    .descGuidePointRadius(config.getDescGuidePointRadius())
                    .textGuideLineStrokeWidth(config.getTextGuideLineStrokeWidth())
                    .setTextLineTransitionLength(config.getTextLineTransitionLength())
                    .setTextLineStartMargin(config.getTextLineStartMargin())
                    .directText(config.isDirectText())
                    .canTouch(config.isCanTouch())
                    .splitAngle(config.getSplitAngle())
                    .focusAlphaType(config.getFocusAlphaType(), config.getFocusAlphaCut())
                    .strokePaintCap(config.getStrokePaintCap());
            List<InternalPieInfo> infos = config.getImplDatas();
            mDatas.clear();
            for (InternalPieInfo info : infos) {
                addData(info.getPieInfo(), info.isAutoDesc());
            }
        }
        return this;
    }

    protected final class AnimatedPieViewHelper {
        private double sumValue;
        private InternalPieInfo lastFoundInfo;

        private void prepare() {
            if (ToolUtil.isListEmpty(mDatas)) return;
            sumValue = 0;
            //算总和
            for (InternalPieInfo dataImpl : mDatas) {
                IPieInfo info = dataImpl.getPieInfo();
                sumValue += Math.abs(info.getValue());
            }

            //算每部分的角度
            float start = startAngle;
            for (InternalPieInfo data : mDatas) {
                data.setStartAngle(start);
                float angle = (float) (360f * (Math.abs(data.getPieInfo().getValue()) / sumValue));
                float endAngle = start + angle;
                data.setEndAngle(endAngle);
                start = endAngle;
                //自动填充描述auto
                if (data.isAutoDesc()) {
                    data.setAutoDescStr(sFormateRate.format((data.getPieInfo().getValue() / sumValue) * 100) + "%");
                }
            }
        }

        public InternalPieInfo findPieinfoWithAngle(float angle) {
            if (ToolUtil.isListEmpty(mDatas)) return null;
            if (lastFoundInfo != null && lastFoundInfo.isInAngleRange(angle)) {
                return lastFoundInfo;
            }
            for (InternalPieInfo info : mDatas) {
                if (info.isInAngleRange(angle)) {
                    lastFoundInfo = info;
                    return info;
                }
            }
            return null;
        }

        public double getSumValue() {
            return sumValue;
        }

    }
}
