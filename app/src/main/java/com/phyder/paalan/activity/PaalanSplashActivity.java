package com.phyder.paalan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        setContentView(R.layout.activity_paalan_splash);

        dbAdapter = new DBAdapter(com.phyder.paalan.activity.PaalanSplashActivity.this);
        pref = new PreferenceUtils(com.phyder.paalan.activity.PaalanSplashActivity.this);

        dbAdapter.open();
        if(dbAdapter.getMasterTableCount()<1){
            dbAdapter.insertTimeSpan("0","0","0","0");
        }
        dbAdapter.close();

        getRetrofitCallBannerSlider();

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_TIME_OUT);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent;
                    if (isScreensAvailable)
                        intent = new Intent(com.phyder.paalan.activity.PaalanSplashActivity.this, WhatsNew.class);
                    else
                        //todo change landing page
                        intent = new Intent(com.phyder.paalan.activity.PaalanSplashActivity.this, ActivityFragmentPlatform.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                }
            }
        };
        timer.start();

    }

    public void getRetrofitCallBannerSlider() {

        if (NetworkUtil.isInternetConnected(PaalanSplashActivity.this)) {
            CommanUtils.showDialog(PaalanSplashActivity.this);
            Device.newInstance(PaalanSplashActivity.this);

            RequestInitialData slidingBanner = RequestInitialData.get("", "", "0");
            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
            final Call<ResponseInitialData> appSyncBanner = mPaalanServices.appSyncBanner(slidingBanner);

            appSyncBanner.enqueue(new Callback<ResponseInitialData>() {
                @Override
                public void onResponse(Call<ResponseInitialData> call, Response<ResponseInitialData> response) {
                    CommanUtils.hideDialog();

                    ResponseInitialData.Bannerlist[] banners = response.body().getData()[0].getBannerlist();

                    CommanUtils.setDataInSharedPrefs(PaalanSplashActivity.this, "bannerUrlLength", banners);


                    ResponseInitialData.Screenlist[] screenlist = response.body().getData()[0].getScreenlist();
                    if (screenlist.length > 0) {
                        isScreensAvailable = true;
                        CommanUtils.setDataForScreens(PaalanSplashActivity.this, screenlist);
                    }

                }

                @Override
                public void onFailure(Call<ResponseInitialData> call, Throwable t) {
                    CommanUtils.hideDialog();
                }
            });
        }
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