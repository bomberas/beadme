package it.polimi.group03.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import it.polimi.group03.R;
import it.polimi.group03.manager.AnimationManager;
import it.polimi.group03.manager.MusicManager;
import it.polimi.group03.manager.ThemeManager;
import it.polimi.group03.manager.VibrationManager;
import it.polimi.group03.manager.NotificationManager;

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

    private String TAG = GenericActivity.class.getSimpleName();
    private boolean keepMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keepMusicOn = true;
        hideBars();
    }

    @Override
    protected void  onPause() {
        super.onPause();
        if ( !keepMusicOn ) {
            getMusicManager().pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        keepMusicOn = false;
        getMusicManager().start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.w(TAG, "I'm destroying this activity");

        View rootView = null;

        try {
            rootView = ((ViewGroup) findViewById(android.R.id.content))
                    .getChildAt(0);
        } catch (Exception e) {
            Log.w(TAG, "Cannot find root view to call unbindDrawables on");
        }

        if (rootView != null) {
            Log.i(TAG, "Calling unbindDrawables");
            unbindDrawables(rootView);
        }
    }

    public ThemeManager getThemeManager() {
        return ThemeManager.getInstance();
    }

    public MusicManager getMusicManager() {
        return MusicManager.getInstance();
    }

    public NotificationManager getNotificationManager() {
        return NotificationManager.getInstance();
    }

    public VibrationManager getVibrationManager() {
        return VibrationManager.getInstance();
    }

    public AnimationManager getAnimationManager() {
        return AnimationManager.getInstance();
    }

    /**
     * Utility method to unbind drawables when an activity is destroyed.  This
     * ensures the drawables can be garbage collected.
     */
    public void unbindDrawables(View view) {

        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }

        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageBitmap(null);
        } else if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }

            try {
                // AdapterView objects do not support the removeAllViews method
                if (!(view instanceof AdapterView)) {
                    ((ViewGroup) view).removeAllViews();
                }
            } catch (Exception e) {
                Log.w(TAG, "Ignore Exception in unbindDrawables", e);
            }
        }
    }

    /**
     * This methods hides the action bar set it by default for the OS; in order to obtain
     * a full screen view.
     */
    public void hideBars() {
        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null ) {
            actionBar.hide();
        }
    }
}
