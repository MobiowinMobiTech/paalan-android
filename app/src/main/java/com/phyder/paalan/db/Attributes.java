package com.phyder.paalan.db;

/**
 * Created by cmss on 12/1/17.
 */

public class Attributes {

    public static class Database {

        public static final String DB_NAME = "paalan_database";

        public static final String ACHIEVEMENT_TABLE_NAME = "achievement_table";
        public static final String ACHIEVEMENT_ID = "achievement_id";
        public static final String ACHIEVEMENT_TITLE = "achievement_title";
        public static final String ACHIEVEMENT_SUB_TITLE = "achievement_sub_title";
        public static final String ACHIEVEMENT_DESCRIPTION = "achievement_description";
        public static final String ACHIEVEMENT_OTHERS = "achievement_others";
        public static final String ACHIEVEMENT_FIRST_IMAGE = "achievement_first_image";
        public static final String ACHIEVEMENT_SECOND_IMAGE = "achievement_second_image";
        public static final String ACHIEVEMENT_THIRD_IMAGE = "achievement_third_image";
        public static final String ACHIEVEMENT_FORTH_IMAGE = "achievement_forth_image";
        public static final String ACHIEVEMENT_IS_DELETED = "achievement_is_deleted";

        public static final String CREATE_ACHIEVEMENT_QUERY = "create table " + ACHIEVEMENT_TABLE_NAME +
                " (_id integer primary key autoincrement, " + ACHIEVEMENT_ID + " text not null, " + ACHIEVEMENT_TITLE + " text not null, "
                + ACHIEVEMENT_SUB_TITLE + " text not null, " + ACHIEVEMENT_DESCRIPTION + " text not null, "
                + ACHIEVEMENT_OTHERS + " text not null, " + ACHIEVEMENT_FIRST_IMAGE + " text not null, "
                + ACHIEVEMENT_SECOND_IMAGE + " text not null, " + ACHIEVEMENT_THIRD_IMAGE + " text not null, "
                + ACHIEVEMENT_FORTH_IMAGE + " text not null, " + ACHIEVEMENT_IS_DELETED + " text not null);";


        public static final String REQUEST_TABLE_NAME = "request_table";
        public static final String REQUEST_ID = "request_id";
        public static final String REQUEST_TITLE = "request_title";
        public static final String REQUEST_SUB_TITLE = "request_sub_title";
        public static final String REQUEST_DESCRIPTION = "request_description";
        public static final String REQUEST_OTHER = "request_others";
        public static final String REQUEST_LOCATION = "request_location";
        public static final String REQUEST_IS_DELETED = "request_is_deleted";

        public static final String CREATE_REQUEST_QUERY = "create table " + REQUEST_TABLE_NAME +
                " (_id integer primary key autoincrement, " + REQUEST_ID + " text not null, "
                + REQUEST_TITLE + " text not null, " + REQUEST_SUB_TITLE + " text not null, "
                + REQUEST_DESCRIPTION + " text not null, " + REQUEST_OTHER + " text not null, "
                + REQUEST_LOCATION + " text not null, " + REQUEST_IS_DELETED + " text not null);";


        public static final String EVENT_TABLE_NAME = "event_table";
        public static final String EVENT_ID = "event_id";
        public static final String EVENT_TITLE = "event_title";
        public static final String EVENT_SUB_TITLE = "event_sub_title";
        public static final String EVENT_DESCRIPTION = "event_description";
        public static final String EVENT_OTHERS = "event_others";
        public static final String EVENT_START_DATE = "event_start_date";
        public static final String EVENT_END_DATE = "event_end_date";
        public static final String EVENT_IS_DELETED = "event_is_deleted";

        public static final String CREATE_EVENT_QUERY = "create table " + EVENT_TABLE_NAME +
                " (_id integer primary key autoincrement, " + EVENT_ID + " text not null, "
                + EVENT_TITLE + " text not null, " + EVENT_SUB_TITLE + " text not null, "
                + EVENT_DESCRIPTION + " text not null, " + EVENT_OTHERS + " text not null, "
                + EVENT_START_DATE + " text not null, " + EVENT_END_DATE + " text not null, " +
                EVENT_IS_DELETED + " text not null);";


        public static final String PROFILE_TABLE_NAME = "profile_table";
        public static final String PROFILE_IMAGE = "profile_image";
        public static final String PROFILE_ROLE = "profile_role";
        public static final String PROFILE_REGISTER_NO = "profile_reg_no";
        public static final String PROFILE_FB_LINK = "profile_fb_link";
        public static final String PROFILE_LINKED_IN_LINK = "profile_linkedIn";
        public static final String PROFILE_WEB_LINK = "profile_web_link";
        public static final String PROFILE_TWITTER_LINK = "profile_twitter_link";
        public static final String PROFILE_PRESENCE_AREA = "profile_presence_area";

        public static final String CREATE_PROFILE_QUERY = "create table " + PROFILE_TABLE_NAME +
                " (_id integer primary key autoincrement, "
                + PROFILE_IMAGE + " text not null, " + PROFILE_ROLE + " text not null, "
                + PROFILE_REGISTER_NO + " text not null, " + PROFILE_FB_LINK + " text not null, "
                + PROFILE_LINKED_IN_LINK + " text not null, " + PROFILE_WEB_LINK + " text not null, "
                + PROFILE_TWITTER_LINK + " text not null, " + PROFILE_PRESENCE_AREA + " text not null);";
    }



    public static class MasterDatabase {

        public static final String MASTER_TABLE = "master_table";
        public static final String ACHIEVEMENT_TIMESPAN = "achievement_timespan";
        public static final String EVENT_TIMESPAN = "event_timespan";
        public static final String REQUEST_TIMESPAN = "request_timespan";


        public static final String CREATE_MASTER_QUERY = "create table " + MASTER_TABLE +
                " (_id integer primary key autoincrement, " + ACHIEVEMENT_TIMESPAN + " text not null, "
                + EVENT_TIMESPAN + " text not null, " + REQUEST_TIMESPAN + " text not null);";
    }


}
