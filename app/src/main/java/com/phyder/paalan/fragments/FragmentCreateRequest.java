package com.phyder.paalan.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.organization.OrgReqCreateAchievments;
import com.phyder.paalan.payload.request.organization.OrgReqCreateRequest;
import com.phyder.paalan.payload.response.organization.OrgResCreateAchievments;
import com.phyder.paalan.payload.response.organization.OrgResCreateRequest;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentCreateRequest extends Fragment{

    private static final String TAG = FragmentCreateRequest.class.getCanonicalName();

    private EditTextOpenSansRegular edtTitle,edtSubTitle,edtDescription,edtOthers,edtLocation;
    private ButtonOpenSansSemiBold btnSubmit;

    private String requestID = "",strTitle = "",strSubTitle = "",strDescription = "",strOthers = "",strLocation = "";

    private PreferenceUtils pref;
    private DBAdapter dbAdapter;
    private boolean shouldBeUpdated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.fragment_create_request,null,false);
            init(view);
            clickEventFire();
        return view;
    }

    private void init(View view) {

        pref = new PreferenceUtils(getActivity());
        dbAdapter = new DBAdapter(getActivity());
        edtTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_title);
        edtSubTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOthers = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);
        edtLocation = (EditTextOpenSansRegular) view.findViewById(R.id.edt_location);

        btnSubmit = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_submit);

        Bundle bundle = getArguments();

        if(bundle!=null){

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            requestID = bundle.getString("ID");
            edtTitle.setText(bundle.getString("TITLE"));
            edtSubTitle.setText(bundle.getString("SUB_TITLE"));
            edtDescription.setText(bundle.getString("DESCRIPTION"));
            edtOthers.setText(bundle.getString("OTHER"));
            edtLocation.setText(bundle.getString("LOCATION"));

        }

        btnSubmit.setText(shouldBeUpdated ? getResources().getString(R.string.Update) :
                getResources().getString(R.string.Create));
    }


    private void clickEventFire() {

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strTitle = edtTitle.getText().toString();
                strSubTitle = edtSubTitle.getText().toString();
                strDescription = edtDescription.getText().toString();
                strOthers = edtOthers.getText().toString();
                strLocation = edtLocation.getText().toString();

                if(strTitle.isEmpty()){
                    edtTitle.setError(getResources().getString(R.string.error_empty_title));
                } else if(strSubTitle.isEmpty()){
                    edtSubTitle.setError(getResources().getString(R.string.error_empty_sub_title));
                } else if(strDescription.isEmpty()){
                    edtDescription.setError(getResources().getString(R.string.error_empty_description));
                } else if(strOthers.isEmpty()){
                    edtOthers.setError(getResources().getString(R.string.error_empty_others));
                } else if(strLocation.isEmpty()){
                    edtLocation.setError(getResources().getString(R.string.error_empty_location));
                } else{

                    getRetrofitCall();

                }
            }
        });
    }

    public void getRetrofitCall(){

        if(NetworkUtil.isInternetConnected(getActivity())){

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());

            String action = shouldBeUpdated ? Social.UPDATE_ACTION : Social.EVENT_ACTION;
            OrgReqCreateRequest orgReqCreateRequest = OrgReqCreateRequest.get(pref.getOrgId(),
                    strTitle,strSubTitle,strDescription,strOthers,strLocation,action);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrgResCreateRequest> resCreateRequest = mPaalanServices.orgCreateRequest(orgReqCreateRequest);

            resCreateRequest.enqueue(new Callback<OrgResCreateRequest>() {
                @Override
                public void onResponse(Call<OrgResCreateRequest> call, Response<OrgResCreateRequest> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();
                            if(shouldBeUpdated){
                                dbAdapter.updateRequest(requestID, strTitle, strSubTitle, strDescription, strOthers,
                                        strLocation);
                                getActivity().getSupportFragmentManager().popBackStack();
                            }else {
                                dbAdapter.insertRequest(response.body().getData()[0].getRequestid(), strTitle, strSubTitle,
                                        strDescription, strOthers,strLocation,"F");
                            }
                            dbAdapter.close();
                            getClearFields();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrgResCreateRequest> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        }else{
            CommanUtils.getInternetAlert(getActivity());
        }
    }

    private void getClearFields(){

        requestID= "";
        strTitle = "";
        strSubTitle = "";
        strDescription = "";
        strOthers = "";
        strLocation = "";

        edtTitle.setText("");
        edtSubTitle.setText("");
        edtDescription.setText("");
        edtOthers.setText("");
        edtLocation.setText("");

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!shouldBeUpdated) {
            ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getStringArray(R.array.request_array)[0]);
        }else{
            ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.update_request));
        }
    }

}
