package it.polimi.group03.manager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
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

    private static final String TAG = AnimationManager.class.getSimpleName();
    private static AnimationManager ourInstance = new AnimationManager();

    public static AnimationManager getInstance() {
        return ourInstance;
    }

    private AnimationManager() {
    }

    public void rotate(Context context, View image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
        image.startAnimation(animRotate);
    }

    public void bounce(Context context, View image) {
        Animation bounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        image.startAnimation(bounce);
    }

    public void zoomIn(Context context, View view) {
        Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        view.startAnimation(zoomIn);
    }

    public void zoomOut(Context context, View view) {
        Animation zoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        view.startAnimation(zoomOut);
    }

    public void slideInFromRight(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        view.startAnimation(slide);
    }

    public void slideInFromLeft(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        view.startAnimation(slide);
    }

    public void slideOutFromRight(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slideout_right);
        view.startAnimation(slide);
    }

    public void slideOutFromLeft(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slideout_left);
        view.startAnimation(slide);
    }

    public void blink(Context context, View view) {
        Animation blink = AnimationUtils.loadAnimation(context, R.anim.blink);
        view.startAnimation(blink);
    }

    public void moveUp(Context context, View view) {
        Animation moveUp = AnimationUtils.loadAnimation(context, R.anim.move_up);
        view.startAnimation(moveUp);
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

    public void moveXY(boolean accelerate, boolean reverse, int[] offsetX, int[] offsetY, ImageView ... items) {

        if (items != null) {

            ObjectAnimator[] animations = new ObjectAnimator[items.length *2];
            TimeInterpolator[] interpolators = {new AccelerateDecelerateInterpolator(), new AccelerateInterpolator(), new DecelerateInterpolator()};
            int c = 0;

            for (int i = 0; i < items.length; ++i) {
                Log.i(TAG, "moving [" + i + "] x = " + offsetX[i] + " y = " + offsetY[i]);
                ObjectAnimator animX = ObjectAnimator.ofFloat(items[i], "x", offsetX[i]);
                animX.setRepeatCount(ValueAnimator.INFINITE);
                animX.setRepeatMode(reverse ? ValueAnimator.REVERSE : ValueAnimator.RESTART);
                if ( accelerate ) animX.setInterpolator(new AccelerateInterpolator());
                //animX.setStartDelay(new Random().nextInt(3) * 1000);
                animations[c] = animX;
                c++;
                ObjectAnimator animY = ObjectAnimator.ofFloat(items[i], "y", offsetY[i]);
                animY.setRepeatCount(ValueAnimator.INFINITE);
                animY.setRepeatMode(reverse ? ValueAnimator.REVERSE : ValueAnimator.RESTART);
                if ( accelerate ) animY.setInterpolator(new AccelerateInterpolator());
                //animX.setStartDelay(new Random().nextInt(3) * 1000);
                animations[c] = animY;
                c++;
            }

            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.setDuration(12000);

            animSetXY.playTogether(animations);
            animSetXY.start();
        }
    }

    public void confetti(final Rect mDisplaySize, final float mScale, final ImageView aniView) {

        aniView.setPivotX(aniView.getWidth() / 2);
        aniView.setPivotY(aniView.getHeight() / 2);

        final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(5000);
        animator.setInterpolator(new AccelerateInterpolator());

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
