package it.polimi.group03.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

import it.polimi.group03.R;
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
 * <li>Bars configuration.</li>
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
        getThemeManager().setTheme(this);
        //Creating the fragment and setting-up the listener
        settingsFragment = new SettingsFragment();
        settingsFragment.setSharedPreferenceListener(getOnSharedPreferenceChangedListener());
        settingsFragment.setTextPreferenceListener(getOnPreferenceChangedListener());
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

    /**
     * Called when the bars configuration preference is changed, added, or removed. This will validate
     * that the input entered on the edit text of the dialog contains 14 characters with only
     * 1,2 or 0 or if is it empty, otherwise, the changes won't be saved.
     */
    public Preference.OnPreferenceChangeListener getOnPreferenceChangedListener() {
        return new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference pref, Object val) {
                return val.toString().length() == 0 || (val.toString().length() == 14 && val.toString().matches("^[0-2]+$"));
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
        getMusicManager().updateSoundPrefs(this);
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
        getNotificationManager().updateNotificationPrefs(this);
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
        getThemeManager().theme(this);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    /**
     * Updates the configuration for the bars, when a game against the AI is started,
     * however it's possible to start such game if this preference is empty, in that
     * case the configuration of the bars will be made randomly.
     */
    @SuppressWarnings("unused")
    protected void setBars() {
        String hBars = PreferenceManager.getDefaultSharedPreferences(this).getString(KEY_PREF_BARS, PREF_BARS_DEFAULT);
        Log.i(TAG, hBars);
        EditTextPreference bars = (EditTextPreference) settingsFragment.findPreference(KEY_PREF_BARS);
        bars.setSummary(bars.getText());
    }

    /**
     * This class is meant to support the settings fragment and insert it in the current activity
     * with all the configurations present in /res/xml/preferences.xml
     */
    public static class SettingsFragment extends PreferenceFragment {

        private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceListener;
        private Preference.OnPreferenceChangeListener textPreferenceListener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            //Setting-up initial values for summaries, otherwise it wont be present at the beginning, just when a change is made
            findPreference(KEY_PREF_PLAYERS).setSummary(((ListPreference) findPreference(KEY_PREF_PLAYERS)).getEntry());
            findPreference(KEY_PREF_THEMES).setSummary(((ListPreference) findPreference(KEY_PREF_THEMES)).getEntry());
            findPreference(KEY_PREF_BARS).setSummary(((EditTextPreference) findPreference(KEY_PREF_BARS)).getText());
            //Setting-up the listener for the edit text view and run validations against the input entered.
            findPreference(KEY_PREF_BARS).setOnPreferenceChangeListener(getTextPreferenceListener());
        }

        public void setSharedPreferenceListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            this.sharedPreferenceListener = listener;
        }

        public SharedPreferences.OnSharedPreferenceChangeListener getSharedPreferenceListener() {
            return sharedPreferenceListener;
        }

        public Preference.OnPreferenceChangeListener getTextPreferenceListener() {
            return textPreferenceListener;
        }

        public void setTextPreferenceListener(Preference.OnPreferenceChangeListener textPreferenceListener) {
            this.textPreferenceListener = textPreferenceListener;
        }

    }
}
