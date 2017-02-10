package com.phyder.paalan.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.activity.Donate;
import com.phyder.paalan.adapter.HorizontalListVAdapter;
import com.phyder.paalan.adapter.SlidingImageAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.CircleIndicator;
import com.phyder.paalan.helper.DialogPopupListener;
import com.phyder.paalan.helper.GPSTracker;
import com.phyder.paalan.payload.request.RequestIndDashboard;
import com.phyder.paalan.payload.response.ResponseIndDashboard;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansSemiBold;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Raheel on 16/1/17.
 */

public class FragmentIndDashboard extends Fragment implements DialogPopupListener {

    private static final String TAG = FragmentIndDashboard.class.getSimpleName();
    private static final int PERMISSION = 1;
    private boolean isFetched = false;
    private boolean isDeny = false;

    private ViewPager mPager;
    private CircleIndicator mCircleIndicator;
    private List<String> images;
    private Handler handler = new Handler();
    private Runnable refresh;
    private int itemPos = 0;

    private GPSTracker gps;
    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private TextViewOpenSansSemiBold txtEventSeeMore,txtSocialSeeMore ,txtGroupSeeMore,txtAchievementsSeeMore;
    private ButtonOpenSansSemiBold btnDonate;
    private LinearLayout llEvent, llGroup, llRequest, llAchievement;
    private RecyclerView recycleEvent, recycleGroup, recycleRequest, recycleAchievement;

    private LinearLayoutManager layoutManagerEvent, layoutManagerGroup, layoutManagerSocial, layoutManagerAchievement;
    private HorizontalListVAdapter eventAdapter, groupAdapter, achievementAdapter, requestAdapter;

    private Cursor eventCursor, groupCursor, requestCursor, achievementCursor;
    private ArrayList<String> eventLists, groupLists, requestLists, achievementLists;
    private ArrayList<String> eventLogo,eventIdsLists,groupLogo, groupIdsLists, groupOrgIdsLists, requestIdsLists, achievementIdsLists;

