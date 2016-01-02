package it.polimi.group03.manager;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
}
