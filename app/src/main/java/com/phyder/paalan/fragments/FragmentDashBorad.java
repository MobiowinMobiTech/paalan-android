package com.phyder.paalan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.SlidingImageAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created on 21/12/16.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class FragmentDashBorad extends android.support.v4.app.Fragment {

    View view;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
  //  CustomListAdapter customListAdapter;
    private static final Integer[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    String[] itemname = {"Publish event", "Achievements", "Event history", "Social Strength", "Social Request", "About us", "Contact us"};

    int[] imgid = {R.drawable.ic_publish_event, R.drawable.ic_achievments, R.drawable.event_history, R.drawable.social_strength,
            R.drawable.ic_social_request, R.drawable.ic_about_us, R.drawable.contact_us};


    SlidingImageAdapter SlidingImageAdapter;
    ArrayList<Integer> dashboardImages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_framelayout, container, false);
        mPager = (ViewPager) view.findViewById(R.id.image_pager);

//        ListView list;
//        Context context;
//
//        list = (ListView) view.findViewById(R.id.list);
//        list.setAdapter(new CustomListAdapter(getActivity(), itemname, imgid));

        init();
        return view;
    }




    private void init() {
        {
            for (int i = 0; i < IMAGES.length; i++)
                ImagesArray.add(IMAGES[i]);

            mPager.setAdapter(new SlidingImageAdapter(getActivity(), ImagesArray));

            final float density = getResources().getDisplayMetrics().density;

            NUM_PAGES = IMAGES.length;

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 1500, 1500);
        }
    }

}







