package com.phyder.paalan.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.CustomGridViewAdapter;
import com.phyder.paalan.adapter.Item;
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
    private static final Integer[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    GridView gridView;
    ArrayList<Item> gridArray = new ArrayList<Item>();
    CustomGridViewAdapter customGridViewAdapter;
    SlidingImageAdapter slidingImageAdapter;
    ArrayList<Integer> dashboardImages = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_framelayout, container, false);
        mPager = (ViewPager) view.findViewById(R.id.image_pager);
        init();

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"Selected "+gridArray.get(position).getTitle(),Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard_framelayout);
//        init();
//
//
//        gridArray.add(new Item(R.drawable.ic_publish_event, "Publish event"));
//        gridArray.add(new Item(R.drawable.ic_achievments, "Achievements"));
//        gridArray.add(new Item(R.drawable.event_history, " Event history"));
//        gridArray.add(new Item(R.drawable.social_strength, " Social Strength"));
//        gridArray.add(new Item(R.drawable.ic_social_request, "  Social Request"));
//        gridArray.add(new Item(R.drawable.ic_about_us, "About us"));
//        gridArray.add(new Item(R.drawable.contact_us, "Contact us"));
//
//
//        gridView = (GridView) findViewById(R.id.gridView1);
//        mPager = (ViewPager) view.findViewById(R.id.image_pager);
//        customGridViewAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
//        gridView.setAdapter(customGridViewAdapter);
//    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.dashboard_framelayout, container, false);
//        init();
//
//
//        gridArray.add(new Item(R.drawable.ic_publish_event, "Publish event"));
//        gridArray.add(new Item(R.drawable.ic_achievments, "Achievements"));
//        gridArray.add(new Item(R.drawable.event_history, " Event history"));
//        gridArray.add(new Item(R.drawable.social_strength, " Social Strength"));
//        gridArray.add(new Item(R.drawable.ic_social_request, "  Social Request"));
//        gridArray.add(new Item(R.drawable.ic_about_us, "About us"));
//        gridArray.add(new Item(R.drawable.contact_us, "Contact us"));
//
//
//        gridView = (GridView) view.findViewById(R.id.gridView1);
//        mPager =
//        mPager = (ViewPager) view.findViewById(R.id.pager);
//        customGridViewAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
//        gridView.setAdapter(customGridViewAdapter);
////            }
////
////        }
////
////
////
////
//        return view;
//    }

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
            }, 3000, 3000);
        }
    }


}
