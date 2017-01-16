package com.phyder.paalan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


/**
 * Created by cmss on 12/1/17.
 */

public class PreferenceUtils {

    private Context context;
    private SharedPreferences preferences;

    public PreferenceUtils(Context context){
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setOrgID(String orgId){
        Editor editor = preferences.edit();
        editor.putString("ORG_ID",orgId);
        editor.commit();
    }

    public String getOrgId(){
        return preferences.getString("ORG_ID",null);
    }
}
