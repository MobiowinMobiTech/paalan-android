package com.phyder.paalan.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.w3c.dom.Attr;


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


//	Database methods for achievement insertion,updation,deletion,selection operations

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

	public long deleteAchievement(String achieve_id)
	{
		return sqLiteDatabase.delete(Attributes.Database.ACHIEVEMENT_TABLE_NAME,Attributes.Database.ACHIEVEMENT_ID
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


	//	Database methods for event insertion,updation,deletion,selection operations

	public long insertEvent(String event_id, String event_title, String event_sub_title, String event_desc,
							String event_others, String event_startdate, String event_enddate,
							String event_is_Deleted,String event_category,String location) {
		ContentValues cv = new ContentValues();
		try {
			cv.put(Attributes.Database.EVENT_ID, event_id);
			cv.put(Attributes.Database.EVENT_TITLE, (event_title != null ? event_title : ""));
			cv.put(Attributes.Database.EVENT_SUB_TITLE, (event_sub_title != null ? event_sub_title : ""));
			cv.put(Attributes.Database.EVENT_DESCRIPTION, (event_desc != null ? event_desc : ""));
			cv.put(Attributes.Database.EVENT_OTHERS, (event_others != null ? event_others : ""));
			cv.put(Attributes.Database.EVENT_START_DATE, (event_startdate != null ? event_startdate : ""));
			cv.put(Attributes.Database.EVENT_END_DATE, (event_enddate != null ? event_enddate : ""));
			cv.put(Attributes.Database.EVENT_CATEGORY, (event_category != null ? event_category : ""));
			cv.put(Attributes.Database.EVENT_LOCATION, (location != null ? location : ""));
			cv.put(Attributes.Database.EVENT_IS_DELETED, event_is_Deleted);
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "insertEvent: "+e.getMessage());
		}
		return sqLiteDatabase.insert(Attributes.Database.EVENT_TABLE_NAME, null, cv);
	}


	public long updateEvent(String event_id, String event_title, String event_sub_title, String event_desc,
							String event_others, String event_startdate, String event_enddate,String event_category,
							String location) {
		ContentValues cv = new ContentValues();
		try {
			cv.put(Attributes.Database.EVENT_ID, event_id);
			cv.put(Attributes.Database.EVENT_TITLE, event_title);
			cv.put(Attributes.Database.EVENT_SUB_TITLE, event_sub_title);
			cv.put(Attributes.Database.EVENT_DESCRIPTION, event_desc);
			cv.put(Attributes.Database.EVENT_OTHERS, event_others);
			cv.put(Attributes.Database.EVENT_START_DATE, event_startdate);
			cv.put(Attributes.Database.EVENT_END_DATE, event_enddate);
			cv.put(Attributes.Database.EVENT_CATEGORY, (event_category != null ? event_category : ""));
			cv.put(Attributes.Database.EVENT_LOCATION, (location != null ? location : ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.Database.EVENT_TABLE_NAME, cv, Attributes.Database.EVENT_ID
				+ " = '" + event_id + "'", null);
	}

	public long deleteEvent(String event_id) {

		return sqLiteDatabase.delete(Attributes.Database.EVENT_TABLE_NAME, Attributes.Database.EVENT_ID
				+ " = '" + event_id + "'", null);
	}

	public Cursor getAllEvent(String isDeleted) {
		return sqLiteDatabase.rawQuery("Select * from " + Attributes.Database.EVENT_TABLE_NAME + " where " +
				Attributes.Database.EVENT_IS_DELETED + " = '" + isDeleted + "'", null);
	}

	public Cursor getEventById(String id) {
		return sqLiteDatabase.rawQuery("Select * from " + Attributes.Database.EVENT_TABLE_NAME + " where " +
				Attributes.Database.EVENT_ID + " = '" + id + "'", null);
	}


	public boolean isEventExist(String id) {
		boolean isExist = false;
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Attributes.Database.EVENT_TABLE_NAME + " where " +
				Attributes.Database.EVENT_ID + " = '" + id + "'", null);
		if (cursor != null) {
			cursor.moveToFirst();
			if (cursor.moveToFirst()) {
				do {
					if (cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_TITLE)) != null) {
						isExist = true;
						break;
					}
				} while (cursor.moveToNext());
			}
		}
		return isExist;
	}



//	Database methods of master db for insertion,updation last sync timespan

	public int getMasterTableCount(){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.MasterDatabase.MASTER_TABLE, null).getCount();
	}

	public Cursor getMasterTableData(){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.MasterDatabase.MASTER_TABLE, null);
	}

	public long insertTimeSpan(String achieve_time_span,String event_time_span, String request_time_span,
							   String ind_time_span)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.ACHIEVEMENT_TIMESPAN, achieve_time_span);
			cv.put(Attributes.MasterDatabase.EVENT_TIMESPAN,event_time_span);
			cv.put(Attributes.MasterDatabase.REQUEST_TIMESPAN, request_time_span);
			cv.put(Attributes.MasterDatabase.IND_DASH_TIMESPAN, ind_time_span);
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

	public long updateIndDashTimeSpan(String timespan)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.MasterDatabase.IND_DASH_TIMESPAN, timespan);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.MasterDatabase.MASTER_TABLE,cv,null,null);
	}




