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
                + ACHIEVEMENT_OTHERS + " text not null, "+ ACHIEVEMENT_FIRST_IMAGE + " text not null, "
                + ACHIEVEMENT_SECOND_IMAGE + " text not null, "+ ACHIEVEMENT_THIRD_IMAGE + " text not null, "
                + ACHIEVEMENT_FORTH_IMAGE + " text not null, "+ ACHIEVEMENT_IS_DELETED + " text not null);";
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
