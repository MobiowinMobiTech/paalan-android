package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.ListsAdapter;

import java.util.ArrayList;

public class FragmentEventHistory extends Fragment {

    private ListView eventsListView;
    private ArrayList<String> itemsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_history, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        eventsListView = (ListView) view.findViewById(R.id.listView);
        itemsList= new ArrayList<String>();
        itemsList.add("Create Events");
        itemsList.add("Update Events");
        itemsList.add("Sync Events");
        itemsList.add("Delete Events");
        eventsListView.setAdapter(new ListsAdapter(getActivity(),0,itemsList));

        eventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
