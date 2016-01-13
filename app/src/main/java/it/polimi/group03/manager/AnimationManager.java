package it.polimi.group03.manager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Random;

import it.polimi.group03.R;

/**
 * This class holds the logic to support the animations used in the game
 * according to the selected <i>theme</i>.<br /><br />
 *
 * @see ThemeManager
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

    /**
     *
     * Rotates the given view 360° to the right.
     *
     * @param context Calling activity
     * @param view view to be rotated
     */
    public void rotate(Context context, View view) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
        view.startAnimation(animRotate);
    }

    /**
     *
     * Makes the given view bounce one time.
     *
     * @param context Calling activity
     * @param view view to bounced
     */
    public void bounce(Context context, View view) {
        Animation bounce = AnimationUtils.loadAnimation(context, R.anim.bounce);
        view.startAnimation(bounce);
    }

    /**
     *
     * Makes the effect of zooming in the given view.
     *
     * @param context Calling activity
     * @param view view to be zoomed in
     */
    public void zoomIn(Context context, View view) {
        Animation zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
        view.startAnimation(zoomIn);
    }

    /**
     *
     * Makes the effect of zooming out the given view.
     *
     * @param context Calling activity
     * @param view view to be zoomed out
     */
    public void zoomOut(Context context, View view) {
        Animation zoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
        view.startAnimation(zoomOut);
    }

    /**
     *
     * Makes the given view slide from the right to an inner position.
     *
     * @param context Calling activity
     * @param view view to slide
     */
    public void slideInFromRight(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        view.startAnimation(slide);
    }

    /**
     *
     * Makes the given view slide from the left to an inner position.
     *
     * @param context Calling activity
     * @param view view to slide
     */
    public void slideInFromLeft(Context context, View view) {
        Animation slide = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        view.startAnimation(slide);
    }

    /**
     *
     * Makes the given view blink continuously.
     *
     * @param context Calling activity
     * @param view view to blink
     */
    public void blink(Context context, View view) {
        Animation blink = AnimationUtils.loadAnimation(context, R.anim.blink);
        view.startAnimation(blink);
    }

    /**
     *
     * Fades out the given view.
     *
     * @param view view to be faded out
     */
    public void fadeOut(View view){
        view.animate().setDuration(500);
        view.animate().alpha(0);
    }

    /**
     *
     * Fades in the given view.
     *
     * @param view view to be faded out
     */
    public void fadeIn(View view){
        view.animate().setDuration(500);
        view.animate().alpha(1);
    }

    /**
     *
     * Moves the given view across the axis specified with the parameter {@code axis}. It displaces the view
     * the length specified with {@code offset}
     *
     * @param view view to move
     * @param axis property to animate
     * @param offset length to move the view
     */
    public void move(View view, String axis, int offset){
        ObjectAnimator cloudAnim = ObjectAnimator.ofFloat(view, axis, offset);
        cloudAnim.setDuration(10000);
        cloudAnim.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnim.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnim.start();
    }

    /**
     *
     * Moves a set of views both in x and y axis the length specified with {@code offsetX} y {@code offsetY}
     *
     * @param accelerate {@code true} if the animation interpolator will be set as {@link AccelerateInterpolator}
     *                          {@code false} if no interpolator needs to be set.
     * @param reverse {@code true} if the animation mode will be set to {@link ValueAnimator#INFINITE},{@code false} if the animation mode
     *                              will be set to {@link ValueAnimator#RESTART}.
     * @param offsetX List of length to be move on the x axis.
     * @param offsetY List of length to be move on the y axis.
     * @param items List of view to animate
     */
    public void moveXY(boolean accelerate, boolean reverse, int[] offsetX, int[] offsetY, ImageView ... items) {

        if (items != null) {

            ObjectAnimator[] animations = new ObjectAnimator[items.length *2];
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

    /**
     *
     * Animates an image view in such a way that looks that is slowly falling from the top of the screen
     * as a confetti.
     *
     * @param mDisplaySize Rect to be used
     * @param mScale scale to be used with the images
     * @param aniView view to animate
     */
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
