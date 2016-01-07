package it.polimi.group03.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import it.polimi.group03.manager.ThemeManager;

/**
 * Customized ListPreference to support style changes.<br /><br />
 * <p>References</p>
 * {@link #onBindView(View)}<br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 18/12/2015.
 */
public class ListPreference extends android.preference.ListPreference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public ListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public ListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public ListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("unused")
    public ListPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        TextView summaryView = (TextView) view.findViewById(android.R.id.summary);
        //Set the style for the text views and icon manually
        ThemeManager.getInstance().setPreferenceStyle(getContext(), titleView, summaryView);
    }

}