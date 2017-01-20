package com.phyder.paalan.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    final String TAG = DBHelper.class.getCanonicalName();

    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Attributes.MasterDatabase.CREATE_MASTER_QUERY);
        db.execSQL(Attributes.Database.CREATE_ACHIEVEMENT_QUERY);
        db.execSQL(Attributes.Database.CREATE_REQUEST_QUERY);
        db.execSQL(Attributes.Database.CREATE_EVENT_QUERY);
        Log.i(TAG, "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + Attributes.Database.ACHIEVEMENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + Attributes.Database.REQUEST_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + Attributes.Database.EVENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + Attributes.MasterDatabase.MASTER_TABLE);
    }

}
