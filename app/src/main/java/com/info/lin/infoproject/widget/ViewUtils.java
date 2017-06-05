package com.info.lin.infoproject.widget;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;


/**
 * Created by sunjianfei on 15-9-1.
 * description:view的一些操作工具类，比如防止连续快速点击同一个view两次
 *
 * @author crab
 */
public class ViewUtils {
    private static final int ANIMATION_DURATION = 200;
    private static final LinearInterpolator sAnimationInterpolator = new LinearInterpolator();

    private ViewUtils() {

    }

    /**
     * 设置view多少毫秒后可以再次点击
     *
     * @param v
     * @param delayMillis
     */
    public static void setDelayedClickable(final View v, int delayMillis) {
        v.setClickable(false);
        setDelayedClickable(v, true, delayMillis);
    }

    /**
     * 设置view多少毫秒后可以再次点击
     *
     * @param v
     * @param delayMillis
     */
    public static void setDelayedEnable(final View v, int delayMillis) {
        v.setEnabled(false);
        setDelayedClickable(v, true, delayMillis);
    }



    private static void setDelayedClickable(final View v, final boolean clickable, int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setClickable(clickable);
                v.setEnabled(true);
            }
        }, delayMillis);
    }

    public static void showViewFromBottom(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        int height = view.getHeight();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, height, Animation.ABSOLUTE, 0);
        translateAnimation.setDuration(ANIMATION_DURATION);
        translateAnimation.setInterpolator(sAnimationInterpolator);
        view.startAnimation(translateAnimation);
    }

    public static void hideViewFromBottom(final View view) {
        if (view.getVisibility() == View.INVISIBLE) {
            return;
        }
        int height = view.getHeight();
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, height);
        translateAnimation.setDuration(ANIMATION_DURATION);
        translateAnimation.setInterpolator(sAnimationInterpolator);
        translateAnimation.setAnimationListener(new AnimationAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        view.startAnimation(translateAnimation);
    }

    private static class AnimationAdapter implements Animation.AnimationListener {

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