//	Database methods for request insertion,updation,deletion,selection operations

	public long insertRequest(String request_id,String request_title, String request_sub_title, String request_desc,
							  String request_others, String request_location, String isDeleted)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.REQUEST_ID, request_id);
			cv.put(Attributes.Database.REQUEST_TITLE,(request_title!=null ? request_title : ""));
			cv.put(Attributes.Database.REQUEST_SUB_TITLE, (request_sub_title!=null ? request_sub_title : ""));
			cv.put(Attributes.Database.REQUEST_DESCRIPTION, (request_desc!=null ? request_desc : ""));
			cv.put(Attributes.Database.REQUEST_OTHER, (request_others!=null ? request_others : ""));
			cv.put(Attributes.Database.REQUEST_LOCATION, (request_location!=null ? request_location : ""));
			cv.put(Attributes.Database.REQUEST_IS_DELETED, isDeleted);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.insert(Attributes.Database.REQUEST_TABLE_NAME, null, cv);
	}


	public long updateRequest(String request_id,String request_title, String request_sub_title, String request_desc,
							  String request_others, String request_location)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.REQUEST_ID, request_id);
			cv.put(Attributes.Database.REQUEST_TITLE,request_title);
			cv.put(Attributes.Database.REQUEST_SUB_TITLE,request_sub_title);
			cv.put(Attributes.Database.REQUEST_DESCRIPTION,request_desc);
			cv.put(Attributes.Database.REQUEST_OTHER,request_others);
			cv.put(Attributes.Database.REQUEST_LOCATION,request_location);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.Database.REQUEST_TABLE_NAME,cv,Attributes.Database.REQUEST_ID
				+" = '"+ request_id + "'",null);
	}

	public long deleteRequest(String request_id,String isDeleted)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.REQUEST_IS_DELETED, isDeleted);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.update(Attributes.Database.REQUEST_TABLE_NAME,cv,Attributes.Database.REQUEST_ID
				+" = '"+ request_id + "'",null);
	}

	public Cursor getAllRequests(String isDeleted){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.REQUEST_TABLE_NAME+" where "+
				Attributes.Database.REQUEST_IS_DELETED+" = '"+isDeleted+"'", null);
	}

	public Cursor getRequestById(String id){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.REQUEST_TABLE_NAME+" where "+
				Attributes.Database.REQUEST_ID+" = '"+id+"'", null);
	}


	public boolean isRequestExist(String id){
		boolean isExist = false;
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.REQUEST_TABLE_NAME+" where "+
				Attributes.Database.REQUEST_ID+" = '"+id+"'", null);
		if(cursor !=null){
			cursor.moveToFirst();
			if(cursor.moveToFirst()){
				do{
					if(cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_TITLE))!=null){
						isExist = true;
						break;
					}
				}while (cursor.moveToNext());
			}
		}
		return isExist;
	}


	//	Database methods for profile insertion,updation,deletion,selection operations

	public long insertProfile(String image,String role,String regNo, String fbLink, String linkedIn,
							  String webLink, String twitter, String presenceArea)
	{
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.PROFILE_IMAGE, (image!=null ? image : ""));
			cv.put(Attributes.Database.PROFILE_ROLE,(role!=null ? role : ""));
			cv.put(Attributes.Database.PROFILE_REGISTER_NO,(regNo!=null ? regNo : ""));
			cv.put(Attributes.Database.PROFILE_FB_LINK, (fbLink!=null ? fbLink : ""));
			cv.put(Attributes.Database.PROFILE_LINKED_IN_LINK, (linkedIn!=null ? linkedIn : ""));
			cv.put(Attributes.Database.PROFILE_WEB_LINK, (webLink!=null ? webLink : ""));
			cv.put(Attributes.Database.PROFILE_TWITTER_LINK, (twitter!=null ? twitter : ""));
			cv.put(Attributes.Database.PROFILE_PRESENCE_AREA, (presenceArea!=null ? presenceArea : ""));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sqLiteDatabase.insert(Attributes.Database.PROFILE_TABLE_NAME, null, cv);
	}

	public int deleteProfile(){
		return sqLiteDatabase.delete(Attributes.Database.PROFILE_TABLE_NAME, null,null);
	}


	public Cursor getProfile(){
		return sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.PROFILE_TABLE_NAME, null);
	}

	public String getProfileDP(){
		String encodedDp = "";
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.PROFILE_TABLE_NAME, null);
		if(cursor!=null)
			cursor.moveToFirst();
		if(cursor.moveToFirst()){
			do{
				encodedDp = cursor.getString(cursor.getColumnIndex(Attributes.Database.PROFILE_IMAGE));
			}while(cursor.moveToNext());
		}
		return encodedDp;
	}

	public String getlastSyncdate(String status) {
		String date = "0";
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from " + Attributes.MasterDatabase.MASTER_TABLE, null);
		if(cursor!=null)
			cursor.moveToFirst();
		if(cursor.moveToFirst()){
			do{
				if(status.equals("Achievement")){
					date = cursor.getString(cursor.getColumnIndex(Attributes.MasterDatabase.ACHIEVEMENT_TIMESPAN));
				}else if(status.equals("Event")){
					date = cursor.getString(cursor.getColumnIndex(Attributes.MasterDatabase.EVENT_TIMESPAN));
				}else if(status.equals("Request")){
					date = cursor.getString(cursor.getColumnIndex(Attributes.MasterDatabase.REQUEST_TIMESPAN));
				}else if(status.equals("IND")){
					date = cursor.getString(cursor.getColumnIndex(Attributes.MasterDatabase.IND_DASH_TIMESPAN));
				}
			}while (cursor.moveToNext());
		}
		return date;
	}
}
