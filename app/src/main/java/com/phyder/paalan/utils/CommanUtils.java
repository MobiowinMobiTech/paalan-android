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
import android.widget.ImageView;

import com.phyder.paalan.R;
import com.phyder.paalan.model.WhatsNewScreenModel;
import com.phyder.paalan.payload.response.organization.ResponseInitialData;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by cmss on 11/1/17.
 */

public class CommanUtils {

    private final static String TAG = CommanUtils.class.getSimpleName();

    private static ProgressDialog mProgressDialog;

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

    private static String getImeiNo(Context context){
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

    public static void updateImage(Context context, ImageView img, String imgUrl) {
       Picasso.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.paalan_logo)
                .error(R.drawable.paalan_logo)
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

}
