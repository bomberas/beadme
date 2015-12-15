package it.polimi.group03.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import it.polimi.group03.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        hide();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean onVibration = sharedPref.getBoolean(SettingsActivity.KEY_PREF_VIBRATION, true);
        Log.i("tati 123", String.valueOf(onVibration));
    }

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

}