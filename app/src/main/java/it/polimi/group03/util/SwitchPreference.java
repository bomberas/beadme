package it.polimi.group03.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.polimi.group03.manager.ThemeManager;

/**
 * Customized SwitchPreference to support style changes.<br /><br />
 * <p>References</p>
 * {@link #onBindView(View)}<br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 18/12/2015.
 */
public class SwitchPreference extends android.preference.SwitchPreference {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("unused")
    public SwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressWarnings("unused")
    public SwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("unused")
    public SwitchPreference(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.title);
        TextView summaryView = (TextView) view.findViewById(android.R.id.summary);
        ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
        //Set the style for the text views and icon manually
        ThemeManager.getInstance().setPreferenceStyle(getContext(), titleView, summaryView, icon, this.getOrder());
    }
}
