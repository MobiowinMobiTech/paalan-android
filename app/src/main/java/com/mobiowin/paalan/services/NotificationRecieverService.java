package com.mobiowin.paalan.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mobiowin.paalan.db.DBAdapter;
import com.mobiowin.paalan.helper.PaalanGetterSetter;
import com.mobiowin.paalan.utils.Config;

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

        if(remoteMessage.getData().size()>0) {
            // call local broadcast to display notification

            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.open();
            dbAdapter.insertNotification(remoteMessage.getData().get(Config.RECORD_ID),
                    remoteMessage.getData().get(Config.ENTITY),remoteMessage.getData().get(Config.BODY),
                    remoteMessage.getData().get(Config.ORG_ID));
            dbAdapter.close();

            PaalanGetterSetter.setNotificationTitle(remoteMessage.getData().get(Config.TITLE));
            PaalanGetterSetter.setNotificationBody(remoteMessage.getData().get(Config.BODY));
            PaalanGetterSetter.setNotificationEntity(remoteMessage.getData().get(Config.ENTITY));
            PaalanGetterSetter.setNotificationOrgId(remoteMessage.getData().get(Config.ORG_ID));
            PaalanGetterSetter.setNotificationRecordId(remoteMessage.getData().get(Config.RECORD_ID));
            Intent intent = new Intent();
            intent.setAction("com.phyder.paalan.SendBroadcast");
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            sendBroadcast(intent);
        }
    }
}



