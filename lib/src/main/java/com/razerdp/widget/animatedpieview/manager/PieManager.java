package com.razerdp.widget.animatedpieview.manager;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;

import com.razerdp.widget.animatedpieview.IPieView;
import com.razerdp.widget.animatedpieview.render.BaseRender;
import com.razerdp.widget.animatedpieview.utils.PLog;
import com.razerdp.widget.animatedpieview.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by 大灯泡 on 2018/2/1.
 * <p>
 * <h3>CN:</h3>pieview的管理类，主要负责内容区域的管理
 * <p>
 * <h3>EN:</h3>manager the content area and some tool methods
 */
public class PieManager {

    private IPieView pieView;
    private List<BaseRender> mRenders;

    private RectF drawBounds;
    private Paint textMeasurePaint;
    private Rect textBounds;


    public PieManager(IPieView pieView) {
        this.pieView = pieView;
        mRenders = new ArrayList<>();
        drawBounds = new RectF();
        textBounds = new Rect();
        textMeasurePaint = new TextPaint();
        textMeasurePaint.setStyle(Paint.Style.FILL);
    }


    public void setChartContentRect(int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        PLog.i(String.format(Locale.getDefault(),
                "size change : { \n width = %s;\n height = %s;\n paddingLeft = %s;\n padding top = %s;\n paddingRight = %s;\n paddingBottom = %s;\n}",
                width, height, paddingLeft, paddingTop, paddingRight, paddingBottom));
        drawBounds.set(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        for (BaseRender baseRender : mRenders) {
            baseRender.onSizeChanged(width, height, paddingLeft, paddingTop, paddingRight, paddingBottom);
        }
    }

    public float getDrawWidth() {
        return drawBounds.width();
    }

    public float getDrawHeight() {
        return drawBounds.height();
    }

    public RectF getDrawBounds() {
        return drawBounds;
    }

    public Rect measureTextBounds(String text, int textSize) {
        if (TextUtils.isEmpty(text)) {
            textBounds.setEmpty();
            return textBounds;
        }
        textMeasurePaint.setTextSize(textSize);
        textMeasurePaint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds;
    }

    public Rect measureTextBounds(String text, Paint paint) {
        if (TextUtils.isEmpty(text) || paint == null) {
            textBounds.setEmpty();
            return textBounds;
        }
        paint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds;
    }


    //-----------------------------------------render observer-----------------------------------------
    public void registerRender(BaseRender render) {
        if (render == null) return;
        if (!mRenders.contains(render)) {
            mRenders.add(render);
        }
    }

    public void unRegisterRender(BaseRender render) {
        if (Util.isListEmpty(mRenders) || !mRenders.contains(render)) return;
        mRenders.remove(render);
    }
}
