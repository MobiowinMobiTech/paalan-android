package com.phyder.paalan.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.phyder.paalan.services.CronJobService;

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


        setCronJob();


    }

    private void setCronJob() {
        if (CommanUtils.isNewUser(this)){

            AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, CronJobService.class);
            PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, 0);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() +60 * 1000, alarmIntent);
        }
    }
}
