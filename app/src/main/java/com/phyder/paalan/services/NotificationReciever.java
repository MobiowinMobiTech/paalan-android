package com.phyder.paalan.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Gouresh on 25/1/17
 * Company PhyderCmss.
 */



public class NotificationReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        android.widget.Toast.makeText(context,"broadcast Reciever", android.widget.Toast.LENGTH_LONG).show();

        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String imageurl = intent.getStringExtra("imageurl");

          // Using RemoteViews to bind custom layouts into Notification
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(),
                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        Intent intent2 = new Intent(context, PostLoginActivity.class);

        android.app.PendingIntent pIntent = android.app.PendingIntent.getActivity(context, 0, intent2,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.leftimageicon,R.drawable.fteam);
        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title ,title);
        remoteViews.setTextViewText(R.id.body ,body);


        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(context)
                .setPriority(android.app.Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setTicker(title)
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(remoteViews)
                .setContentIntent(pIntent)
                .setDefaults(android.app.Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher);

        int notificationId = (int) android.os.SystemClock.currentThreadTimeMillis();

        //set Image Using picasso


    /*    com.squareup.picasso.Picasso.with(context)
                .load(imageurl)
                .into(remoteViews, R.id.imagenotileft, notificationId, builder.build());*/

        //MainActivity.displayNotification(getApplicationContext(),imageurl,remoteViews,R.id.imagenotileft,notificationId,builder.build());

        // Create Notification Manager
        android.app.NotificationManager notificationmanager = (android.app.NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(notificationId , builder.build());


    }

}
