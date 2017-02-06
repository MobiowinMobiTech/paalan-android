package com.phyder.paalan.services;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;

/**
 * Created by Gouresh on 25/1/17
 * Company PhyderCmss.
 */



public class NotificationReciever extends BroadcastReceiver {

    private Bitmap remote_picture;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        android.widget.Toast.makeText(context,"broadcast Reciever", android.widget.Toast.LENGTH_LONG).show();

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String imageurl = intent.getStringExtra("imageurl");

//        imageurl = "";

        // Using RemoteViews to bind custom layouts into Notification
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
                R.layout.customnotification);

        //// TODO: 30/1/17
        // Open NotificationView Class on Notification Click
        Intent intent2 = new Intent(context, ActivityFragmentPlatform.class);

        android.app.PendingIntent pIntent = android.app.PendingIntent.getActivity(context, 0, intent2,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.leftimageicon,R.drawable.fteam);

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
                        .setSmallIcon(R.mipmap.ic_launcher).build();

        int notificationId = (int) android.os.SystemClock.currentThreadTimeMillis();

//        NotificationTarget notificationTarget = new NotificationTarget(
//                context,
//                remoteViews,
//                R.id.leftimageicon,
//                builder,
//                notificationId);
//
//
//                Glide.with( context.getApplicationContext() ) // safer!
//                .load( imageurl )
//                .asBitmap()
//                .into( notificationTarget );
//


        // Create Notification Manager
        android.app.NotificationManager notificationmanager = (android.app.NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(notificationId , builder);

    }

}
