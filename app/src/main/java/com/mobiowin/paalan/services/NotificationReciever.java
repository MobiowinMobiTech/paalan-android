package com.mobiowin.paalan.services;

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

import com.mobiowin.paalan.activity.ActivityFragmentPlatform;
import com.mobiowin.paalan.helper.PaalanGetterSetter;
import com.mobiowin.paalan.utils.Config;
import com.phyder.paalan.R;


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
            displayNotification(context);
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
     * @param context : current context
     */
    private void displayNotification(Context context) {
        // Using RemoteViews to bind custom layouts into Notification
//        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
//                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        PaalanGetterSetter.setAppInitCall(false);
        Intent resultIntent = new Intent(context, ActivityFragmentPlatform.class);
        int notificationCount = PaalanGetterSetter.getNotificationTitle().split("~").length;
        String title = "";
        String body = "";
        String entity = "";
        if(notificationCount > 1){
            title = "Paalan";
            body = "You have "+notificationCount+" paalan notifications";
            entity = "multiple_notifications";
        }else{
            title = PaalanGetterSetter.getNotificationTitle();
            body = PaalanGetterSetter.getNotificationBody();
            entity = PaalanGetterSetter.getNotificationEntity();
        }

        resultIntent.putExtra(Config.ENTITY,entity);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        android.app.PendingIntent pIntent = android.app.PendingIntent.getActivity(context, 0, resultIntent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Locate and set the Text into customnotificationtext.xml TextViews
//        remoteViews.setTextViewText(R.id.title ,title);
//        remoteViews.setTextViewText(R.id.body ,body);

        Notification builder =
                new android.support.v4.app.NotificationCompat.Builder(context)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentTitle(title)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pIntent)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND).build();

        //int notificationId = (int) android.os.SystemClock.currentThreadTimeMillis();

        // Create Notification Manager
        android.app.NotificationManager notificationmanager = (android.app.NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0 , builder);

    }

}
