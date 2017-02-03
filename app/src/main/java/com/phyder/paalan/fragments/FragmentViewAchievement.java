package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.ListsAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.payload.request.organization.OrgReqSyncAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentViewAchievement extends Fragment {

    private static final String TAG = FragmentViewAchievement.class.getCanonicalName();
    private ListView listView;
    private LinearLayout llNoData;
    private String[] listOfAchievementIds;
    private String[] listOfAchievementTitles;
    private String[] listOfAchievementSubTitles;

    private int counter = 0;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;

    private boolean isFetched = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view,null,false);
        init(view);

        if(!isFetched)
            getRetrofitCall();

        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        llNoData = (LinearLayout) view.findViewById(R.id.llStatus);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PaalanGetterSetter.setAchivementID(listOfAchievementIds[position]);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform,new FragmentViewDetailsAchievement()).addToBackStack(null).commit();
            }
        });

    }

    public void getPopulated(){

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllAchievements("F");
        listOfAchievementIds =new String[cursor.getCount()];
        listOfAchievementTitles =new String[cursor.getCount()];
        listOfAchievementSubTitles =new String[cursor.getCount()];

        if(cursor !=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    listOfAchievementIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_ID)));
                    listOfAchievementTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE)));
                    listOfAchievementSubTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SUB_TITLE)));
                    counter = counter+1;
                }while(cursor.moveToNext());
            }
        }
        counter=0;
        dbAdapter.close();

        if(listOfAchievementTitles!=null && listOfAchievementTitles.length>0 ) {
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfAchievementTitles,listOfAchievementSubTitles,null));
            listView.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

    }



    public void getRetrofitCall(){
        isFetched = true;
        if(NetworkUtil.isInternetConnected(getActivity())){

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());

            dbAdapter.open();
            OrgReqSyncAchievement orgReqSyncAchiement = OrgReqSyncAchievement.get(pref.getOrgId(),
                    dbAdapter.getlastSyncdate("Achievement"));
            dbAdapter.close();

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrgResSyncAchievement> resSyncAchievement = mPaalanServices.orgSyncAchievements(orgReqSyncAchiement);

            resSyncAchievement.enqueue(new Callback<OrgResSyncAchievement>() {
                @Override
                public void onResponse(Call<OrgResSyncAchievement> call, Response<OrgResSyncAchievement> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();

                            for(int i=0;i<response.body().getData()[0].getOrglist().length;i++) {

                                dbAdapter.populatingAchievementsIntoDB(null,response.body().getData()[0].getOrglist()[i].getAchievementId(),
                                        response.body().getData()[0].getOrglist()[i].getTitle(),
                                        response.body().getData()[0].getOrglist()[i].getSubTitle(),
                                        response.body().getData()[0].getOrglist()[i].getDiscription(),
                                        response.body().getData()[0].getOrglist()[i].getOthers(),
                                        response.body().getData()[0].getOrglist()[i].getImage1(),
                                        response.body().getData()[0].getOrglist()[i].getImage2(),
                                        response.body().getData()[0].getOrglist()[i].getImage3(),
                                        response.body().getData()[0].getOrglist()[i].getImage4(),
                                        response.body().getData()[0].getOrglist()[i].getDeleteFlag());
                            }
                            dbAdapter.updateAchievementTimeSpan(response.body().getMessage());
                            dbAdapter.close();
                            getPopulated();

                        } else {
                            CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_went_wrong));
                        }
                    }else if(response.body()==null){
                        CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_server));
                    }
                }

                @Override
                public void onFailure(Call<OrgResSyncAchievement> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    CommanUtils.showToast(getActivity(),getResources().getString(R.string.error_timeout));
                }
            });
        }else{
            CommanUtils.showAlertDialog(getActivity(),getResources().getString(R.string.error_internet));
        }
    }






    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getStringArray(R.array.achievements_array)[1]);
        getPopulated();
        PaalanGetterSetter.setAchivementID(null);
    }
}
