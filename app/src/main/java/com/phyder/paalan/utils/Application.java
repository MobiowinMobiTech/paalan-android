package com.phyder.paalan.utils;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;

import io.fabric.sdk.android.Fabric;

/**
 * Created by yashika on 1/2/17.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Crashlytics());

        FirebaseMessaging.getInstance().subscribeToTopic("event");
        FirebaseMessaging.getInstance().subscribeToTopic("achievement");
        FirebaseMessaging.getInstance().subscribeToTopic("socialrequest");


    }
}
