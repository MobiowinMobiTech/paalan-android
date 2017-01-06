package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
import com.phyder.paalan.payload.request.organization.OrgReqSyncEvent;
import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
import com.phyder.paalan.payload.response.organization.OrgResSyncEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivitySyncEvent extends AppCompatActivity {

    Button btnTest;
    private String TAG = ActivitySyncEvent.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_event);

        final String orgId = "paalan1";

        btnTest = (Button) findViewById(R.id.btnSyncEvent);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrgReqSyncEvent reqUpdateProfile = OrgReqSyncEvent.get(orgId);

                Device.newInstance(ActivitySyncEvent.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<OrgResSyncEvent> resProfileCall = mPaalanServices.orgSyncEvent(reqUpdateProfile);
                resProfileCall.enqueue(new Callback<OrgResSyncEvent>() {
                    @Override
                    public void onResponse(Call<OrgResSyncEvent> call, Response<OrgResSyncEvent> response) {
                        Log.d(TAG, "onResponse: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<OrgResSyncEvent> call, Throwable t) {

                    }
                });
            }
        });
    }
}
