package com.mobiowin.paalan.utils;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;

/**
 * Created by yashika on 1/2/17.
 */

public class Application extends android.app.Application {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Log.d("", "scheduleAlarm onCreate: ");

        FirebaseMessaging.getInstance().subscribeToTopic("event");
        FirebaseMessaging.getInstance().subscribeToTopic("achievement");
        FirebaseMessaging.getInstance().subscribeToTopic("socialrequest");

    }

}
