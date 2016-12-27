package com.phyder.paalan.services;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

/**
 * Created by jay on 12/07/16.
 *
 * @author Uncle
 */
public class Device {

    private static final String APP_SHORT_CODE = "social-sdk-android";
    private static final String DEVICE_ANDROID = "android";

    private static Device ourInstance;

    private String mAppName;
    private String mModel;
    private String mVersionName;
    private int mVersionCode;

    public Device(Context context) {
        initAppName(context);
        initModel();
        initVersionName(context);
        initVersionName(context);
    }

    /**
     * @param context instance of Context to access details link getPackageManager()
     * @return
     */
    public static Device newInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new Device(context);
        }
        return ourInstance;
    }

    public static Device getInstance() {
        if (ourInstance == null) {
            throw new RuntimeException("This is singleton instance, must be initialized using" +
                    "Device.newInstance(context)");
        }
        return ourInstance;
    }

    /**
     * @return
     */
    public String getAppName() {
        return mAppName;
    }

    /**
     * Give User Agent for the device in
     * <p>APP_SHORT_CODE/VERSION_NAME(VERSION_CODE) DEVICE_OS/OS_VERSION/DEVICE_MODEL Locale</p>
     * format
     *
     * @return user agent of the device
     */
    public String getUserAgent() {

        return getAppName() + "/" + getVersionName() + "(" + getVersionCode() + ")"
                + " " + DEVICE_ANDROID + "/" + Build.VERSION.SDK_INT + "/" + getModel()
                + " " + Locale.getDefault();
    }

    /**
     * {@link Device} model includes manufacturer is not unknown
     *
     * @return device's model
     */
    public String getModel() {
        return mModel;
    }

    /**
     * Version name of the installed app
     *
     * @return version name of installed app
     */
    public String getVersionName() {
        return mVersionName;
    }

    /**
     * Version code of the installed app
     *
     * @return version code of installed app
     */
    public int getVersionCode() {
        return mVersionCode;
    }

    private void initAppName(Context context) {
        mAppName = APP_SHORT_CODE;
    }

    private void initModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        mModel = manufacturer.equals("unknown") ? model : manufacturer + "," + model;
    }

    private void initVersionName(Context context) {
        try {
            mVersionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mVersionName = "N/A";
        }
    }

    private void initVersioncode(Context context) {
        try {
            mVersionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            mVersionCode = -1;
        }
    }
}
