package com.phyder.paalan.services;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
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
        DBAdapter dbAdapter = new DBAdapter(this);
        dbAdapter.open();

        try {
            String clickEvent = remoteMessage.getData().get(Config.CLICK_EVENT);
            Log.d(TAG, "PUSH : click event"+clickEvent);
            if (clickEvent.equalsIgnoreCase(getString(R.string.click_event_event))){
                Log.d(TAG, "PUSH : click event = event");

            }else if (clickEvent.equalsIgnoreCase(getString(R.string.click_event_achievement))){

            }else if (clickEvent.equalsIgnoreCase(getString(R.string.click_event_social_request))){

            }
        }catch (Exception ex){

        }

        // call local broadcast to display notification
        Intent intent = new Intent();
        intent.putExtra(Config.TITLE,remoteMessage.getData().get(Config.TITLE));
        intent.putExtra(Config.BODY,remoteMessage.getData().get(Config.BODY));
        intent.putExtra(Config.IMAGE_URL,remoteMessage.getData().get(Config.IMAGE_URL));
        intent.putExtra(Config.CLICK_EVENT,remoteMessage.getData().get(Config.CLICK_EVENT));
        intent.setAction("com.phyder.paalan.SendBroadcast");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        //  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendBroadcast(intent);

    }


}



