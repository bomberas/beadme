package it.polimi.group03.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.polimi.group03.R;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * This constant holds the key of the sound preference.
     */
    public static final String KEY_PREF_SOUND = "key_vibration";
    /**
     * This constant holds the key of the vibration preference.
     */
    public static final String KEY_PREF_VIBRATION = "key_vibration";
    /**
     * This constant holds the key of the notification preference.
     */
    public static final String KEY_PREF_NOTIFICATION = "key_notification";
    /**
     * This constant holds the key of the lock orientation preference.
     */
    public static final String KEY_PREF_ORIENTATION = "key_orientation";
    /**
     * This constant holds the key of the light preference.
     */
    public static final String KEY_PREF_LIGHT = "key_light";
    /**
     * This constant holds the key of the number of players preference.
     */
    public static final String KEY_PREF_PLAYERS = "key_players";
    /**
     * This constant holds the key of the themes preference.
     */
    public static final String KEY_PREF_THEMES = "key_themes";
    /**
     * This constant holds the key of the language preference.
     */
    public static final String KEY_PREF_LANGUAGE = "key_languages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hide();
        findViewById(R.id.btn_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

    }

/*
    private void setupWindowAnimations() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);

            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setReturnTransition(slide);
        }
    }
*/
    /**
     * This methods hides the action bar set it by default for the OS; in order to obtain
     * a full screen view.
     */
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * Called when a shared preference is changed, added, or removed. This may be called even if a preference is set to its
     * existing value. This callback will be run on your main thread.
     *
     * <p>Additionally, it will trigger the events to handle these changes i.e. when a preference is
     * changed, the main application will update the corresponding settings on fly.</p>
     *
     * @param {@code sharedPreferences} The SharedPreferences that received the change.
     * @param {@code key} The key of the preference that was changed, added, or removed.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ( key.equals(KEY_PREF_VIBRATION) ) {
            boolean onVibration = sharedPreferences.getBoolean(key, true);
            Log.i("tati 123", String.valueOf(onVibration));

        }
    }

    /**
     * This class is meant to support the fragment and insert it in the current activity
     * with all the configurations present in /res/xml/preferences.xml
     */
    public static class SettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}