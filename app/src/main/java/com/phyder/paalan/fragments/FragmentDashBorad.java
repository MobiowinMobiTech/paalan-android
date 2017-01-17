
package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.CustomListAdapter;
import com.phyder.paalan.adapter.SlidingImageAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentDashBorad extends Fragment {

    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private ListView list;
    private String[] itemname = {"Publish event", "Achievements", "Event history", "Social Strength",
            "Social Request", "About us", "Contact us"};

    private Integer[] imgid = {R.drawable.ic_publish_event, R.drawable.ic_achievments, R.drawable.event_history,
            R.drawable.social_strength,R.drawable.ic_social_request, R.drawable.ic_about_us, R.drawable.contact_us};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dashboard_framelayout, null, false);
            init(view);
        return view;
    }


    private void init(View view) {

        mPager = (ViewPager) view.findViewById(R.id.image_pager);
        list = (ListView) view.findViewById(R.id.list);
        list.setAdapter( new CustomListAdapter(getActivity(),0,itemname,imgid));

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


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem = itemname[position];
                Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(),getResources().getString(R.string.dash_borad));
    }

}

