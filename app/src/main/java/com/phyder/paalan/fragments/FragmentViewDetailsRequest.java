package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteAchievement;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteRequest;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResDeleteRequest;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 18/1/17.
 */

public class FragmentViewDetailsRequest extends Fragment {

    private final static String TAG = FragmentViewDetailsRequest.class.getCanonicalName();

    private TextViewOpenSansRegular txtTitle,txtSubTitle,txtDesc,txtOther,txtLocation;
    private ButtonOpenSansSemiBold btnUpdate,btnDelete;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private String requestID,strTitle,strSubTitle,strDescriptions,strOthers,strLocation;

    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_details_request,container,false);
        init(view);
        clickEventFire();
        return view;
    }


    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        txtTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtTitleValue);
        txtSubTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtSubTitleValue);
        txtDesc = (TextViewOpenSansRegular) view.findViewById(R.id.txtDescValue);
        txtOther = (TextViewOpenSansRegular) view.findViewById(R.id.txtOtherValue);
        txtLocation = (TextViewOpenSansRegular) view.findViewById(R.id.txtLocationValue);

        btnUpdate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnUpdateRequest);
        btnDelete = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDeleteRequest);

    }



    private void getPoulatedData() {

        if(PaalanGetterSetter.getRequestID()!=null) {
            requestID = PaalanGetterSetter.getRequestID();
        }

        dbAdapter.open();
        cursor = dbAdapter.getRequestById(requestID);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    strTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_TITLE));
                    strSubTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_SUB_TITLE));
                    strDescriptions = cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_DESCRIPTION));
                    strOthers = cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_OTHER));
                    strLocation = cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_LOCATION));
                }while (cursor.moveToNext());
            }
        }
        dbAdapter.close();

        txtTitle.setText(strTitle);
        txtSubTitle.setText(strSubTitle);
        txtDesc.setText(strDescriptions);
        txtOther.setText(strOthers);
        txtLocation.setText(strLocation);

    }




    private void clickEventFire() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("OPERATION_STATUS",true);
                bundle.putString("ID",requestID);
                bundle.putString("TITLE",strTitle);
                bundle.putString("SUB_TITLE",strSubTitle);
                bundle.putString("DESCRIPTION",strDescriptions);
                bundle.putString("OTHER",strOthers);
                bundle.putString("LOCATION",strLocation);
                Fragment fragment = new FragmentCreateRequest();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                        addToBackStack(null).commit();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getRetrofitCall();
            }
        });
    }




    public void getRetrofitCall() {

        if(NetworkUtil.isInternetConnected(getActivity())) {

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());
            OrgReqDeleteRequest reqDeleteRequest = OrgReqDeleteRequest.get(pref.getOrgId(), requestID);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<OrgResDeleteRequest> resLogin = mPaalanServices.orgDeleteRequest(reqDeleteRequest);

            resLogin.enqueue(new Callback<OrgResDeleteRequest>() {
                @Override
                public void onResponse(Call<OrgResDeleteRequest> call, Response<OrgResDeleteRequest> response) {
                    Log.e(TAG, "onResponse: " + response.body());

                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter.open();
                            dbAdapter.updateRequestTimeSpan(response.body().getMessage());
                            dbAdapter.deleteRequest(requestID,"T");
                            dbAdapter.close();
                            getActivity().getSupportFragmentManager().popBackStack();

                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrgResDeleteRequest> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        }else{
            CommanUtils.getInternetAlert(getActivity());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(),getResources().getString(R.string.update_delete_request));
        getPoulatedData();
    }
}
