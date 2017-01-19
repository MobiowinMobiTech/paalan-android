package com.phyder.paalan.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBAdapter {

	final String TAG = DBAdapter.class.getCanonicalName();
	private DBHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;


	public DBAdapter(Context c) {
		context=c;
		
	}
	
	public DBAdapter open(){
		sqLiteHelper = new DBHelper(context, Attributes.Database.DB_NAME, null,1);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close(){
		if(sqLiteDatabase!=null && sqLiteDatabase.isOpen())
			sqLiteDatabase.close();
			if(sqLiteHelper!=null)
			sqLiteHelper.close();
	}
	public long insertAchievement(String achieve_id,String achieve_title, String achieve_sub_title, String achieve_desc,
								  String achieve_others, String achieve_img1, String achieve_img2, String achieve_img3,
								  String achieve_img4,String isDeleted)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.ACHIEVEMENT_ID, achieve_id);
			cv.put(Attributes.Database.ACHIEVEMENT_TITLE,(achieve_title!=null ? achieve_title : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_SUB_TITLE, (achieve_sub_title!=null ? achieve_sub_title : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_DESCRIPTION, (achieve_desc!=null ? achieve_desc : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_OTHERS, (achieve_others!=null ? achieve_others : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE, (achieve_img1!=null ? achieve_img1 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE, (achieve_img2!=null ? achieve_img2 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE, (achieve_img3!=null ? achieve_img3 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE, (achieve_img4!=null ? achieve_img4 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_IS_DELETED, isDeleted);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.insert(Attributes.Database.ACHIEVEMENT_TABLE_NAME, null, cv);
	}


	public long updateAchievement(String achieve_id,String achieve_title, String achieve_sub_title, String achieve_desc,
								  String achieve_others, String achieve_img1, String achieve_img2, String achieve_img3,
								  String achieve_img4)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.ACHIEVEMENT_TITLE, achieve_title);
			cv.put(Attributes.Database.ACHIEVEMENT_SUB_TITLE, achieve_sub_title);
			cv.put(Attributes.Database.ACHIEVEMENT_DESCRIPTION, achieve_desc);
			cv.put(Attributes.Database.ACHIEVEMENT_OTHERS, achieve_others);
			cv.put(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE, achieve_img1);
			cv.put(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE, achieve_img2);
			cv.put(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE, achieve_img3);
			cv.put(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE, achieve_img4);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.Database.ACHIEVEMENT_TABLE_NAME,cv,Attributes.Database.ACHIEVEMENT_ID
				+" = '"+ achieve_id + "'",null);
	}

	public long deleteAchievement(String achieve_id,String isDeleted)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.ACHIEVEMENT_IS_DELETED, isDeleted);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.Database.ACHIEVEMENT_TABLE_NAME,cv,Attributes.Database.ACHIEVEMENT_ID
				+" = '"+ achieve_id + "'",null);
	}

	public Cursor getAllAchievements(String isDeleted){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.ACHIEVEMENT_TABLE_NAME+" where "+
				Attributes.Database.ACHIEVEMENT_IS_DELETED+" = '"+isDeleted+"'", null);
	}

	public Cursor getAchievementById(String id){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.ACHIEVEMENT_TABLE_NAME+" where "+
				Attributes.Database.ACHIEVEMENT_ID+" = '"+id+"'", null);
	}


	public boolean isAchievementExist(String id){
		boolean isExist = false;
		 Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.ACHIEVEMENT_TABLE_NAME+" where "+
				Attributes.Database.ACHIEVEMENT_ID+" = '"+id+"'", null);
		if(cursor !=null){
			cursor.moveToFirst();
			if(cursor.moveToFirst()){
				do{
					if(cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE))!=null){
						isExist = true;
						break;
					}
				}while (cursor.moveToNext());
			}
		}
		return isExist;
	}

	public int getMasterTableCount(){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.MasterDatabase.MASTER_TABLE, null).getCount();
	}

	public Cursor getMasterTableData(){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.MasterDatabase.MASTER_TABLE, null);
	}

	public long insertTimeSpan(String achieve_time_span,String event_time_span, String request_time_span)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.ACHIEVEMENT_TIMESPAN, achieve_time_span);
			cv.put(Attributes.MasterDatabase.EVENT_TIMESPAN,event_time_span);
			cv.put(Attributes.MasterDatabase.REQUEST_TIMESPAN, request_time_span);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.insert(Attributes.MasterDatabase.MASTER_TABLE, null, cv);
	}


	public long updateAchievementTimeSpan(String timespan)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.ACHIEVEMENT_TIMESPAN, timespan);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.MasterDatabase.MASTER_TABLE,cv,null,null);
	}


	public long updateEventTimeSpan(String timespan)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.EVENT_TIMESPAN, timespan);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.MasterDatabase.MASTER_TABLE,cv,null,null);
	}

	public long updateRequestTimeSpan(String timespan)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.REQUEST_TIMESPAN, timespan);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.MasterDatabase.MASTER_TABLE,cv,null,null);
	}


}
