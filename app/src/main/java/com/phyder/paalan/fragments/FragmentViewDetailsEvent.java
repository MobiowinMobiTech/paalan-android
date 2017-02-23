package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.OrganizerProfileListener;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.payload.request.organization.OrgReqDeleteEvent;
import com.phyder.paalan.payload.response.ResponseOrganizerProfile;
import com.phyder.paalan.payload.response.organization.OrgResDeleteEvent;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;
import com.phyder.paalan.utils.TextViewOpenSansSemiBold;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentViewDetailsEvent extends Fragment {

    private final static String TAG = FragmentViewDetailsEvent.class.getCanonicalName();

    private LinearLayout llOrganizedBy;
    private TextViewOpenSansRegular txtName,txtTitle,txtSubTitle,txtDesc,txtOther,txtCategory,txtLocation,txtStartdate,txtEnddate;
    private TextViewOpenSansSemiBold txtStartDateDay,txtEndDateDay;
    private ButtonOpenSansSemiBold btnUpdate,btnDelete,btnViewProfile;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private String orgId,eventId,strName,strTitle,strSubTitle,strDescriptions,strOthers,strStartdate,strEnddate,strCategory,strLocation;

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

        llOrganizedBy = (LinearLayout) view.findViewById(R.id.llOrganizedby);

        txtName = (TextViewOpenSansRegular) view.findViewById(R.id.txtNameValue);
        txtTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtTitleValue);
        txtSubTitle = (TextViewOpenSansRegular) view.findViewById(R.id.txtSubTitleValue);
        txtDesc = (TextViewOpenSansRegular) view.findViewById(R.id.txtDescValue);
        txtOther = (TextViewOpenSansRegular) view.findViewById(R.id.txtOtherValue);
        txtCategory =(TextViewOpenSansRegular)view.findViewById(R.id.txtCategoryValue);
        txtLocation =(TextViewOpenSansRegular)view.findViewById(R.id.txtLocationValue);
        txtStartdate =(TextViewOpenSansRegular)view.findViewById(R.id.txtStartDate);
        txtEnddate =(TextViewOpenSansRegular)view.findViewById(R.id.txtEndDate);

        txtStartDateDay =(TextViewOpenSansSemiBold) view.findViewById(R.id.txtStartDateDay);
        txtEndDateDay =(TextViewOpenSansSemiBold) view.findViewById(R.id.txtEndDateDay);

        btnUpdate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnUpdateEvent);
        btnDelete = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDeleteEvent);
        btnViewProfile = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnView);

        if(pref.getLoginType().equals(Social.IND_ENTITY)){
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }else{
            llOrganizedBy.setVisibility(View.GONE);
        }

    }



    private void getPoulatedData() {

        if(PaalanGetterSetter.getEventID()!=null) {
            eventId = PaalanGetterSetter.getEventID();
        }

        dbAdapter.open();
        cursor = dbAdapter.getEventById(eventId);
        if(cursor!=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    orgId = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_ORG_ID));
                    strName = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_NAME));
                    strTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_TITLE));
                    strSubTitle = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_SUB_TITLE));
                    strDescriptions = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_DESCRIPTION));
                    strOthers = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_OTHERS));
                    strStartdate = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_START_DATE));
                    strEnddate = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_END_DATE));
                    strCategory = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_CATEGORY));
                    strLocation = cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_LOCATION));
                }while (cursor.moveToNext());
            }
        }
        dbAdapter.close();

        txtName.setText(strName);
        txtTitle.setText(strTitle);
        txtSubTitle.setText(strSubTitle);
        txtDesc.setText(strDescriptions);
        txtOther.setText(strOthers);
        txtStartdate.setText(strStartdate);
        txtEnddate.setText(strEnddate);
        txtCategory.setText(strCategory);
        txtLocation.setText(strLocation);

        try{
            txtStartDateDay.setText(strStartdate.substring(0,2));
            txtEndDateDay.setText(strEnddate.substring(0,2));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void clickEventFire() {

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("OPERATION_STATUS",true);
                bundle.putString("ID",eventId);
                bundle.putString("NAME",strName);
                bundle.putString("TITLE",strTitle);
                bundle.putString("SUB_TITLE",strSubTitle);
                bundle.putString("DESCRIPTION",strDescriptions);
                bundle.putString("OTHER",strOthers);
                bundle.putString("STARTDATE",strStartdate);
                bundle.putString("ENDDATE",strEnddate);
                bundle.putString("CATEGORY",strCategory);
                bundle.putString("LOCATION",strLocation);

                Fragment fragment = new FragmentCreateEvent();
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

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.getOrganizerProfile(getActivity(), orgId, new OrganizerProfileListener() {
                    @Override
                    public void onSuccess(Response<ResponseOrganizerProfile> responseOrganizerProfile) {
                        Bundle bundle=new Bundle();
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_DP_IMG,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getDpImgLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_NAME,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getName());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_MOBILE_NO,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getMobileNo());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_EMAIL,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getEmailId());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_ADDRESS,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getAddress());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_ROLE,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getRole());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_IS_NEWS_LETTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getIsNewsLetter());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_IS_GOVT_REGISTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getIsGovtRegister());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_REGISTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getRegistrationNo());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_FB_LINK,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getFbLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_LINKEDIN,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getLinkedinLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_WEBSITE,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getWebsiteLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_TWITTER,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getTwitterLink());
                        bundle.putString(Attributes.Database.GROUPS_PROFILE_PRESENCE_AREA,responseOrganizerProfile.body().getData()[0].getOrgprofilelist()[0].getPresenceArea());
                        Fragment fragment =new FragmentGroupsProfile();
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                                addToBackStack(null).commit();
                    }
                });
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
                            dbAdapter.deleteEvent(eventId);
                            dbAdapter.close();
                            getActivity().getSupportFragmentManager().popBackStack();
                            CommanUtils.showToast(getActivity(), getResources().getString(R.string.event_deleted));
                        } else {
                            CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_went_wrong));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<OrgResDeleteEvent> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.showToast(getActivity(), getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if(pref.getLoginType().equals(Social.IND_ENTITY)){
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.event_details),
                    R.drawable.ic_arrow_back_black_24dp);
        }else {
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.update_delete_event),
                    R.drawable.ic_arrow_back_black_24dp);
        }
        getPoulatedData();
    }
}
