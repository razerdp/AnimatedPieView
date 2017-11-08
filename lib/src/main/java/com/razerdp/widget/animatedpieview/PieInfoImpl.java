package com.razerdp.widget.animatedpieview;

import android.graphics.Paint;

import com.razerdp.widget.animatedpieview.data.IPieInfo;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

final class PieInfoImpl {
    private final IPieInfo mPieInfo;
    private float startAngle;
    private float endAngle;
    private Paint mPaint;

    private PieInfoImpl(IPieInfo info) {
        this.mPieInfo = info;
        if (mPaint == null) mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(info.getColor());
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

    public PieInfoImpl setStrokeWidth(int width) {
        mPaint.setStrokeWidth(width);
        return this;
    }

    public float getSweepAngle() {
        return Math.abs(endAngle - startAngle);
    }

    public boolean isInAngleRange(float angle) {
        return angle >= startAngle && angle <= endAngle;
    }
}
