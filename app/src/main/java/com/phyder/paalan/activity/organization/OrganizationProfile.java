package com.phyder.paalan.activity.organization;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.organization.OrganisationReqProfile;
import com.phyder.paalan.payload.response.organization.OrganizationResProfile;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrganizationProfile extends Fragment {

    Button btnOrgProfile;
    private String TAG = OrganizationProfile.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_organization_profile,null,false);
        init(view);
        return view;
    }

    private void init(View view) {

        btnOrgProfile = (Button) view.findViewById(R.id.btnOrgProfile);
        btnOrgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pincode = "12334";
        /*Base64 Decoded Image for Profile Image*/
                final String dpimage = Social.DP_IMG;
//        final ArrayList<OrganizationReqProfile> presence = new ArrayList<>();
//                final ArrayList<String> presence = new ArrayList<>();
                final String registrationno = "AKHPG8935";
//                final ArrayList<String> socialLink = new ArrayList<>();
                final String orgid ="paalan";
                final String role="7709642004";
                final String imeino="1236547893";
                final String isnewsletter="cmss";
                final String isregisterd="Y";
                final String fblink="https://www.facebook.com/Paalan-1729449980622228/";
                final String twitterlink="https://www.facebook.com/Paalan-1729449980622228/";
                final String websitelink="http://www.paalan.com/";
                final String linkedinlink="https://www.facebook.com/Paalan-1729449980622228/";
                final String preencearea="india";

//                OrganizationReqProfile reqProfile = OrganizationReqProfile.get(pincode, dpimage, presence, memberid, registrationno, name, state, socialLink, mobileno, country, city);
                OrganisationReqProfile reqProfile = OrganisationReqProfile.get(orgid,role,imeino,isnewsletter, isregisterd,registrationno, dpimage, fblink, linkedinlink, websitelink, twitterlink,preencearea );
                Device.newInstance(getActivity());


                Retrofit mRetrofit = NetworkUtil.getRetrofit();

                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
                Call<OrganizationResProfile> resProfileCall = mPaalanServices.orgProfile(reqProfile);

                resProfileCall.enqueue(new Callback<OrganizationResProfile>() {
                    @Override
                    public void onResponse(Call<OrganizationResProfile> call, Response<OrganizationResProfile> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("success")) {
                                Log.d(TAG, "onResponse: " + response.body().getData());
                            } else {
                                Toast.makeText(getActivity(), "Server error. Please try after sometime.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Server Not Available", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrganizationResProfile> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

}
