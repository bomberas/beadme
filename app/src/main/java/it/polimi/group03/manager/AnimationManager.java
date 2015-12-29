package it.polimi.group03.manager;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

    public void SlideInFromLeft(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        image.startAnimation(animRotate);
    }

    public void SlideOutFromRight(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slideout_right);
        image.startAnimation(animRotate);
    }

    public void SlideOutFromLeft(Context context, ImageView image) {
        Animation animRotate = AnimationUtils.loadAnimation(context, R.anim.slideout_left);
        image.startAnimation(animRotate);
    }

}
