package com.phyder.paalan.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;

import static android.support.v7.appcompat.R.attr.icon;

/**
 * Created by Gouresh on 22/11/16
 * Company PhyderCmss.
 */

public class NotificationRecieverService extends FirebaseMessagingService {

    private static final String TAG = NotificationRecieverService.class.getName();
    private Bitmap remote_picture;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM Notification Message: " +
                remoteMessage.getNotification());
        Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

         String imageurl = remoteMessage.getData().get("imageurl");
         String title = remoteMessage.getData().get("title");
         String body = remoteMessage.getData().get("body");


       // Utils.di(getApplicationContext(),imageurl,title,body);

        //displayBigPictureNotification(title,body,imageurl);
        //displayCustomNotification(title,body,imageurl);
        //displayBigTextNotification(title,body,imageurl);
        Intent intent1 = new Intent();
        intent1.putExtra("title",title);
        intent1.putExtra("body",body);
        intent1.putExtra("imageurl",imageurl);
        intent1.setAction("com.example.SendBroadcast");
        intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
      //  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent1);
      //  broadcastIntent();
    }

    public void broadcastIntent()
    {
        Intent intent = new Intent();
        intent.setAction("com.example.SendBroadcast");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
    }

    private void displayCustomNotification(String title,String body,String imageurl) {

        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, PostLoginActivity.class);
        // Send data to NotificationView Class
        // intent.putExtra("title", strtitle);
        //intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

      /*  // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.leftimageicon,R.drawable.fteam);
        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title ,title);
        remoteViews.setTextViewText(R.id.body ,body);*/

        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setTicker(title)
                .setCustomContentView(remoteViews)
                .setCustomBigContentView(remoteViews)
                .setContentIntent(pIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.mipmap.ic_launcher);

        int notificationId = (int) SystemClock.currentThreadTimeMillis();

        //set Image Using picasso
      /*  Picasso.with(this)
                .load(imageurl)
                .into(remoteViews, R.id.imagenotileft, notificationId, builder.build());*/

        //MainActivity.displayNotification(getApplicationContext(),imageurl,remoteViews,R.id.imagenotileft,notificationId,builder.build());

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(notificationId , builder.build());
    }

    public void displayBigPictureNotification(String title,String body,String imageurl)
        {

            NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
            notiStyle.setSummaryText(body);

            try {
                remote_picture = BitmapFactory.decodeStream((InputStream) new URL(imageurl).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            notiStyle.bigPicture(remote_picture);
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            PendingIntent contentIntent = null;

            Intent gotoIntent = new Intent(this, MainActivity.class);//Start activity when user taps on notification.
            contentIntent = PendingIntent.getActivity(this,
                    (int) (Math.random() * 100), gotoIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);
            Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentIntent(contentIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(body)
                    .setStyle(notiStyle).build();

            int notificationId = (int) SystemClock.currentThreadTimeMillis();

            notificationManager.notify(notificationId, notification);//This will generate seperate notification each time server sends.
            startForeground(notificationId, notification);
        }



    private void displayBigTextNotification(String title,String body,String imageurl) {

    PendingIntent pendingIntent = null;

            if (Utils.islogIn) {

                Intent intent = new Intent(this, PostLoginActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addParentStack(PostLoginActivity.class);

                stackBuilder.addNextIntent(intent);
                pendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

            } else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0 , intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
            }
        int notificationId = (int) Calendar.getInstance().getTimeInMillis();

    //    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.fresher);

        //    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setColor(Color.parseColor("#0054A5"));
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setNumber(notificationId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(body));


       /* NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
        bigPicStyle.bigPicture(bitmap);
        bigPicStyle.setBigContentTitle(title);
        notificationBuilder.setStyle(bigPicStyle);*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());



}


    }



