package com.mobiowin.paalan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobiowin.paalan.activity.ActivityFragmentPlatform;
import com.mobiowin.paalan.db.DBAdapter;
import com.mobiowin.paalan.payload.response.OrgResCreateRequest;
import com.mobiowin.paalan.services.Device;
import com.mobiowin.paalan.utils.CommanUtils;
import com.mobiowin.paalan.utils.EditTextOpenSansRegular;
import com.mobiowin.paalan.utils.NetworkUtil;
import com.mobiowin.paalan.utils.PreferenceUtils;
import com.phyder.paalan.R;
import com.mobiowin.paalan.payload.request.OrgReqCreateRequest;
import com.mobiowin.paalan.services.PaalanServices;
import com.mobiowin.paalan.helper.Social;
import com.mobiowin.paalan.utils.ButtonOpenSansSemiBold;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentCreateRequest extends Fragment{

    private static final String TAG = FragmentCreateRequest.class.getCanonicalName();

    private EditTextOpenSansRegular edtName,edtTitle,edtSubTitle,edtDescription,edtOthers,edtLocation;
    private ButtonOpenSansSemiBold btnSubmit;

    private String requestID = "",strName = "",strTitle = "",strSubTitle = "",strDescription = "",strOthers = "",strLocation = "";

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
        edtName = (EditTextOpenSansRegular) view.findViewById(R.id.edt_name);
        edtSubTitle = (EditTextOpenSansRegular) view.findViewById(R.id.edt_subtitle);
        edtDescription = (EditTextOpenSansRegular) view.findViewById(R.id.edt_description);
        edtOthers = (EditTextOpenSansRegular) view.findViewById(R.id.edt_other);
        edtLocation = (EditTextOpenSansRegular) view.findViewById(R.id.edt_location);

        btnSubmit = (ButtonOpenSansSemiBold) view.findViewById(R.id.btn_submit);

        Bundle bundle = getArguments();

        if(bundle!=null){

            shouldBeUpdated = bundle.getBoolean("OPERATION_STATUS");

            requestID = bundle.getString("ID");
            edtName.setText(bundle.getString("NAME"));
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
                strName = edtName.getText().toString();

                if(strName.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_name));
                }else if(strTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_title));
                } else if(strSubTitle.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_sub_title));
                } else if(strDescription.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_description));
                } else if(strOthers.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_others));
                } else if(strLocation.isEmpty()){
                    CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_empty_location));
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
            String reqId = shouldBeUpdated ? requestID : "";

            OrgReqCreateRequest orgReqCreateRequest = OrgReqCreateRequest.get(pref.getOrgId(),reqId,
                    strName,strTitle,strSubTitle,strDescription,strOthers,strLocation,action,Device.getNotificationId(getActivity()));

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

                            int status = dbAdapter.populatingRequestIntoDB(pref.getOrgId(),response.body().getData()[0].getRequestid(),
                                    requestID, strName,strTitle, strSubTitle, strDescription, strOthers,
                                        strLocation,"F",pref.getLoginType());
                            String message = status == 0 ? getResources().getString(R.string.request_created) :
                                    getResources().getString(R.string.request_updated);
                            CommanUtils.showToast(getActivity(),message);
                            getActivity().getSupportFragmentManager().popBackStack();

                            dbAdapter.close();
                            getClearFields();
                        } else {
                            CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_went_wrong));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<OrgResCreateRequest> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }

    private void getClearFields(){

        requestID= "";
        strTitle = "";
        strSubTitle = "";
        strDescription = "";
        strOthers = "";
        strLocation = "";
        strName = "";

        edtTitle.setText("");
        edtSubTitle.setText("");
        edtDescription.setText("");
        edtOthers.setText("");
        edtLocation.setText("");
        edtName.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!shouldBeUpdated) {
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getStringArray(R.array.request_array)[0],
                    R.drawable.ic_arrow_back_black_24dp);
        }else{
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.update_request),
                    R.drawable.ic_arrow_back_black_24dp);
        }
    }

}
