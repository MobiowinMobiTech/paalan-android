
package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.SlidingImageAdapter;
import com.phyder.paalan.model.DashboardModel;
import com.phyder.paalan.utils.RoundedImageView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentDashBorad extends Fragment {

    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    //  CustomListAdapter customListAdapter;

    ArrayList<DashboardModel> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;

    String dashboardContent[] = {"Publish Event", "Achievements", "Event history", "Social Request", "Contact Us", "About Us"};
    String dashboardDescription[] = {"Testing for Create Event", "Testing for Create Achievements", "", "", "", "", ""};
    int Images[] = {R.drawable.publish_event, R.drawable.achievement, R.drawable.event_req,
            R.drawable.social_strength, R.drawable.event_req, R.drawable.about_us, R.drawable.contactus};
    private int colors[];
    private static final Integer[] IMAGES = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_framelayout, null, false);

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new ORGDashboardAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);
        initializeList();
        init(view);
        return view;
    }

    private void init(View view) {

        mPager = (ViewPager) view.findViewById(R.id.image_pager);
        recyclerView = (RecyclerView) view.findViewById(R.id.cardView);

        recyclerView.setAdapter(new ORGDashboardAdapter(listitems));


//        for (int i = 0; i < IMAGES.length; i++)
//            ImagesArray.add(IMAGES[i]);

        mPager.setAdapter(new SlidingImageAdapter(getActivity(), IMAGES));

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

    public class ORGDashboardAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<DashboardModel> list;

        public ORGDashboardAdapter(ArrayList<DashboardModel> Data) {
            list = Data;
            colors = getActivity().getResources().getIntArray(R.array.colors);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            holder.txteventname.setText(list.get(position).getDashboardContentName());
            holder.txttagline.setText(list.get(position).getDashboardContentDescription());
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.linearLayout.setBackgroundColor(colors[position]);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txteventname, txttagline, txtCreate, txtView;
        public RoundedImageView coverImageView;
        LinearLayout linearLayout;

        public MyViewHolder(View v) {

            super(v);
            linearLayout = (LinearLayout) v.findViewById(R.id.linear_view);
            txteventname = (TextView) v.findViewById(R.id.event_name);
            txttagline = (TextView) v.findViewById(R.id.tagline);
            coverImageView = (RoundedImageView) v.findViewById(R.id.coverImageView);
            txtCreate = (TextView) v.findViewById(R.id.text_create);
            txtView = (TextView) v.findViewById(R.id.text_view);

            txtCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txteventname.getText().equals("Publish Event")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    } else if (txteventname.getText().equals("Publish Event")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    } else if (txteventname.getText().equals("Achievements")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    } else if (txteventname.getText().equals("Social Strength")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    } else if (txteventname.getText().equals("Contact Us")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    } else if (txteventname.getText().equals("About Us")) {
                        Toast.makeText(getActivity(), "You Clicked" + txteventname.getText(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void initializeList() {
        listitems.clear();

        for (int i = 0; i < 6; i++) {
            DashboardModel item = new DashboardModel();
            item.setDashboardContentName(dashboardContent[i]);
            item.setDashboardContentDescription(dashboardDescription[i]);
            item.setImageResourceId(Images[i]);
            listitems.add(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getActivity(), getResources().getString(R.string.dash_borad));
    }

}

