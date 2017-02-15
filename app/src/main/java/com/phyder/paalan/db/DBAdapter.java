package com.phyder.paalan.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


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

	public int populatingAchievementsIntoDB(String orgId,String tempId, String achieve_id,String name,String achieve_title, String achieve_sub_title, String achieve_desc,
								  String achieve_remarks, String achieve_img1, String achieve_img2, String achieve_img3,
								  String achieve_img4,String isDeleted) {
		int status = 0;
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.ACHIEVEMENT_ORG_ID,(orgId!=null ? orgId : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_NAME,(name!=null ? name : "NA"));
			cv.put(Attributes.Database.ACHIEVEMENT_TITLE,(achieve_title!=null ? achieve_title : "NA"));
			cv.put(Attributes.Database.ACHIEVEMENT_SUB_TITLE, (achieve_sub_title!=null ? achieve_sub_title : "NA"));
			cv.put(Attributes.Database.ACHIEVEMENT_DESCRIPTION, (achieve_desc!=null ? achieve_desc : "NA"));
			cv.put(Attributes.Database.ACHIEVEMENT_REMARKS, (achieve_remarks!=null ? achieve_remarks : "NA"));
			cv.put(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE, (achieve_img1!=null ? achieve_img1 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE, (achieve_img2!=null ? achieve_img2 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE, (achieve_img3!=null ? achieve_img3 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE, (achieve_img4!=null ? achieve_img4 : ""));
			cv.put(Attributes.Database.ACHIEVEMENT_IS_DELETED, isDeleted);

			String id = tempId==null ? achieve_id : tempId;
			if(!isAchievementExist(id)) {
				status = 0;
				cv.put(Attributes.Database.ACHIEVEMENT_ID, id);
				sqLiteDatabase.insert(Attributes.Database.ACHIEVEMENT_TABLE_NAME, null, cv);
			}else{
				status = 1;
				sqLiteDatabase.update(Attributes.Database.ACHIEVEMENT_TABLE_NAME,cv,Attributes.Database.ACHIEVEMENT_ID
						+" = '"+ id + "'",null);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	return status;

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

	public int populatingEventsIntoDB(String orgId,String tempId,String event_id, String name,String event_title, String event_sub_title, String event_desc,
							String event_others, String event_startdate, String event_enddate,
							String event_is_Deleted,String event_category,String location,String logo) {

		int status = 0;
		ContentValues cv = new ContentValues();
		try {
			cv.put(Attributes.Database.EVENT_ORG_ID, (orgId != null ? orgId : ""));
			cv.put(Attributes.Database.EVENT_NAME, (name != null ? name : "NA"));
			cv.put(Attributes.Database.EVENT_TITLE, (event_title != null ? event_title : "NA"));
			cv.put(Attributes.Database.EVENT_SUB_TITLE, (event_sub_title != null ? event_sub_title : "NA"));
			cv.put(Attributes.Database.EVENT_DESCRIPTION, (event_desc != null ? event_desc : "NA"));
			cv.put(Attributes.Database.EVENT_OTHERS, (event_others != null ? event_others : "NA"));
			cv.put(Attributes.Database.EVENT_START_DATE, (event_startdate != null ? event_startdate : "NA"));
			cv.put(Attributes.Database.EVENT_END_DATE, (event_enddate != null ? event_enddate : "NA"));
			cv.put(Attributes.Database.EVENT_CATEGORY, (event_category != null ? event_category : "NA"));
			cv.put(Attributes.Database.EVENT_LOCATION, (location != null ? location : "NA"));
			cv.put(Attributes.Database.EVENT_LOGO, (logo != null ? logo : "NA"));
			cv.put(Attributes.Database.EVENT_IS_DELETED, event_is_Deleted);

			String id = tempId==null ? event_id : tempId;

			if(!isEventExist(id)) {
				status = 0;
				cv.put(Attributes.Database.EVENT_ID, id);
				sqLiteDatabase.insert(Attributes.Database.EVENT_TABLE_NAME, null, cv);
			}else{
				status = 1;
				sqLiteDatabase.update(Attributes.Database.EVENT_TABLE_NAME, cv, Attributes.Database.EVENT_ID
						+ " = '" + id + "'", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
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



	//	Database methods for organization insertion,updation,deletion,selection operations

	public void populatingGroupsIntoDB(String id, String org_id, String name,String mobile_no, String email,
								   String address,String city,String state,String pincode,String country,
								   String deleted) {

		ContentValues cv = new ContentValues();
		try {

			cv.put(Attributes.Database.GROUPS_ORGANIZATION_ID, (org_id != null ? org_id : ""));
			cv.put(Attributes.Database.GROUPS_NAME, (name != null ? name : "NA"));
			cv.put(Attributes.Database.GROUPS_MOBILE_NO, (mobile_no != null ? mobile_no : "NA"));
			cv.put(Attributes.Database.GROUPS_EMAIL, (email != null ? email : "NA"));
			cv.put(Attributes.Database.GROUPS_ADDRESS, (address != null ? address : "NA"));
			cv.put(Attributes.Database.GROUPS_CITY, (city != null ? city : "NA"));
			cv.put(Attributes.Database.GROUPS_STATE, (state != null ? state : "NA"));
			cv.put(Attributes.Database.GROUPS_PINCODE, (pincode != null ? pincode : "NA"));
			cv.put(Attributes.Database.GROUPS_COUNTRY, (country != null ? country : "NA"));
			cv.put(Attributes.Database.GROUPS_DELETED, deleted);

			if(!isGroupsExist(id)) {
				cv.put(Attributes.Database.GROUPS_ID, id);
				sqLiteDatabase.insert(Attributes.Database.GROUPS_TABLE_NAME, null, cv);
			}else{
				sqLiteDatabase.update(Attributes.Database.GROUPS_TABLE_NAME, cv, Attributes.Database.GROUPS_ID
						+ " = '" + id + "'", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Cursor getAllGroups(String isDeleted) {
		return sqLiteDatabase.rawQuery("Select * from " + Attributes.Database.GROUPS_TABLE_NAME + " where " +
				Attributes.Database.GROUPS_DELETED + " = '" + isDeleted + "'", null);
	}


	public boolean isGroupsExist(String id){
		boolean isExist = false;
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.GROUPS_TABLE_NAME+" where "+
				Attributes.Database.GROUPS_ID+" = '"+id+"'", null);
		if(cursor !=null){
			cursor.moveToFirst();
			if(cursor.moveToFirst()){
				do{
					if(cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_ID))!=null){
						isExist = true;
						break;
					}
				}while (cursor.moveToNext());
			}
		}
		return isExist;
	}


	//	Database methods for organization profile insertion,updation,deletion,selection operations

	public void populatingGroupsProfileIntoDB(String id, String org_id, String name,String mobile_no, String email,
								    String address,String role,String is_news_latter,String is_govt,String is_register,
								    String dp_img,String fb_link,String linkedin,String website,String twitter,
									String presence_area,String deleted) {

		ContentValues cv = new ContentValues();
		try {
			cv.put(Attributes.Database.GROUPS_PROFILE_ORG_ID, (org_id != null ? org_id : ""));
			cv.put(Attributes.Database.GROUPS_PROFILE_NAME, (name != null ? name : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_MOBILE_NO, (mobile_no != null ? mobile_no : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_EMAIL, (email != null ? email : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_ADDRESS, (address != null ? address : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_ROLE, (role != null ? role : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_IS_NEWS_LETTER, (is_news_latter != null ? is_news_latter : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_IS_GOVT_REGISTER, (is_govt != null ? is_govt : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_REGISTER, (is_register != null ? is_register : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_DP_IMG, (dp_img != null ? dp_img : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_FB_LINK, (fb_link != null ? fb_link : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_LINKEDIN, (linkedin != null ? linkedin : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_WEBSITE, (website != null ? website : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_TWITTER, (twitter != null ? twitter : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_PRESENCE_AREA, (presence_area != null ? presence_area : "NA"));
			cv.put(Attributes.Database.GROUPS_PROFILE_DELETED, deleted);

			if(!isGroupsProfileExist(id)) {
				cv.put(Attributes.Database.GROUPS_PROFILE_ID, id);
				sqLiteDatabase.insert(Attributes.Database.GROUPS_PROFILE_TABLE_NAME, null, cv);
			}else{
				sqLiteDatabase.update(Attributes.Database.GROUPS_PROFILE_TABLE_NAME, cv, Attributes.Database.GROUPS_PROFILE_ID
						+ " = '" + id + "'", null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Cursor getGroupsProfileById(String id) {
		return sqLiteDatabase.rawQuery("Select * from " + Attributes.Database.GROUPS_PROFILE_TABLE_NAME + " where " +
				Attributes.Database.GROUPS_PROFILE_ORG_ID + " = '" + id + "'", null);
	}


	public String getGroupsProfileDp(String id) {
		String dp = "";
		Cursor cursor = sqLiteDatabase.rawQuery("Select " + Attributes.Database.GROUPS_PROFILE_DP_IMG +" from " +
				Attributes.Database.GROUPS_PROFILE_TABLE_NAME +" where " +
				Attributes.Database.GROUPS_PROFILE_ORG_ID + " = '" + id + "'", null);

		if(cursor!=null)
			cursor.moveToFirst();
		if(cursor.moveToFirst()){
			do{
				dp = cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_DP_IMG));
			}while (cursor.moveToNext());
		}

		return dp;
	}


	public boolean isGroupsProfileExist(String id){
		boolean isExist = false;
		Cursor cursor = sqLiteDatabase.rawQuery("Select * from "+Attributes.Database.GROUPS_PROFILE_TABLE_NAME+" where "+
				Attributes.Database.GROUPS_PROFILE_ID+" = '"+id+"'", null);
		if(cursor !=null){
			cursor.moveToFirst();
			if(cursor.moveToFirst()){
				do{
					if(cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_PROFILE_ID))!=null){
						isExist = true;
						break;
					}
				}while (cursor.moveToNext());
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

	public int populatingRequestIntoDB(String orgId,String tempId,String request_id,String name,String request_title, String request_sub_title, String request_desc,
							  String request_others, String request_location, String isDeleted){

		int status = 0;
		ContentValues cv=new ContentValues();
		try{
			cv.put(Attributes.Database.REQUEST_ORG_ID,(orgId!=null ? orgId : ""));
			cv.put(Attributes.Database.REQUEST_NAME,(name!=null ? name : "NA"));
			cv.put(Attributes.Database.REQUEST_TITLE,(request_title!=null ? request_title : "NA"));
			cv.put(Attributes.Database.REQUEST_SUB_TITLE, (request_sub_title!=null ? request_sub_title : "NA"));
			cv.put(Attributes.Database.REQUEST_DESCRIPTION, (request_desc!=null ? request_desc : "NA"));
			cv.put(Attributes.Database.REQUEST_OTHER, (request_others!=null ? request_others : "NA"));
			cv.put(Attributes.Database.REQUEST_LOCATION, (request_location!=null ? request_location : "NA"));
			cv.put(Attributes.Database.REQUEST_IS_DELETED, isDeleted);

			String id = tempId==null ? request_id : tempId;

			if(!isRequestExist(id)) {
				status = 0;
				cv.put(Attributes.Database.REQUEST_ID, id);
				sqLiteDatabase.insert(Attributes.Database.REQUEST_TABLE_NAME, null, cv);
			}else{
				status = 1;
				sqLiteDatabase.update(Attributes.Database.REQUEST_TABLE_NAME, cv, Attributes.Database.REQUEST_ID
						+ " = '" + id + "'", null);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}


	public long deleteRequest(String id){
		return sqLiteDatabase.delete(Attributes.Database.REQUEST_TABLE_NAME,Attributes.Database.REQUEST_ID
				+" = '"+ id + "'",null);
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
			cv.put(Attributes.Database.PROFILE_ROLE,(role!=null ? role : "NA"));
			cv.put(Attributes.Database.PROFILE_REGISTER_NO,(regNo!=null ? regNo : "NA"));
			cv.put(Attributes.Database.PROFILE_FB_LINK, (fbLink!=null ? fbLink : "NA"));
			cv.put(Attributes.Database.PROFILE_LINKED_IN_LINK, (linkedIn!=null ? linkedIn : "NA"));
			cv.put(Attributes.Database.PROFILE_WEB_LINK, (webLink!=null ? webLink : "NA"));
			cv.put(Attributes.Database.PROFILE_TWITTER_LINK, (twitter!=null ? twitter : "NA"));
			cv.put(Attributes.Database.PROFILE_PRESENCE_AREA, (presenceArea!=null ? presenceArea : "NA"));
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
