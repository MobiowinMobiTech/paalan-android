package com.phyder.paalan.fragments;

import android.Manifest;
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
import com.phyder.paalan.adapter.HorizontalListVAdapter;
import com.phyder.paalan.adapter.SlidingImageAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.CircleIndicator;
import com.phyder.paalan.helper.GPSTracker;
import com.phyder.paalan.payload.request.RequestIndDashboard;
import com.phyder.paalan.payload.response.ResponseIndDashboard;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 16/1/17.
 */

public class FragmentIndDashboard extends Fragment{

    private static final String TAG = FragmentIndDashboard.class.getSimpleName();
    private ViewPager mPager;
    private CircleIndicator mCircleIndicator;

    private LinearLayout llEvents,llRequests,llAchievements;
    private RecyclerView mrecycleEventList, mrecycleSocialList, mrecycleAchievementsList;
    private LinearLayoutManager mLayoutManagerEvent, mLayoutManagerAchievement, mLayoutManagerSocial;
    private RecyclerView.Adapter mAdapter, mAchievementAdapter, mEventAdapter;

    List<String> images;
    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private boolean isFetched = false;

    private static final int PERMISSION = 1;

    private GPSTracker gps;

    private Cursor eventCursor,achievementCursor,requestCursor;
    private ArrayList<String> eventLists,achievementLists,requestLists;

    private Handler handler = new Handler();
    private Runnable refresh;
    private int itemPos = 0;

