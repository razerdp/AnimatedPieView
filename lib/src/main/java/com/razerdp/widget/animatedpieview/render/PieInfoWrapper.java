package com.razerdp.widget.animatedpieview.render;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;

import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.PieOption;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.razerdp.widget.animatedpieview.utils.DegreeUtil;
import com.razerdp.widget.animatedpieview.utils.PLog;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 大灯泡 on 2018/2/1.
 */
final class PieInfoWrapper implements Serializable {

    private static final long serialVersionUID = -8551831728967624659L;

    private final String id;
    private volatile boolean hasCached;
    private final IPieInfo mPieInfo;

    //============= 绘制设置 =============
    private Paint mDrawPaint;
    private Paint mAlphaDrawPaint;
    private Paint mTexPaint;
    private Paint mIconPaint;
    private Path mLinePath;
    private Path mLinePathMeasure;
    private Bitmap icon;
    //============= 参数 =============
    private float fromAngle;
    private float sweepAngle;
    private float toAngle;
    private boolean autoDesc;
    private String desc;

    //============= 节点 =============
    private PieInfoWrapper preWrapper;
    private PieInfoWrapper nextWrapper;

    PieInfoWrapper(IPieInfo pieInfo) {
        if (pieInfo == null) {
            throw new NullPointerException("pieinfo must not be null");
        }
        id = generateId();
        mPieInfo = pieInfo;
    }

    String getId() {
        return id;
    }


    PieInfoWrapper prepare(AnimatedPieViewConfig config) {
        hasCached = false;
        if (mDrawPaint == null) mDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if (mAlphaDrawPaint == null)
            mAlphaDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if (mTexPaint == null) mTexPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        if (mIconPaint == null) {
            mIconPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mIconPaint.setFilterBitmap(true);
        }
        if (mLinePath == null) mLinePath = new Path();
        if (mLinePathMeasure == null) mLinePathMeasure = new Path();

        mDrawPaint.setStyle(config.isStrokeMode() ? Paint.Style.STROKE : Paint.Style.FILL);
        mDrawPaint.setStrokeWidth(config.getStrokeWidth());
        mDrawPaint.setColor(mPieInfo.getColor());
        mAlphaDrawPaint.set(mDrawPaint);

        mTexPaint.setStyle(Paint.Style.FILL);
        mTexPaint.setTextSize(config.getTextSize());

        mLinePath.reset();
        return this;
    }

    public IPieInfo getPieInfo() {
        return mPieInfo;
    }

    public Paint getIconPaint() {
        return mIconPaint;
    }

    public Paint getDrawPaint() {
        return mDrawPaint;
    }

    public Paint getAlphaDrawPaint() {
        mAlphaDrawPaint.set(mDrawPaint);
        return mAlphaDrawPaint;
    }

    public Path getLinePath() {
        mLinePath.rewind();
        return mLinePath;
    }

    public Path getLinePathMeasure() {
        mLinePathMeasure.rewind();
        return mLinePathMeasure;
    }

    public float getFromAngle() {
        return fromAngle;
    }

    public float getMiddleAngle() {
        return fromAngle + sweepAngle / 2;
    }

    public float getToAngle() {
        return toAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public boolean isAutoDesc() {
        return autoDesc;
    }

    public void setAutoDesc(boolean autoDesc) {
        this.autoDesc = autoDesc;
    }

    public String getDesc() {
        return desc;
    }

    public PieOption getPieOption() {
        return mPieInfo.getPieOpeion();
    }

    public Bitmap getIcon(int textWidth, int textHeight) {
        if (textWidth == 0 || textHeight == 0) return null;
        if (icon != null) return icon;
        if (mPieInfo.getPieOpeion() == null || mPieInfo.getPieOpeion().getLabelIcon() == null)
            return null;
        Bitmap mIcon = mPieInfo.getPieOpeion().getLabelIcon();
        int iconWidth = mIcon.getWidth();
        int iconHeight = mIcon.getHeight();
        if (iconWidth > textWidth || iconHeight > textHeight) {
            Matrix matrix = new Matrix();
            float sX = 1.0f;
            float sY = 1.0f;
            if (iconWidth > textWidth) {
                sX = (float) textWidth / iconWidth;
            }
            if (iconHeight > textHeight) {
                sY = (float) textHeight / iconHeight;
            }
            float scale = Math.min(sX, sY);
            matrix.postScale(scale, scale);
            icon = Bitmap.createBitmap(mIcon, 0, 0, iconWidth, iconHeight, matrix, true);
        }
        return icon;
    }

    public float calculateDegree(float lastPieDegree, double sum, AnimatedPieViewConfig config) {
        fromAngle = lastPieDegree;
        sweepAngle = (float) (360f * (Math.abs(mPieInfo.getValue()) / sum));
        toAngle = fromAngle + sweepAngle;
        if (autoDesc) {
            //自动填充描述auto
            desc = String.format(config.getAutoDescStringFormat(), AnimatedPieViewConfig.sFormateRate.format((mPieInfo.getValue() / sum) * 100));
            if (mPieInfo instanceof SimplePieInfo) {
                ((SimplePieInfo) mPieInfo).setDesc(desc);
            }
        } else {
            desc = mPieInfo.getDesc();
        }
        PLog.d("【calculate】 " + "{ \n" + "id = " + id + "\nfromAngle = " + fromAngle + "\nsweepAngle = " + sweepAngle + "\ntoAngle = " + toAngle + "\n desc = " + desc + "\n  }");
        return toAngle;
    }

    public boolean isCached() {
        return hasCached;
    }

    public void setCached(boolean hasCached) {
        this.hasCached = hasCached;
    }

    public PieInfoWrapper getPreWrapper() {
        return preWrapper;
    }

    public void setPreWrapper(PieInfoWrapper preWrapper) {
        this.preWrapper = preWrapper;
    }

    public PieInfoWrapper getNextWrapper() {
        return nextWrapper;
    }

    public void setNextWrapper(PieInfoWrapper nextWrapper) {
        this.nextWrapper = nextWrapper;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PieInfoWrapper)) {
            return false;
        } else {
            PieInfoWrapper from = (PieInfoWrapper) obj;
            return obj == this || TextUtils.equals(from.getId(), id);
        }
    }

    //=============================================================generate id
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private String generateId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();

            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1;//Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return Integer.toString(result);
            }
        }
    }

    //=============================================================tools
    boolean contains(float angle) {
        return angle >= fromAngle && angle <= toAngle;
    }

    boolean containsTouch(float angle) {
        //所有点击的角度都需要收归到0~360的范围，兼容任意角度
        final float tAngle = DegreeUtil.limitDegreeInTo360(angle);
        float tStart = DegreeUtil.limitDegreeInTo360(fromAngle);
        float tEnd = DegreeUtil.limitDegreeInTo360(toAngle);
        PLog.d("containsTouch  >>  tStart： " + tStart + "   tEnd： " + tEnd + "   tAngle： " + tAngle);
        boolean result;
        if (tEnd < tStart) {
            if (tAngle > 180) {
                //已经过界
                result = tAngle >= tStart && (360 - tAngle) <= sweepAngle;
            } else {
                result = tAngle + 360 >= tStart && tAngle <= tEnd;
            }
        } else {
            result = tAngle >= tStart && tAngle <= tEnd;
        }
        if (result) {
            PLog.i("find touch point  >>  " + toString());
        }
        return result;
    }


    @Override
    public String toString() {
        return "{ \nid = " + id + '\n'
                + "value =  " + getPieInfo().getValue() + '\n'
                + "fromAngle = " + fromAngle + '\n'
                + "toAngle = " + toAngle + "\n  }";
    }
}
