package com.phyder.paalan.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.Config;


/**
 * @author  Pramod Waghmare
 * created on 3/02/17
 * Company PhyderCmss.
 */
public class NotificationReciever extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase("com.phyder.paalan.SendBroadcast")){
            displayNotification(intent,context);
        }else {
            scheduleAlarm(context);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void scheduleAlarm(Context context) {
        Log.d("", "scheduleAlarm: new user ");
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CronJobService.class);
        PendingIntent alarmIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                Config.TRIGGER_TIME, alarmIntent);
    }

    /**
     * Function used to display notification
     * @param intent : for notification values
     * @param context : current context
     */
    private void displayNotification(Intent intent,Context context) {
        // Using RemoteViews to bind custom layouts into Notification
//        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
//                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        PaalanGetterSetter.setAppInitCall(false);
        String entity = intent.getStringExtra(Config.ENTITY);
        String title = intent.getStringExtra(Config.TITLE);
        String body = intent.getStringExtra(Config.BODY);

        Intent resultIntent = new Intent(context, ActivityFragmentPlatform.class);

        String orgId = intent.getStringExtra(Config.ORG_ID);
        String recordId = intent.getStringExtra(Config.RECORD_ID);

        resultIntent.putExtra(Config.ENTITY,entity);
        resultIntent.putExtra(Config.ORG_ID,orgId);
        resultIntent.putExtra(Config.RECORD_ID,recordId);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        DBAdapter dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        dbAdapter.insertNotification(recordId, entity, body,orgId);
        dbAdapter.close();

        android.app.PendingIntent pIntent = android.app.PendingIntent.getActivity(context, recordId.hashCode(), resultIntent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Locate and set the Text into customnotificationtext.xml TextViews
//        remoteViews.setTextViewText(R.id.title ,title);
//        remoteViews.setTextViewText(R.id.body ,body);

        Notification builder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setContentText(body)
                        .setContentTitle(title)
                        .setContentIntent(pIntent)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setSmallIcon(R.drawable.paalan_logo).build();

        int notificationId = (int) android.os.SystemClock.currentThreadTimeMillis();

        // Create Notification Manager
        android.app.NotificationManager notificationmanager = (android.app.NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(notificationId , builder);

    }

}
