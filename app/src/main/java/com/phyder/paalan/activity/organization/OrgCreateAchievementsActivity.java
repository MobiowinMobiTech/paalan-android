package com.phyder.paalan.activity.organization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgCreateAchievementsActivity extends AppCompatActivity {


    Button btnAchiements;

    ArrayList<String> imgAchievements = null;
    private String TAG = OrgCreateAchievementsActivity.class.getSimpleName();

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

                String title = "Title";
                String subTitle = "Subtitle";
                String discription = "Description";
                String others = "Others";
                String orgId = "paalan1";


                OrgReqCreateAchievments createAchievments = OrgReqCreateAchievments.get(orgId, imgAchievements, discription, others, subTitle, title);

                Device.newInstance(OrgCreateAchievementsActivity.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrgResCreateAchievments> achievmentsCall = mPaalanServices.orgCreateAchievements(createAchievments);

                achievmentsCall.enqueue(new Callback<OrgResCreateAchievments>() {
                    @Override
                    public void onResponse(Call<OrgResCreateAchievments> call, Response<OrgResCreateAchievments> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                        Intent intent = new Intent(OrgCreateAchievementsActivity.this, OrgUpdateAchievementsActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<OrgResCreateAchievments> call, Throwable t) {

                    }
                });
            }
        });
    }
}
