package com.phyder.paalan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.HeaderInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jay on 02/07/16.
 *
 * @author Jayant Tiwari
 */
public class NetworkUtil {

    private static Retrofit sRetrofit;
    private static Gson sGson;
    boolean connected = false;

    public static Gson getGson() {
        if (sGson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .setDateFormat("yyyy-mm-DD");
            sGson = gsonBuilder.create();
        }
        return sGson;
    }

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {

            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(60, TimeUnit.SECONDS);

            //   Default Header Interceptor
            HeaderInterceptor heaederInterceptor = new HeaderInterceptor();
            heaederInterceptor.setUserAgent(Device.getInstance().getUserAgent());
            clientBuilder.addInterceptor(heaederInterceptor);

//            if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
//
//            }

//            Log.i("SERVER", "" + BuildConfig.DEBUG);
//            http://192.168.0.15:8080/paalan/PaalanGateway
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.15:8080/")
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(clientBuilder.build())
                    .build();
        }
        return sRetrofit;
    }


    public static boolean checkInternet(Activity mActivity) {
        ConnectivityManager connec = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected() || mobile.isConnected()) {
            return true;
        } else if (!mobile.isConnected() || !wifi.isConnected()) {
            alert(mActivity,"No Internet connection");
            return false;
        }
        return false;
    }


    public static boolean alert(final Context context, final String message) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("No Internet Connection Found");

        alertDialog.setMessage(message);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Please enable ", message);
            }
        });
        alertDialog.show();
        return true;
    }
}
