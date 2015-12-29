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
        getThemeManager().setHelpText(this, findViewById(R.id.txt_help_intro_desc));
        findViewById(R.id.btn_help_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }

    /**
     * Using the following method, we will handle all screen swaps.
     *
     * @param event
     * @return
     */
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
                    // If there aren't any other children, just break.
                    if ( flipper.getDisplayedChild() == 0 ) {
                        break;
                    }
                    // Next screen comes in from left.
                    flipper.setInAnimation(this, R.anim.slidein_left);
                    // Current screen goes out from right.
                    flipper.setOutAnimation(this, R.anim.slideout_right);
                    // Display next screen.
                    flipper.showNext();
                }
                // Handling right to left screen swap.
                if ( lastX > currentX ) {
                    // If there is a child (to the left), kust break.
                    if ( flipper.getDisplayedChild() == 1 ){
                        break;
                    }
                    // Next screen comes in from right.
                    flipper.setInAnimation(this, R.anim.slidein_right);
                    // Current screen goes out from left.
                    flipper.setOutAnimation(this, R.anim.slideout_left);
                    // Display previous screen.
                    flipper.showPrevious();
                }
                break;
        }
        return false;
    }

}