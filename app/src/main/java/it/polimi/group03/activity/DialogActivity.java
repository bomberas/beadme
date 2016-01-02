package it.polimi.group03.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import it.polimi.group03.R;

/**
 * This class holds the logic to support the action call when a player
 * is trying to leave the play activity while playing.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 28/12/2015.
 */
public class DialogActivity extends GenericActivity {

    private static final String TAG = "DialogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getThemeManager().setTheme(this);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Starting Home Activity");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    /**
     * This methods hides the action bar set it by default for the OS; in order to obtain
     * a full screen view.
     */
    @Override
    public void hideBars() {

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "Skipping onBackPressed...");
    }

}