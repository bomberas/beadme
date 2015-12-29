package it.polimi.group03.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

import it.polimi.group03.R;
import it.polimi.group03.manager.MusicManager;
import it.polimi.group03.manager.NotificationManager;
import it.polimi.group03.manager.ThemeManager;
import it.polimi.group03.util.Preferences;

import static it.polimi.group03.util.Constant.*;

/**
 * This class holds the logic to support the Settings page of the application, the look and feel
 * will depend on the selected <i>theme</i>. This activity includes a fragment for the preference
 * screen (now within a fragment instead of the regular PreferenceActivity).<br />
 *
 * <p>The following options are available:
 *
 * <ul style="list-style-type:circle">
 * <li>Sound.</li>
 * <li>Vibration.</li>
 * <li>Notification.</li>
 * <li>Light.</li>
 * <li>Number of Players.</li>
 * <li>Theme.</li>
 * <li>Language.</li>
 * </ul>
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class SettingsActivity extends GenericActivity {

    private static final String TAG = "SettingsActivity";
    /**
     * Fragment created on fly to hold all the preferences of the application.
     */
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        //Creating the fragment and setting-up the listener
        settingsFragment = new SettingsFragment();
        settingsFragment.setSharedPreferenceListener(getOnSharedPreferenceChangedListener());
        // Display the fragment as part of the content.
        getFragmentManager().beginTransaction().replace(R.id.preferences, settingsFragment).commit();
        //Setting-up default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.btn_home).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }

    /**
     * Called when a shared preference is changed, added, or removed. This may be called even if a preference is set to its
     * existing value. This callback will be run on the main thread.
     *
     * <p>Additionally, it will trigger the events to handle these changes i.e. when a preference is
     * changed, the main application will update the corresponding settings on fly.</p>
     */
    public SharedPreferences.OnSharedPreferenceChangeListener getOnSharedPreferenceChangedListener() {
        final SettingsActivity thisActivity = this;
        return new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                try {
                    Preferences preference = Preferences.getPreference(key);
                    Log.i(TAG, "Accessing: " + preference.name());
                    Method method = thisActivity.getClass().getDeclaredMethod(preference.getMethod());
                    //invoking specific method for the selected preference by reflection
                    method.invoke(thisActivity);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Unregister listener
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(settingsFragment.getSharedPreferenceListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register listener
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(settingsFragment.getSharedPreferenceListener());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /**
     * Updates the application sound effects, changed through the settings fragment inserted in this activity.
     * <code>true</code> if sound effects are set to on.
     * <code>false</code> if not.
     */
    @SuppressWarnings("unused")
    protected void setSound() {
        boolean isSoundOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_PREF_SOUND, PREF_SOUND_DEFAULT);
        Log.i(TAG, String.valueOf(isSoundOn));
        MusicManager.updateSoundPrefs(this);
    }

    /**
     * Updates the application vibration effect, changed through the settings fragment inserted in this activity.
     * <code>true</code> if vibration effects are set to on.
     * <code>false</code> if not.
     */
    @SuppressWarnings("unused")
    protected void setVibration() {
        boolean isVibrationOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_PREF_VIBRATION, PREF_VIBRATION_DEFAULT);
        Log.i(TAG, String.valueOf(isVibrationOn));
        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(isVibrationOn ? AudioManager.RINGER_MODE_VIBRATE : AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * Updates the application notifications, changed through the settings fragment inserted in this activity.
     * <code>true</code> if notifications are set to on.
     * <code>false</code> if not.
     */
    @SuppressWarnings("unused")
    protected void setNotification() {
        boolean isNotificationOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_PREF_NOTIFICATION, PREF_NOTIFICATION_DEFAULT);
        Log.i(TAG, String.valueOf(isNotificationOn));
        NotificationManager.updateNotificationPrefs(this);
    }

    /**
     * Updates the application light, changed through the settings fragment inserted in this activity.
     * <code>true</code> if light effects are set to on.
     * <code>false</code> if not.
     */
    @SuppressWarnings("unused")
    protected void setLight() {
        boolean isLightOn = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEY_PREF_LIGHT, PREF_LIGHT_DEFAULT);
        Log.i(TAG, String.valueOf(isLightOn));
    }

    /**
     * Updates the number of players for the next game, changed through the settings fragment inserted in this activity.
     */
    @SuppressWarnings("unused")
    protected void setNumberOfPlayers() {
        String numberOfPlayers = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_PREF_PLAYERS, PREF_PLAYER_DEFAULT);
        Log.i(TAG, numberOfPlayers);
        ListPreference preference = (ListPreference) settingsFragment.findPreference(KEY_PREF_PLAYERS);
        preference.setSummary(preference.getEntry());
    }

    /**
     * Updates the application theme, changed through the settings fragment inserted in this activity.
     * Selected theme related with every or almost every UI aspect of the game i.e.
     * when selecting "Star Wars" or "Dr. Who" not only colors, icons but sound effects
     * according to the theme will be set. CHEWBACCA=Star Wars theme, WHO= Dr. Who theme
     * EXPELLIARMUS= Harry Potter theme, etc.
     */
    @SuppressWarnings("unused")
    protected void setTheme() {
        String theme = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_PREF_THEMES, PREF_THEME_DEFAULT);
        Log.i(TAG, theme);
        ListPreference preference = (ListPreference) settingsFragment.findPreference(KEY_PREF_THEMES);
        preference.setSummary(preference.getEntry());
        ThemeManager.updateTheme(this, findViewById(R.id.settings_frame), (FloatingActionButton) findViewById(R.id.btn_home));
    }

    /**
     * Updates the application language, changed through the settings fragment inserted in this activity.
     * Predefined language. EN=English, ES=Spanish, SQ=Albanian, IT=Italian
     */
    @SuppressWarnings("unused")
    protected void setLanguage() {
        String language = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_PREF_LANGUAGE, PREF_LANGUAGE_DEFAULT);
        Log.i(TAG, language);
        ListPreference preference = (ListPreference) settingsFragment.findPreference(KEY_PREF_LANGUAGE);
        preference.setSummary(preference.getEntry());

    }

    /**
     * This class is meant to support the settings fragment and insert it in the current activity
     * with all the configurations present in /res/xml/preferences.xml
     */
    public static class SettingsFragment extends PreferenceFragment {

        private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            //Setting-up initial values for summaries, otherwise it wont be present at the beginning, just when a change is made
            findPreference(KEY_PREF_PLAYERS).setSummary(((ListPreference) findPreference(KEY_PREF_PLAYERS)).getEntry());
            findPreference(KEY_PREF_THEMES).setSummary(((ListPreference) findPreference(KEY_PREF_THEMES)).getEntry());
            findPreference(KEY_PREF_LANGUAGE).setSummary(((ListPreference) findPreference(KEY_PREF_LANGUAGE)).getEntry());
        }

        /*@Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(sharedPreferenceListener);
        }
        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(sharedPreferenceListener);
        }*/

        public void setSharedPreferenceListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            this.sharedPreferenceListener = listener;
        }

        public SharedPreferences.OnSharedPreferenceChangeListener getSharedPreferenceListener() {
            return sharedPreferenceListener;
        }
    }

}