package com.phyder.paalan.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.HorizontalListVAdapter;
import com.phyder.paalan.social.DashBoardModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cmss on 16/1/17.
 */

public class FragmentIndDashboard extends Fragment{

    private RecyclerView mrecycleEventList, mrecycleSocialList, mrecycleAchievementsList;
    private LinearLayoutManager mLayoutManagerEvent,mLayoutManagerAchievement,mLayoutManagerSocial;
    private RecyclerView.Adapter mAdapter, mAchievementAdapter, mEventAdapter;
    private ArrayList<DashBoardModel> dashBoardModelList;

//    private int pastVisiblesItems, visibleItemCount, totalItemCount;
//    private ImageView imgLeftArrawEvent,imgRightArrawEvent,imgLeftArrawAchiement,
//            imgRightArrawAchiement,imgLeftSocial,imgRightSocial;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_ind_dashboard, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        ArrayList<String> alName = new ArrayList<>(Arrays.asList("Cheesy...", "Crispy... ", "Fizzy...", "Cool...", "Softy...", "Fruity...", "Fresh...", "Sticky..."));
        ArrayList<Integer> alImage = new ArrayList<>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.cheesy, R.drawable.cheesy, R.drawable.a, R.drawable.b));
        dashBoardModelList = new ArrayList<>();


        for (
                int i = 0; i < alName.size(); i++)

        {
            DashBoardModel dashBoardModel = new DashBoardModel();
            dashBoardModel.setEvenName(alName.get(i));
            dashBoardModel.setEventIcons(alImage.get(i));
            dashBoardModelList.add(dashBoardModel);
        }


        mrecycleEventList = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        mrecycleEventList.setHasFixedSize(true);
        mLayoutManagerEvent = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleEventList.setLayoutManager(mLayoutManagerEvent);
        mAdapter = new HorizontalListVAdapter(getActivity(), dashBoardModelList);
        mrecycleEventList.setAdapter(mAdapter);

        mrecycleAchievementsList = (RecyclerView) view.findViewById(R.id.achievement_recycler_view);
        mLayoutManagerAchievement = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleAchievementsList.setLayoutManager(mLayoutManagerAchievement);
        mAchievementAdapter = new HorizontalListVAdapter(getActivity(), dashBoardModelList);
        mrecycleAchievementsList.setAdapter(mAchievementAdapter);

        mrecycleSocialList = (RecyclerView) view.findViewById(R.id.social_recycler_view);
        mLayoutManagerSocial = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mrecycleSocialList.setLayoutManager(mLayoutManagerSocial);
        mEventAdapter = new HorizontalListVAdapter(getActivity(), dashBoardModelList);
        mrecycleSocialList.setAdapter(mEventAdapter);


//        imgLeftArrawEvent = (ImageView) view.findViewById(R.id.img_left_event);
//        imgRightArrawEvent = (ImageView) view.findViewById(R.id.img_right_event);
//
//        imgLeftArrawAchiement = (ImageView) view.findViewById(R.id.img_left_achievement);
//        imgRightArrawAchiement = (ImageView) view.findViewById(R.id.img_right_achievement);
//
//        imgLeftSocial = (ImageView) view.findViewById(R.id.img_left_social);
//        imgRightSocial = (ImageView) view.findViewById(R.id.img_right_social);



    }
}
