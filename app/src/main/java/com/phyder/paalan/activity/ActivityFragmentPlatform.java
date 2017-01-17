package com.phyder.paalan.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.organization.OrganizationProfile;
import com.phyder.paalan.fragments.FragmentCreateAchievement;
import com.phyder.paalan.fragments.FragmentDashBorad;
import com.phyder.paalan.fragments.FragmentIndDashboard;
import com.phyder.paalan.fragments.FragmentUpdateAchievement;
import com.phyder.paalan.fragments.FragmentViewAchievement;

import java.util.HashMap;

/**
 * Created by cmss on 13/1/17.
 */
public class ActivityFragmentPlatform extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ExpandableListView expListView;
    private HashMap<String, String[]> listDataChild;
    private ExpandableListAdapter listAdapter;
    private String[] listDataHeader;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
        setUpDrawer();
    }


    private void setUpDrawer() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getIntent().getStringExtra("LOGIN").equals("org")){
            transaction.replace(R.id.platform, new FragmentDashBorad());
        }else{
            transaction.replace(R.id.platform, new FragmentIndDashboard());
        }

        transaction.addToBackStack(null);
        transaction.commit();


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id)
            {
                if(groupPosition == 0){
                    fragment = new OrganizationProfile();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.platform, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    mDrawerLayout.closeDrawer(mDrawerList);
                }

                return false;

            }
        });



        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                  switch (groupPosition){
                    case 1:
                        switch (childPosition){
                            case 0:
                                fragment = new FragmentCreateAchievement();
                                expListView.collapseGroup(1);
                                break;

                            case 1:
                                fragment = new FragmentUpdateAchievement();
                                expListView.collapseGroup(1);
                                break;

                            case 2:
                                fragment = new FragmentViewAchievement();
                                expListView.collapseGroup(1);
                                break;
                        }
                        break;

                    case 2:
                        switch (childPosition){
                            case 0:
                                expListView.collapseGroup(2);
                                break;

                            case 1:
                                expListView.collapseGroup(2);
                                break;

                            case 2:
                                expListView.collapseGroup(2);
                                break;
                        }
                        break;

                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.platform, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                mDrawerLayout.closeDrawer(mDrawerList);
                return false;
            }
        });
    }

    View.OnClickListener homeOnclickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mDrawerLayout.isDrawerOpen(expListView)){
                mDrawerLayout.closeDrawer(expListView);
            }else{
                mDrawerLayout.openDrawer(expListView);
            }
        }
    };


    private void prepareListData() {

        // Adding child data
        listDataHeader = getResources().getStringArray(R.array.drawer_array);
        // Adding child data
        String[] achievementItems = getResources().getStringArray(R.array.achievements_array);
        // Adding child data
        String[] eventItems = getResources().getStringArray(R.array.events_array);
        String emptyArray[] = new String[0];
        listDataChild =new HashMap<String,String[]>();
        listDataChild.put(listDataHeader[0], emptyArray);
        listDataChild.put(listDataHeader[1], achievementItems); // Header, Child data
        listDataChild.put(listDataHeader[2], eventItems);
        listDataChild.put(listDataHeader[3], emptyArray);
        listDataChild.put(listDataHeader[4], emptyArray);
        listDataChild.put(listDataHeader[5], emptyArray);
        listDataChild.put(listDataHeader[6], emptyArray);
        listDataChild.put(listDataHeader[7], emptyArray);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private Context _context;
        private String[] _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, String[]> _listDataChild;

        public ExpandableListAdapter(Context context, String[] listDataHeader,
                                     HashMap<String, String[]> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader[groupPosition])
                    [childPosititon];
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_items, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader[groupPosition])
                    .length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader[groupPosition];
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.length;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }


    @Override
    public void onBackPressed() {

        if(mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawers();
        }else if(getSupportFragmentManager().findFragmentById(R.id.platform) instanceof FragmentDashBorad){
                finish();
        }else{
            super.onBackPressed();
        }
    }
}