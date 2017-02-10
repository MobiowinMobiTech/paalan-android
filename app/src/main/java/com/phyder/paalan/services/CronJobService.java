package com.phyder.paalan.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by yashika on 9/2/17.
 */
public class CronJobService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getLatestTopics();

        return super.onStartCommand(intent, flags, startId);

    }

    /**
     * Function to get latest topics from server
     */
    private void getLatestTopics() {

    }
}
