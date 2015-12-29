package it.polimi.group03;

import android.app.Application;

import com.facebook.FacebookSdk;

public class BeadMeApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}