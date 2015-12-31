package it.polimi.group03.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support changes over the <b>Theme</b> preference. It will be used across the whole application
 * (all the activities) to retrieve the current theme, and all extra information
 * related to it. Since some components (like PreferencesFragment) can not use the theme of the application, or in some cases,
 * some texts should be set up per theme, the manager will set the info programmatically.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class ThemeManager {

    private static final String TAG = "ThemeManager";

    private static ThemeManager ourInstance = new ThemeManager();

    public static ThemeManager getInstance() {
        return ourInstance;
    }

    private ThemeManager() {
    }

    /**
     * Retrieves the preference corresponding to the application <i>theme</i> by default <tt>PINK</tt>.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #setTheme(Activity)}<br/>
     * {@link #updateTheme(Activity, View, FloatingActionButton)}<br/>
     * {@link #setPreferenceStyle(Context, TextView, TextView, ImageView, int)}<br/>
     * {@link #setDrawableButton(Context, View)}<br/>
     *
     * @param context Calling Activity
     * @return {@code theme} <tt>PINK</tt> for "On wednesdays we wear PINK",
     * <tt>CHEWBACCA</tt> for "Star Wars" or <tt>EXPELLIARMUS</tt> for "Harry Potter".
     */
    public String theme(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPref.getString(Constant.KEY_PREF_THEMES, Constant.PREF_THEME_DEFAULT);

        Log.i(TAG, "Theme selected: " + String.valueOf(theme));
        return theme;
    }

    /**
     * Set the theme for the current activity. Should be called in the Activity.onCreate method before setting the views.
     *
     * @param activity Calling Activity
     */
    public void setTheme(Activity activity) {
        switch ( theme(activity) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                activity.setTheme(R.style.PINK);
                break;
            case Constant.PREF_THEME_STAR_WARS:
                activity.setTheme(R.style.CHEWBACCA);
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                activity.setTheme(R.style.EXPELLIARMUS);
                break;
        }
    }

    /**
     * When a change is made this manager will set the new theme, since the current activity
     * {@link it.polimi.group03.activity.SettingsActivity} was already started, the manager will
     * update the background, buttons, music and icons of the activity, this changes will be made to avoid restarting (calling
     * first the finish method of the activity and then start this activity with an Intent) the activity with the new selected
     * theme, instead will just change the properties on fly.<br /><br />
     *
     * @param activity Calling Activity
     * @param mainFrame Main container <tt>frameLayout</tt> of the Activity
     * @param home Home button of the Activity
     */
    public void updateTheme(Activity activity, View mainFrame, FloatingActionButton home) {
        switch ( theme(activity) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                activity.setTheme(R.style.PINK);
                mainFrame.setBackgroundColor(ContextCompat.getColor(activity, R.color.pinkbackground));
                home.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.pinkcolorAccent)));
                MusicManager.getInstance().updateSoundPrefs(activity);
                break;
            case Constant.PREF_THEME_STAR_WARS:
                activity.setTheme(R.style.CHEWBACCA);
                mainFrame.setBackground(ContextCompat.getDrawable(activity, R.drawable.starwars));
                home.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.starcolorAccent)));
                MusicManager.getInstance().updateSoundPrefs(activity);
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                activity.setTheme(R.style.EXPELLIARMUS);
                mainFrame.setBackground(ContextCompat.getDrawable(activity, R.drawable.harrypotter));
                home.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.hpotbackground)));
                MusicManager.getInstance().updateSoundPrefs(activity);
                break;
        }

    }

    /**
     * A preference fragment doesn't use the style of the calling activity, so we need to set it
     * <b>manually</b> for each preference we have in the fragment. So, the manager will
     * update the text color of the bound textview, as well as the text color of the summaries
     * of the listviews. When the chosen theme is <b>Star Wars</b> the manager will also
     * update the styles for the bound icons of the preferences.
     *
     *
     * @param context Calling Activity
     * @param title Bound text view for the title
     * @param summary Bound text view for the summary
     * @param icon Bound ImageView for the icon
     * @param order in which the icon is displayed on the fragment
     */
    public void setPreferenceStyle(Context context, TextView title, TextView summary, ImageView icon, int order) {
        switch ( theme(context) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                title.setTextColor(ContextCompat.getColor(context, R.color.black));
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                summary.setTextColor(ContextCompat.getColor(context, R.color.black));
                break;
            case Constant.PREF_THEME_STAR_WARS:
                title.setTextColor(ContextCompat.getColor(context, R.color.white));
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                summary.setTextColor(ContextCompat.getColor(context, R.color.white));
                setIcon(context, icon, order);
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                title.setTextColor(ContextCompat.getColor(context, R.color.black));
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                summary.setTextColor(ContextCompat.getColor(context, R.color.black));
                break;
        }
    }

    /**
     * If the selected theme is <b>Star Wars</b> the bound icon to the preferences will be different, and the manager will set up
     * a new style for this icon, for the remaining themes, it won't be changes.
     *
     * @param context Calling Activity
     * @param icon image view bound to the preference
     * @param order in which the icon is displayed on the fragment
     */
    public void setIcon(Context context, ImageView icon, int order) {
        switch ( order ) {
            default:
            case 1:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_speaker_white));
                break;
            case 2:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_vibration_white));
                break;
            case 3:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_notification_white));
                break;
            case 4:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_light_white));
                break;
            case 5:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_player_white));
                break;
            case 6:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_theme_white));
                break;
            case 7:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_language_white));
                break;
        }
    }

    public int getSummaryIcon(Context context, int beads) {
        int summaryIconID;
        switch ( theme(context) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                switch (beads) {
                    default:
                    case 0:
                        summaryIconID = R.drawable.star0;
                        break;
                    case 1:
                        summaryIconID = R.drawable.star1;
                        break;
                    case 2:
                        summaryIconID = R.drawable.star2;
                        break;
                    case 3:
                        summaryIconID = R.drawable.star3;
                        break;
                    case 4:
                        summaryIconID = R.drawable.star4;
                        break;
                    case 5:
                        summaryIconID = R.drawable.star5;
                        break;
                }
                break;
            case Constant.PREF_THEME_STAR_WARS:
                switch (beads) {
                    default:
                    case 0:
                        summaryIconID = R.drawable.star0;
                        break;
                    case 1:
                        summaryIconID = R.drawable.star1;
                        break;
                    case 2:
                        summaryIconID = R.drawable.star2;
                        break;
                    case 3:
                        summaryIconID = R.drawable.star3;
                        break;
                    case 4:
                        summaryIconID = R.drawable.star4;
                        break;
                    case 5:
                        summaryIconID = R.drawable.star5;
                        break;
                }
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                switch (beads) {
                    default:
                    case 0:
                        summaryIconID = R.drawable.snitch0;
                        break;
                    case 1:
                        summaryIconID = R.drawable.snitch1;
                        break;
                    case 2:
                        summaryIconID = R.drawable.snitch2;
                        break;
                    case 3:
                        summaryIconID = R.drawable.snitch3;
                        break;
                    case 4:
                        summaryIconID = R.drawable.snitch4;
                        break;
                    case 5:
                        summaryIconID = R.drawable.snitch5;
                        break;
                }
                break;
        }
        return summaryIconID;
    }

    public int getPlayerIcon(Context context, int id) {
        int playerIconID;
        switch ( theme(context) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:
                switch (id) {
                    default:
                    case 0:
                        playerIconID = R.drawable.santa;
                        break;
                    case 1:
                        playerIconID = R.drawable.mermaid;
                        break;
                    case 2:
                        playerIconID = R.drawable.clown;
                        break;
                    case 3:
                        playerIconID = R.drawable.devil;
                        break;
                }
                break;
            case Constant.PREF_THEME_STAR_WARS:
                switch (id) {
                    default:
                    case 0:
                        playerIconID = R.drawable.santa;
                        break;
                    case 1:
                        playerIconID = R.drawable.mermaid;
                        break;
                    case 2:
                        playerIconID = R.drawable.clown;
                        break;
                    case 3:
                        playerIconID = R.drawable.devil;
                        break;
                }
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                switch (id) {
                    default:
                    case 0:
                        playerIconID = R.drawable.harry;
                        break;
                    case 1:
                        playerIconID = R.drawable.hermione;
                        break;
                    case 2:
                        playerIconID = R.drawable.snape;
                        break;
                    case 3:
                        playerIconID = R.drawable.voldemort;
                        break;
                }
                break;
        }
        return playerIconID;
    }

}