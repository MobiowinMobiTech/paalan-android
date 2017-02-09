package com.phyder.paalan.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.phyder.paalan.payload.request.RequestSyncNotification;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
            Log.d(TAG, "onTokenRefresh: "+token);
            Device.newInstance(this);
            Device.setNotificationId(token,this);

            sendNotificationToServer();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "FCM Token: " + token);
    }

    /**
     * Function used to send notification id to server
     */
    private void sendNotificationToServer() {

        Device.newInstance(this);
        Device device = Device.getInstance();

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        RequestSyncNotification requestSyncNotification = RequestSyncNotification.get(Social.EVENT_SYNC_ACTION,
                Social.ENTITY_APP,Social.NOTIFICATION,device.getModel(),
                device.getNotificationId(this),device.getOSVersion(),device.getDeviceId(this));

        Call<RequestSyncNotification> requestSyncNotificationCall = mPaalanServices.syncNotificationId(requestSyncNotification);
        requestSyncNotificationCall.enqueue(new Callback<RequestSyncNotification>() {
            @Override
            public void onResponse(Call<RequestSyncNotification> call, Response<RequestSyncNotification> response) {
                Log.d(TAG, "onTokenRefresh onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<RequestSyncNotification> call, Throwable t) {
                Log.d(TAG, "onTokenRefresh onResponse: failure "+t.getMessage());
            }
        });

    }

}
