package com.phyder.paalan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phyder.paalan.BuildConfig;
import com.phyder.paalan.R;
import com.phyder.paalan.helper.OrganizerProfileListener;
import com.phyder.paalan.payload.request.RequestOrganizerProfile;
import com.phyder.paalan.payload.response.ResponseOrganizerProfile;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.HeaderInterceptor;
import com.phyder.paalan.services.PaalanServices;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    private static OrganizerProfileListener organizerProfileListener;

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

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.UAT_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(clientBuilder.build())
                    .build();
        }

        return sRetrofit;
    }


    public static boolean isInternetConnected(Activity mActivity) {
        ConnectivityManager connec = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnected() || mobile.isConnected()) {
            return true;
        } else if (!mobile.isConnected() || !wifi.isConnected()) {
            return false;
        }
        return false;
    }


    public static boolean isWifiConnected(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return  wifiManager.isWifiEnabled();
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






    public static void getOrganizerProfile(final Context context, String orgID, OrganizerProfileListener _organizerProfileListener) {

            organizerProfileListener=_organizerProfileListener;
            CommanUtils.showDialog(context);
            Device.newInstance(context);

            RequestOrganizerProfile requestOrganizerProfile = RequestOrganizerProfile.get(orgID);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<ResponseOrganizerProfile> resOrgrofile = mPaalanServices.orgProfile(requestOrganizerProfile);

             resOrgrofile.enqueue(new Callback<ResponseOrganizerProfile>() {
                @Override
                public void onResponse(Call<ResponseOrganizerProfile> call, Response<ResponseOrganizerProfile> response) {

                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            organizerProfileListener.onSuccess(response);
                        } else {
                            CommanUtils.showToast(context,context.getResources().getString(R.string.error_org_profile));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(context,context.getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<ResponseOrganizerProfile> call, Throwable t) {
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(context,context.getResources().getString(R.string.error_timeout));
                }
            });
    }

}
