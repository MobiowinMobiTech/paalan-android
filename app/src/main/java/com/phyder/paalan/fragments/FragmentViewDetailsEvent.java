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
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.response.organization.OrgResDeleteAchievement;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.text.AttributedCharacterIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 18/1/17.
 */

public class FragmentViewDetailsEvent extends Fragment {

    private final static String TAG = FragmentViewDetailsEvent.class.getCanonicalName();

    private LinearLayout Attachedlayout;
    private TextViewOpenSansRegular txtTitle,txtSubTitle,txtDesc,txtOther,txtStartdate,txtEnddate;
    private ButtonOpenSansSemiBold btnUpdate,btnDelete;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private String eventId,strTitle,strSubTitle,strDescriptions,strOthers,strStartdate,strEnddate;

    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_details_event,container,false);
        init(view);
        getPoulatedData();
        clickEventFire();
        return view;
    }


    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        Attachedlayout = (LinearLayout) view.findViewById(R.id.Attachmentevent);

        txtTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtTitleValue);
        txtSubTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtSubTitleValue);
        txtDesc = (TextViewOpenSansRegular) view.findViewById(R.id.txtDescValue);
        txtOther = (TextViewOpenSansRegular) view.findViewById(R.id.txtOtherValue);
        txtStartdate =(TextViewOpenSansRegular)view.findViewById(R.id.txtstartdate);
        txtEnddate =(TextViewOpenSansRegular)view.findViewById(R.id.txtEnddate);

        btnUpdate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnUpdateAchievement);
        btnDelete = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDeleteAchievement);

    }



    private void getPoulatedData() {

        if(PaalanGetterSetter.getEventId()!=null) {
            eventId = PaalanGetterSetter.getEventId();
        }

        dbAdapter.open();
        cursor = dbAdapter.getEventById(eventId);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    strTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_TITLE));
                    strSubTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_SUB_TITLE));
                    strDescriptions = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_DESCRIPTION));
                    strOthers = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_OTHERS));
                    strStartdate = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_START_DATE));
                    strEnddate = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_END_DATE));

                }while (cursor.moveToNext());
            }
        }
        dbAdapter.close();

        txtTitle.setText(strTitle);
        txtSubTitle.setText(strSubTitle);
        txtDesc.setText(strDescriptions);
        txtOther.setText(strOthers);
        txtStartdate.setText(strStartdate);
        txtEnddate.setText(strEnddate);
    }


    private void clickEventFire() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("OPERATION_STATUS",true);
                bundle.putString("ID",eventId);
                bundle.putString("TITLE",strTitle);
                bundle.putString("SUB_TITLE",strSubTitle);
                bundle.putString("DESCRIPTION",strDescriptions);
                bundle.putString("OTHER",strOthers);
                bundle.putString("STARTDATE",strStartdate);
                bundle.putString("ENDDATE",strEnddate);

                Fragment fragment = new FragmentCreateEventRequest();
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
            OrgReqDeleteEvent reqDeleteEvent = OrgReqDeleteEvent.get(pref.getOrgId(), eventId);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<OrgResDeleteEvent> resLogin = mPaalanServices.orgDeleteEvent(reqDeleteEvent);

            resLogin.enqueue(new Callback<OrgResDeleteEvent>() {
                @Override
                public void onResponse(Call<OrgResDeleteEvent> call, Response<OrgResDeleteEvent> response) {
                    Log.e(TAG, "onResponse: " + response.body());

                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter.open();
                            dbAdapter.updateEventTimeSpan(response.body().getMessage());
                            dbAdapter.deleteEvent(eventId,"T");
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
                public void onFailure(Call<OrgResDeleteEvent> call, Throwable t) {
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
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(),getResources().getString(R.string.update_delete_event));
        getPoulatedData();
    }
}
