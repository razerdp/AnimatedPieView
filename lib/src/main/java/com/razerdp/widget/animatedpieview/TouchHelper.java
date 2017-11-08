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

    public TouchHelper(AnimatedPieViewConfig config) {
        mConfig = config;
    }

    public void setPieParam(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public PieInfoImpl pointToInfo(float x, float y) {
        final float strokeWidth = mConfig.getStrokeWidth();
        //外圆半径
        final float exCircleRadius = radius;
        //内圆半径
        final float innerCircleRadius = exCircleRadius - strokeWidth;
        //点击位置到圆心的直线距离(没开根)
        final double touchDistancePow = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        //内圆半径<=直线距离<=外圆半径
        final boolean isTouchInRing = touchDistancePow >= Math.pow(innerCircleRadius, 2) && touchDistancePow <= Math.pow(exCircleRadius, 2);
        if (!isTouchInRing) return null;
        return findPieInfoImpl(x, y, touchDistancePow);
    }

    /**
     * 通过点击的三角形斜线距离和起点距离的夹角得出具体点击的哪个圆环区段
     * <p>
     * Math.atan2比Math.atan稳定很多
     * <p>
     * 若要用度表示反正切值，请将结果再乘以 180/3.14159(PI)
     *
     * @param x
     * @param y
     * @param touchDistancePow
     * @return
     */
    private PieInfoImpl findPieInfoImpl(float x, float y, double touchDistancePow) {
        //获取设定好的起点角
        final float startAngle = mConfig.getStartAngle();
        //得到角度
        double touchAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
        touchAngle = fixedTouchAngle(touchAngle, startAngle);
        for (PieInfoImpl pieInfo : mConfig.getImplDatas()) {
            if (pieInfo.isInAngleRange((float) touchAngle)) {
                return pieInfo;
            }
        }
        return null;
    }

    private double fixedTouchAngle(double touchAngle, float startAngle) {
        double result = touchAngle;
        DebugLogUtil.logTouchAngle("修正前角度  >>>  ", result);
        if (result < 0) {
            result += 360.0;
        }
        int turns = (int) (startAngle / 360.0f);
        if (startAngle < -180.0 || startAngle > 360.0) {
            result += 360.0f * turns;
        }
        result += startAngle;

        DebugLogUtil.logTouchAngle("修正后角度  >>>  ", result);
        return result;
    }

}
