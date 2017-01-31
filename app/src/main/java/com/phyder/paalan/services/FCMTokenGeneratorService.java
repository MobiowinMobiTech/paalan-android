package com.phyder.paalan.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by Gouresh on 22/11/16
 * Company PhyderCmss.
 */

public class FCMTokenGeneratorService extends FirebaseInstanceIdService {

    private static final String TAG = FCMTokenGeneratorService.class.getName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        try {
            Device.getInstance().setmNotificationId(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "FCM Token: " + token);
    }

}
