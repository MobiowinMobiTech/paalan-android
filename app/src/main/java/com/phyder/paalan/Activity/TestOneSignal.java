package com.phyder.paalan.Activity;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import com.onesignal.OneSignal;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class TestOneSignal extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("OneSignalTag", "Before OneSignal init");
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.startInit(this).init();
        Log.e(TAG,"sdgf");
        Log.d("OneSignalTag", "After OneSignal init");
//        OneSignal.startInit(this).init();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
