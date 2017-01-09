package com.phyder.paalan.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.CustomGridViewAdapter;
import com.phyder.paalan.adapter.Item;

import java.util.ArrayList;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class FragmentActivityDashBorad extends Fragment {

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridViewAdapter;
//    ArrayList<Integer> dashboardImages = new ArrayList<>()

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_framelayout, container, false);


        gridArray.add(new Item(R.drawable.ic_publish_event, "Publish event"));
        gridArray.add(new Item(R.drawable.ic_achievments, "Achievements"));
        gridArray.add(new Item(R.drawable.event_history, " Event history"));
        gridArray.add(new Item(R.drawable.social_strength, " Social Strength"));
        gridArray.add(new Item(R.drawable.ic_social_request, "  Social Request"));
        gridArray.add(new Item(R.drawable.ic_about_us, "About us"));
        gridArray.add(new Item(R.drawable.contact_us, "Contact us"));


        gridView = (GridView) view.findViewById(R.id.gridView1);
        customGridViewAdapter = new CustomGridViewAdapter(getActivity(), R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridViewAdapter);
//            }
//
//        }
//
//
//
//
        return view;
    }


}
