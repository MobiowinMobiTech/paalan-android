package com.phyder.paalan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.RequestInitialData;
import com.phyder.paalan.payload.response.organization.ResponseInitialData;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

import java.util.HashSet;
import java.util.Set;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaalanSplashActivity extends AppCompatActivity {

    private static final String TAG = PaalanSplashActivity.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 3000;
    private boolean isScreensAvailable = false;

    Set<String> bannerList = new HashSet<>();

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_paalan_splash);

        dbAdapter = new DBAdapter(com.phyder.paalan.activity.PaalanSplashActivity.this);
        pref = new PreferenceUtils(com.phyder.paalan.activity.PaalanSplashActivity.this);

        dbAdapter.open();
        if(dbAdapter.getMasterTableCount()<1){
            dbAdapter.insertTimeSpan("0","0","0","0");
        }
        dbAdapter.close();

        getRetrofitCallBannerSlider();

    }

    public void getRetrofitCallBannerSlider() {
        if (NetworkUtil.isInternetConnected(PaalanSplashActivity.this)) {
            Device.newInstance(PaalanSplashActivity.this);

            RequestInitialData slidingBanner = RequestInitialData.get("", "", "0");
            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            final Call<ResponseInitialData> appSyncBanner = mPaalanServices.appSyncBanner(slidingBanner);

            appSyncBanner.enqueue(new Callback<ResponseInitialData>() {
                @Override
                public void onResponse(Call<ResponseInitialData> call, Response<ResponseInitialData> response) {
                    Log.d(TAG, "onFailure: ");
                    ResponseInitialData.Bannerlist[] banners = response.body().getData()[0].getBannerlist();

                    CommanUtils.setDataInSharedPrefs(PaalanSplashActivity.this, "bannerUrlLength", banners);

                    ResponseInitialData.Screenlist[] screenlist = response.body().getData()[0].getScreenlist();
                    Log.d(TAG, "onResponse: "+screenlist.length);
                    if (screenlist.length > 0) {
                        isScreensAvailable = true;
                        CommanUtils.setDataForScreens(PaalanSplashActivity.this, screenlist);
                    }

                    launchDashboard(!isScreensAvailable);
                }

                @Override
                public void onFailure(Call<ResponseInitialData> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    if (CommanUtils.isNewUser(PaalanSplashActivity.this)){
                        showExitAlert();
                    }
                }
            });
        }else {
            if (CommanUtils.isNewUser(PaalanSplashActivity.this)){
                showExitAlert();
            }else{
                launchDashboard(true);
            }
        }
    }

    /**
     * Function used to exit app
     */
    private void showExitAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.connection_error));
        alert.setMessage(getString(R.string.first_time_user_without_network));
        alert.setCancelable(false);
        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.show();
    }


    private void launchDashboard(boolean status) {
        Intent intent;
        if (status)
            intent = new Intent(PaalanSplashActivity.this, ActivityFragmentPlatform.class);
        else
            intent = new Intent(PaalanSplashActivity.this, WhatsNew.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommanUtils.hideDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CommanUtils.hideDialog();
    }
}