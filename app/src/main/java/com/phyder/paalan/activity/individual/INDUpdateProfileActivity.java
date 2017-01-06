package com.phyder.paalan.activity.individual;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.individual.IndivitualReqUpdateProfile;
import com.phyder.paalan.payload.response.individual.IndivitualResUpdateProfile;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class INDUpdateProfileActivity extends AppCompatActivity {

    Button btnUpdateIndProfile;

    EditText userFirstName;
    private static final String TAG = INDUpdateProfileActivity.class.getSimpleName();

    final String pincode = "12334";
    /*Base64 Decoded Image for Profile Image*/
    final String dpimage = Social.DP_IMG;
    final String memberid = "1748731722";
    final String registrationno = "dfhgdfgh";
    final String firstName = "aman";
    final String lastName = "priyadrshi";
    final String dob = "31-05-1988";
    final String gender = "male";
    final String city = "mumbai";
    final String state = "maharashtra";
    final String country = "india";
    final String mobileno = "400065";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        btnUpdateIndProfile = (Button) findViewById(R.id.btnUpdateIndProfile);
// TODO: 4/1/17
        userFirstName = (EditText) findViewById(R.id.edit_ngo_name);

        btnUpdateIndProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IndivitualReqUpdateProfile reqUpdateProfile = IndivitualReqUpdateProfile.get(pincode, dpimage, memberid, dob, gender, firstName, lastName, state, mobileno, country, city);

                Device.newInstance(INDUpdateProfileActivity.this);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<IndivitualResUpdateProfile> resProfileCall = mPaalanServices.indUpdateProfile(reqUpdateProfile);
                resProfileCall.enqueue(new Callback<IndivitualResUpdateProfile>() {
                    @Override
                    public void onResponse(Call<IndivitualResUpdateProfile> call, Response<IndivitualResUpdateProfile> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getSts().equals("success")) {
                                Log.d(TAG, "onResponse: " + response.body().getData());
                            } else {
                                Toast.makeText(INDUpdateProfileActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(INDUpdateProfileActivity.this, "Server is Temp out of Service.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<IndivitualResUpdateProfile> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
}
