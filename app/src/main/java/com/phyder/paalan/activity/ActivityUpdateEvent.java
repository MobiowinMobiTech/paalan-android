package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqUpdateEvent;
import com.phyder.paalan.payload.response.organization.OrgResUpdateEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityUpdateEvent extends AppCompatActivity {

    Button btnUpdateEvent;
    private String TAG = ActivityUpdateEvent.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

        final String orgId = "paalan1";
        final String eventId = "paalan1_0949";
        final String title = "ToDay Special";
        final String subTitle = "Football match";
        final String description = "Testing to create Location";
        final String startDate = "1483488000020";
        final String endDate = "1483488000021";
        final String location = "1650 Charleston Road";
        final String other = "Testing for Update Event's";

        btnUpdateEvent = (Button) findViewById(R.id.btnUpdateEvent);
        btnUpdateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrgReqUpdateEvent orgReqUpdateEvent = OrgReqUpdateEvent.get(orgId, eventId, title, subTitle, description, startDate, endDate, location, other);

                Device.newInstance(ActivityUpdateEvent.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<OrgResUpdateEvent> eventCall = mPaalanServices.orgupdateEvent(orgReqUpdateEvent);

                eventCall.enqueue(new Callback<OrgResUpdateEvent>() {
                    @Override
                    public void onResponse(Call<OrgResUpdateEvent> call, Response<OrgResUpdateEvent> response) {
                        Log.d(TAG, "onResponse: " + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<OrgResUpdateEvent> call, Throwable t) {

                    }
                });
            }
        });

    }
}
