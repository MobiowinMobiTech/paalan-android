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
import com.phyder.paalan.activity.ActivityFragmentPlatform;
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

    private int counter = 0;

    private DBAdapter dbAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_update_view_achievement,null,false);
            init(view);
        return view;
    }

    private void init(View view) {

        dbAdapter = new DBAdapter(getActivity());

        listView = (ListView) view.findViewById(R.id.listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("ID",listOfAchievementIds[position]);
                bundle.putString("TITLE",listOfAchievementTitles[position]);
                bundle.putString("SUB_TITLE",listOfAchievementSubTitles[position]);

                Fragment fragment = new FragmentViewDetailsAchievement();
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.platform,fragment).addToBackStack(null).commit();
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
            listView.setAdapter(new ListsAdapter(getActivity(), 0, listOfAchievementTitles,listOfAchievementSubTitles));
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(),getResources().getStringArray(R.array.achievements_array)[1]);
        getPopulated();
    }
}
