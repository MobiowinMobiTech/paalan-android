package com.phyder.paalan.services;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.utils.Config;

/**
 * Created by Gouresh on 25/1/17
 * Company PhyderCmss.
 */
public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra(Config.TITLE);
        String body = intent.getStringExtra(Config.BODY);
        String clickEvent = intent.getStringExtra(Config.CLICK_EVENT);


        // Using RemoteViews to bind custom layouts into Notification
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        Intent resultIntent = new Intent(context, ActivityFragmentPlatform.class);
        resultIntent.putExtra(Config.CLICK_EVENT,clickEvent);

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
