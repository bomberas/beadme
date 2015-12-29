package it.polimi.group03.manager;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
}
