package com.razerdp.widget.animatedpieview;

import android.graphics.BlurMaskFilter;
import android.graphics.Paint;

import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.utils.DegreeUtil;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

final class PieInfoImpl {
    private final IPieInfo mPieInfo;
    private boolean drawStrokeOnly = true;
    private float startAngle;
    private float endAngle;
    private int strokeWidth;
    private Paint mPaint;

    private PieInfoImpl(IPieInfo info) {
        this.mPieInfo = info;
        initPaint(info);
    }

    private void initPaint(IPieInfo info) {
        if (mPaint == null) mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(drawStrokeOnly ? Paint.Style.STROKE : Paint.Style.FILL);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(info.getColor());
        if (mPaint.getMaskFilter() == null) {
            BlurMaskFilter maskFilter = new BlurMaskFilter(18, BlurMaskFilter.Blur.SOLID);
            mPaint.setMaskFilter(maskFilter);
        }
    }

    public static PieInfoImpl create(IPieInfo info) {
        return new PieInfoImpl(info);
    }

    public IPieInfo getPieInfo() {
        return mPieInfo;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public boolean isDrawStrokeOnly() {
        return drawStrokeOnly;
    }

    public PieInfoImpl setDrawStrokeOnly(boolean drawStrokeOnly) {
        if (this.drawStrokeOnly == drawStrokeOnly) return this;
        this.drawStrokeOnly = drawStrokeOnly;
        initPaint(mPieInfo);
        return this;
    }

    public PieInfoImpl setStrokeWidth(int width) {
        this.strokeWidth = width;
        mPaint.setStrokeWidth(width);
        return this;
    }

    public float getSweepAngle() {
        return Math.abs(endAngle - startAngle);
    }

    boolean isInAngleRange(float angle) {
        return angle >= startAngle && angle <= endAngle;
    }

    boolean isTouchInAngleRange(float angle) {
        //所有点击的角度都需要收归到0~360的范围，兼容任意角度
        final float tAngle = DegreeUtil.limitDegreeInTo360(angle);
        float tStart = DegreeUtil.limitDegreeInTo360(startAngle);
        float tEnd = DegreeUtil.limitDegreeInTo360(endAngle);
        DebugLogUtil.logTouchAngle("isTouchInAngleRange  >>  tStart： " + tStart + "   tEnd： " + tEnd + "   tAngle： ", tAngle);
        boolean result;
        if (tEnd < tStart) {
            //已经过界
            result = tAngle >= tStart && (360 - tAngle) <= tEnd;
        } else {
            result = tAngle >= tStart && tAngle <= tEnd;
        }
        if (result) {
            DebugLogUtil.findTouchInfo(this);
        }
        return result;
    }

    @Override
    public String toString() {
        return "值： " + getPieInfo().getValue() + "    开始角度: " + getStartAngle() + "    结束角度： " + getEndAngle();
    }
}
