package com.razerdp.widget.animatedpieview.render;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.IPieView;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.PieOption;
import com.razerdp.widget.animatedpieview.utils.AnimationCallbackUtils;
import com.razerdp.widget.animatedpieview.utils.PLog;
import com.razerdp.widget.animatedpieview.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2018/2/1.
 */
public class PieChartRender extends BaseRender implements ITouchRender {

    enum DrawMode {
        DRAW,
        TOUCH
    }

    enum LineDirection {
        TOP_RIGHT(1, 0),
        BOTTOM_RIGHT(1, 1),
        TOP_LEFT(0, 0),
        BOTTOM_LEFT(0, 1),
        CENTER_RIGHT(1, -1),
        CENTER_LEFT(0, -1);

        int xDirection;//0:left 1:right
        int yDirection;//-1:center 0:top 1:bottom

        LineDirection(int xDirection, int yDirection) {
            this.xDirection = xDirection;
            this.yDirection = yDirection;
        }

    }

    private List<PieInfoWrapper> mDataWrappers;
    private List<PieInfoWrapper> mCachedDrawWrappers;
    private PathMeasure mPathMeasure;
    private AnimatedPieViewConfig mConfig;
    private DrawMode mDrawMode = DrawMode.DRAW;
    //-----------------------------------------draw area-----------------------------------------
    private RectF pieBounds;
    private float pieRadius;
    private int maxDescTextLength;
    private volatile boolean isInAnimating;
    //-----------------------------------------anim area-----------------------------------------
    private PieInfoWrapper mDrawingPie;
    private float animAngle;
    //-----------------------------------------other-----------------------------------------
    private TouchHelper mTouchHelper;
    private RenderAnimation mRenderAnimation;
    private volatile boolean animHasStart;

    public PieChartRender(IPieView iPieView) {
        super(iPieView);
        mDataWrappers = new ArrayList<>();
        mCachedDrawWrappers = new ArrayList<>();
        mPathMeasure = new PathMeasure();
        pieBounds = new RectF();
        mTouchHelper = new TouchHelper();
        pieRadius = 0;
    }

    @Override
    public void reset() {
        mTouchHelper.reset();
        pieBounds.setEmpty();
        animHasStart = false;
        isInAnimating = false;
        pieRadius = 0;

        mDataWrappers = mDataWrappers == null ? new ArrayList<PieInfoWrapper>() : mDataWrappers;
        mDataWrappers.clear();

        mCachedDrawWrappers = mCachedDrawWrappers == null ? new ArrayList<PieInfoWrapper>() : mCachedDrawWrappers;
        mCachedDrawWrappers.clear();

        mDrawingPie = null;
        mRenderAnimation = null;
        mIPieView.getPieView().clearAnimation();
    }

    @Override
    public boolean onPrepare() {
        mConfig = mIPieView.getConfig();
        if (mConfig == null) {
            Log.e(TAG, "onPrepare: config is null,abort draw because of preparing failed");
            return false;
        }
        setDrawMode(DrawMode.DRAW);
        mTouchHelper.prepare();
        prepareAnim();
        //wrap datas and calculate sum value
        //包裹数据并且计算总和
        double sum = 0;
        PieInfoWrapper preWrapper = null;
        for (Pair<IPieInfo, Boolean> info : mConfig.getDatas()) {
            sum += Math.abs(info.first.getValue());
            PieInfoWrapper wrapper = new PieInfoWrapper(info.first);
            wrapper.setAutoDesc(info.second);
            //简单的形成一个链表
            if (preWrapper != null) {
                preWrapper.setNextWrapper(wrapper);
                wrapper.setPreWrapper(preWrapper);
            }
            preWrapper = wrapper;
            mDataWrappers.add(wrapper);
        }

        //calculate degree for each pieInfoWrapper
        //计算每个wrapper的角度
        float lastAngle = mConfig.getStartAngle();
        for (PieInfoWrapper dataWrapper : mDataWrappers) {
            dataWrapper.prepare(mConfig);
            lastAngle = dataWrapper.calculateDegree(lastAngle, sum, mConfig);
            int textWidth = mPieManager.measureTextBounds(dataWrapper.getDesc(), (int) mConfig.getTextSize()).width();
            int textHeight = mPieManager.measureTextBounds(dataWrapper.getDesc(), (int) mConfig.getTextSize()).height();
            int labelWidth = 0;
            int labelHeight = 0;
            int labelPadding = 0;
            Bitmap label = dataWrapper.getIcon(textWidth, textHeight);
            if (label != null) {
                if (dataWrapper.getPieOption() != null) {
                    labelPadding = dataWrapper.getPieOption().getLabelPadding();
                }
                labelWidth = label.getWidth();
                labelHeight = label.getHeight();
            }
            textWidth += labelWidth + labelPadding * 2;
            maxDescTextLength = Math.max(maxDescTextLength, textWidth);
            PLog.i("desc >> " + dataWrapper.getDesc() + "  maxDesTextSize >> " + maxDescTextLength);
        }

        return true;
    }

