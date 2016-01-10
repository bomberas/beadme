package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
        Log.i(TAG, "Choosing Characters");
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
        getAnimationManager().blink(this, findViewById(R.id.btn_first));
        getAnimationManager().zoomOut(this, findViewById(R.id.characters_players));
        getAnimationManager().zoomIn(this, findViewById(R.id.characters_players));

        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));

        if ( numberOfPlayers > 1) {
            ViewFlipper layout = (ViewFlipper)findViewById(R.id.viewFlipperCharacters);
            layout.removeViewAt(0);
        }
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
        animate();
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

        animate();
    }

    private void animate() {
        Button pickMe = (Button) ((LinearLayout) flipper.getCurrentView()).getChildAt(3);
        if ( pickMe.isEnabled() ) {
            getAnimationManager().blink(this, pickMe);
        }
    }

    public void pickPlayerCharacter(View v) {
        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));
        v.setEnabled(false);
        v.clearAnimation();
        ImageView character = (ImageView)((LinearLayout) v.getParent()).getChildAt(0);
        playActivityIntent.putExtra(Constant.PLAYER_NAME + (pickedCharacters - 1), character.getContentDescription());



        if ( numberOfPlayers == 1){ // This means we're playing against Mr. Roboto
            if ( character.getContentDescription().equals(Constant.MR_ROBOTO) ) {
                playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), R.drawable.img_app);
                playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), false);

                numberOfPlayers --;
            } else {
                playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), getThemeManager().getPlayerIcon(this, Integer.valueOf((String) character.getTag())));
                playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), true);

                playActivityIntent.putExtra(Constant.PLAYER_NAME + "1", Constant.MR_ROBOTO);
                playActivityIntent.putExtra(Constant.PLAYER_ICON + "1", R.drawable.img_app);
                playActivityIntent.putExtra(Constant.PLAYER_HUMAN + "1", false);
            }
        } else {
            playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), getThemeManager().getPlayerIcon(this, Integer.valueOf((String) character.getTag())));
            playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), true);
        }

        if ( numberOfPlayers == pickedCharacters ) {
            Log.i(TAG, "Starting Play Activity");
            FrameLayout frame = (FrameLayout)findViewById(R.id.characters_frame);
            playActivityIntent.putExtra(Constant.HEIGHT, frame.getHeight());
            playActivityIntent.putExtra(Constant.WIDTH, frame.getWidth());

            startActivity(playActivityIntent);
        } else {
            pickedCharacters++;
            ((TextView) findViewById(R.id.characters_players)).setText(String.valueOf(pickedCharacters));
            getAnimationManager().zoomOut(this, findViewById(R.id.characters_players));
            moveToLeft();
            getAnimationManager().zoomIn(this, findViewById(R.id.characters_players));
        }
    }

}