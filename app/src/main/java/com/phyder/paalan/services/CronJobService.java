package com.phyder.paalan.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.phyder.paalan.db.DBAdapter;


/**
 * @author  Pramod Waghmare
 * created on 3/02/17
 * Company PhyderCmss.
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
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();

//        DBHelper dbHelper = new DBHelper(this);



    }
}
