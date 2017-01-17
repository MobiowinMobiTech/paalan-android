package com.phyder.paalan.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.ListsAdapter;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.utils.PreferenceUtils;

/**
 * Created by cmss on 12/1/17.
 */

public class FragmentViewAchievement extends Fragment {

    private ListView listView;
    private String[] listOfAchievementIds;
    private String[] listOfAchievementTitles;
    private String[] listOfAchievementSubTitles;
    private String[] listOfAchievementDescriptions;
    private String[] listOfAchievementOthers;

    private String[] listOfAchievementImage1;
    private String[] listOfAchievementImage2;
    private String[] listOfAchievementImage3;
    private String[] listOfAchievementImage4;

    private int counter = 0;

    private PreferenceUtils pref;
    private DBAdapter dbAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_update_view_achievement,null,false);
            init(view);
        return view;
    }

    private void init(View view) {

        pref = new PreferenceUtils(getActivity());
        dbAdapter = new DBAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);

        dbAdapter.open();
        Cursor cursor = dbAdapter.getAllAchievements();
        listOfAchievementIds =new String[cursor.getCount()];
        listOfAchievementTitles =new String[cursor.getCount()];
        listOfAchievementSubTitles =new String[cursor.getCount()];
        listOfAchievementDescriptions =new String[cursor.getCount()];
        listOfAchievementOthers =new String[cursor.getCount()];

        listOfAchievementImage1 =new String[cursor.getCount()];
        listOfAchievementImage2 =new String[cursor.getCount()];
        listOfAchievementImage3 =new String[cursor.getCount()];
        listOfAchievementImage4 =new String[cursor.getCount()];
        if(cursor !=null){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                do{
                    listOfAchievementIds[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_ID)));
                    listOfAchievementTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_TITLE)));
                    listOfAchievementSubTitles[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SUB_TITLE)));
                    listOfAchievementDescriptions[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_DESCRIPTION)));
                    listOfAchievementOthers[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_OTHERS)));

                    listOfAchievementImage1[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FIRST_IMAGE)));
                    listOfAchievementImage2[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_SECOND_IMAGE)));
                    listOfAchievementImage3[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_THIRD_IMAGE)));
                    listOfAchievementImage4[counter] = (cursor.getString(cursor.getColumnIndex(Attributes.Database.ACHIEVEMENT_FORTH_IMAGE)));

                    counter = counter+1;
                }while(cursor.moveToNext());
            }
        }
        counter=0;
        dbAdapter.close();


        if(listOfAchievementTitles!=null && listOfAchievementTitles.length>0 ) {
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfAchievementTitles));
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}
