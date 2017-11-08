package com.razerdp.widget.animatedpieview;

import android.support.annotation.NonNull;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.ToolUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

public class AnimatedPieViewConfig implements Serializable {
    private static final int DEFAULT_STROKE_WIDTH = 60;
    private static final float DEFAULT_START_ANGLE = -90.0f;
    private static final long DEFAULT_ANIMATION_DURATION = 2500;
    private static final Interpolator DEFAULT_ANIMATION_INTERPOLATOR = new DecelerateInterpolator(1.2f);

    private int strokeWidth = DEFAULT_STROKE_WIDTH;
    private float startAngle = DEFAULT_START_ANGLE;
    private long duration = DEFAULT_ANIMATION_DURATION;

    private volatile boolean reApply;
    private List<PieInfoImpl> mDatas;
    private AnimatedPieViewHelper mPieViewHelper;
    private Interpolator mInterpolator = DEFAULT_ANIMATION_INTERPOLATOR;

    public AnimatedPieViewConfig() {
        mPieViewHelper = new AnimatedPieViewHelper();
        mDatas = new ArrayList<>();
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

    public AnimatedPieViewConfig setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        return setReApply(true);
    }

    public AnimatedPieViewConfig addData(@NonNull IPieInfo info) {
        assert info != null : "不能添加空数据";
        mDatas.add(PieInfoImpl.create(info).setStrokeWidth(strokeWidth));
        mPieViewHelper.prepare();
        return this;
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
                    .setStartAngle(config.getStartAngle());
            for (IPieInfo info : config.getDatas()) {
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
