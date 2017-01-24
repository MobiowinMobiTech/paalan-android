package com.phyder.paalan.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.phyder.paalan.R;
import com.phyder.paalan.activity.PaalanSplashActivity;
import com.phyder.paalan.utils.NotificationUtils;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

//custom view

/* RemoteViews remoteViews = new RemoteViews(getPackageName(),
R.layout.notification_view);

remoteViews.setTextViewText(R.id.title,remoteMessage.getNotification().getTitle());
remoteViews.setTextViewText(R.id.text,remoteMessage.getNotification().getBody());*/

        String msgText = "Jeally Bean Notification example!! Jeally Bean Notification example "
                + "where you will see three different kind of notification. "
                + "you can even put the very long string here.";

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(remoteMessage.getNotification().getTitle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);

        Intent intent = new Intent(this , PaalanSplashActivity.class);
        intent.putExtra("message", "Approved");
        intent.setAction("APPROVED_ACTION");
        PendingIntent approval = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent ignore = new Intent(this , PaalanSplashActivity.class);
        ignore.putExtra("message", "Reject");
        ignore.setAction("REJECT_ACTION");
        PendingIntent reject = PendingIntent.getActivity(this, 123, ignore, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(0, "Approved", approval);
        builder.addAction(0, "Reject", reject);

        builder.setContentIntent(reject);
        builder.setContentIntent(approval);


        Notification notification = new Notification.BigTextStyle(builder)
                .bigText(remoteMessage.getNotification().getBody()).build();

        notificationManager.notify(1101, notification);
    }

}