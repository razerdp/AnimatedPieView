package com.razerdp.widget.animatedpieview;

import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
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
    private static final float DEFAULT_TEXT_MARGIN_LINE = 8;
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
    private float textMarginLine = DEFAULT_TEXT_MARGIN_LINE;
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

    /**
     * <p>是否只画线（甜甜圈）
     *
     * @return true：甜甜圈<br>false：饼图
     */
    public boolean isDrawStrokeOnly() {
        return isStroke;
    }

    /**
     * <p>是否只画线</p>
     *
     * @param stroke <br>true：甜甜圈<br>false：饼图
     */
    public AnimatedPieViewConfig setDrawStrokeOnly(boolean stroke) {
        isStroke = stroke;
        return setReApply(true);
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
    public AnimatedPieViewConfig setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        return setReApply(true);
    }

    /**
     * 起始偏移角度
     */
    public float getStartAngle() {
        return startAngle;
    }

    /**
     * 甜甜圈生长动画的插值器
     */
    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    /**
     * 设置甜甜圈生长动画插值器
     *
     * @param interpolator 插值器，不建议用{@link android.view.animation.OvershootInterpolator}，可能会
     *                     出现意料之外的效果
     */
    public AnimatedPieViewConfig setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        return setReApply(true);
    }

    /**
     * 生长动画时间
     */
    public long getDuration() {
        return duration;
    }

    /**
     * 设置生长动画的时间
     *
     * @param duration 动画时间
     */
    public AnimatedPieViewConfig setDuration(long duration) {
        this.duration = duration;
        return setReApply(true);
    }

    /**
     * 设置起始偏移角度
     * <strong><p>**任意角度**
     *
     * @param startAngle 起始角度（任意）
     */
    public AnimatedPieViewConfig setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        return setReApply(true);
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
     * 添加一个数据数组
     * <p>see @{@link AnimatedPieViewConfig#addData(IPieInfo)}
     *
     * @param infos 数据实体数组
     */
    public AnimatedPieViewConfig addDatas(@NonNull List<IPieInfo> infos) {
        if (infos == null) return this;
        for (IPieInfo info : infos) {
            addData(info);
        }
        return this;
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
        mDatas.add(PieInfoImpl.create(info).setStrokeWidth(strokeWidth).setDrawStrokeOnly(isStroke));
        mPieViewHelper.prepare(autoDesc);
        return this;
    }

    protected boolean isReApply() {
        return reApply;
    }

    protected AnimatedPieViewConfig setReApply(boolean reApply) {
        this.reApply = reApply;
        return this;
    }

    /**
     * 获取View绑定的数据（与原对象不一致）
     */
    public List<IPieInfo> getDatas() {
        List<IPieInfo> result = new ArrayList<>();
        for (PieInfoImpl data : mDatas) {
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

    public AnimatedPieViewConfig setTouchScaleSize(float touchScaleSize) {
        this.touchScaleSize = touchScaleSize;
        return this;
    }

    /**
     * 获取点击浮现动画时间
     */
    public long getTouchScaleUpDuration() {
        return touchScaleUpDuration;
    }

    /**
     * 设置点击浮现动画时间
     * <p>缩小归位动画时间{@link AnimatedPieViewConfig#setTouchScaleDownDuration(long)}
     *
     * @param touchScaleUpDuration 时间
     */
    public AnimatedPieViewConfig setTouchScaleUpDuration(long touchScaleUpDuration) {
        this.touchScaleUpDuration = touchScaleUpDuration;
        return this;
    }

    /**
     * 获取缩小归位动画时间
     */
    public long getTouchScaleDownDuration() {
        return touchScaleDownDuration;
    }

    /**
     * 设置缩小归位动画时间
     * <p>点击放大动画时间{@link AnimatedPieViewConfig#setTouchScaleUpDuration(long)} (long)}
     *
     * @param touchScaleDownDuration 时间
     */
    public AnimatedPieViewConfig setTouchScaleDownDuration(long touchScaleDownDuration) {
        this.touchScaleDownDuration = touchScaleDownDuration;
        return this;
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
    public AnimatedPieViewConfig setTouchShadowRadius(float touchShadowRadius) {
        this.touchShadowRadius = touchShadowRadius;
        return this;
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
    public AnimatedPieViewConfig setTouchExpandAngle(float touchExpandAngle) {
        this.touchExpandAngle = touchExpandAngle;
        return this;
    }

    /**
     * 是否播放点击动画（否的情况下会直接绘制而不产生动画过渡）
     */
    public boolean isTouchAnimation() {
        return touchAnimation;
    }

    /**
     * 设置是否播放点击动画（否的情况下会直接绘制而不产生动画过渡）
     *
     * @param touchAnimation <br>true：启动点击动画过渡<br>false：关闭点击动画过渡
     * @return
     */
    public AnimatedPieViewConfig setTouchAnimation(boolean touchAnimation) {
        this.touchAnimation = touchAnimation;
        return this;
    }

    /**
     * 获取甜甜圈半径比例
     * <p>该比例是针对View大小，将会影响到甜甜圈绘制半径（减去padding）
     * <p>当绘制文字的时候，默认固定为0.55f
     * <p>文字绘制开关：{@link AnimatedPieViewConfig#setDrawText(boolean)}
     */
    public float getPieRadiusScale() {
        return drawText ? 0.55f : pieRadiusScale;
    }

    /**
     * 设置甜甜圈半径比例
     * <p>该比例是针对View大小，将会影响到甜甜圈绘制半径（减去padding）
     * <p>当绘制文字的时候，默认固定为0.55f
     * <p>文字绘制开关：{@link AnimatedPieViewConfig#setDrawText(boolean)}
     *
     * @param pieRadiusScale 半径比例，不能超过1
     */
    public AnimatedPieViewConfig setPieRadiusScale(@FloatRange(from = 0.0f, to = 1.0f) float pieRadiusScale) {
        if (pieRadiusScale <= 0.0f) pieRadiusScale = 0.0f;
        if (pieRadiusScale > 1.0f) pieRadiusScale = 1.0f;
        this.pieRadiusScale = pieRadiusScale;
        return this;
    }

    /**
     * 是否绘制文字描述
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为true时，文字描述强制为显示百分比
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为false时，文字描述显示为数据的desc{@link IPieInfo#getDesc()}
     */
    public boolean isDrawText() {
        return drawText;
    }

    /**
     * 是否绘制文字描述
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为true时，文字描述强制为显示百分比
     * <p>当{@link AnimatedPieViewConfig#addData(IPieInfo, boolean)}第二个参数为false时，文字描述显示为数据的desc{@link IPieInfo#getDesc()}
     *
     * @param drawText 是否绘制文字
     */
    public AnimatedPieViewConfig setDrawText(boolean drawText) {
        this.drawText = drawText;
        return setReApply(true);
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
    public <T extends IPieInfo> AnimatedPieViewConfig setOnPieSelectListener(OnPieSelectListener<T> onPieSelectListener) {
        mOnPieSelectListener = onPieSelectListener;
        return this;
    }

    /**
     * 获取文字与描述线的距离
     */
    public float getTextMarginLine() {
        return textMarginLine;
    }

    /**
     * 设置文字与描述线的距离
     *
     * @param textMarginLine 距离
     */
    public AnimatedPieViewConfig setTextMarginLine(float textMarginLine) {
        this.textMarginLine = textMarginLine;
        return setReApply(true);
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

    /**
     * 将新config应用到当前config
     *
     * @param config 新config
     */
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
                    .setTextMarginLine(config.getTextMarginLine())
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
