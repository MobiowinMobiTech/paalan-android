package com.mobiowin.paalan.helper;


/**
 * Created by cmss on 19/1/17.
 */

public class PaalanGetterSetter {

    static String ACHIEVEMENT_ID = null;
    static String EVENT_ID = null;
    static String REQUEST_ID = null;
    static String ORG_ID = null;


    public static void setAchivementID(String id){
        ACHIEVEMENT_ID = id;
    }

    public static String getAchievementID(){
        return ACHIEVEMENT_ID;
    }

    public static void setEventID(String id){
        EVENT_ID = id;
    }

    public static String getEventID(){
        return EVENT_ID;
    }

    public static void setRequestID(String id){
        REQUEST_ID = id;
    }

    public static String getRequestID(){
        return REQUEST_ID;
    }


    public static void setOrgID(String id){
        ORG_ID = id;
    }

    public static String getOrgID(){
        return ORG_ID;
    }


    // For FCM Notification

    static boolean SHOULD_APP_INIT_SERVICE_CALL = true;
    static String NOTIFICATION_TITLE = "";
    static String NOTIFICATION_BODY = "";
    static String NOTIFICATION_ENTITY = "";
    static String NOTIFICATION_ORG_ID = "";
    static String NOTIFICATION_RECORD_ID = "";

    public static void clearNotifications(){

        NOTIFICATION_TITLE = "";
        NOTIFICATION_BODY = "";
        NOTIFICATION_ENTITY = "";
        NOTIFICATION_ORG_ID = "";
        NOTIFICATION_RECORD_ID = "";

    }

    public static boolean shouldAppInitCall() {
        return SHOULD_APP_INIT_SERVICE_CALL;
    }

    public static void setAppInitCall(boolean shouldAppInitServiceCall) {
        SHOULD_APP_INIT_SERVICE_CALL = shouldAppInitServiceCall;
    }

    public static String getNotificationTitle() {
        return NOTIFICATION_TITLE;
    }

    public static void setNotificationTitle(String notificationTitle) {

        NOTIFICATION_TITLE = NOTIFICATION_TITLE.isEmpty() ? notificationTitle : "~" +notificationTitle;
    }

    public static String getNotificationBody() {
        return NOTIFICATION_BODY;
    }

    public static void setNotificationBody(String notificationBody) {
        NOTIFICATION_BODY = NOTIFICATION_BODY.isEmpty() ? notificationBody : "~" +notificationBody;
    }

    public static String getNotificationEntity() {
        return NOTIFICATION_ENTITY;
    }

    public static void setNotificationEntity(String notificationEntity) {
        NOTIFICATION_ENTITY = NOTIFICATION_ENTITY.isEmpty() ? notificationEntity : "~" +notificationEntity;
    }

    public static String getNotificationOrgId() {
        return NOTIFICATION_ORG_ID;
    }

    public static void setNotificationOrgId(String notificationOrgId) {
        NOTIFICATION_ORG_ID = NOTIFICATION_ORG_ID.isEmpty() ? notificationOrgId : "~" +notificationOrgId;
    }

    public static String getNotificationRecordId() {
        return NOTIFICATION_RECORD_ID;
    }

    public static void setNotificationRecordId(String notificationRecordId) {
        NOTIFICATION_RECORD_ID = NOTIFICATION_RECORD_ID.isEmpty() ? notificationRecordId : "~" +notificationRecordId;
    }

}

