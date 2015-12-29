package it.polimi.group03.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.Log;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support changes over the <b>Sound</b> preference
 * according to the selected <i>theme</i>. It will be used across the whole application
 * (all the activities) to retrieve the current status of the playing audio (if enabled).<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class MusicManager {

    private static final String TAG = "MusicManager";

    private static MusicManager ourInstance = new MusicManager();
    private MediaPlayer media;
    private String theme;

    public static MusicManager getInstance() {
        return ourInstance;
    }

    private MusicManager() {
    }

    /**
     * Checks if the preference corresponding to the application <i>sound</i> is
     * enabled.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #start(Context)}<br/>
     * {@link #updateSoundPrefs(Context)}<br/>
     *
     * @param context Calling Activity
     * @return {@code isSoundOn} <tt>true</tt> if sound is enabled
     * <tt>false</tt> if not
     */
    public boolean isSoundOn(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isSoundOn = sharedPref.getBoolean(Constant.KEY_PREF_SOUND, Constant.PREF_SOUND_DEFAULT);

        Log.i(TAG, "isVolumeOn: " + String.valueOf(isSoundOn));
        return isSoundOn;
    }

    /**
     * If the application <i>sound</i> is enabled, the manager will start (or continue) playing the selected track
     * (in a loop) corresponding to the application <i>theme</i>.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #getSoundTrack(Context)}<br/>
     *
     * @param context Calling Activity
     */
    public void start(Context context) {
        if ( !isSoundOn(context) ) {
            Log.i(TAG, "The sound preference is off");
            return;
        }
        if ( media == null ) {
            Log.i(TAG, "There is no media detected on the application.");
            media = MediaPlayer.create(context, getSoundTrack(context));
        } else if ( !ThemeManager.getInstance().theme(context).equals(theme) ) { //in case the theme has been changed
            Log.i(TAG, "Changing media...");
            media.reset();
            media = MediaPlayer.create(context, getSoundTrack(context));
        }
        //Just to check if the theme was changed
        theme = ThemeManager.getInstance().theme(context);

        if ( !media.isPlaying() ) {
            media.start();
            media.setLooping(true);
        }
    }

    /**
     * If the application <i>sound</i> is enabled (and previously started and playing), the manager will
     * pause the current track.<br/><br/>
     */
    public void pause() {
        if ( media != null && media.isPlaying() ) {
            Log.i(TAG, "The track will be paused.");
            media.pause();
        }
    }

    /**
     * When a change is made within the {@link it.polimi.group03.activity.SettingsActivity}, the manager
     * will update the preferences related to the sound, to <tt>on</tt> or <tt>off</tt> and the selected
     * theme (if any change was made).
     *
     * @param context Calling Activity
     */
    public void updateSoundPrefs(Context context) {
        try {
            if ( !isSoundOn(context) ) {
                pause();
            } else {
                start(context);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * If <u>a change is made within the theme preference</u> or if <u>an activity is started</u>, the manager will retrieve
     * the corresponding track for the chosen theme.<br /><br />
     *
     * @param context Calling Activity
     * @return {@code id} of the soundtrack according to the theme selected.
     */
    private int getSoundTrack(Context context) {
        int soundtrack;

        switch ( ThemeManager.getInstance().theme(context) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                soundtrack = R.raw.pink;
                break;
            case Constant.PREF_THEME_STAR_WARS:
                soundtrack = R.raw.starwars;
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                soundtrack = R.raw.harrypotter;
                break;
        }
        return soundtrack;
    }

}