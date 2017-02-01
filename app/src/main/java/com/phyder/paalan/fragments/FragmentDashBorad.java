
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

import com.phyder.paalan.R;
import com.phyder.paalan.activity.ActivityFragmentPlatform;
import com.phyder.paalan.adapter.SlidingImageAdapter;
import com.phyder.paalan.helper.CircleIndicator;
import com.phyder.paalan.model.DashboardModel;
import com.phyder.paalan.utils.RoundedImageView;

import java.util.ArrayList;


public class FragmentDashBorad extends Fragment {

    private ViewPager mPager;
    private CircleIndicator mCircleIndicator;
    private RecyclerView recyclerView;
    private RecyclerView MyRecyclerView;
    private Handler handler = new Handler();
    private Runnable refresh;
    private int itemPos = 0;

    private ArrayList<DashboardModel> listitems = new ArrayList<>();

    private Integer dashboardIcons[] = {R.drawable.publish_event,R.drawable.achievement,R.drawable.social_strength,
            R.drawable.about_us,R.drawable.contactus};
    private Integer dashboardSlideShowIcons[] = {R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_framelayout, null, false);

        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);

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
        mCircleIndicator = (CircleIndicator) view.findViewById(R.id.indicator);
       // mPager.setAdapter(new SlidingImageAdapter(getActivity(),dashboardSlideShowIcons));
        mCircleIndicator.setViewPager(mPager);

        recyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        recyclerView.setAdapter(new ORGDashboardAdapter(listitems));
    }

    public class ORGDashboardAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<DashboardModel> list;

        public ORGDashboardAdapter(ArrayList<DashboardModel> Data) {
            list = Data;
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
            holder.txteventname.setTag(position);
            holder.txttagline.setText(list.get(position).getDashboardContentDescription());
            holder.txttagline.setTag(position);
            holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.layout.setBackgroundColor(getActivity().getResources().getIntArray(R.array.colors)[position]);

            if(position == 3 || position == 4){
                holder.txtCreate.setVisibility(View.GONE);
                holder.viewDivider.setVisibility(View.GONE);
            }else{
                holder.txtCreate.setVisibility(View.VISIBLE);
                holder.viewDivider.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txteventname, txttagline, txtCreate, txtView;
        public RoundedImageView coverImageView;
        public LinearLayout layout;
        public View viewDivider;
        public Fragment fragment;

        public MyViewHolder(View v) {

            super(v);
            layout = (LinearLayout) v.findViewById(R.id.llLayout);
            viewDivider = (View) v.findViewById(R.id.view_divider);
            txteventname = (TextView) v.findViewById(R.id.event_name);
            txttagline = (TextView) v.findViewById(R.id.tagline);
            coverImageView = (RoundedImageView) v.findViewById(R.id.coverImageView);
            txtCreate = (TextView) v.findViewById(R.id.text_create);
            txtView = (TextView) v.findViewById(R.id.text_view);

            txtCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (Integer)txteventname.getTag();

                    if(pos==0){
                        fragment =new FragmentCreateEvent();
                    }else if(pos==1){
                        fragment =new FragmentCreateAchievement();
                    }else if(pos==2){
                        fragment =new FragmentCreateRequest();
                    }

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                            addToBackStack(null).commit();

                }
            });

            txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (Integer)txttagline.getTag();
                    if(pos==0){
                        fragment =new FragmentViewEvent();
                    }else if(pos==1){
                        fragment =new FragmentViewAchievement();
                    }else if(pos==2){
                        fragment =new FragmentViewRequest();
                    } else if(pos==3){
                        fragment =new FragmentAboutUs();
                    }else if(pos==4) {
                        fragment =new FragmentContactUs();
                    }
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.platform,fragment).
                            addToBackStack(null).commit();
                }
            });
        }
    }

    public void initializeList() {
        listitems.clear();

        for (int i = 0; i < 5; i++) {
            DashboardModel item = new DashboardModel();
            item.setDashboardContentName(getResources().getStringArray(R.array.dashboard_content_array)[i]);
            item.setDashboardContentDescription(getResources().getStringArray(R.array.dashboard_descrition_array)[i]);
            item.setImageResourceId(dashboardIcons[i]);
            listitems.add(item);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ActivityFragmentPlatform.getChangeToolbarTitle(getResources().getString(R.string.dash_borad));
        initializeTimer();
    }


    public void initializeTimer(){

        itemPos = 0;

        if(handler!=null){
            handler.removeCallbacks(refresh);
        }

        handler = new Handler();

        refresh = new Runnable() {
            public void run() {
                if (mPager.getCurrentItem() < dashboardSlideShowIcons.length-1) {
                    mPager.setCurrentItem(itemPos, true);
                    itemPos = itemPos + 1;
                }else{
                    itemPos = 0;
                    mPager.setCurrentItem(itemPos, true);
                }
                handler.postDelayed(refresh, 4000);
            }
        };
        handler.post(refresh);
    }

}

