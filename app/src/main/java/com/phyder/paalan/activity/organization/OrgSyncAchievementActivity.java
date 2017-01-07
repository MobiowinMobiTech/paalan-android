package com.phyder.paalan.activity.organization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqSyncAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgSyncAchievementActivity extends AppCompatActivity {

    private String TAG = OrgSyncAchievementActivity.class.getSimpleName();

    Button btnSyncData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_sync_achievement);

        btnSyncData = (Button) findViewById(R.id.btnSyncData);
        btnSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgId = "paalan1";
                OrgReqSyncAchievement orgReqSyncAchievement = OrgReqSyncAchievement.get(orgId);
                Device.newInstance(OrgSyncAchievementActivity.this);
                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrgResSyncAchievement> achievementCall = mPaalanServices.orgSyncAchievements(orgReqSyncAchievement);

                achievementCall.enqueue(new Callback<OrgResSyncAchievement>() {
                    @Override
                    public void onResponse(Call<OrgResSyncAchievement> call, Response<OrgResSyncAchievement> response) {
                        Log.d(TAG, "onResponse: " + response.body());

                        Intent intent = new Intent(OrgSyncAchievementActivity.this, OrgDeleteAchievementActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<OrgResSyncAchievement> call, Throwable t) {

                    }
                });
            }
        });

    }
}
