package com.mobiowin.paalan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.mobiowin.paalan.db.DBAdapter;
import com.mobiowin.paalan.payload.request.RequestInitialData;
import com.mobiowin.paalan.payload.response.ResponseInitialData;
import com.mobiowin.paalan.services.Device;
import com.mobiowin.paalan.utils.CommanUtils;
import com.mobiowin.paalan.utils.NetworkUtil;
import com.mobiowin.paalan.utils.PreferenceUtils;
import com.phyder.paalan.R;
import com.mobiowin.paalan.services.PaalanServices;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaalanSplashActivity extends AppCompatActivity {

    private static final String TAG = PaalanSplashActivity.class.getSimpleName();
    private boolean isScreensAvailable = false;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_paalan_splash);

        dbAdapter = new DBAdapter(this);
        pref = new PreferenceUtils(this);

        dbAdapter.open();
        if(dbAdapter.getMasterTableCount()<1){
            dbAdapter.insertTimeSpan("0","0","0","0","0");
        }
        dbAdapter.close();

        getRetrofitCallBannerSlider();

    }

    public void getRetrofitCallBannerSlider() {
        if (NetworkUtil.isInternetConnected(PaalanSplashActivity.this)) {
            Device.newInstance(PaalanSplashActivity.this);
            dbAdapter.open();
            RequestInitialData slidingBanner = RequestInitialData.get("", "", dbAdapter.getlastSyncdate("WhatsNew"));
            dbAdapter.close();
            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            final Call<ResponseInitialData> appSyncBanner = mPaalanServices.appSyncBanner(slidingBanner);

            appSyncBanner.enqueue(new Callback<ResponseInitialData>() {
                @Override
                public void onResponse(Call<ResponseInitialData> call, Response<ResponseInitialData> response) {

                    if(response.body()!=null) {

                        ResponseInitialData.Bannerlist[] banners = response.body().getData()[0].getBannerlist();
                        if(banners.length>0) {
                            pref.setBanners(banners);
                        }
                        ResponseInitialData.Screenlist[] screenlist = response.body().getData()[0].getScreenlist();
                        Log.d(TAG, "onResponse: "+screenlist.length);
                        if (screenlist.length > 0) {
                            isScreensAvailable = true;
                            CommanUtils.setDataForScreens(PaalanSplashActivity.this, screenlist);
                        }
                        dbAdapter.open();
                        dbAdapter.updateWhatsNewTimeSpan(response.body().getMessage());
                        dbAdapter.close();
                    launchDashboard(!isScreensAvailable);
                    }else{
                        showExitAlert(getString(R.string.technical_issue));
                    }
                }

                @Override
                public void onFailure(Call<ResponseInitialData> call, Throwable t) {
                    Log.d(TAG, "onFailure: ");
                    if (CommanUtils.isNewUser(PaalanSplashActivity.this)){
                        showExitAlert(getString(R.string.technical_issue));
                    }
                }
            });
        }else {
            if (CommanUtils.isNewUser(PaalanSplashActivity.this)){
                showExitAlert(getString(R.string.first_time_user_without_network));
            }else{
                launchDashboard(true);
            }
        }
    }

    /**
     * Function used to exit app
     * @param message
     */
    private void showExitAlert(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.connection_error));
        alert.setMessage(message);
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