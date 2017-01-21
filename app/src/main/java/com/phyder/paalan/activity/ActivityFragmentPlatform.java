package com.phyder.paalan.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.fragments.FragmentAboutUs;
import com.phyder.paalan.fragments.FragmentContactUs;
import com.phyder.paalan.fragments.FragmentCreateAchievement;
import com.phyder.paalan.fragments.FragmentCreateRequest;
import com.phyder.paalan.fragments.FragmentDashBorad;
import com.phyder.paalan.fragments.FragmentIndDashboard;
import com.phyder.paalan.fragments.FragmentMyProfile;
import com.phyder.paalan.fragments.FragmentCreateEventRequest;
import com.phyder.paalan.fragments.FragmentViewAchievement;
import com.phyder.paalan.fragments.FragmentViewEvent;
import com.phyder.paalan.fragments.FragmentViewRequest;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.RoundedImageView;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

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
    private RoundedImageView imgProfile;
    private TextView txtUserName;
    private PreferenceUtils pref;

    private static Toolbar TOOLBAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
        initToolBar();
        setUpDrawer();
    }


    private void initToolBar() {
        TOOLBAR = (Toolbar) findViewById(R.id.toolbar);
        TOOLBAR.setTitle(R.string.dash_borad);
        TOOLBAR.setTitleTextColor(getResources().getColor(R.color.icons));
        setSupportActionBar(TOOLBAR);
    }

    public static void getChangeToolbarTitle(Context context, String title) {
        if (TOOLBAR != null) {
            TOOLBAR.setTitle(title);
            TOOLBAR.setTitleTextColor(context.getResources().getColor(R.color.icons));
        }
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

        imgProfile = (RoundedImageView) findViewById(R.id.img_user_profile);
        txtUserName = (TextView) findViewById(R.id.textView2);

        pref = new PreferenceUtils(ActivityFragmentPlatform.this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (getIntent().getStringExtra("LOGIN").equals("org")) {
            transaction.replace(R.id.platform, new FragmentDashBorad());
        } else {
            transaction.replace(R.id.platform, new FragmentIndDashboard());
        }

        transaction.addToBackStack(null);
        transaction.commit();


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    getFragmentTransaction(new FragmentMyProfile());
                    mDrawerLayout.closeDrawer(mDrawerList);
                }else if(groupPosition == 4){
                    getFragmentTransaction(new FragmentAboutUs());
                    mDrawerLayout.closeDrawer(mDrawerList);
                }else if(groupPosition == 5){
                    getFragmentTransaction(new FragmentContactUs());
                    mDrawerLayout.closeDrawer(mDrawerList);
                }

                return false;

            }
        });


        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (groupPosition) {
                    case 1:
                        switch (childPosition) {
                            case 0:
                                fragment = new FragmentCreateAchievement();
                                expListView.collapseGroup(1);
                                break;

                            case 1:
                                fragment = new FragmentViewAchievement();
                                expListView.collapseGroup(1);
                                break;

                        }
                        break;

                    case 2:
                        switch (childPosition) {
                            case 0:
                                fragment = new FragmentCreateEventRequest();
                                expListView.collapseGroup(2);
                                break;

                            case 1:
                                fragment = new FragmentViewEvent();
                                expListView.collapseGroup(2);
                                break;

                        }
                        break;

                    case 3:
                        switch (childPosition) {
                            case 0:
                                fragment = new FragmentCreateRequest();
                                expListView.collapseGroup(3);
                                break;

                            case 1:
                                fragment = new FragmentViewRequest();
                                expListView.collapseGroup(3);
                                break;

                        }
                        break;

                }
                mDrawerLayout.closeDrawer(mDrawerList);
                getFragmentTransaction(fragment);
                return false;
            }
        });
    }


    private void getFragmentTransaction(Fragment fragment){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.platform, fragment)
                .addToBackStack(null).commit();
    }



    private void prepareListData() {

        // Adding child data
        listDataHeader = getResources().getStringArray(R.array.drawer_array);
        // Adding child data
        String[] achievementItems = getResources().getStringArray(R.array.achievements_array);
        // Adding child data
        String[] eventItems = getResources().getStringArray(R.array.events_array);

        String[] requestItems = getResources().getStringArray(R.array.request_array);

        String emptyArray[] = new String[0];

        listDataChild = new HashMap<String, String[]>();
        listDataChild.put(listDataHeader[0], emptyArray);
        listDataChild.put(listDataHeader[1], achievementItems); // Header, Child data
        listDataChild.put(listDataHeader[2], eventItems);
        listDataChild.put(listDataHeader[3], requestItems);
        listDataChild.put(listDataHeader[4], emptyArray);
        listDataChild.put(listDataHeader[5], emptyArray);
        listDataChild.put(listDataHeader[6], emptyArray);

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

            TextViewOpenSansRegular txtListChild = (TextViewOpenSansRegular) convertView
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

            TextViewOpenSansRegular lblListHeader = (TextViewOpenSansRegular) convertView
                    .findViewById(R.id.lblListHeader);
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

        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawers();
        } else if (getSupportFragmentManager().findFragmentById(R.id.platform) instanceof FragmentDashBorad) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pref.getProfileImg() != null) {
            imgProfile.setImageBitmap(CommanUtils.decodeBase64(pref.getProfileImg()));
        }

        if (pref.getUserName() != null) {
            txtUserName.setText(pref.getUserName());
        }
    }
}