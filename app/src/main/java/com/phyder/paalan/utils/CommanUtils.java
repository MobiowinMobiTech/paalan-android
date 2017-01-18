package com.phyder.paalan.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.ByteArrayOutputStream;

/**
 * Created by cmss on 11/1/17.
 */

public class CommanUtils {


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


    public static boolean getInternetAlert(final Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setMessage("Enable your internet connection");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Log.i("Please enable ", "internet connection");
            }
        });
        alertDialog.show();
        return true;
    }
}
