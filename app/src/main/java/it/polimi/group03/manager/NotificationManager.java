package it.polimi.group03.manager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import it.polimi.group03.R;
import it.polimi.group03.activity.HomeActivity;
import it.polimi.group03.util.Constant;

/**
 * This class holds the logic to support changes over the <b>Notification</b> preference.
 * If notifications are enabled, the manager will create a notification if the user hasn't played
 * in the last 24 hours. Also, if the <b>light</b> preference is enabled, the notification will
 * have the sensor led on with a Pink color.<br /><br />
 *
 * @author tatibloom
 * @version 1.0
 * @since 11/12/2015.
 */
public class NotificationManager extends BroadcastReceiver {

    private static final String TAG = "NotificationManager";

    private static NotificationManager ourInstance = new NotificationManager();
    private static final int PENDING_INTENT_ID = 135792468;

    public static NotificationManager getInstance() {
        return ourInstance;
    }

    public NotificationManager() {
    }

    /**
     * Checks if the preference corresponding to the application <i>sound</i> is
     * enabled.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #start(Context)}<br/>
     * {@link #updateNotificationPrefs(Context)}<br/>
     *
     * @param context Calling activity
     * @return {@code isNotificationOn} <tt>true</tt> if notification is enabled
     * <tt>false</tt> if not
     */
    public boolean isNotificationOn(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isNotificationOn = sharedPref.getBoolean(Constant.KEY_PREF_NOTIFICATION, Constant.PREF_NOTIFICATION_DEFAULT);

        Log.i(TAG, "isNotificationOn: " + String.valueOf(isNotificationOn));
        return isNotificationOn;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, HomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 1, i, 0);
        android.app.NotificationManager mNotificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Create the notification for the intent received(every 24 hours)
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getText(R.string.app_name))
                .setContentText(context.getText(R.string.notification_text))
                //Set the intent for opening the HomeActivity
                .setContentIntent(pi)
                .setDefaults(Notification.DEFAULT_SOUND)
                //When true the notification is automatically canceled when the user clicks it in the panel
                .setAutoCancel(true);

        //Needs to validate if lights preferences are on
        setLight(context, mBuilder);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * If the application <i>notifications</i> are enabled, the manager will create a pending intent
     * every 24 hours if the user doesn't play.<br/><br/>
     * <b>Reference</b><br/>
     *
     * {@link #updateNotificationPrefs(Context)}<br/>
     *
     * @param context Calling activity
     */
    public void start(Context context) {
        if ( !isNotificationOn(context) ) {
            return;
        }

        Intent intent = new Intent(context, NotificationManager.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Use the alarm manager to setup a pending intent every 24 hours
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 24 * 60 * 1000, pendingIntent);
    }

    /**
     * When a change is made within the {@link it.polimi.group03.activity.SettingsActivity}, the manager
     * will update the preferences related to the notifications, to <tt>on</tt> or <tt>off</tt> and if
     * the notifications were disabled, the manager will search for the pending intent created (when
     * the notifications were enabled) and will cancel it, so the user won't see any notification. If
     * the notifications were enabled, the manager will start a new intent.<br /><br />
     * <p>Reference</p>
     *
     * {@link #start(Context)}
     *
     * @param context Calling activity
     */
    public void updateNotificationPrefs(Context context) {
        if ( isNotificationOn(context) ) {
            start(context);
        } else {
            Intent intent = new Intent(context, NotificationManager.class);
            PendingIntent.getBroadcast(context, PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT).cancel();
        }
    }

    /**
     * Checks if the preference corresponding to the application <i>light</i> is
     * enabled, if so the notification created will set the led on with a pink color.<br/><br/>
     * <b>Reference</b><br/>
     *
     * @param context Calling activity
     * @param mBuilder Builder in charge of build the notification
     */
    private void setLight(Context context, NotificationCompat.Builder mBuilder) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isLightOn = sharedPref.getBoolean(Constant.KEY_PREF_LIGHT, Constant.PREF_LIGHT_DEFAULT);

        Log.i(TAG, "isLightOn: " + String.valueOf(isLightOn));
        if ( isLightOn ) {
            mBuilder.setLights(Color.argb(0, 255, 0, 85), 0, 1000);
        }
    }

}
