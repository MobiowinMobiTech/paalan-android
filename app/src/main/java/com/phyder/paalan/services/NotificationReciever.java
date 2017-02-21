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
            String title = intent.getStringExtra(Config.TITLE);
            String body = intent.getStringExtra(Config.BODY);
            String clickEvent = intent.getStringExtra(Config.ENTITY);
            String type = intent.getStringExtra(Config.TYPE);
            displayNotification(title,body,clickEvent,type,context);

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
     * @param title : title of notification
     * @param body : message of notification
     * @param clickEvent : event should fire after notification
     * @param context : current context
     */
    private void displayNotification(String title, String body, String clickEvent, String type,Context context) {
        // Using RemoteViews to bind custom layouts into Notification
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        Intent resultIntent = new Intent(context, ActivityFragmentPlatform.class);
        resultIntent.putExtra(Config.CLICK_EVENT,clickEvent);
        resultIntent.putExtra(Config.TYPE,type);
        resultIntent.putExtra(Config.BODY,body);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        android.app.PendingIntent pIntent = android.app.PendingIntent.getActivity(context, 0, resultIntent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title ,title);
        remoteViews.setTextViewText(R.id.body ,body);

        Notification builder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .setTicker(title)
                        .setCustomContentView(remoteViews)
                        .setCustomBigContentView(remoteViews)
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
