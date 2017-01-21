package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.ListsAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;

import com.phyder.paalan.payload.request.organization.OrgReqSyncEvent;
import com.phyder.paalan.payload.response.organization.OrgResSyncEvent;
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

public class FragmentViewEvent extends Fragment {

    private static final String TAG = FragmentViewEvent.class.getCanonicalName();
    private ListView listView;
    private String[] listOfEventIds;
    private String[] listOfEventTitles;
    private String[] listOfEventSubTitles;

    private int counter = 0;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_event, null, false);
        init(view);
        getRetrofitCall();
        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PaalanGetterSetter.setEventId(listOfEventIds[position]);

               // PaalanGetterSetter.setEventID(listOfEventIds[position]);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform, new FragmentViewDetailsEvent()).addToBackStack(null).commit();
            }
        });

    }

    public void getPopulated() {

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllEvent("F");
        listOfEventIds = new String[cursor.getCount()];
        listOfEventTitles = new String[cursor.getCount()];
        listOfEventSubTitles = new String[cursor.getCount()];

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                do {
                    listOfEventIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_ID)));
                    listOfEventTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_TITLE)));
                    listOfEventSubTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.EVENT_SUB_TITLE)));
                    counter = counter + 1;
                } while (cursor.moveToNext());
            }
        }
        counter = 0;
        dbAdapter.close();

        if (listOfEventTitles != null && listOfEventTitles.length > 0) {
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfEventTitles, listOfEventSubTitles));
        }

    }


    public void getRetrofitCall() {

        if (NetworkUtil.isInternetConnected(getActivity())) {

            CommanUtils.showDialog(getActivity());
            Device.newInstance(getActivity());

            OrgReqSyncEvent orgReqSyncEvent = OrgReqSyncEvent.get(pref.getOrgId());

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            Call<OrgResSyncEvent> resSyncEventCall = mPaalanServices.orgSyncEvent(orgReqSyncEvent);

            resSyncEventCall.enqueue(new Callback<OrgResSyncEvent>() {
                @Override
                public void onResponse(Call<OrgResSyncEvent> call, Response<OrgResSyncEvent> response) {
                    CommanUtils.hideDialog();
                    Log.d(TAG, "onResponse: " + response);
                    if (response.isSuccessful()) {


                        if (response.body().getStatus().equals("success")) {
                            dbAdapter.open();


                            for (int i = 0; i < response.body().getData().length; i++) {
//
                                if (!dbAdapter.isEventExist(response.body().getData()[i].getOrglist()[i].getOrgId())) {
                                    //  getorgid()[i].getEventById()))    {

                                    dbAdapter.insertEvent(response.body().getData()[i].getOrglist()[i].getEventId(),
                                            response.body().getData()[i].getOrglist()[i].getTitle(),
                                            response.body().getData()[i].getOrglist()[i].getSubTitle(),
                                            response.body().getData()[i].getOrglist()[i].getDiscription(),
                                            response.body().getData()[i].getOrglist()[i].getOthers(),
                                            response.body().getData()[i].getOrglist()[i].getStartDt(),
                                            response.body().getData()[i].getOrglist()[i].getEndDt(),
                                            response.body().getData()[i].getOrglist()[i].getDeleteFlag());
                                }
                            }
                            dbAdapter.updateEventTimeSpan(response.body().getMessage());
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
                public void onFailure(Call<OrgResSyncEvent> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    CommanUtils.hideDialog();
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        } else {
            CommanUtils.getInternetAlert(getActivity());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(), getResources().getStringArray(R.array.events_array)[0]);
        getPopulated();
        PaalanGetterSetter.setAchivementID(null);
    }
}