    private TextViewOpenSansRegular txtEventSeeMore,txtAchievementsSeeMore,txtSocialSeeMore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ind_dashboard, container, false);


        initSlider(view);
        init(view);

        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        mrecycleEventList = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mrecycleAchievementsList = (RecyclerView) view.findViewById(R.id.achievement_recycler_view);
        mrecycleSocialList = (RecyclerView) view.findViewById(R.id.social_recycler_view);

        llEvents = (LinearLayout) view.findViewById(R.id.llEventStatus);
        llRequests = (LinearLayout) view.findViewById(R.id.llRequestStatus);
        llAchievements = (LinearLayout) view.findViewById(R.id.llAchievementStatus);

        txtEventSeeMore = (TextViewOpenSansRegular) view.findViewById(R.id.txtEventSeeMore);
        txtSocialSeeMore = (TextViewOpenSansRegular) view.findViewById(R.id.txtSocialSeeMore);
        txtAchievementsSeeMore = (TextViewOpenSansRegular) view.findViewById(R.id.txtAchievementSeeMore);

        eventLists = new ArrayList<String>();
        achievementLists = new ArrayList<String>();
        requestLists = new ArrayList<String>();


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
    }

    private void initSlider(View view) {
        mPager = (ViewPager) view.findViewById(R.id.image_pager);
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);

        images = new ArrayList<>();
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

        mPager.setAdapter(new SlidingImageAdapter(getActivity(), images));
        mCircleIndicator.setViewPager(mPager);

    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    if(!isFetched)
                        getRetrofitCall();

                } else {
                    ActivityFragmentPlatform.getFinished(getActivity());
                }
                return;
            }

        }
    }


    public void getRetrofitCall() {
        gps = new GPSTracker(getActivity());
        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            if(latitude == 0.0 && longitude == 0.0){
                if(pref.getLocation()!=null){
                    isFetched = true;
                    callApi(Double.parseDouble(pref.getLocation().split("~")[0]),
                            Double.parseDouble(pref.getLocation().split("~")[1]));
                }else{
                   CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_location));
                }
            }else {
                pref.setLocation(latitude +"~"+longitude);
                isFetched = true;
                callApi(latitude, longitude);
            }
        }else{
            gps.showSettingsAlert();
        }

    }


    public void getPopulated() {

        eventLists.clear();
        achievementLists.clear();
        requestLists.clear();

        dbAdapter.open();
        eventCursor = dbAdapter.getAllEvent("F");

        if(eventCursor != null)
            eventCursor.moveToFirst();
        if(eventCursor.moveToFirst()){
            do{
                eventLists.add(eventCursor.getString(eventCursor.getColumnIndex(Attributes.Database.EVENT_TITLE)));
            }while (eventCursor.moveToNext());
        }

        achievementCursor = dbAdapter.getAllAchievements("F");

        if(achievementCursor != null)
            achievementCursor.moveToFirst();
        if(achievementCursor.moveToFirst()){
            do{
                achievementLists.add(achievementCursor.getString(achievementCursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE)));
            }while (achievementCursor.moveToNext());
        }

        requestCursor = dbAdapter.getAllRequests("F");

        if(requestCursor != null)
            requestCursor.moveToFirst();
        if(requestCursor.moveToFirst()){
            do{
                requestLists.add(requestCursor.getString(requestCursor.getColumnIndex(Attributes.Database.REQUEST_TITLE)));
            }while (requestCursor.moveToNext());
        }
        dbAdapter.close();

        mrecycleEventList.setHasFixedSize(true);
        mLayoutManagerEvent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleEventList.setLayoutManager(mLayoutManagerEvent);
        mAdapter = new HorizontalListVAdapter(getActivity(), eventLists,R.drawable.publish_event);
        mrecycleEventList.setAdapter(mAdapter);


        mLayoutManagerAchievement = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleAchievementsList.setLayoutManager(mLayoutManagerAchievement);
        mAchievementAdapter = new HorizontalListVAdapter(getActivity(), achievementLists,R.drawable.achievement);
        mrecycleAchievementsList.setAdapter(mAchievementAdapter);


        mLayoutManagerSocial = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleSocialList.setLayoutManager(mLayoutManagerSocial);
        mEventAdapter = new HorizontalListVAdapter(getActivity(), requestLists,R.drawable.social_strength);
        mrecycleSocialList.setAdapter(mEventAdapter);

        if(eventLists.size()>0){
            mrecycleEventList.setVisibility(View.VISIBLE);
            llEvents.setVisibility(View.GONE);
        }else{
            mrecycleEventList.setVisibility(View.GONE);
            llEvents.setVisibility(View.VISIBLE);
        }

        if(requestLists.size()>0){
            mrecycleSocialList.setVisibility(View.VISIBLE);
            llRequests.setVisibility(View.GONE);
        }else{
            mrecycleSocialList.setVisibility(View.GONE);
            llRequests.setVisibility(View.VISIBLE);
        }

        if(achievementLists.size()>0){
            mrecycleAchievementsList.setVisibility(View.VISIBLE);
            llAchievements.setVisibility(View.GONE);
        }else{
            mrecycleAchievementsList.setVisibility(View.GONE);
            llAchievements.setVisibility(View.VISIBLE);
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
                    CommanUtils.hideDialog();


                    Log.d(TAG, "onResponse: "+response.isSuccessful());

                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {

                            dbAdapter = new DBAdapter(getActivity());
                            dbAdapter.open();

                            for (int i = 0; i < response.body().getData()[0].getEventlist().length; i++) {

                                if (!dbAdapter.isEventExist(response.body().getData()[0].getEventlist()[i].getEventId())) {

                                    dbAdapter.insertEvent(response.body().getData()[0].getEventlist()[i].getEventId(),
                                            response.body().getData()[0].getEventlist()[i].getTitle(),
                                            response.body().getData()[0].getEventlist()[i].getSubTitle(),
                                            response.body().getData()[0].getEventlist()[i].getDiscription(),
                                            response.body().getData()[0].getEventlist()[i].getOthers(),
                                            response.body().getData()[0].getEventlist()[i].getStartDt(),
                                            response.body().getData()[0].getEventlist()[i].getEndDt(),
                                            response.body().getData()[0].getEventlist()[i].getDeleteFlag(),
                                            response.body().getData()[0].getEventlist()[i].getCategory(),
                                            response.body().getData()[0].getEventlist()[i].getLocation());
                                }
                            }

                            for(int i=0;i<response.body().getData()[0].getOrgachievementlist().length;i++) {

                                if(!dbAdapter.isAchievementExist(response.body().getData()[0].
                                        getOrgachievementlist()[i].getAchievementId()))    {

                                    dbAdapter.insertAchievement(response.body().getData()[0].getOrgachievementlist()[i].getAchievementId(),
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
                            }

//                            for(int i=0;i<response.body().getData()[0].getOrgreqlist().length;i++) {
//
//                                if(!dbAdapter.isRequestExist(response.body().getData()[0].
//                                        getOrglist()[i].getRequestId()))    {
//
//                                    dbAdapter.insertRequest(response.body().getData()[0].getOrglist()[i].getRequestId(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getTitle(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getSubTitle(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getDiscription(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getOthers(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getLocation(),
//                                            response.body().getData()[0].getOrgreqlist()[i].getDeleteFlag());
//                                }
//                            }

                            dbAdapter.updateIndDashTimeSpan(response.body().getMessage());
                            dbAdapter.close();
                            getPopulated();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else if (response.body() == null) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
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


    public void initializeTimer(){

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

        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.dash_borad));
        initializeTimer();
        getPopulated();

        if (requestPermission()) {
            if(!isFetched)
                getRetrofitCall();
        }

    }
}


