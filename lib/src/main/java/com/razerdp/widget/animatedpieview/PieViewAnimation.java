package com.razerdp.widget.animatedpieview;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.razerdp.widget.animatedpieview.exception.NoViewConfigException;

/**
 * Created by 大灯泡 on 2017/11/7.
 */

class PieViewAnimation extends Animation {
    private AnimatedPieViewConfig mViewConfig;
    private AnimationHandler mHandler;

    public PieViewAnimation(AnimatedPieViewConfig viewConfig) {
        mViewConfig = viewConfig;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (mViewConfig == null) {
            throw new NoViewConfigException("viewConfig为空");
        }
        Log.i("anima","time  >>  "+interpolatedTime);
        if (interpolatedTime >= 0.0f && interpolatedTime <= 1.0f) {
            float angle = 360 * interpolatedTime;
            angle = Math.max(1.0f, angle);
            PieInfoImpl info = mViewConfig.getHelper().findPieinfoWithAngle(angle);
            if (mHandler != null && info != null) {
                mHandler.onAnimationProcessing(angle, info);
            }
        }
    }

    public void bindAnimationHandler(AnimationHandler animationHandler) {
        this.mHandler = animationHandler;
    }

    public interface AnimationHandler {
        void onAnimationProcessing(float angle, @NonNull PieInfoImpl infoImpl);
    }
}
