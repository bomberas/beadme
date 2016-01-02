package it.polimi.group03.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

import it.polimi.group03.R;

/**
 * This class holds the logic to support the Help page of the application, the look and feel
 * will depend on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 22/12/2015.
 */
public class HelpActivity extends GenericActivity {

    private static final String TAG = "HelpActivity";
    private ViewFlipper flipper;
    private float lastX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Accessing to Help");
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_help);
        flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        findViewById(R.id.btn_help_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }

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

    public void moveToLeft(View v) {
        moveToLeft();
    }

    public void moveToRight(View view) {
        moveToRight();
    }

    private void moveToLeft() {
        // Next screen comes in from right.
        flipper.setInAnimation(this, R.anim.slidein_right);
        // Current screen goes out from left.
        flipper.setOutAnimation(this, R.anim.slideout_left);

        // If there aren't any other children (to the left), jump to the end.
        if ( flipper.getDisplayedChild() == 3 ){
            flipper.setDisplayedChild(0);
            flipper.animate();
        } else {
            // Display next screen.
            flipper.showNext();
        }
    }

    private void moveToRight() {
        // Next screen comes in from left.
        flipper.setInAnimation(this, R.anim.slidein_left);
        // Current screen goes out from right.
        flipper.setOutAnimation(this, R.anim.slideout_right);

        // If there aren't any other children (to the right), jump to the beginning.
        if ( flipper.getDisplayedChild() == 0) {
            flipper.setDisplayedChild(3);
            flipper.animate();
        } else {
            // Display previous screen.
            flipper.showPrevious();
        }
    }

}