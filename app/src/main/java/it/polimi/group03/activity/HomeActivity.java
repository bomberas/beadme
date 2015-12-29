package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.polimi.group03.R;
import it.polimi.group03.manager.ThemeManager;
import it.polimi.group03.manager.VibrationManager;

/**
 * This class holds the logic to support the Home page of the application, the look and feel
 * will depend on the selected <i>theme</i>.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class HomeActivity extends GenericActivity {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeManager.setTheme(this);
        setContentView(R.layout.activity_home);
        setButtonStyles();
        createGenericListeners();
    }

    /**
     * Set the listeners for all the buttons present in this activity.
     */
    private void createGenericListeners() {
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Play Activity");
                startActivity(new Intent(getApplicationContext(), PlayBeadMeActivity.class));
            }
        });
        findViewById(R.id.btn_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Settings Activity");
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        });
        findViewById(R.id.btn_statistics).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Statistics Activity");
                VibrationManager.vibrate(getApplicationContext());
            }
        });
        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Help Activity");
                startActivity(new Intent(getApplicationContext(), HelpActivity.class));
            }
        });
        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting About Activity");
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
            }
        });
    }

    /**
     * Set the styles for the button: <tt>Play</tt>, <tt>Settings</tt> and <tt>Statistics</tt> and
     * the customizable welcoming text, according to the selected theme.
     */
    private void setButtonStyles(){
        ThemeManager.setDrawableButton(this, findViewById(R.id.btn_play));
        ThemeManager.setDrawableButton(this, findViewById(R.id.btn_statistics));
        ThemeManager.setDrawableButton(this, findViewById(R.id.btn_settings));
        ThemeManager.setTextHome(this, findViewById(R.id.txt_home));
    }

}
