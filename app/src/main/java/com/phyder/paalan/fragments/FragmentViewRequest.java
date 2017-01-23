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
import com.phyder.paalan.payload.request.organization.OrgReqSyncRequest;
import com.phyder.paalan.payload.response.organization.OrgResSyncAchievement;
import com.phyder.paalan.payload.response.organization.OrgResSyncRequest;
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

public class FragmentViewRequest extends Fragment {

    private static final String TAG = FragmentViewRequest.class.getCanonicalName();
    private ListView listView;
    private LinearLayout llNoData;
    private String[] listOfRequestIds;
    private String[] listOfRequestTitles;
    private String[] listOfRequestSubTitles;

    private int counter = 0;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_view_achievement,null,false);
            init(view);
            getRetrofitCall();
        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        llNoData = (LinearLayout) view.findViewById(R.id.llStatus) ;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PaalanGetterSetter.setRequestID(listOfRequestIds[position]);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform,new FragmentViewDetailsRequest()).addToBackStack(null).commit();
            }
        });

    }

    public void getPopulated(){

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllRequests("F");
        listOfRequestIds =new String[cursor.getCount()];
        listOfRequestTitles =new String[cursor.getCount()];
        listOfRequestSubTitles =new String[cursor.getCount()];

        if(cursor !=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    listOfRequestIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_ID)));
                    listOfRequestTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_TITLE)));
                    listOfRequestSubTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.REQUEST_SUB_TITLE)));
                    counter = counter+1;
                }while(cursor.moveToNext());
            }
        }
        counter=0;
        dbAdapter.close();

        if(listOfRequestTitles!=null && listOfRequestTitles.length>0 ) {
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfRequestTitles,listOfRequestSubTitles));
            listView.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

    }



    public void getRetrofitCall(){

        if(NetworkUtil.isInternetConnected(getActivity())){

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());

            dbAdapter.open();
            OrgReqSyncRequest orgReqSyncRequest = OrgReqSyncRequest.get(pref.getOrgId(),dbAdapter.getlastSyncdate("Request"));
            dbAdapter.close();

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrgResSyncRequest> resSyncRequest = mPaalanServices.orgSyncRequest(orgReqSyncRequest);

            resSyncRequest.enqueue(new Callback<OrgResSyncRequest>() {

                @Override
                public void onResponse(Call<OrgResSyncRequest> call, Response<OrgResSyncRequest> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();

                            for(int i=0;i<response.body().getData()[0].getOrgreqlist().length;i++) {

                                if(!dbAdapter.isRequestExist(response.body().getData()[0].
                                        getOrgreqlist()[i].getRequestId()))    {

                                    dbAdapter.insertRequest(response.body().getData()[0].getOrgreqlist()[i].getRequestId(),
                                            response.body().getData()[0].getOrgreqlist()[i].getTitle(),
                                            response.body().getData()[0].getOrgreqlist()[i].getSubTitle(),
                                            response.body().getData()[0].getOrgreqlist()[i].getDiscription(),
                                            response.body().getData()[0].getOrgreqlist()[i].getOthers(),
                                            response.body().getData()[0].getOrgreqlist()[i].getLocation(),
                                            response.body().getData()[0].getOrgreqlist()[i].getDeleteFlag());
                                }
                            }
                            dbAdapter.updateRequestTimeSpan(response.body().getMessage());
                            dbAdapter.close();
                            getPopulated();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.error_went_wrong),
                                    Toast.LENGTH_LONG).show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<OrgResSyncRequest> call, Throwable t) {
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






    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getStringArray(R.array.request_array)[1]);
        getPopulated();
        PaalanGetterSetter.setRequestID(null);
    }
}
