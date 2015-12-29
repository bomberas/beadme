package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

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
                Intent intent = new Intent(getApplicationContext(), PlayBeadMeActivity.class);
                FrameLayout frame = findViewById(R.id.home_frame);
                intent.putExtra(Constant.HEIGHT, frame.getLayoutParams().height);
                intent.putExtra(Constant.WIDTH, frame.getLayoutParams().width);
                startActivity(intent);
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

    /**
     * Set the styles for the button: <tt>Play</tt>, <tt>Settings</tt> and <tt>Statistics</tt> and
     * the customizable welcoming text, according to the selected theme.
     */
    private void setButtonStyles(){
        getThemeManager().setDrawableButton(this, findViewById(R.id.btn_play));
        getThemeManager().setDrawableButton(this, findViewById(R.id.btn_statistics));
        getThemeManager().setDrawableButton(this, findViewById(R.id.btn_settings));
        getThemeManager().setTextHome(this, findViewById(R.id.txt_home));
    }

}
