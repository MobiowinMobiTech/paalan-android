package com.phyder.paalan.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.Config;

/**
 * @author  Pramod Waghmare
 * created on 3/02/17
 * Company PhyderCmss.
 */

public class NotificationRecieverService extends FirebaseMessagingService {

    private static final String TAG = NotificationRecieverService.class.getName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "ad Message: " +
                remoteMessage.getNotification());

        Log.d(TAG, "FCM Message Id: " + remoteMessage.getData());

        // call local broadcast to display notification
        Intent intent = new Intent();
        intent.putExtra(Config.TITLE,remoteMessage.getData().get(Config.TITLE));
        intent.putExtra(Config.BODY,remoteMessage.getData().get(Config.BODY));
        intent.putExtra(Config.IMAGE_URL,remoteMessage.getData().get(Config.IMAGE_URL));
        intent.putExtra(Config.TYPE,remoteMessage.getData().get(Config.TYPE));
        intent.putExtra(Config.ENTITY,remoteMessage.getData().get(Config.ENTITY));

        if(remoteMessage.getData().get(Config.TYPE).equals(Social.BROADCAST)){
            intent.putExtra(Config.ORG_ID,remoteMessage.getData().get(Config.ORG_ID));
            intent.putExtra(Config.RECORD_ID,remoteMessage.getData().get(Config.RECORD_ID));
        }
        intent.setAction("com.phyder.paalan.SendBroadcast");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);

    }


}



