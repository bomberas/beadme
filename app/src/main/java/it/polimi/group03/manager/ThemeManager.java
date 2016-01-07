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
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.polimi.group03.R;
import it.polimi.group03.util.Constant;

/**
 *
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

   /**
     *
     * Set the background animation for the {@link it.polimi.group03.activity.PlayBeadMeActivity} activity according
     * to the selected theme.
     *
     *
     * @param context Calling Activity
     * @param box Parent view of the new images created for this animation
     * @param height Height of display
     * @param width Width of display
     */
    public void setPlayBackgroundAnimation(Context context, RelativeLayout box, int height, int width) {
        switch ( theme(context) ) {
            default:
            case Constant.PREF_THEME_DEFAULT:

                ImageView image1 = new ImageView(context);
                image1.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                image1.setPadding((int) (width * 0.13), (int) (height * 0.13), 0, 0);
                image1.setImageResource(R.drawable.cloud);

                box.addView(image1);

                ImageView image2 = new ImageView(context);
                image2.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                image2.setPadding((int) (width * 0.80), (int) (height * 0.17), 0, 0);
                image2.setImageResource(R.drawable.cloud);

                box.addView(image2);

                AnimationManager.getInstance().move(image1,"x", (int)(width * 0.8));
                AnimationManager.getInstance().move(image2,"x", -(int)(width * 0.8));

                break;
            case Constant.PREF_THEME_STAR_WARS:

                RelativeLayout.LayoutParams paramsSW1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                ImageView[] animations1 = new ImageView[6];
                int[] offsetX = new int[6];
                int[] offsetY = new int[6];
                ImageView img_TopLeft1 = new ImageView(context);
                paramsSW1.leftMargin = 10;
                paramsSW1.topMargin  = 10;
                img_TopLeft1.setScaleType(ImageView.ScaleType.FIT_XY);
                img_TopLeft1.setLayoutParams(paramsSW1);
                img_TopLeft1.setImageResource(R.drawable.shooting_star2);
                box.addView(img_TopLeft1);
                animations1[0] = img_TopLeft1;
                offsetX[0] = (int)(0.9 * width);
                offsetY[0] = (int)(0.9 * height);

                ImageView img_TopCenter = new ImageView(context);
                RelativeLayout.LayoutParams paramsSW2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsSW2.leftMargin = (int) (width * 0.60);
                paramsSW2.topMargin  = 10;
                img_TopCenter.setLayoutParams(paramsSW2);
                img_TopCenter.setScaleType(ImageView.ScaleType.FIT_XY);
                img_TopCenter.setImageResource(R.drawable.planet2);
                box.addView(img_TopCenter);
                animations1[1] = img_TopCenter;
                offsetX[1] = 0;
                offsetY[1] = (int)(0.9 * height);

                ImageView img_TopRight1 = new ImageView(context);
                RelativeLayout.LayoutParams paramsSW3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsSW3.leftMargin =  width - 50;
                paramsSW3.topMargin = 10;
                img_TopRight1.setLayoutParams(paramsSW3);
                img_TopRight1.setScaleType(ImageView.ScaleType.FIT_XY);
                img_TopRight1.setImageResource(R.drawable.planet);
                box.addView(img_TopRight1);
                animations1[2] = img_TopRight1;
                offsetX[2] = -(int)(0.9 * width);
                offsetY[2] =  (int)(0.9 * height);

                ImageView img_BottomLeft1 = new ImageView(context);
                RelativeLayout.LayoutParams paramsSW4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsSW4.leftMargin =  10;
                paramsSW4.topMargin  =  height - 50;
                img_BottomLeft1.setLayoutParams(paramsSW4);
                img_BottomLeft1.setScaleType(ImageView.ScaleType.FIT_XY);
                img_BottomLeft1.setImageResource(R.drawable.shooting_star);
                box.addView(img_BottomLeft1);
                animations1[3] = img_BottomLeft1;
                offsetX[3] =  (int)(0.8 * width);
                offsetY[3] = -(int)(0.8 * height);

                ImageView img_BottomCenter = new ImageView(context);
                RelativeLayout.LayoutParams paramsSW5 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsSW5.leftMargin =  (int) (width * 0.6);
                paramsSW5.topMargin  =  height - 50;
                img_BottomCenter.setLayoutParams(paramsSW5);
                img_BottomCenter.setScaleType(ImageView.ScaleType.FIT_XY);
                img_BottomCenter.setImageResource(R.drawable.meteorite2);
                box.addView(img_BottomCenter);
                animations1[4] = img_BottomCenter;
                offsetX[4] =  0;
                offsetY[4] = -(int)(0.8 * height);

                ImageView img_BottomRight1 = new ImageView(context);
                RelativeLayout.LayoutParams paramsSW6 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsSW6.leftMargin =  width - 50;
                paramsSW6.topMargin  =  height - 50;
                img_BottomRight1.setLayoutParams(paramsSW6);
                img_BottomRight1.setScaleType(ImageView.ScaleType.FIT_XY);
                img_BottomRight1.setImageResource(R.drawable.meteorite);
                box.addView(img_BottomRight1);
                animations1[5] = img_BottomRight1;
                offsetX[5] = -(int)(0.8 * width);
                offsetY[5] = -(int)(0.8 * height);

                AnimationManager.getInstance().moveXY( offsetX, offsetY, animations1);
                break;
            case Constant.PREF_THEME_HARRY_POTTER:

                RelativeLayout.LayoutParams paramsHP1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                ImageView[] animations = new ImageView[4];
                int[] offsetX1 = new int[4];
                int[] offsetY1 = new int[4];

                ImageView img_TopLeft = new ImageView(context);
                paramsHP1.topMargin =  (int) (height * 0.05);
                paramsHP1.leftMargin =  (int) (width * 0.05);
                img_TopLeft.setLayoutParams(paramsHP1);
                img_TopLeft.setScaleType(ImageView.ScaleType.FIT_XY);
                img_TopLeft.setImageResource(R.drawable.dementor);
                box.addView(img_TopLeft);
                animations[0] = img_TopLeft;
                offsetX1[0] = (int)(0.9 * width);
                offsetY1[0] = (int)(0.9 * height);

                ImageView img_TopRight = new ImageView(context);
                RelativeLayout.LayoutParams paramsHP2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsHP2.topMargin =  (int) (height * 0.05);
                paramsHP2.leftMargin =  width - 50;
                img_TopRight.setLayoutParams(paramsHP2);
                img_TopRight.setScaleType(ImageView.ScaleType.FIT_XY);
                img_TopRight.setImageResource(R.drawable.dementor);
                box.addView(img_TopRight);
                animations[1] = img_TopRight;
                offsetX1[1] = -(int)(0.85 * width);
                offsetY1[1] =  (int)(0.85 * height);

                ImageView img_BottomLeft = new ImageView(context);
                RelativeLayout.LayoutParams paramsHP3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                paramsHP3.topMargin  =  height - 50;
                paramsHP3.leftMargin =  10;
                img_BottomLeft.setLayoutParams(paramsHP3);
                img_BottomLeft.setScaleType(ImageView.ScaleType.FIT_XY);
                img_BottomLeft.setImageResource(R.drawable.dementor);
                box.addView(img_BottomLeft);
                animations[2] = img_BottomLeft;
                offsetX1[2] = (int)(0.85 * width);
                offsetY1[2] = -(int)(0.85 * height);

                ImageView img_BottomRight = new ImageView(context);
                RelativeLayout.LayoutParams paramsHP4 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                img_BottomRight.setScaleType(ImageView.ScaleType.FIT_XY);
                paramsHP4.topMargin  =  height - 50;
                paramsHP4.leftMargin =  width - 50;
                img_BottomRight.setLayoutParams(paramsHP4);
                img_BottomRight.setImageResource(R.drawable.dementor);
                box.addView(img_BottomRight);
                animations[3] = img_BottomRight;
                offsetX1[3] = -(int)(0.85 * width);
                offsetY1[3] = -(int)(0.85 * height);

                AnimationManager.getInstance().moveXY( offsetX1, offsetY1, animations);

                break;
        }
    }
}