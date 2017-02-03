package com.phyder.paalan.helper;


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
}

