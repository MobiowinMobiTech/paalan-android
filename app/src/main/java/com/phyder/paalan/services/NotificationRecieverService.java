package com.phyder.paalan.services;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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

        Log.d(TAG, "FCM Message Id: " + remoteMessage.getData());

        String imageurl = remoteMessage.getData().get("imageurl");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        Intent intent1 = new Intent();
        intent1.putExtra("title",title);
        intent1.putExtra("body",body);
        intent1.putExtra("imageurl",imageurl);
        intent1.setAction("com.phyder.paalan.SendBroadcast");
        intent1.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent1);


    }


}



