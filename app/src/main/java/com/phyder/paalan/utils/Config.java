package com.phyder.paalan.utils;

/**
 * Created by yashika on 20/12/16.
 */

public class Config {
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";
    public static final String TITLE = "title";
    public static final String BODY = "body";
    public static final String IMAGE_URL = "imageurl";
    public static final String CLICK_EVENT = "click_event";
    public static final long TRIGGER_TIME = 86400000;
}
