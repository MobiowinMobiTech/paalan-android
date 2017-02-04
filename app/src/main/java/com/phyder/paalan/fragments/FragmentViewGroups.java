package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.ListsAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.helper.PaalanGetterSetter;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentViewGroups extends Fragment {

    private static final String TAG = FragmentViewGroups.class.getCanonicalName();
    private ListView listView;
    private LinearLayout llNoData;
    private String[] listOfGroupProfileIds;
    private String[] listOfGroupName;
    private String[] listOfGroupAddress;

    private int counter = 0;

    private DBAdapter dbAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view,null,false);
        init(view);

        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);
        llNoData = (LinearLayout) view.findViewById(R.id.llStatus);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PaalanGetterSetter.setOrgID(listOfGroupProfileIds[position]);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform,new FragmentGroupsProfile()).addToBackStack(null).commit();
            }
        });

    }

    public void getPopulated(){

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllGroups("F");
        listOfGroupProfileIds =new String[cursor.getCount()];
        listOfGroupName =new String[cursor.getCount()];
        listOfGroupAddress =new String[cursor.getCount()];

        if(cursor !=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    listOfGroupProfileIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_ORGANIZATION_ID)));
                    listOfGroupName[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_NAME)));
                    listOfGroupAddress[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.GROUPS_ADDRESS)));
                    counter = counter+1;
                }while(cursor.moveToNext());
            }
        }
        counter=0;
        dbAdapter.close();

        if(listOfGroupName!=null && listOfGroupName.length>0 ) {
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfGroupName,listOfGroupAddress,null));
            listView.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.groups));
        getPopulated();
    }
}
