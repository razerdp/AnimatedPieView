package com.razerdp.widget.animatedpieview;

/**
 * Created by 大灯泡 on 2017/11/8.
 * <p>
 * 点击事件helper
 */

public class TouchHelper {
    private AnimatedPieViewConfig mConfig;
    //圆心
    private float centerX;
    private float centerY;
    private float radius;

    //因为判断点击时是判断内圆和外圆半径，可能很苛刻，所以这里可以考虑增加点击范围
    //ps，这个是会被平方的
    private int expandClickRange = 5;

    public TouchHelper(AnimatedPieViewConfig config) {
        mConfig = config;
    }

    public void setPieParam(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public PieInfoImpl pointToInfo(float x, float y) {
        final boolean isStrokeOnly = mConfig.isDrawStrokeOnly();
        final float strokeWidth = mConfig.getStrokeWidth();
        //外圆半径
        final float exCircleRadius = radius;
        //内圆半径
        final float innerCircleRadius = isStrokeOnly ? exCircleRadius - strokeWidth : 0;
        //点击位置到圆心的直线距离(没开根)
        final double touchDistancePow = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        //内圆半径<=直线距离<=外圆半径
        final boolean isTouchInRing = touchDistancePow >= Math.pow(innerCircleRadius + expandClickRange, 2)
                && touchDistancePow <= Math.pow(exCircleRadius + expandClickRange, 2);
        if (!isTouchInRing) return null;
        return findPieInfoImpl(x, y, touchDistancePow);
    }

    /**
     * 通过点击的三角形斜线距离和起点距离的夹角得出具体点击的哪个圆环区段
     * <p>
     * Math.atan2比Math.atan稳定很多
     * <p>
     *
     * @param x
     * @param y
     * @param touchDistancePow
     * @return
     */
    private PieInfoImpl findPieInfoImpl(float x, float y, double touchDistancePow) {
        //得到角度
        double touchAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        if (touchAngle < 0) {
            touchAngle += 360.0f;
        }
        for (PieInfoImpl pieInfo : mConfig.getImplDatas()) {
            if (pieInfo.isTouchInAngleRange((float) touchAngle)) {
                return pieInfo;
            }
        }
        return null;
    }


}
