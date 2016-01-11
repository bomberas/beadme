package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
public class CharactersActivity extends FlipperActivity {

    private static final String TAG = CharactersActivity.class.getSimpleName();
    private int pickedCharacters;
    private Intent playActivityIntent;
    private boolean isBrainPicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Choosing Characters");
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_characters);
        setFlipper((ViewFlipper) findViewById(R.id.viewFlipperCharacters));
        setLayouts(4);

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
        } else {
            isBrainPicked = false;
            setLayouts(5);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToLeft() {
        super.moveToLeft();
        animate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveToRight() {
        super.moveToRight();
        animate();
    }

    /**
     * Starts a blink animation for the button <b>Pick Me</b>.
     */
    private void animate() {
        Button pickMe = (Button) ((LinearLayout) getFlipper().getCurrentView()).getChildAt(3);
        if ( pickMe.isEnabled() ) {
            getAnimationManager().blink(this, pickMe);
        }
    }

    /**
     * This method will put extra information on the intent according to the
     * characters selected, like: name and icon. When all the players have chosen
     * their characters an intent will be fired up to start the play activity.
     *
      * @param v Button clicked
     */
    public void pickPlayerCharacter(View v) {
        int numberOfPlayers = Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getString(Constant.KEY_PREF_PLAYERS, Constant.PREF_PLAYER_DEFAULT));
        v.setEnabled(false);
        v.clearAnimation();
        ImageView character = (ImageView)((LinearLayout) v.getParent()).getChildAt(0);
        playActivityIntent.putExtra(Constant.PLAYER_NAME + (pickedCharacters - 1), character.getContentDescription());

        if ( numberOfPlayers == 1){ // This means we're playing against Mr. Roboto
            if ( character.getContentDescription().equals(Constant.MR_ROBOTO) ) {
                Log.i(TAG, "Mr Roboto was picked");
                playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), R.drawable.img_app);
                playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), false);

                isBrainPicked = true;

            } else {
                playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), getThemeManager().getPlayerIcon(this, Integer.valueOf((String) character.getTag())));
                playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), true);

                if ( !isBrainPicked ) {
                    pickedCharacters++;
                    playActivityIntent.putExtra(Constant.PLAYER_NAME + "1", Constant.MR_ROBOTO);
                    playActivityIntent.putExtra(Constant.PLAYER_ICON + "1", R.drawable.img_app);
                    playActivityIntent.putExtra(Constant.PLAYER_HUMAN + "1", false);
                }
            }
        } else {
            playActivityIntent.putExtra(Constant.PLAYER_ICON + (pickedCharacters - 1), getThemeManager().getPlayerIcon(this, Integer.valueOf((String) character.getTag())));
            playActivityIntent.putExtra(Constant.PLAYER_HUMAN + (pickedCharacters - 1), true);
        }

        if ( (numberOfPlayers == 1 && numberOfPlayers + 1 == pickedCharacters) || (numberOfPlayers > 1 && numberOfPlayers == pickedCharacters)) {
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