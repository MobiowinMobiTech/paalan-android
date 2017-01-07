package com.phyder.paalan.activity.organization;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrganizationReqRegistration;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.individual.IndivitualResLogin;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgRegistrationActivity extends AppCompatActivity {

    private static final String TAG = "OrgRegistrationActivity.java";
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude;
    String imeiNo = "sdajnf";
    ArrayList<String> places = new ArrayList<>();
    String registrationNo = "sdfsdf";
    String isNewsLetter = "sdfsdf";
    String name = "sdfsdf";
    String role = "sdfsdf";
    String lat = "sdfsdf";
    String longi = "sdfsdf";
    String isRegistered = "sdfsdf";
    String password = "sdfsdf";
    ArrayList<String> socialLinks = new ArrayList<>();
    String email = "sdfsdf";
    String mobile = "sdfsdf";

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_registration);

        btnRegister = (Button) findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                places.add("1");
                places.add("2");
                places.add("3");
                places.add("4");

                socialLinks.add("facebook");
                socialLinks.add("twiter");
                socialLinks.add("gmail");
                OrganizationReqRegistration organizationReqRegistration = OrganizationReqRegistration.get(imeiNo, places, registrationNo, isNewsLetter, name, role, lat, longi, isRegistered, password, socialLinks, email, mobile);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

//                Call<OrganizationResRegistration> Call = mPaalanServices.orgRegistration(organizationReqRegistration);
//                Call<OrganizationResRegistration> registrationCall = mPaalanServices.orgRegistration(organizationReqRegistration);
//                registrationCall.enqueue(new Callback<OrganizationResRegistration>() {
//                    @Override
//                    public void onResponse(Call<OrganizationResRegistration> call, Response<OrganizationResRegistration> response) {
//                        if (response.isSuccessful()) {
//                            Log.d(TAG, "onResponse: Registration Response" + response.body().getStatus() + "\n" + response.body().getStatus());
//                        } else {
//                            Log.d(TAG, "onResponse: Registration Response" + response.errorBody());
//                        }
//                    }

//                    @Override
//                    public void onFailure(Call<OrganizationResRegistration> call, Throwable t) {
//                        Log.d(TAG, "onFailure: " + t.getMessage());
//                    }
//                });
            }
        });

//        GPSTracker gpsTracker = new GPSTracker(this);
    }
}
