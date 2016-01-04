package it.polimi.group03.manager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.Random;

import it.polimi.group03.R;

/**
 * This class holds the logic to support the animations present during the game
 * according to the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 29/12/2015.
 */
public class AnimationManager {

    private static AnimationManager ourInstance = new AnimationManager();

    public static AnimationManager getInstance() {
        return ourInstance;
    }

    private AnimationManager() {
    }

    public void rotate(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
        image.startAnimation(animRotate);
    }

    public void bounce(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.bounce);
        image.startAnimation(animRotate);
    }

    public void zoom(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.zoom);
        image.startAnimation(animRotate);
    }

    public void slideInFromRight(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        image.startAnimation(animRotate);
    }

    public void slideInFromLeft(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        image.startAnimation(animRotate);
    }

    public void slideOutFromRight(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slideout_right);
        image.startAnimation(animRotate);
    }

    public void slideOutFromLeft(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slideout_left);
        image.startAnimation(animRotate);
    }

    public void fadeOut(ImageView image){
        image.animate().setDuration(500);
        image.animate().alpha(0);
    }

    public void fadeIn(ImageView image){
        image.animate().setDuration(500);
        image.animate().alpha(1);
    }

    public void move(ImageView image, String axis, int offset){
        ObjectAnimator cloudAnim = ObjectAnimator.ofFloat(image, axis, offset);
        cloudAnim.setDuration(10000);
        cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnim.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnim.start();
    }

    public void moveXY(int[] offsetX, int[] offsetY, ImageView ... items) {

        if (items != null) {

            ObjectAnimator[] animations = new ObjectAnimator[items.length *2];
            TimeInterpolator[] interpolators = {new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new DecelerateInterpolator()};
            int c = 0;
            long duration = 12000;

            for (int i = 0; i < items.length; ++i) {
                ObjectAnimator animX = ObjectAnimator.ofFloat(items[i], "x", offsetX[i]);
                animX.setRepeatCount(ValueAnimator.INFINITE);
                animX.setRepeatMode(ValueAnimator.REVERSE);
                //animX.setInterpolator(interpolators[new Random().nextInt(interpolators.length - 1)]);
                long delay = (new Random().nextInt((int)(duration/4000)) * 1000);
                animX.setStartDelay(delay);
                animations[c] = animX;
                c++;
                ObjectAnimator animY = ObjectAnimator.ofFloat(items[i], "y", offsetY[i]);
                animY.setRepeatCount(ValueAnimator.INFINITE);
                animY.setRepeatMode(ValueAnimator.REVERSE);
                //animY.setInterpolator(interpolators[new Random().nextInt(interpolators.length - 1)]);
                animX.setStartDelay(delay);
                animations[c] = animY;
                c++;
            }

            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.setDuration(duration);
            animSetXY.playTogether(animations);
            animSetXY.start();
        }
    }

    public void confetti(final Rect mDisplaySize, final float mScale, final ImageView aniView) {

        aniView.setPivotX(aniView.getWidth() / 2);
        aniView.setPivotY(aniView.getHeight() / 2);

        long delay = new Random().nextInt(3000);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(7000);
        animator.setInterpolator(new AccelerateInterpolator());
        //animator.setStartDelay(delay);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            int angle = 50 + (int) (Math.random() * 101);
            int movex = new Random().nextInt(mDisplaySize.right);

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) (animation.getAnimatedValue())).floatValue();

                aniView.setRotation(angle * value);
                aniView.setTranslationX((movex - 40) * value);
                aniView.setTranslationY((mDisplaySize.bottom + (150 * mScale)) * value);
            }
        });

        animator.start();

    }
}
