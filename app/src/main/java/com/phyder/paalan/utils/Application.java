package com.phyder.paalan.utils;

/**
 * Created by yashika on 1/2/17.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
    }
}