    private void prepareAnim() {
        if (mConfig.isAnimatePie()) {
            mRenderAnimation = new RenderAnimation();
            mRenderAnimation.setInterpolator(mConfig.getAnimationInterpolator());
            mRenderAnimation.setDuration(mConfig.getDuration());
            mRenderAnimation.setAnimationListener(new AnimationCallbackUtils.SimpleAnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    super.onAnimationStart(animation);
                    isInAnimating = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isInAnimating = false;
                }
            });
        }

    }

    @Override
    public void onSizeChanged(int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        if (mTouchHelper != null) {
            mTouchHelper.setCenter();
        }
    }

    //-----------------------------------------render draw-----------------------------------------
    @Override
    public void onDraw(Canvas canvas) {
        float width = mPieManager.getDrawWidth();
        float height = mPieManager.getDrawHeight();

        float centerX = width / 2;
        float centerY = height / 2;

        canvas.translate(centerX, centerY);

        measurePieRadius(width, height);

        switch (mDrawMode) {
            case DRAW:
                renderDraw(canvas);
                break;
            case TOUCH:
                renderTouch(canvas);
                break;
        }
    }

    private void renderDraw(Canvas canvas) {
        if (mConfig.isAnimatePie()) {
            if (mRenderAnimation != null && !isInAnimating && !animHasStart) {
                animHasStart = true;
                mIPieView.getPieView().startAnimation(mRenderAnimation);
                return;
            }
            renderAnimaDraw(canvas);
        } else {
            renderNormalDraw(canvas);
        }
    }

    private void renderTouch(Canvas canvas) {
        drawCachedPie(canvas, mTouchHelper.sameClick ? mTouchHelper.lastFloatWrapper : mTouchHelper.floatingWrapper);
        renderTouchDraw(canvas, mTouchHelper.lastFloatWrapper, mTouchHelper.floatDownTime);
        PLog.i("lastFloatWrapper id = " + (mTouchHelper.lastFloatWrapper == null ? "null" : mTouchHelper.lastFloatWrapper.getId()) + "  downTime = " + mTouchHelper.floatDownTime);
        renderTouchDraw(canvas, mTouchHelper.floatingWrapper, mTouchHelper.floatUpTime);
        PLog.d("floatingWrapper id = " + (mTouchHelper.floatingWrapper == null ? "null" : mTouchHelper.floatingWrapper.getId()) + "  upTime = " + mTouchHelper.floatUpTime);

    }

    private void renderNormalDraw(Canvas canvas) {
        if (Util.isListEmpty(mCachedDrawWrappers) || mCachedDrawWrappers.size() != mDataWrappers.size()) {
            mCachedDrawWrappers.clear();
            mCachedDrawWrappers.addAll(mDataWrappers);
        }
        drawCachedPie(canvas, null);
    }

    private void renderAnimaDraw(Canvas canvas) {
        if (mDrawingPie != null) {
            drawCachedPie(canvas, mDrawingPie);
            canvas.drawArc(pieBounds,
                    mDrawingPie.getFromAngle(),
                    animAngle - mDrawingPie.getFromAngle() - mConfig.getSplitAngle(),
                    !mConfig.isStrokeMode(),
                    mDrawingPie.getDrawPaint());
            if (mConfig.isDrawText() && animAngle >= mDrawingPie.getMiddleAngle() && animAngle <= mDrawingPie.getToAngle()) {
                drawText(canvas, mDrawingPie);
            }
        }
    }

    private void renderTouchDraw(Canvas canvas, PieInfoWrapper wrapper, float timeSet) {
        if (wrapper == null) return;
        mTouchHelper.setTouchBounds(timeSet);
        Paint touchPaint = mTouchHelper.prepareTouchPaint(wrapper);
        touchPaint.setShadowLayer(mConfig.getFloatShadowRadius() * timeSet, 0, 0, touchPaint.getColor());
        touchPaint.setStrokeWidth(mConfig.getStrokeWidth() + (10 * timeSet));
        applyAlphaToPaint(wrapper, touchPaint);
        canvas.drawArc(mTouchHelper.touchBounds,
                wrapper.getFromAngle() - (mConfig.getFloatExpandAngle() * timeSet),
                wrapper.getSweepAngle() + (mConfig.getFloatExpandAngle() * 2 * timeSet) - mConfig.getSplitAngle(),
                !mConfig.isStrokeMode(),
                touchPaint);
    }


    private void drawCachedPie(Canvas canvas, PieInfoWrapper excluded) {
        if (!Util.isListEmpty(mCachedDrawWrappers)) {
            for (PieInfoWrapper cachedDrawWrapper : mCachedDrawWrappers) {
                if (mConfig.isDrawText()) {
                    drawText(canvas, cachedDrawWrapper);
                }
                Paint paint = cachedDrawWrapper.getAlphaDrawPaint();
                applyAlphaToPaint(cachedDrawWrapper, paint);
                if (cachedDrawWrapper.equals(excluded)) {
                    continue;
                }
                canvas.drawArc(pieBounds,
                        cachedDrawWrapper.getFromAngle(),
                        cachedDrawWrapper.getSweepAngle() - mConfig.getSplitAngle(),
                        !mConfig.isStrokeMode(),
                        paint);
            }
        }
    }

    private void drawText(Canvas canvas, PieInfoWrapper wrapper) {
        if (wrapper == null) return;

        //根据touch扩大量修正指示线和描述文字的位置
        float fixPos = (wrapper.equals(mTouchHelper.floatingWrapper) ? getFixTextPos(wrapper) : 0) + (wrapper.equals(mTouchHelper.lastFloatWrapper) ? getFixTextPos(wrapper) : 0);

        final float pointMargins = fixPos
                + pieRadius
                + mConfig.getGuideLineMarginStart()
                + (mConfig.isStrokeMode() ? mConfig.getStrokeWidth() / 2 : 0);
        float cx = (float) (pointMargins * Math.cos(Math.toRadians(wrapper.getMiddleAngle())));
        float cy = (float) (pointMargins * Math.sin(Math.toRadians(wrapper.getMiddleAngle())));

        //画点
        Paint paint = wrapper.getAlphaDrawPaint();
        if (angleToProgress(animAngle, wrapper) > 0.5) {
            applyAlphaToPaint(wrapper, paint);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, mConfig.getGuidePointRadius(), paint);
        }

        //画线
        float guideLineEndX1 = -1;
        float guideLineEndY1 = -1;

        float guideLineEndX2 = -1;
        float guideLineEndY2 = -1;


        String desc = TextUtils.isEmpty(wrapper.getDesc()) ? "null" : wrapper.getDesc();
        Rect textBounds = mPieManager.measureTextBounds(desc, (int) mConfig.getTextSize());
        int textBoundsWidth = textBounds.width();
        int textBoundsHeight = textBounds.height();

        //label
        Bitmap icon = wrapper.getIcon(textBoundsWidth, textBoundsHeight);
        int labelWidth = 0;
        int labelHeight = 0;
        int labelPadding = 0;
        if (icon != null) {
            labelWidth = icon.getWidth();
            labelHeight = icon.getHeight();
            labelPadding = Math.max(0, wrapper.getPieOption() == null ? 0 : wrapper.getPieOption().getLabelPadding());
        }

        float textLength = textBoundsWidth + mConfig.getTextMargin() + 2 * labelPadding + labelWidth;

        //计算拐角方向
        LineDirection direction = calculateLineGravity(cx, cy);
        float guideMiddleLength = Math.abs(cy / 6);

        switch (direction) {
            case TOP_LEFT:
                guideLineEndX1 = cx - guideMiddleLength * absMathCos(-45) - fixPos;
                guideLineEndY1 = cy - guideMiddleLength * absMathCos(-45) - fixPos;

                guideLineEndX2 = guideLineEndX1 - textLength;
                break;
            case TOP_RIGHT:
                guideLineEndX1 = cx + guideMiddleLength * absMathCos(45) + fixPos;
                guideLineEndY1 = cy - guideMiddleLength * absMathCos(45) - fixPos;

                guideLineEndX2 = guideLineEndX1 + textLength;
                break;
            case CENTER_LEFT:
                guideLineEndX1 = cx - guideMiddleLength - fixPos;
                guideLineEndY1 = cy;

                guideLineEndX2 = guideLineEndX1 - textLength;
                break;
            case CENTER_RIGHT:
                guideLineEndX1 = cx + guideMiddleLength + fixPos;
                guideLineEndY1 = cy;

                guideLineEndX2 = guideLineEndX1 + textLength;
                break;
            case BOTTOM_LEFT:
                guideLineEndX1 = cx - guideMiddleLength * absMathCos(-45) - fixPos;
                guideLineEndY1 = cy + guideMiddleLength * absMathCos(-45) + fixPos;

                guideLineEndX2 = guideLineEndX1 - textLength;
                break;
            case BOTTOM_RIGHT:
                guideLineEndX1 = cx + guideMiddleLength * absMathCos(45) + fixPos;
                guideLineEndY1 = cy + guideMiddleLength * absMathCos(45) + fixPos;

                guideLineEndX2 = guideLineEndX1 + textLength;
                break;

        }
        guideLineEndY2 = guideLineEndY1;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mConfig.getGuideLineWidth());
        paint.setStrokeJoin(Paint.Join.ROUND);

        Path path = wrapper.getLinePath();
        Path measurePathDst = wrapper.getLinePathMeasure();
        path.moveTo(cx, cy);
        path.lineTo(guideLineEndX1, guideLineEndY1);
        path.lineTo(guideLineEndX2, guideLineEndY2);
        mPathMeasure.nextContour();
        mPathMeasure.setPath(path, false);
        float progress = angleToProgress(animAngle, wrapper);
        mPathMeasure.getSegment(0, progress * mPathMeasure.getLength(), measurePathDst, true);
        canvas.drawPath(measurePathDst, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(mConfig.getTextSize());
        paint.setAlpha((int) (255 * progress));

        float textStartX = calculateTextStartX(guideLineEndX1, guideLineEndX2, direction, textBoundsWidth);
        float textStartY = calculateTextStartY(guideLineEndY1, guideLineEndY2, direction, textBoundsHeight);

        if (icon != null) {
            textStartX = fitTextStartXWithLabel(textStartX, textBoundsWidth, labelWidth, direction, wrapper.getPieOption());
            float iconLeft;
            float iconTop;
            iconLeft = calculateLabelX(wrapper.getPieOption(), labelWidth, textStartX, direction, textBoundsWidth);
            iconTop = textStartY - textBoundsHeight;
            if (iconLeft != -1 && iconTop != -1) {
                canvas.drawBitmap(icon, iconLeft, iconTop, wrapper.getIconPaint());
            }
        }

        //画文字
        canvas.drawText(desc, textStartX, textStartY, paint);

    }


    private float fitTextStartXWithLabel(float textStartX, int textBoundsWidth, int labelWidth, LineDirection direction, PieOption pieOption) {
        if (pieOption != null) {
            int labelPadding = pieOption.getLabelPadding();
            switch (pieOption.getLabelPosition()) {
                case PieOption.NEAR_PIE:
                    if (direction.xDirection < 1) {
                        //文字在左边，图片在文字右边时，需要针对文字位置适配
                        textStartX -= labelPadding * 2 + labelWidth;
                    } else {
                        textStartX += labelPadding * 2 + labelWidth;
                    }
                    break;
            }
        }
        return textStartX;
    }

    private float calculateLabelX(PieOption pieOption, int iconWidth, float textStartX, LineDirection direction, int textWidth) {
        if (pieOption == null) return -1;
        float result;
        int labelPosition = pieOption.getLabelPosition();
        int padding = pieOption.getLabelPadding();
        switch (direction) {
            case TOP_LEFT:
            case CENTER_LEFT:
            case BOTTOM_LEFT:
                if (labelPosition == PieOption.NEAR_PIE) {
                    result = textStartX + textWidth + padding;
                } else {
                    result = textStartX - iconWidth - padding;
                }
                break;
            case TOP_RIGHT:
            case CENTER_RIGHT:
            case BOTTOM_RIGHT:
                if (labelPosition == PieOption.NEAR_PIE) {
                    result = textStartX - iconWidth - padding;
                } else {
                    result = textStartX + textWidth + padding;
                }
                break;
            default:
                return -1;
        }
        return result;
    }

    private float calculateTextStartX(float guideLineEndX1, float guideLineEndX2, LineDirection direction, int textWidth) {
        final int textGravity = mConfig.getTextGravity();
        final int textMargin = mConfig.getTextMargin();
        switch (direction) {
            case TOP_LEFT:
            case CENTER_LEFT:
            case BOTTOM_LEFT:
                return textGravity == AnimatedPieViewConfig.ALIGN ? guideLineEndX2 - textWidth - textMargin : guideLineEndX1 - textWidth - textMargin;
            case TOP_RIGHT:
            case CENTER_RIGHT:
            case BOTTOM_RIGHT:
                return textGravity == AnimatedPieViewConfig.ALIGN ? guideLineEndX2 + textMargin : guideLineEndX1 + textMargin;
            default:
                return guideLineEndX1;
        }
    }

    private float calculateTextStartY(float guideLineEndY1, float guideLineEndY2, LineDirection direction, int textHeight) {
        final int textGravity = mConfig.getTextGravity();
        final int textMargin = mConfig.getTextMargin();
        switch (direction) {
            case TOP_LEFT:
            case CENTER_LEFT:
            case TOP_RIGHT:
                if (textGravity == AnimatedPieViewConfig.ABOVE || textGravity == AnimatedPieViewConfig.ECTOPIC) {
                    return guideLineEndY1 - textMargin - textHeight / 2;
                } else if (textGravity == AnimatedPieViewConfig.BELOW) {
                    return guideLineEndY1 + textMargin + textHeight;
                } else {
                    return guideLineEndY1 + textHeight / 2;
                }
            case BOTTOM_LEFT:
            case CENTER_RIGHT:
            case BOTTOM_RIGHT:
                if (textGravity == AnimatedPieViewConfig.ABOVE) {
                    return guideLineEndY1 - textMargin - textHeight / 2;
                } else if (textGravity == AnimatedPieViewConfig.BELOW || textGravity == AnimatedPieViewConfig.ECTOPIC) {
                    return guideLineEndY1 + textMargin + textHeight;
                } else {
                    return guideLineEndY1 + textHeight / 2;
                }
            default:
                return guideLineEndY1 - textMargin - textHeight / 2;
        }
    }

    private LineDirection calculateLineGravity(float startX, float startY) {
        if (startX > 0) {
            //在右边
            return startY > 0 ? LineDirection.BOTTOM_RIGHT : LineDirection.TOP_RIGHT;
        } else if (startX < 0) {
            //在左边
            return startY > 0 ? LineDirection.BOTTOM_LEFT : LineDirection.TOP_LEFT;
        } else if (startY == 0) {
            //刚好中间
            return startX > 0 ? LineDirection.CENTER_RIGHT : LineDirection.CENTER_LEFT;
        }
        return LineDirection.TOP_RIGHT;
    }

    private float getFixTextPos(PieInfoWrapper wrapper) {
        if (wrapper == null) return 0;
        final float scaleSizeInTouch = !mConfig.isStrokeMode() ? mConfig.getFloatExpandSize() : 10;
        boolean up = wrapper.equals(mTouchHelper.floatingWrapper);
        return up ? scaleSizeInTouch * mTouchHelper.floatUpTime : scaleSizeInTouch * mTouchHelper.floatDownTime;
    }

    //-----------------------------------------render draw fin-----------------------------------------

    private void applyAlphaToPaint(PieInfoWrapper target, Paint paint) {
        if (paint == null) return;
        if (mDrawMode == DrawMode.DRAW) {
            paint.setAlpha(255);
            return;
        }

        //如果点的是同一个，则需要特殊处理
        boolean selected = false;
        if (mTouchHelper.floatingWrapper != null) {
            //针对浮起来的
            selected = mTouchHelper.floatingWrapper.equals(target);
        } else {
            if (mTouchHelper.lastFloatWrapper != null) {
                selected = mTouchHelper.lastFloatWrapper.equals(target);
            }
        }
        final float alphaCut = 255 - mConfig.getFocusAlpha();
        switch (mConfig.getFocusAlphaType()) {
            case AnimatedPieViewConfig.FOCUS_WITH_ALPHA:
                //选中的对象有alpha变化
                boolean alphaDown = selected && mTouchHelper.floatingWrapper != null;
                if (selected) {
                    paint.setAlpha((int) (255 - (alphaCut * (alphaDown ? mTouchHelper.floatUpTime : mTouchHelper.floatDownTime))));
                } else {
                    paint.setAlpha(255);
                }
                break;
            case AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV:
                boolean alphaDown2 = !selected && mTouchHelper.floatingWrapper != null;
                if (selected) {
                    paint.setAlpha(255);
                } else {
                    paint.setAlpha((int) (255 - (alphaCut * (alphaDown2 ? mTouchHelper.floatUpTime : mTouchHelper.floatDownTime))));
                }
                break;
            case AnimatedPieViewConfig.FOCUS_WITHOUT_ALPHA:
            default:
                paint.setAlpha(255);
                break;
        }

    }


    @Override
    public void onDestroy() {

    }

    //-----------------------------------------touch-----------------------------------------

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTouchHelper.handleTouch(event);
    }

    @Override
    public void forceAbortTouch() {

    }

    //-----------------------------------------tools-----------------------------------------

    private void setCurPie(PieInfoWrapper infoWrapper, float degree) {
        if (mDrawingPie != null) {
            if (degree >= mDrawingPie.getToAngle() / 2) {
                if (!mDrawingPie.isCached()) {
                    // fix anim duration too short
                    PieInfoWrapper preWrapper = mDrawingPie.getPreWrapper();
                    if (preWrapper != null && !preWrapper.isCached()) {
                        preWrapper.setCached(true);
                        mCachedDrawWrappers.add(preWrapper);
                    }
                    mCachedDrawWrappers.add(mDrawingPie);
                    mDrawingPie.setCached(true);
                }
            }
        }
        mDrawingPie = infoWrapper;
        animAngle = degree;
        callInvalidate();
    }

    private void setDrawMode(DrawMode drawMode) {
        if (drawMode == DrawMode.TOUCH && isInAnimating) return;
        mDrawMode = drawMode;
    }

    private void measurePieRadius(float width, float height) {
        if (pieRadius > 0) {
            pieBounds.set(-pieRadius, -pieRadius, pieRadius, pieRadius);
            return;
        }
        final float minSize = Math.min(width / 2, height / 2);
        //最低0.5的最小高宽值
        float minPieRadius = minSize / 4;
        if (mConfig.isAutoSize()) {
            //按照最大的文字测量
            float radius = Integer.MAX_VALUE;
            while (radius > minSize) {
                if (radius == Integer.MAX_VALUE) {
                    radius = minSize - maxDescTextLength
                            - (mConfig.isStrokeMode() ? (mConfig.getStrokeWidth() >> 1) : 0)
                            - mConfig.getGuideLineMarginStart();
                } else {
                    radius -= minSize / 10;
                }
            }
            pieRadius = Math.max(minPieRadius, radius);

        } else {
            //优先判定size
            if (mConfig.getPieRadius() > 0) {
                pieRadius = mConfig.getPieRadius();
            } else if (mConfig.getPieRadiusRatio() > 0) {
                pieRadius = minSize / 2 * mConfig.getPieRadiusRatio();
            } else {
                pieRadius = minPieRadius;
            }
        }
        pieBounds.set(-pieRadius, -pieRadius, pieRadius, pieRadius);
    }

    private float absMathSin(double angdeg) {
        return (float) Math.abs(Math.sin(Math.toRadians(angdeg)));
    }

    private float absMathCos(double angdeg) {
        return (float) Math.abs(Math.cos(Math.toRadians(angdeg)));
    }

    private float angleToProgress(float angle, PieInfoWrapper wrapper) {
        if (wrapper == null || !mConfig.isAnimatePie()) return 1f;
        if (angle < wrapper.getMiddleAngle()) return 0f;
        if (angle >= wrapper.getToAngle()) return 1f;
        return (angle - wrapper.getMiddleAngle()) / (wrapper.getToAngle() - wrapper.getMiddleAngle());
    }

    //-----------------------------------------inner helper-----------------------------------------

    private class RenderAnimation extends Animation {
        private PieInfoWrapper lastFoundWrapper;

        public RenderAnimation() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (mConfig == null) {
                throw new NullPointerException("viewConfig为空");
            }
            PLog.i("interpolatedTime = " + interpolatedTime);
            if (interpolatedTime >= 0.0f && interpolatedTime <= 1.0f) {
                float angle = 360 * interpolatedTime + mConfig.getStartAngle();
                PieInfoWrapper info = findPieinfoWithAngle(angle);
                setCurPie(info == null ? lastFoundWrapper : info, angle);
            }
        }

        public PieInfoWrapper findPieinfoWithAngle(float angle) {
            if (Util.isListEmpty(mDataWrappers)) return null;
            if (lastFoundWrapper != null && lastFoundWrapper.contains(angle)) {
                return lastFoundWrapper;
            }
            for (PieInfoWrapper infoWrapper : mDataWrappers) {
                if (infoWrapper.contains(angle)) {
                    lastFoundWrapper = infoWrapper;
                    return infoWrapper;
                }
            }
            return null;
        }
    }

    private class TouchHelper {
        //因为判断点击时是判断内圆和外圆半径，可能很苛刻，所以这里可以考虑增加点击范围
        private int expandClickRange;
        private float centerX;
        private float centerY;

        private RectF touchBounds;
        private PieInfoWrapper floatingWrapper;
        private ValueAnimator floatUpAnim;
        private float floatUpTime;
        private PieInfoWrapper lastFloatWrapper;
        private ValueAnimator floatDownAnim;
        private float floatDownTime;

        private float touchX = -1;
        private float touchY = -1;

        private Paint mTouchPaint;
        private boolean sameClick;


        private PieInfoWrapper lastTouchWrapper;

        TouchHelper() {
            this(25);
        }

        TouchHelper(int expandClickRange) {
            this.expandClickRange = expandClickRange;
            touchBounds = new RectF();
        }

        void reset() {
            centerX = 0;
            centerY = 0;
            touchBounds.setEmpty();
            floatUpAnim = floatUpAnim == null ? ValueAnimator.ofFloat(0, 1) : floatUpAnim;
            floatUpAnim.removeAllUpdateListeners();
            floatUpTime = 0;

            floatDownAnim = floatDownAnim == null ? ValueAnimator.ofFloat(0, 1) : floatDownAnim;
            floatDownAnim.removeAllUpdateListeners();
            floatUpTime = 0;

            floatingWrapper = null;
            lastFloatWrapper = null;
            lastTouchWrapper = null;

            touchX = -1;
            touchY = -1;
            sameClick = false;
        }

        void prepare() {
            setCenter();

            mTouchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

            floatUpAnim = ValueAnimator.ofFloat(0, 1);
            floatUpAnim.setDuration(mConfig.getFloatUpDuration());
            floatUpAnim.setInterpolator(new DecelerateInterpolator());
            floatUpAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    floatUpTime = (float) animation.getAnimatedValue();
                    callInvalidate();
                }
            });

            floatDownAnim = ValueAnimator.ofFloat(1, 0);
            floatDownAnim.setDuration(mConfig.getFloatDownDuration());
            floatDownAnim.setInterpolator(new DecelerateInterpolator());
            floatDownAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    floatDownTime = (float) animation.getAnimatedValue();
                    callInvalidate();
                }
            });
        }

        private void setCenter() {
            centerX = mPieManager.getDrawWidth() / 2;
            centerY = mPieManager.getDrawHeight() / 2;
        }

        Paint prepareTouchPaint(PieInfoWrapper wrapper) {
            if (mTouchPaint == null) {
                mTouchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            }
            if (wrapper != null) {
                mTouchPaint.set(wrapper.getDrawPaint());
            }
            return mTouchPaint;
        }

        PieInfoWrapper pointToPieInfoWrapper(float x, float y) {
            final boolean isStrokeMode = mConfig.isStrokeMode();
            final float strokeWidth = mConfig.getStrokeWidth();
            //外圆半径
            final float exCircleRadius = isStrokeMode ? pieRadius + strokeWidth / 2 : pieRadius;
            //内圆半径
            final float innerCircleRadius = isStrokeMode ? pieRadius - strokeWidth / 2 : 0;
            //点击位置到圆心的直线距离(没开根)
            final double touchDistancePow = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
            //内圆半径<=直线距离<=外圆半径
            final boolean isTouchInRing = touchDistancePow >= expandClickRange + Math.pow(innerCircleRadius, 2)
                    && touchDistancePow <= expandClickRange + Math.pow(exCircleRadius, 2);
            if (!isTouchInRing) return null;
            return findWrapper(x, y);
        }

        PieInfoWrapper findWrapper(float x, float y) {
            //得到角度
            double touchAngle = Math.toDegrees(Math.atan2(y - centerY, x - centerX));
            if (touchAngle < 0) {
                touchAngle += 360.0f;
            }
            if (lastTouchWrapper != null && lastTouchWrapper.containsTouch((float) touchAngle)) {
                return lastTouchWrapper;
            }
            PLog.i("touch角度 = " + touchAngle);
            for (PieInfoWrapper wrapper : mDataWrappers) {
                if (wrapper.containsTouch((float) touchAngle)) {
                    lastTouchWrapper = wrapper;
                    return wrapper;
                }
            }
            return null;
        }

        void setTouchBounds(float timeSet) {
            final float scaleSizeInTouch = !mConfig.isStrokeMode() ? mConfig.getFloatExpandSize() : 0;
            touchBounds.set(pieBounds.left - scaleSizeInTouch * timeSet,
                    pieBounds.top - scaleSizeInTouch * timeSet,
                    pieBounds.right + scaleSizeInTouch * timeSet,
                    pieBounds.bottom + scaleSizeInTouch * timeSet);
        }

        boolean handleTouch(MotionEvent event) {
            if (mConfig == null || !mConfig.isCanTouch() || isInAnimating) return false;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchX = event.getX();
                    touchY = event.getY();
                    return true;
                case MotionEvent.ACTION_UP:
                    PieInfoWrapper touchWrapper = pointToPieInfoWrapper(touchX, touchY);
                    if (touchWrapper == null) return false;
                    setDrawMode(DrawMode.TOUCH);
                    if (touchWrapper.equals(floatingWrapper)) {
                        //如果点的是当前正在浮起的wrapper，则移到上一个，当前的置空
                        lastFloatWrapper = touchWrapper;
                        floatingWrapper = null;
                        sameClick = true;
                    } else {
                        lastFloatWrapper = floatingWrapper;
                        floatingWrapper = touchWrapper;
                        sameClick = false;
                    }

                    if (mConfig.isAnimTouch()) {
                        floatUpAnim.start();
                        floatDownAnim.start();
                    } else {
                        floatUpTime = 1;
                        floatDownTime = 1;
                        callInvalidate();
                    }

                    if (mConfig.getSelectListener() != null) {
                        mConfig.getSelectListener().onSelectPie(touchWrapper.getPieInfo(), touchWrapper.equals(floatingWrapper));
                    }

                    return true;
            }

            return false;
        }
    }
}
