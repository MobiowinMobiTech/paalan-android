package com.phyder.paalan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.fragments.FragmentAboutUs;
import com.phyder.paalan.fragments.FragmentContactUs;
import com.phyder.paalan.fragments.FragmentCreateAchievement;
import com.phyder.paalan.fragments.FragmentCreateRequest;
import com.phyder.paalan.fragments.FragmentDashBorad;
import com.phyder.paalan.fragments.FragmentIndDashboard;
import com.phyder.paalan.fragments.FragmentMyProfile;
import com.phyder.paalan.fragments.FragmentViewAchievement;
import com.phyder.paalan.fragments.FragmentViewEvent;
import com.phyder.paalan.fragments.FragmentViewRequest;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.RoundedImageView;
import com.phyder.paalan.utils.TextViewOpenSansRegular;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by cmss on 13/1/17.
 */
public class ActivityFragmentPlatform extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ExpandableListView expListView;
    private HashMap<String, String[]> listDataChild;
    private ExpandableListAdapter listAdapter;
    private String[] listDataHeader;
    private Fragment fragment;
    private static RoundedImageView IMG_PROFILE;
    private static TextView TXT_USER_NAME;
    private static PreferenceUtils PREF;
    private static DBAdapter DB_ADAPTER;

    private static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
        // initToolBar();
        setUpDrawer();
    }

    public static void getChangeToolbarTitle(String title) {

        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    private void setUpDrawer() {

        PREF = new PreferenceUtils(ActivityFragmentPlatform.this);
        DB_ADAPTER = new DBAdapter(ActivityFragmentPlatform.this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();

        IMG_PROFILE = (RoundedImageView) findViewById(R.id.img_user_profile);
        TXT_USER_NAME = (TextView) findViewById(R.id.textView2);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (PREF.getLoginType().equals(Social.IND_ENTITY)) {
            transaction.replace(R.id.platform, new FragmentIndDashboard());
        } else {
            transaction.replace(R.id.platform, new FragmentDashBorad());
        }

        transaction.addToBackStack(null);
        transaction.commit();


        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {

                    fragment = PREF.getLoginType().equals(Social.ORG_ENTITY) ? new FragmentMyProfile() :
                            new FragmentViewEvent();
                    getFragmentTransaction(fragment);
                    mDrawerLayout.closeDrawer(mDrawerList);
                }else if(groupPosition == 1){
                    if(PREF.getLoginType().equals(Social.IND_ENTITY)) {
                        getFragmentTransaction(new FragmentViewRequest());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 2){
                    if(PREF.getLoginType().equals(Social.IND_ENTITY)) {

                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 3){
                    if(PREF.getLoginType().equals(Social.IND_ENTITY)) {
                        getFragmentTransaction(new FragmentViewAchievement());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 4){
                    if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                        getFragmentTransaction(new FragmentAboutUs());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 5){
                    if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                        getFragmentTransaction(new FragmentContactUs());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 6){
                    if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                        PREF.setLogin(false);
                        PREF.setLoginType(Social.IND_ENTITY);
                        DB_ADAPTER.open();
                        DB_ADAPTER.deleteProfile();
                        DB_ADAPTER.close();
                        prepareListData();
                        getFragmentTransaction(new FragmentIndDashboard());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
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
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentCreateAchievement();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(1);
                                }
                                break;

                            case 1:
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentViewAchievement();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(1);
                                }
                                break;
                        }
                        break;

                    case 2:
                        switch (childPosition) {
                            case 0:
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentViewAchievement();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(2);
                                }
                                break;

                            case 1:
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentViewEvent();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(2);
                                }
                                break;

                        }
                        break;

                    case 3:
                        switch (childPosition) {
                            case 0:
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentCreateRequest();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(3);
                                }
                                break;

                            case 1:
                                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                                    fragment = new FragmentViewRequest();
                                    getFragmentTransaction(fragment);
                                    expListView.collapseGroup(3);
                                }
                                break;
                        }
                        break;

                    case 6:
                        switch (childPosition) {
                            case 0:
                                if(PREF.getLoginType().equals(Social.IND_ENTITY)) {
                                    Intent intent = new Intent(ActivityFragmentPlatform.this,Login.class);
                                    startActivity(intent);
                                }
                                expListView.collapseGroup(6);
                                break;

                            case 1:
                                if(PREF.getLoginType().equals(Social.IND_ENTITY)) {
                                   Intent intent = new Intent(ActivityFragmentPlatform.this,RegisterUser.class);
                                   startActivity(intent);
                                }
                                expListView.collapseGroup(6);
                                break;

                        }
                        break;

                }
                mDrawerLayout.closeDrawer(mDrawerList);
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

        String emptyArray[] = new String[0];

        if (PREF.getLoginType().equals(Social.IND_ENTITY)) {

            listDataHeader = getResources().getStringArray(R.array.ind_drawer_array);
            String[] loginItems = getResources().getStringArray(R.array.login_array);

            listDataChild = new HashMap<String, String[]>();
            listDataChild.put(listDataHeader[0], emptyArray);
            listDataChild.put(listDataHeader[1], emptyArray); // Header, Child data
            listDataChild.put(listDataHeader[2], emptyArray);

            listDataChild.put(listDataHeader[3], emptyArray);
            listDataChild.put(listDataHeader[4], emptyArray);
            listDataChild.put(listDataHeader[5], emptyArray);

            listDataChild.put(listDataHeader[6], loginItems);
            listDataChild.put(listDataHeader[7], emptyArray);
            listDataChild.put(listDataHeader[8], emptyArray);


        }else {
            // Adding child data
            listDataHeader = getResources().getStringArray(R.array.drawer_array);
            // Adding child data
            String[] achievementItems = getResources().getStringArray(R.array.achievements_array);
            // Adding child data
            String[] eventItems = getResources().getStringArray(R.array.events_array);

            String[] requestItems = getResources().getStringArray(R.array.request_array);

            listDataChild = new HashMap<String, String[]>();
            listDataChild.put(listDataHeader[0], emptyArray);
            listDataChild.put(listDataHeader[1], achievementItems); // Header, Child data
            listDataChild.put(listDataHeader[2], eventItems);
            listDataChild.put(listDataHeader[3], requestItems);
            listDataChild.put(listDataHeader[4], emptyArray);
            listDataChild.put(listDataHeader[5], emptyArray);
            listDataChild.put(listDataHeader[6], emptyArray);
        }

        listAdapter = new ExpandableListAdapter(ActivityFragmentPlatform.this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

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
        } else if (getSupportFragmentManager().findFragmentById(R.id.platform) instanceof FragmentIndDashboard ) {
            ActivityFragmentPlatform.this.finish();
        } else if (getSupportFragmentManager().findFragmentById(R.id.platform) instanceof FragmentDashBorad) {
            ActivityFragmentPlatform.this.finish();
        } else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProfileUpdate(ActivityFragmentPlatform.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                    mDrawerLayout.closeDrawers();  // CLOSE DRAWER
                else
                    mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public static void getProfileUpdate(Context context){
        DB_ADAPTER.open();
        String encodedImage = DB_ADAPTER.getProfileDP();
        if(!encodedImage.isEmpty() && !encodedImage.contains("http://")){
            IMG_PROFILE.setImageBitmap(CommanUtils.decodeBase64(encodedImage));

        }else if(!encodedImage.isEmpty() && encodedImage.contains("http://")){
            Picasso.with(context)
                    .load(encodedImage)
                    .placeholder(R.drawable.unknown)   // optional
                    .error(R.drawable.unknown)      // optional
                    .into(IMG_PROFILE);
        }
        DB_ADAPTER.close();

        if (PREF.getUserName() != null) {
            TXT_USER_NAME.setText(PREF.getUserName());
        }
    }

    public static void getFinished(FragmentActivity context){
        context.finish();
    }


}