    private SlidingImageAdapter slidingImageAdapter;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_ind_dashboard, container, false);
                init(view);
                clickEventFire();
            return view;
        }



        private void init(View view) {

                dbAdapter = new DBAdapter(getActivity());
                pref = new PreferenceUtils(getActivity());

                mPager = (ViewPager) view.findViewById(R.id.image_pager);
                mCircleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);

                recycleEvent = (RecyclerView) view.findViewById(R.id.event_recycler_view);
                recycleGroup = (RecyclerView) view.findViewById(R.id.groups_recycler_view);
                recycleRequest = (RecyclerView) view.findViewById(R.id.social_recycler_view);
                recycleAchievement = (RecyclerView) view.findViewById(R.id.achievement_recycler_view);

                llEvent = (LinearLayout) view.findViewById(R.id.llEventStatus);
                llGroup = (LinearLayout) view.findViewById(R.id.llGroupsStatus);
                llRequest = (LinearLayout) view.findViewById(R.id.llRequestStatus);
                llAchievement = (LinearLayout) view.findViewById(R.id.llAchievementStatus);

                txtEventSeeMore = (TextViewOpenSansSemiBold) view.findViewById(R.id.txtEventSeeMore);
                txtGroupSeeMore = (TextViewOpenSansSemiBold) view.findViewById(R.id.txtGroupSeeMore);
                txtSocialSeeMore = (TextViewOpenSansSemiBold) view.findViewById(R.id.txtSocialSeeMore);
                txtAchievementsSeeMore = (TextViewOpenSansSemiBold) view.findViewById(R.id.txtAchievementSeeMore);

                btnDonate = (ButtonOpenSansSemiBold) view.findViewById(R.id.btnDonate);

                eventLists = new ArrayList<String>();
                groupLists = new ArrayList<String>();
                requestLists = new ArrayList<String>();
                achievementLists = new ArrayList<String>();
                eventIdsLists = new ArrayList<String>();
                eventLogo = new ArrayList<String>();
                groupLogo = new ArrayList<String>();
                groupIdsLists = new ArrayList<String>();
                requestIdsLists = new ArrayList<String>();
                achievementIdsLists = new ArrayList<String>();
                groupOrgIdsLists = new ArrayList<String>();

            }

        private void handlingSlideShow() {

            images = new ArrayList<>();
            images.clear();
            if(CommanUtils.getDataFromSharedPrefs(getActivity(), "bannerUrlLength")!=null) {
                for (int i = 0; i < Integer.parseInt(CommanUtils.getDataFromSharedPrefs(getActivity(), "bannerUrlLength")); i++) {
                    String bannerImageURL = CommanUtils.getDataFromSharedPrefs(getActivity(), "bannerUrl" + i);
                    images.add(bannerImageURL);
                }
            }else{
                for(int i=0;i<4;i++){
                    images.add("Drawables");
                }
            }

            slidingImageAdapter =new SlidingImageAdapter(getActivity(), images);
            mPager.setAdapter(slidingImageAdapter);
            mCircleIndicator.setViewPager(mPager);
        }

        private void clickEventFire() {


            txtEventSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(eventLists.size()>0) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.platform, new FragmentViewEvent())
                                .addToBackStack(null).commit();
                    }else{
                        CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.no_events));
                    }
                }
            });


            txtSocialSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(requestLists.size()>0) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.platform, new FragmentViewRequest())
                                .addToBackStack(null).commit();
                    }else{
                        CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.no_requests));
                    }
                }
            });


            txtAchievementsSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(achievementLists.size()>0) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.platform, new FragmentViewAchievement())
                                .addToBackStack(null).commit();
                    }else{
                        CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.no_achievements));
                    }
                }
            });

            txtGroupSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(groupLists.size()>0) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.platform, new FragmentViewGroups())
                                .addToBackStack(null).commit();
                    }else{
                        CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.no_groups));
                    }
                }
            });

            btnDonate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), Donate.class));
                }
            });

        }


    private boolean requestPermission(String permissionName, int permissionRequestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (getActivity().checkSelfPermission(permissionName) == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {

            if(!isDeny) {
                requestPermissions(new String[]{permissionName}, permissionRequestCode);
            }
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

       switch (requestCode) {
            case PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!isFetched)
                        getRetrofitCall();

                }else if(pref.getLocation()!=null) {
                    isFetched = true;
                    isDeny =true;
                    callApi(Double.parseDouble(pref.getLocation().split("~")[0]),
                    Double.parseDouble(pref.getLocation().split("~")[1]));
                }else {
                    ActivityFragmentPlatform.getFinished(getActivity());
                }
            }
        }
    }



        public void getRetrofitCall() {

            gps = new GPSTracker(getActivity());

            if(pref.getLocation()!=null && !gps.canGetLocation()){
                CommanUtils.showGpsLocationDialog(getActivity(),"Use Existing",this);
            }else if(!gps.canGetLocation()){
                CommanUtils.showGpsLocationDialog(getActivity(),"Cancel",this);
            }else {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                if(latitude != 0.0 && longitude != 0.0){
                    pref.setLocation(latitude + "~" + longitude);
                    isFetched = true;
                    callApi(latitude, longitude);
                }
                else if(!NetworkUtil.isWifiConnected(getActivity())){
                    CommanUtils.showWifiLocationDialog(getActivity(),this);
                }else{
                    CommanUtils.showRetryDialog(getActivity(),this);
                }
            }
        }


        public void getPopulated() {

            eventLists.clear();
            requestLists.clear();
            groupLists.clear();
            achievementLists.clear();
            eventLogo.clear();
            groupLogo.clear();
            eventIdsLists.clear();
            requestIdsLists.clear();
            groupIdsLists.clear();
            achievementIdsLists.clear();
            groupOrgIdsLists.clear();


            dbAdapter.open();
            eventCursor = dbAdapter.getAllEvent("F");

            if(eventCursor != null)
                eventCursor.moveToFirst();
            if(eventCursor.moveToFirst()){
                do{
                    eventLogo.add(eventCursor.getString(eventCursor.getColumnIndex(Attributes.Database.EVENT_LOGO)));
                    eventLists.add(eventCursor.getString(eventCursor.getColumnIndex(Attributes.Database.EVENT_TITLE)));
                    eventIdsLists.add(eventCursor.getString(eventCursor.getColumnIndex(Attributes.Database.EVENT_ID)));
                }while (eventCursor.moveToNext());
            }

            requestCursor = dbAdapter.getAllRequests("F");

            if(requestCursor != null)
                requestCursor.moveToFirst();
            if(requestCursor.moveToFirst()){
                do{
                    requestLists.add(requestCursor.getString(requestCursor.getColumnIndex(Attributes.Database.REQUEST_TITLE)));
                    requestIdsLists.add(requestCursor.getString(requestCursor.getColumnIndex(Attributes.Database.REQUEST_ID)));
                }while (requestCursor.moveToNext());
            }

            groupCursor = dbAdapter.getAllGroups("F");

            if(groupCursor != null)
                groupCursor.moveToFirst();
            if(groupCursor.moveToFirst()){
                do{
                    groupLists.add(groupCursor.getString(groupCursor.getColumnIndex(Attributes.Database.GROUPS_NAME)));
                    groupIdsLists.add(groupCursor.getString(groupCursor.getColumnIndex(Attributes.Database.GROUPS_ID)));
                    groupOrgIdsLists.add(groupCursor.getString(groupCursor.getColumnIndex(Attributes.Database.GROUPS_ORGANIZATION_ID)));
                    groupLogo.add(dbAdapter.getGroupsProfileDp(groupCursor.getString(groupCursor.getColumnIndex(Attributes.Database.GROUPS_ORGANIZATION_ID))));
                }while (groupCursor.moveToNext());
            }

            achievementCursor = dbAdapter.getAllAchievements("F");

            if(achievementCursor != null)
                achievementCursor.moveToFirst();
            if(achievementCursor.moveToFirst()){
                do{
                    achievementLists.add(achievementCursor.getString(achievementCursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE)));
                    achievementIdsLists.add(achievementCursor.getString(achievementCursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_ID)));
                }while (achievementCursor.moveToNext());
            }


            dbAdapter.close();


            recycleEvent.setHasFixedSize(true);
            layoutManagerEvent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recycleEvent.setLayoutManager(layoutManagerEvent);
            eventAdapter = new HorizontalListVAdapter(getActivity(), eventLogo,eventLists,eventIdsLists,R.drawable.publish_event, Social.NAVIGATE_TO_EVENT);
            recycleEvent.setAdapter(eventAdapter);

            recycleGroup.setHasFixedSize(true);
            layoutManagerGroup = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recycleGroup.setLayoutManager(layoutManagerGroup);
            groupAdapter = new HorizontalListVAdapter(getActivity(), groupLogo,groupLists,groupOrgIdsLists,R.drawable.achievement, Social.NAVIGATE_TO_GROUP);
            recycleGroup.setAdapter(groupAdapter);

            recycleRequest.setHasFixedSize(true);
            layoutManagerSocial = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recycleRequest.setLayoutManager(layoutManagerSocial);
            requestAdapter = new HorizontalListVAdapter(getActivity(), null,requestLists,requestIdsLists,R.drawable.social_strength, Social.NAVIGATE_TO_SOCIAL);
            recycleRequest.setAdapter(requestAdapter);

            recycleAchievement.setHasFixedSize(true);
            layoutManagerAchievement = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recycleAchievement.setLayoutManager(layoutManagerAchievement);
            achievementAdapter = new HorizontalListVAdapter(getActivity(), null,achievementLists,achievementIdsLists,R.drawable.achievement,Social.NAVIGATE_TO_ACHIEVEMENT);
            recycleAchievement.setAdapter(achievementAdapter);

            if(eventLists.size()>0){
                recycleEvent.setVisibility(View.VISIBLE);
                llEvent.setVisibility(View.GONE);
            }else{
                recycleEvent.setVisibility(View.GONE);
                llEvent.setVisibility(View.VISIBLE);
            }

            if(groupLists.size()>0){
                recycleGroup.setVisibility(View.VISIBLE);
                llGroup.setVisibility(View.GONE);
            }else{
                recycleGroup.setVisibility(View.GONE);
                llGroup.setVisibility(View.VISIBLE);
            }

            if(requestLists.size()>0){
               recycleRequest.setVisibility(View.VISIBLE);
               llRequest.setVisibility(View.GONE);
            }else{
                recycleRequest.setVisibility(View.GONE);
                llRequest.setVisibility(View.VISIBLE);
            }

            if(achievementLists.size()>0){
                recycleAchievement.setVisibility(View.VISIBLE);
                llAchievement.setVisibility(View.GONE);
            }else{
                recycleAchievement.setVisibility(View.GONE);
                llAchievement.setVisibility(View.VISIBLE);
            }

        }


        public void callApi(double latitude,double longitude){

            if (NetworkUtil.isInternetConnected(getActivity())) {

                CommanUtils.showDialog(getActivity());
                Device.newInstance(getActivity());

                dbAdapter.open();
                RequestIndDashboard reqIndDash = RequestIndDashboard.get("19.1863001","72.8358083",dbAdapter.getlastSyncdate("IND"));
                dbAdapter.close();

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<ResponseIndDashboard> resSyncEventCall = mPaalanServices.appIndDashborad(reqIndDash);

                resSyncEventCall.enqueue(new Callback<ResponseIndDashboard>() {
                    @Override
                    public void onResponse(Call<ResponseIndDashboard> call, Response<ResponseIndDashboard> response) {
                        Log.e(TAG,"response : "+response.body());

                        if (response.isSuccessful()) {

                            if (response.body().getStatus().equals("success")) {

                                insertIntoDB(response);

                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong),
                                        Toast.LENGTH_LONG).show();
                            }
                        } else if (response.body() == null) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                    .show();
                        }
                        CommanUtils.hideDialog();

                    }

                    @Override
                    public void onFailure(Call<ResponseIndDashboard> call, Throwable t) {
                        CommanUtils.hideDialog();
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                                .show();
                    }
                });
            } else {
                CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
            }
        }


        private void insertIntoDB(Response<ResponseIndDashboard> response){

            dbAdapter = new DBAdapter(getActivity());
            dbAdapter.open();

            for (int i = 0; i < response.body().getData()[0].getEventlist().length; i++) {

                    dbAdapter.populatingEventsIntoDB(response.body().getData()[0].getEventlist()[i].getOrgId(),null,
                            response.body().getData()[0].getEventlist()[i].getEventId(),
                            response.body().getData()[0].getEventlist()[i].getName(),
                            response.body().getData()[0].getEventlist()[i].getTitle(),
                            response.body().getData()[0].getEventlist()[i].getSubTitle(),
                            response.body().getData()[0].getEventlist()[i].getDiscription(),
                            response.body().getData()[0].getEventlist()[i].getOthers(),
                            response.body().getData()[0].getEventlist()[i].getStartDt(),
                            response.body().getData()[0].getEventlist()[i].getEndDt(),
                            response.body().getData()[0].getEventlist()[i].getDeleteFlag(),
                            response.body().getData()[0].getEventlist()[i].getCategory(),
                            response.body().getData()[0].getEventlist()[i].getLocation(),
                            response.body().getData()[0].getEventlist()[i].getEventLogo());
            }

            for(int i=0;i<response.body().getData()[0].getOrgachievementlist().length;i++) {

                    dbAdapter.populatingAchievementsIntoDB(response.body().getData()[0].getOrgachievementlist()[i].getOrgId(),null,
                            response.body().getData()[0].getOrgachievementlist()[i].getAchievementId(),
                            response.body().getData()[0].getOrgachievementlist()[i].getName(),
                            response.body().getData()[0].getOrgachievementlist()[i].getTitle(),
                            response.body().getData()[0].getOrgachievementlist()[i].getSubTitle(),
                            response.body().getData()[0].getOrgachievementlist()[i].getDiscription(),
                            response.body().getData()[0].getOrgachievementlist()[i].getOthers(),
                            response.body().getData()[0].getOrgachievementlist()[i].getImage1(),
                            response.body().getData()[0].getOrgachievementlist()[i].getImage2(),
                            response.body().getData()[0].getOrgachievementlist()[i].getImage3(),
                            response.body().getData()[0].getOrgachievementlist()[i].getImage4(),
                            response.body().getData()[0].getOrgachievementlist()[i].getDeleteFlag());
            }


            for (int i = 0; i < response.body().getData()[0].getOrgreqlist().length; i++) {

                dbAdapter.populatingRequestIntoDB(response.body().getData()[0].getOrgreqlist()[i].getOrgId(),null,
                        response.body().getData()[0].getOrgreqlist()[i].getRequestId(),
                        response.body().getData()[0].getOrgreqlist()[i].getOrgName(),
                        response.body().getData()[0].getOrgreqlist()[i].getTitle(),
                        response.body().getData()[0].getOrgreqlist()[i].getSubTitle(),
                        response.body().getData()[0].getOrgreqlist()[i].getDiscription(),
                        response.body().getData()[0].getOrgreqlist()[i].getOthers(),
                        response.body().getData()[0].getOrgreqlist()[i].getLocation(),
                        response.body().getData()[0].getOrgreqlist()[i].getDeleteFlag());
            }


            for(int i=0;i<response.body().getData()[0].getOrglist().length;i++) {

                    dbAdapter.populatingGroupsIntoDB(response.body().getData()[0].getOrglist()[i].getId(),
                            response.body().getData()[0].getOrglist()[i].getOrgId(),
                            response.body().getData()[0].getOrglist()[i].getName(),
                            response.body().getData()[0].getOrglist()[i].getMobileNo(),
                            response.body().getData()[0].getOrglist()[i].getEmailId(),
                            response.body().getData()[0].getOrglist()[i].getAddress(),
                            response.body().getData()[0].getOrglist()[i].getCity(),
                            response.body().getData()[0].getOrglist()[i].getState(),
                            response.body().getData()[0].getOrglist()[i].getPincode(),
                            response.body().getData()[0].getOrglist()[i].getCountry(),
                            response.body().getData()[0].getOrglist()[i].getDeleteFlag());
            }


            for(int i=0;i<response.body().getData()[0].getOrgprofilelist().length;i++) {

                    dbAdapter.populatingGroupsProfileIntoDB(response.body().getData()[0].getOrgprofilelist()[i].getId(),
                            response.body().getData()[0].getOrgprofilelist()[i].getOrgId(),
                            response.body().getData()[0].getOrgprofilelist()[i].getName(),
                            response.body().getData()[0].getOrgprofilelist()[i].getMobileNo(),
                            response.body().getData()[0].getOrgprofilelist()[i].getEmailId(),
                            response.body().getData()[0].getOrgprofilelist()[i].getAddress(),
                            response.body().getData()[0].getOrgprofilelist()[i].getRole(),
                            response.body().getData()[0].getOrgprofilelist()[i].getIsNewsLetter(),
                            response.body().getData()[0].getOrgprofilelist()[i].getIsGovtRegister(),
                            response.body().getData()[0].getOrgprofilelist()[i].getRegistrationNo(),
                            response.body().getData()[0].getOrgprofilelist()[i].getDpImgLink(),
                            response.body().getData()[0].getOrgprofilelist()[i].getFbLink(),
                            response.body().getData()[0].getOrgprofilelist()[i].getLinkedinLink(),
                            response.body().getData()[0].getOrgprofilelist()[i].getWebsiteLink(),
                            response.body().getData()[0].getOrgprofilelist()[i].getTwitterLink(),
                            response.body().getData()[0].getOrgprofilelist()[i].getPresenceArea(),
                            response.body().getData()[0].getOrgprofilelist()[i].getDeleteFlag());
            }


            dbAdapter.updateIndDashTimeSpan(response.body().getMessage());
            dbAdapter.close();
            getPopulated();
        }


        public void initializeTimer(){

            handlingSlideShow();

            itemPos = mPager.getCurrentItem();
            if(handler!=null){
                handler.removeCallbacks(refresh);
            }

            handler = new Handler();

            refresh = new Runnable() {
                public void run() {
                    if (mPager.getCurrentItem() < images.size()-1) {
                        mPager.setCurrentItem(itemPos, true);
                        itemPos = itemPos + 1;
                      }else{
                        itemPos = 0;
                        mPager.setCurrentItem(itemPos, true);
                    }
                    handler.postDelayed(refresh, 4000);
                }
            };
            handler.post(refresh);
        }


        @Override
        public void onResume() {
            super.onResume();
            ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.dash_borad),
                    R.drawable.ic_menu_black_24dp);
            initializeTimer();
            getPopulated();

             if (requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION)){
                if(!isFetched) {
                    getRetrofitCall();
                }
             }
        }



    @Override
    public void onCancelClicked(String label) {
        if(label.equals("Cancel") || label.equals("Exit")){
            getActivity().finish();
        }else if(label.equals("Use Existing")){
            isFetched = true;
            callApi(Double.parseDouble(pref.getLocation().split("~")[0]),
                    Double.parseDouble(pref.getLocation().split("~")[1]));
        }else if(label.equals("Retry")){
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.platform, new FragmentIndDashboard())
                    .addToBackStack(null).commit();
        }
    }
}


