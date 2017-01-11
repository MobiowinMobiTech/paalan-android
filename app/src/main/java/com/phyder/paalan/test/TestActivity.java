//package com.phyder.paalan.test;
//
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.phyder.paalan.R;
//import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
//import com.phyder.paalan.payload.request.organization.OrgReqCreateEvent;
//import com.phyder.paalan.payload.request.organization.OrganizationReqProfile;
//import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
//import com.phyder.paalan.payload.response.organization.OrgResCreateEvent;
//import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
//import com.phyder.paalan.services.Device;
//import com.phyder.paalan.services.PaalanServices;
//import com.phyder.paalan.social.Social;
//import com.phyder.paalan.utils.NetworkUtil;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//import static android.R.attr.country;
//import static android.R.attr.end;
//
//public class TestActivity extends AppCompatActivity {
//
//    private static final String TAG = "TestActivity";
//    Button btnTest;
//
//    private static final int PERMISSION_REQUEST_CODE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_login);
////
////        final String orgId = "paalan1";
////        final String eventId = "";
////        final String title = "Children,s Day Special";
////        final String subTitle = "Football match with old age peoples";
////        final String description = "Testing to create Location";
////        final String startDate = "1483488000020";
////        final String endDate = "1483488000021";
////        final String location = "1650 Charleston Road";
////        final String other = "Testing";
////
////        btnTest = (Button) findViewById(R.id.btnTest);
////        btnTest.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                OrgReqCreateEvent reqUpdateProfile = OrgReqCreateEvent.get(orgId, eventId, title, subTitle, description, startDate, endDate, location, other);
////
////                Device.newInstance(TestActivity.this);
////
////                Retrofit mRetrofit = NetworkUtil.getRetrofit();
////
////                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
////                Call<OrgResCreateEvent> resProfileCall = mPaalanServices.orgCreateEvent(reqUpdateProfile);
////                resProfileCall.enqueue(new Callback<OrgResCreateEvent>() {
////                    @Override
////                    public void onResponse(Call<OrgResCreateEvent> call, Response<OrgResCreateEvent> response) {
////                        Log.d(TAG, "onResponse: " + response.body());
////                    }
////
////                    @Override
////                    public void onFailure(Call<OrgResCreateEvent> call, Throwable t) {
////
////                    }
////                });
////            }
////        });
//    }
//
//
//}
