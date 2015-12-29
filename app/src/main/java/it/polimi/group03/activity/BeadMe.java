package it.polimi.group03.activity;

import android.app.Application;

import com.facebook.FacebookSdk;

public class BeadMe extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}