package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.ListsAdapter;
import com.phyder.paalan.adapter.NotificationListsAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

/**
 * Created by cmss on 21/2/17.
 */

public class FragmentNotifications extends Fragment {

    private static final String TAG = FragmentViewRequest.class.getSimpleName();
    private ListView listView;
    private TextViewOpenSansRegular txtNoData;
    private String[] listOfIds;
    private String[] listOfMessages;
    private String[] listOfTypes;
    private String[] listOfReaded;

    private int counter = 0;

    private DBAdapter dbAdapter;
    private PreferenceUtils pref;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, null, false);
        initializingComponents(view);
        return view;
    }

    private void initializingComponents(View view) {

        dbAdapter = new DBAdapter(getActivity());
        pref = new PreferenceUtils(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        txtNoData = (TextViewOpenSansRegular) view.findViewById(R.id.txtDataNotFound);
    }


    public void getPopulated(){

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllNotification();
        listOfIds =new String[cursor.getCount()];
        listOfMessages =new String[cursor.getCount()];
        listOfTypes =new String[cursor.getCount()];
        listOfReaded =new String[cursor.getCount()];

        if(cursor !=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    listOfIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.NOTIFICATION_ID)));
                    listOfMessages[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.NOTIFICATION_MESSAGE)));
                    listOfTypes[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.NOTIFICATION_TYPE)));
                    listOfReaded[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.NOTIFICATION_READED)));
                    counter = counter+1;
                }while(cursor.moveToNext());
            }
        }
        counter=0;
        dbAdapter.close();

        if(listOfIds!=null && listOfIds.length>0 ) {
            listView.setAdapter(new NotificationListsAdapter(getActivity(), 0, listOfReaded,listOfMessages));
            listView.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.changeToolbarTitleIcon(getResources().getString(R.string.notification),
                R.drawable.ic_arrow_back_black_24dp);
        getPopulated();
    }
}
