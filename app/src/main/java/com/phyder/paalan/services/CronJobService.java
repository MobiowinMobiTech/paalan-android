package com.phyder.paalan.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by yashika on 9/2/17.
 */
public class CronJobService extends Service{
    private static final String TAG = CronJobService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "scheduleAlarm onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLatestTopics();

        return START_STICKY;

    }

    /**
     * Function to get latest topics from server
     */
    private void getLatestTopics() {
        Log.d(TAG, "scheduleAlarm getLatestTopics: ");
    }
}
