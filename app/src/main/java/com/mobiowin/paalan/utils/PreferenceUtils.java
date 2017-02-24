package com.mobiowin.paalan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.mobiowin.paalan.payload.response.ResponseInitialData;
import com.mobiowin.paalan.helper.Social;


/**
 * Created by cmss on 12/1/17.
 */

public class PreferenceUtils {

    private Context context;
    private SharedPreferences preferences;

    public PreferenceUtils(Context context) {
        this.context = context;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setOrgID(String orgId) {
        Editor editor = preferences.edit();
        editor.putString("ORG_ID", orgId);
        editor.commit();
    }

    public String getOrgId() {
        return preferences.getString("ORG_ID", null);
    }

    public void setLogin(boolean isLogin) {
        Editor editor = preferences.edit();
        editor.putBoolean("IS_LOGIN", isLogin);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean("IS_LOGIN", false);
    }

    public void setLoginType(String loginType) {
        Editor editor = preferences.edit();
        editor.putString("LOGIN_TYPE", loginType);
        editor.commit();
    }

    public String getLoginType() {
        return preferences.getString("LOGIN_TYPE", Social.IND_ENTITY);
    }

    public void setUserName(String name) {
        Editor editor = preferences.edit();
        editor.putString("USER_NAME", name);
        editor.commit();
    }

    public String getUserName() {
        return preferences.getString("USER_NAME", null);
    }

    public void setLocation(String location) {
        Editor editor = preferences.edit();
        editor.putString("LOCATION", location);
        editor.commit();
    }

    public String getLocation() {
        return preferences.getString("LOCATION", null);
    }


    public void setBanners(ResponseInitialData.Bannerlist[] banners) {

        Editor editor = preferences.edit();
        editor.putString("BANNERS_SIZE" , ""+banners.length);

        for (int index = 0; index < banners.length; index++) {
            editor.putString("BANNERS"+ index, banners[index].getBannerLink());
        }
        editor.commit();
    }

    public String getBanners() {

        String urls = "";
        int size = Integer.parseInt(preferences.getString("BANNERS_SIZE",null));

        for (int index = 0; index < size; index++) {
            urls = (index != size -1) ? urls + preferences.getString("BANNERS"+ index,null)+"~" : urls +
                    preferences.getString("BANNERS"+ index,null);
        }
        return urls;
    }
}
