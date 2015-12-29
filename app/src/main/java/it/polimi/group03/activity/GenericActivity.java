package it.polimi.group03.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import it.polimi.group03.manager.MusicManager;

/**
 * This class holds the common logic among activities.<br /><br />
 * <p>References</p>
 * {@link HomeActivity}<br />
 * {@link SettingsActivity}<br />
 * {@link AboutActivity}<br />
 * {@link HelpActivity}<br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 22/12/2015.
 */
public class GenericActivity extends AppCompatActivity {

    private boolean keepMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepMusicOn = true;
        hideBars();
    }

    /**
     * This methods hides the action bar set it by default for the OS; in order to obtain
     * a full screen view.
     */
    private void hideBars() {
        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null ) {
            actionBar.hide();
        }
    }

    @Override
    protected void  onPause() {
        super.onPause();
        if ( !keepMusicOn ) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        keepMusicOn = false;
        MusicManager.start(this);
    }

}
