package it.polimi.group03.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support changes over the <b>Theme</b> preference. It will be used across the whole application
 * (all the activities) to retrieve the current theme, and all extra information
 * related to it. Since some components (like PreferencesFragment) can not use the theme of the application, or in some cases,
 * the manager will set the info programmatically.<br /><br />
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
     * {@link #setPreferenceStyle(Context, TextView, TextView)}<br/>
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
     * A preference fragment doesn't use the style of the calling activity, so we need to set it
     * <b>manually</b> for each preference we have in the fragment. So, the manager will
     * update the text color of the bound textview, as well as the text color of the summaries
     * of the listviews.
     *
     * @param context Calling Activity
     * @param title Bound text view for the title
     * @param summary Bound text view for the summary
     */
    public void setPreferenceStyle(Context context, TextView title, TextView summary) {
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
                break;
            case Constant.PREF_THEME_HARRY_POTTER:
                title.setTextColor(ContextCompat.getColor(context, R.color.white));
                title.setTypeface(title.getTypeface(), Typeface.BOLD);
                summary.setTextColor(ContextCompat.getColor(context, R.color.white));
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