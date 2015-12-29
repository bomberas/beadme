package it.polimi.group03.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support changes over the <b>Vibration</b> preference.
 * It will be used across the whole application (all the activities) to retrieve a
 * vibrator if vibration is on.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class VibrationManager {

    private static final String TAG = "VibrationManager";

    /**
     * Retrieves the preference corresponding to the application <i>vibration</i>.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #vibrate(Context)}<br/>
     *
     * @param context Calling Activity
     * @return {@code isSoundOn} <tt>true</tt> if vibration is enabled
     * <tt>false</tt> if not
     */
    public static boolean isVibrationOn(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isVibrationOn = sharedPref.getBoolean(Constant.KEY_PREF_VIBRATION, Constant.PREF_VIBRATION_DEFAULT);

        Log.i(TAG, "isVibrationOn: " + String.valueOf(isVibrationOn));
        return isVibrationOn;
    }

    /**
     * Get the vibrator service and vibrate if the <tt>vibration</tt> preference
     * is on.
     *
     * @param context Calling Activity
     */
    public static void vibrate(Context context) {
        if ( isVibrationOn(context) ) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
        } else {
            Toast.makeText(context, "Vibration effects are disabled", Toast.LENGTH_SHORT).show();
        }
    }

}