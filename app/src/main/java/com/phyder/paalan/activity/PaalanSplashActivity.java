package com.phyder.paalan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.RequestInitialData;
import com.phyder.paalan.payload.response.ResponseInitialData;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaalanSplashActivity extends AppCompatActivity {

    private static final String TAG = PaalanSplashActivity.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 3000;

    private DBAdapter dbAdapter;

    Set<String> bannerList = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paalan_splash);

        getInitialAppData();
        dbAdapter = new DBAdapter(PaalanSplashActivity.this);
        dbAdapter.open();
        if (dbAdapter.getMasterTableCount() < 1) {
            dbAdapter.insertTimeSpan("0", "0", "0");
        }
        dbAdapter.close();

        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SPLASH_TIME_OUT);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(PaalanSplashActivity.this, WhatsNew.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();

    }

    /**
     * Function to get initial data from server
     */
    public void getInitialAppData() {
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


//                    ResponseInitialData.Bannerlist[] banners = response.body().getData()[0].getBannerlist();
//
//                    CommanUtils.setDataInSharedPrefs(PaalanSplashActivity.this, "bannerUrlLength", banners);


                    ResponseInitialData.Screenlist[] screenlist = response.body().getData()[0].getScreenlist();
                    Log.d(TAG, "onResponse: screen length "+screenlist.length);
                    Log.d(TAG, "onResponse: screen name "+screenlist[0].getScreenName());
                    Log.d(TAG, "onResponse: screen screen link "+screenlist[0].getScreenImgLink());
                    Log.d(TAG, "onResponse: screen info "+screenlist[0].toString());

                    CommanUtils.setDataForScreens(PaalanSplashActivity.this,screenlist);



                }

                @Override
                public void onFailure(Call<ResponseInitialData> call, Throwable t) {

                }
            });
        }
    }
}
