package com.phyder.paalan.activity.organization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgDeleteAchievementActivity extends AppCompatActivity {

    private String TAG = OrgDeleteAchievementActivity.class.getSimpleName();

    Button btnDeleteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_delete_achievement);

        btnDeleteData = (Button) findViewById(R.id.btnDeleteData);
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgId = "paalan1";
                String achievementId = "paalan1_achievement_4691";

                OrgReqDeleteAchievement orgReqDeleteAchievement = OrgReqDeleteAchievement.get(orgId, achievementId);

                Device.newInstance(OrgDeleteAchievementActivity.this);
                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrgResDeleteAchievement> achievementCall = mPaalanServices.orgDeleteAchievements(orgReqDeleteAchievement);

                achievementCall.enqueue(new Callback<OrgResDeleteAchievement>() {
                    @Override
                    public void onResponse(Call<OrgResDeleteAchievement> call, Response<OrgResDeleteAchievement> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<OrgResDeleteAchievement> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });

    }
}
