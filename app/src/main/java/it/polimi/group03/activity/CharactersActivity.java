package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support the Choosing Characters page
 * (before the Play activity) of the application, the look and feel
 * will depend on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 22/12/2015.
 */
public class CharactersActivity extends GenericActivity {

    private static final String TAG = "HelpActivity";
    private ViewFlipper flipper;
    private float lastX;
    private int pickedCharacters;
    private Intent playActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Accessing to Choosing Characters");
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_characters);
        flipper = (ViewFlipper) findViewById(R.id.viewFlipperCharacters);
        findViewById(R.id.btn_characters_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
        playActivityIntent = new Intent(getApplicationContext(), PlayBeadMeActivity.class);
        pickedCharacters = 1;
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

    public void pickPlayerCharacter(View v) {
        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));
        v.setEnabled(false);
        ImageView character = (ImageView)((LinearLayout) v.getParent()).getChildAt(0);
        Log.i("tatitati", String.valueOf(pickedCharacters));
        playActivityIntent.putExtra(Constant.PLAYER_NAME + (pickedCharacters - 1), character.getContentDescription());
        playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), getThemeManager().getPlayerIcon(this, Integer.valueOf((String) character.getTag())));

        if ( numberOfPlayers == pickedCharacters ) {
            Log.i(TAG, "Starting Play Activity");
            FrameLayout frame = (FrameLayout)findViewById(R.id.characters_frame);
            playActivityIntent.putExtra(Constant.HEIGHT, frame.getHeight());
            playActivityIntent.putExtra(Constant.WIDTH, frame.getWidth());
            startActivity(playActivityIntent);
        } else {
            pickedCharacters++;
            ((TextView) findViewById(R.id.characters_players)).setText(String.valueOf(pickedCharacters));
            getAnimationManager().bounce(this, findViewById(R.id.characters_players));
        }
    }
}