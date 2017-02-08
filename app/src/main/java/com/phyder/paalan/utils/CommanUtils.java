package com.phyder.paalan.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.helper.DialogPopupListener;
import com.phyder.paalan.model.WhatsNewScreenModel;
import com.phyder.paalan.payload.response.organization.ResponseInitialData;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cmss on 11/1/17.
 */

public class CommanUtils {

    private final static String TAG = CommanUtils.class.getSimpleName();

    private static ProgressDialog mProgressDialog;
    private static DialogPopupListener dialogPopupListener;

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(email);
        Log.d("", "isBasicInfoFilled: valid email i/p:"+email);
        if (!matcher1.matches()) {
            //show your message if not matches with email pattern
            return false;
        }else
            return true;

    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    private static String getImeiNo(Context context){
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }


    public static String encodeToBase64(Bitmap image)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static Bitmap getSquareBitmap(Bitmap sourceBitmap){

        if (sourceBitmap.getWidth() >= sourceBitmap.getHeight()){

            return Bitmap.createBitmap(
                    sourceBitmap,
                    sourceBitmap.getWidth()/2 - sourceBitmap.getHeight()/2,
                    0,
                    sourceBitmap.getHeight(),
                    sourceBitmap.getHeight()
            );

        }else{

            return  Bitmap.createBitmap(
                    sourceBitmap,
                    0,
                    sourceBitmap.getHeight()/2 - sourceBitmap.getWidth()/2,
                    sourceBitmap.getWidth(),
                    sourceBitmap.getWidth()
            );
        }

    }

    public static void showDialog(Context context) {

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please wait...");
        mProgressDialog.setCancelable(false);
        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public static void hideDialog() {
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    public static void showGpsLocationDialog(final Context context, final String btnLabel, DialogPopupListener _dialogPopupListener){
        dialogPopupListener = _dialogPopupListener;
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Location Error");
        alert.setMessage("Turn On Location Service");
        alert.setCancelable(false);

        alert.setPositiveButton("Turn On Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });
        alert.setNegativeButton(btnLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    dialogPopupListener.onCancelClicked(btnLabel);
            }
        });
        alert.show();
    }



    public static void showWifiLocationDialog(final Context context,DialogPopupListener _dialogPopupListener){
        dialogPopupListener = _dialogPopupListener;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Location Error");
        alert.setMessage("Turn On Wifi");
        alert.setCancelable(false);

        alert.setPositiveButton("Turn On Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
             }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogPopupListener.onCancelClicked("Cancel");
            }
        });
        alert.show();
    }



    public static void showAlert(Context context, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(true);
        alert.setIcon(R.mipmap.ic_launcher);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG)
                .show();
    }

    public static boolean showAlertDialog(final Context context,String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
        return true;
    }


    public static void setDataInSharedPrefs(Activity baseActivity, String key, ResponseInitialData.Bannerlist[] banners) {
        SharedPreferences sharedPref = baseActivity.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("bannerUrlLength", banners.length + "");

        for (int index = 0; index < banners.length; index++) {
            Log.d(TAG, "onResponse: " + banners[index].getBannerLink());
            editor.putString("bannerUrl" + index, banners[index].getBannerLink());
        }

        editor.apply();


        // to get banner url data from shared preference
        for (int bannerIndex = 0; bannerIndex < Integer.parseInt(getDataFromSharedPrefs(baseActivity, "bannerUrlLength"))
                ; bannerIndex++) {
            Log.d(TAG, "onResponse setDataInSharedPrefs: " + getDataFromSharedPrefs(baseActivity, "bannerUrl" + bannerIndex));
            getDataFromSharedPrefs(baseActivity, "bannerUrl" + bannerIndex);
        }
    }

    public static String getDataFromSharedPrefs(Activity baseActivity, String key) {
        SharedPreferences sharedPref = baseActivity.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public static void updateImage(Context context, ImageView img, String imgUrl,int defaultImage) {

        imgUrl = (imgUrl==null || imgUrl.isEmpty()) ? "www.error.com" : imgUrl;
        Picasso.with(context)
                .load(imgUrl)
                .placeholder(defaultImage)
                .error(defaultImage)
                .into(img);
    }


    /**
     * Function to set screen list in local data
     * @param context
     * @param screenlist : list to save
     */
    public static void setDataForScreens(Context context, ResponseInitialData.Screenlist[] screenlist) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("welcomeScreenLength", screenlist.length);
        // add add screens
        for (int index = 0; index < screenlist.length; index++) {
            editor.putInt("welcomeScreenSeq" + index, Integer.parseInt(screenlist[index].getScreenSeq()));
            editor.putString("welcomeScreenName" + index, screenlist[index].getScreenName());
            editor.putString("welcomeScreenImgLink" + index, screenlist[index].getScreenImgLink());
            editor.putString("welcomeScreenText" + index, screenlist[index].getScreenTxt());
        }
        editor.apply();
    }

    /**
     * Function to get all screens from shared preference
     * @param context : current context
     * @return : list of screens
     */
    public static ArrayList<WhatsNewScreenModel> getScreens(Context context){
        ArrayList<WhatsNewScreenModel> screens = new ArrayList<>();
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        int totalScreens = sharedPref.getInt("welcomeScreenLength",0);
        for (int screenIndex = 0 ; screenIndex < totalScreens; screenIndex ++){
            WhatsNewScreenModel whatsNewScreenModel = new WhatsNewScreenModel();
            whatsNewScreenModel.setSequence(sharedPref.getInt("welcomeScreenSeq"+screenIndex,0));
            whatsNewScreenModel.setTitle(sharedPref.getString("welcomeScreenName"+screenIndex,context.getString(R.string.dataNotFound)));
            whatsNewScreenModel.setMessage(sharedPref.getString("welcomeScreenText"+screenIndex,context.getString(R.string.dataNotFound)));
            whatsNewScreenModel.setImageLink(sharedPref.getString("welcomeScreenImgLink"+screenIndex,context.getString(R.string.dataNotFound)));

            screens.add(whatsNewScreenModel);

        }

        return screens;
    }


    /**
     * Function to check if user first time visitor
     * @param context : current context
     * @return :
     */
    public static boolean isNewUser(Context context) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("isNewUser",true);
    }

    /**
     * Function used to set new user status
     * whether is 1st time visitor or not
     * @param context
     * @param status : 1st time visiting status
     */
    public static void setNewUserStatus(Context context, boolean status) {
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isNewUser",true);
        editor.apply();
    }

    /**
     * Function to save profile picture in shared preference
     * @param context : current context
     * @param userType
     * @param strEncodedDp : user profile picture
     */
    public static void saveProfilePic(Context context, String userType, String strEncodedDp) {
        Log.d(TAG, "getProfileUpdate: save "+userType);
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("profilePic"+userType,strEncodedDp);
        editor.apply();
    }

    public static Bitmap getUserProfile(Context context, String loginType) {
        Log.d(TAG, "getProfileUpdate: type "+loginType);
        SharedPreferences sharedPref = context.getApplicationContext().getSharedPreferences("shared_prefrence", Context.MODE_PRIVATE);
        String profilePic = sharedPref.getString("profilePic"+loginType,"");
        try {
            byte [] encodeByte=Base64.decode(profilePic,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            return null;
        }

    }
}
