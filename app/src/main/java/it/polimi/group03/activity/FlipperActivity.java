package it.polimi.group03.activity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

import it.polimi.group03.R;

/**
 * This class holds the common logic among activities that include a flipper.<br /><br />
 * <p>References</p>
 * {@link StatisticActivity}<br />
 * {@link CharactersActivity}<br />
 * {@link HelpActivity}<br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 22/12/2015.
 */
public class FlipperActivity extends GenericActivity {

    private ViewFlipper flipper;
    private float lastX;
    private int layouts;

    //Using the following method, we will handle all screen swaps.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = event.getX();
                // Handling left to right screen swap.
                if ( lastX < currentX ) {
                    moveToRight();
                }
                // Handling right to left screen swap.
                if ( lastX > currentX) {
                    moveToLeft();
                }
                break;
        }
        return false;
    }

    /**
     * Move the flipper to the left. Called from the arrow icons.
     */
    public void moveToLeft(View v) {
        moveToLeft();
    }

    /**
     * Move the flipper to the right. Called from the arrow icons.
     */
    public void moveToRight(View view) {
        moveToRight();
    }

    /**
     * Move the flipper to the left. Called from the onTouch event.
     */
    public void moveToLeft() {
        // Next screen comes in from right.
        flipper.setInAnimation(this, R.anim.slidein_right);
        // Current screen goes out from left.
        flipper.setOutAnimation(this, R.anim.slideout_left);

        // If there aren't any other children (to the left), jump to the end.
        if ( flipper.getDisplayedChild() == layouts ){
            flipper.setDisplayedChild(0);
            flipper.animate();
        } else {
            // Display next screen.
            flipper.showNext();
        }
    }

    /**
     * Move the flipper to the right. Called from the onTouch event.
     */
    public void moveToRight() {
        // Next screen comes in from left.
        flipper.setInAnimation(this, R.anim.slidein_left);
        // Current screen goes out from right.
        flipper.setOutAnimation(this, R.anim.slideout_right);

        // If there aren't any other children (to the right), jump to the beginning.
        if ( flipper.getDisplayedChild() == 0) {
            flipper.setDisplayedChild(layouts);
            flipper.animate();
        } else {
            // Display previous screen.
            flipper.showPrevious();
        }
    }

    public ViewFlipper getFlipper() {
        return flipper;
    }

    public void setFlipper(ViewFlipper flipper) {
        this.flipper = flipper;
    }

    public void setLayouts(int layouts) {
        this.layouts = layouts - 1;
    }
}
