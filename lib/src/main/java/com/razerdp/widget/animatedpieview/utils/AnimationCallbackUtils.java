package com.razerdp.widget.animatedpieview.utils;

import android.animation.Animator;
import android.view.animation.Animation;

/**
 * Created by 大灯泡 on 2017/2/28.
 * <p>
 * 动画帮助类
 */

public class AnimationCallbackUtils {


    public static abstract class SimpleAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

    public static abstract class SimpleAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
