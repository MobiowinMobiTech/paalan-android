package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteEventActivity extends AppCompatActivity {

    Button btnTestDeleteEvent;
    private String TAG = DeleteEventActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        btnTestDeleteEvent = (Button) findViewById(R.id.btnTestDeleteEvent);
        btnTestDeleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orgId = "paalan1";
                String eventId = "paalan1_0949";
                OrgReqDeleteEvent reqDeleteEvent = OrgReqDeleteEvent.get(orgId, eventId);

                Device.newInstance(DeleteEventActivity.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<OrgResDeleteEvent> eventCall = mPaalanServices.orgDeleteEvent(reqDeleteEvent);
                eventCall.enqueue(new Callback<OrgResDeleteEvent>() {
                    @Override
                    public void onResponse(Call<OrgResDeleteEvent> call, Response<OrgResDeleteEvent> response) {
                        Log.d(TAG, "onResponse: " + response.body().getMessage() + "\n" + response.body().getData()[0]);
                    }

                    @Override
                    public void onFailure(Call<OrgResDeleteEvent> call, Throwable t) {

                    }
                });

            }
        });
    }
}
