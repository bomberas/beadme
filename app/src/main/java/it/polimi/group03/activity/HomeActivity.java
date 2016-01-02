package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.polimi.group03.R;

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
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_home);
        createGenericListeners();
        getAnimationManager().slideInFromRight(this, findViewById(R.id.btn_play));
        getAnimationManager().slideInFromLeft(this, findViewById(R.id.btn_settings));
        getAnimationManager().slideInFromRight(this, findViewById(R.id.btn_statistics));
    }

    /**
     * Set the listeners for all the buttons present in this activity.
     */
    private void createGenericListeners() {
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Characters Activity");
                startActivity(new Intent(getApplicationContext(), CharactersActivity.class));
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
                getVibrationManager().vibrate(getApplicationContext());
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

}
