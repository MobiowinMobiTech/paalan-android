package com.phyder.paalan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.phyder.paalan.R;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class Util {

    public static boolean checkInternet(Activity mActivity) {
        ConnectivityManager connec = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected() || mobile.isConnected()) {
            return true;
        } else if (!mobile.isConnected() || !wifi.isConnected()) {
            alert(mActivity);
            return false;
        }
        return false;
    }

    public static boolean alert(final Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("No Internet Connection Found");

        alertDialog.setMessage("Enable your internet connection");

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Log.i("Please enable ", "internet connection");
            }
        });
        alertDialog.show();
        return true;
    }

//    public void serverErrorDialog() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Util.this);
//
//        alertDialog.setTitle("Server Error");
//        alertDialog.setMessage(getString(R.string.server_error_message));
//        alertDialog.setIcon(R.drawable.warning);
//        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Log.i("Server Error Found", "");
//            }
//        });
//        alertDialog.show();
//    }
}
