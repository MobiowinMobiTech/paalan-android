package com.phyder.paalan.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.response.ResponseInitialData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by cmss on 11/1/17.
 */

public class CommanUtils {


    private static final String TAG = CommanUtils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    public static String getImeiNo(Context context){
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



    public static String getFBRegId(Context context){
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        return pref.getString("regId", null);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static void showDialog(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Please wait...");
        if(mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public static void hideDialog() {
        if(mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
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

        Log.d(TAG, "getDataFromSharedPrefs: " + key);
        Log.d("", "Emp id value1 " + sharedPref.getString(key, ""));
        return sharedPref.getString(key, "");
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

}
