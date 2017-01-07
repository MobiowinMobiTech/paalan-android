package com.phyder.paalan.activity.organization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResUpdateAchievments;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgUpdateAchievementsActivity extends AppCompatActivity {


    Button btnAchiements;

    ArrayList<String> imgAchievements = null;
    private String TAG = OrgUpdateAchievementsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_create_achievements);

        btnAchiements = (Button) findViewById(R.id.btn_achievments);

        btnAchiements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgAchievements = new ArrayList<String>();
//                for (int i = 0; i < imgAchievements.size(); i++) {
//                    imgAchievements.add(i, Social.DP_IMG);
//                }
                imgAchievements.add(Social.DP_IMG);
                imgAchievements.add(Social.DP_IMG);
                imgAchievements.add(Social.DP_IMG);
                imgAchievements.add(Social.DP_IMG);

                String title = "Test Title";
                String subTitle = "Test Subtitle";
                String discription = "Description";
                String others = "Others";
                String orgId = "paalan1";
                String achievementID = "paalan1_achievement_4691";


                OrgReqUpdateAchievments createAchievments = OrgReqUpdateAchievments.get(orgId,achievementID, imgAchievements, discription, others, subTitle, title);

                Device.newInstance(OrgUpdateAchievementsActivity.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrgResUpdateAchievments> achievmentsCall = mPaalanServices.orgUpdateAchievements(createAchievments);

                achievmentsCall.enqueue(new Callback<OrgResUpdateAchievments>() {
                    @Override
                    public void onResponse(Call<OrgResUpdateAchievments> call, Response<OrgResUpdateAchievments> response) {
                        Intent intent = new Intent(OrgUpdateAchievementsActivity.this,OrgSyncAchievementActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<OrgResUpdateAchievments> call, Throwable t) {

                    }
                });

            }
        });
    }
}
