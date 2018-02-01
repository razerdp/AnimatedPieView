package com.razerdp.widget.animatedpieview.render;

import android.graphics.Canvas;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;

import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.IPieView;
import com.razerdp.widget.animatedpieview.data.IPieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 大灯泡 on 2018/2/1.
 */
public class PieChartRender extends BaseRender implements ITouchRender {

    private List<PieInfoWrapper> mDataWrappers;

    public PieChartRender(IPieView iPieView) {
        super(iPieView);
        mDataWrappers = new ArrayList<>();
    }

    @Override
    public void reset() {
        mDataWrappers = mDataWrappers == null ? new ArrayList<PieInfoWrapper>() : mDataWrappers;
        mDataWrappers.clear();
    }

    @Override
    public boolean onPrepare() {
        AnimatedPieViewConfig config = mIPieView.getConfig();
        if (config == null) {
            Log.e(TAG, "onPrepare: config is null,abort draw because of preparing failed");
            return false;
        }
        //wrap datas and calculate sum value
        //包裹数据并且计算总和
        double sum = 0;
        for (Pair<IPieInfo, Boolean> info : config.getDatas()) {
            sum += Math.abs(info.first.getValue());
            PieInfoWrapper wrapper = new PieInfoWrapper(info.first);
            wrapper.setAutoDesc(info.second);
            mDataWrappers.add(wrapper);
        }

        //calculate degree for each pieInfoWrapper
        //计算每个wrapper的角度
        float lastDegree = config.getStartDegree();
        for (PieInfoWrapper dataWrapper : mDataWrappers) {
            dataWrapper.prepare(config);
            lastDegree = dataWrapper.calculateDegree(lastDegree, sum, config);
        }

        return true;
    }

    @Override
    public void onSizeChanged(int width, int height, int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {

    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void forceAbortTouch() {

    }
}
