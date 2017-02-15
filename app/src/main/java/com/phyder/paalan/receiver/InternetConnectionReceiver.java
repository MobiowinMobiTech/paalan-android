package com.phyder.paalan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.phyder.paalan.helper.InternetConnectionListener;

/**
 * Created by cmss on 15/2/17.
 */

public class InternetConnectionReceiver extends BroadcastReceiver{

    final static String TAG = InternetConnectionReceiver.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            InternetConnectionListener internetConnectionListener = (InternetConnectionListener) context;
            internetConnectionListener.internetConnectionChanged();
        }catch (Exception e){
            Log.e(TAG,"onReceive : "+e);
        }
    }
}
