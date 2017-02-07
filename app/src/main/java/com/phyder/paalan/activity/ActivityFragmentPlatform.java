package com.phyder.paalan.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.adapter.ExpandableListAdapter;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.fragments.FragmentAboutUs;
import com.phyder.paalan.fragments.FragmentConnectWithUs;
import com.phyder.paalan.fragments.FragmentCreateAchievement;
import com.phyder.paalan.fragments.FragmentCreateEvent;
import com.phyder.paalan.fragments.FragmentCreateRequest;
import com.phyder.paalan.fragments.FragmentDashBorad;
import com.phyder.paalan.fragments.FragmentIndDashboard;
import com.phyder.paalan.fragments.FragmentMyProfile;
import com.phyder.paalan.fragments.FragmentViewAchievement;
import com.phyder.paalan.fragments.FragmentViewEvent;
import com.phyder.paalan.fragments.FragmentViewGroups;
import com.phyder.paalan.fragments.FragmentViewRequest;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by cmss on 13/1/17.
 */
public class ActivityFragmentPlatform extends AppCompatActivity{

    private static final String TAG = ActivityFragmentPlatform.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ExpandableListView expListView;
    private Fragment fragment;
    private static RoundedImageView IMG_PROFILE;
    private static TextView TXT_USER_NAME;
    private static PreferenceUtils PREF;
    private static DBAdapter DB_ADAPTER;
    final static int IMG_RESULT = 1;
    final int CAMERA_REQUEST = 1888;
    private static ActionBar actionBar;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 99;
    private static final int STORAGE_PERMISSION_CODE = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
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
        populatingSideDrawerList();
        //Profile image
        IMG_PROFILE = (RoundedImageView) findViewById(R.id.img_user_profile);
        // to update profile image
        IMG_PROFILE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfileImage();
            }
        });
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
                Log.d(TAG, "onGroupClick: "+groupPosition);
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
                        getFragmentTransaction(new FragmentViewGroups());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 3){
                    if(PREF.getLoginType().equals(Social.IND_ENTITY)) {
                        getFragmentTransaction(new FragmentViewAchievement());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if(groupPosition == 7){
                        getFragmentTransaction(new FragmentAboutUs());
                        mDrawerLayout.closeDrawer(mDrawerList);
                }else if(groupPosition == 5 || groupPosition == 8){
                        getFragmentTransaction(new FragmentConnectWithUs());
                        mDrawerLayout.closeDrawer(mDrawerList);
                }else if(groupPosition == 6){
                    if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                        PREF.setLogin(false);
                        DB_ADAPTER.open();
                        DB_ADAPTER.deleteProfile();
                        DB_ADAPTER.close();
                        PREF.setLoginType(Social.IND_ENTITY);
                        getProfileUpdate();
                        populatingSideDrawerList();
                        getFragmentTransaction(new FragmentIndDashboard());
                        mDrawerLayout.closeDrawer(mDrawerList);
                    }
                }else if (groupPosition == 4){
                    Intent donateIntent = new Intent(ActivityFragmentPlatform.this, Donate.class);
                    startActivity(donateIntent);
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
                                    fragment = new FragmentCreateEvent();
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




    /**
     * Function used to update profile image
     */
    private void updateProfileImage() {
        Log.d("", "updateProfileImage: "+PREF.getLoginType());
        if (PREF.getLoginType().equalsIgnoreCase(Social.ORG_ENTITY)) {
            Log.d("", "updateProfileImage: org");
            try {
                fragment = new FragmentMyProfile();
                getFragmentTransaction(fragment);
                mDrawerLayout.closeDrawer(mDrawerList);
            } catch (Exception ex) {

            }
        }else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle(" Select Profile");
            alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                if (requestPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_REQUEST_CODE)){
                    openCameraForDP();
                }

            }
        });
        alertDialog.setNegativeButton("Gallary", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)){
                    openGalleryForDP();
                }
            }


        });

        alertDialog.show();
        }
    }


    /**
     * To open gallery
     */
    private void openGalleryForDP() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMG_RESULT);
    }

    /**
     * To open Camera
     */
    private void openCameraForDP() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    /**
     * To get permission from user
     * @param permissionName : permission to take
     * @param permissionRequestCode : request for open identifier
     * @return : status of permission
     */
    private boolean requestPermission(String permissionName, int permissionRequestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(permissionName) == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            requestPermissions(new String[]{permissionName}, permissionRequestCode);
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: reqCode "+requestCode);

        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)  {
                    openCameraForDP();
                }
                return;
            }
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGalleryForDP();
                }
                break;

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String strEncodedDp;
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            IMG_PROFILE.setImageBitmap(photo);
            strEncodedDp = CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(photo));
            Log.d(TAG, "onActivityResult: CAMERA "+strEncodedDp);
            saveProfilePicture(photo);
        } else if (requestCode == IMG_RESULT || requestCode == IMG_RESULT) {
            try {
                if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                        && null != data) {
                    Uri URI = data.getData();
                    String[] FILE = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(URI,
                            FILE, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(FILE[0]);
                    strEncodedDp = cursor.getString(columnIndex);
                    IMG_PROFILE.setImageBitmap(BitmapFactory
                            .decodeFile(strEncodedDp));

                    saveProfilePicture(BitmapFactory.decodeFile(strEncodedDp));

                    strEncodedDp = CommanUtils.encodeToBase64(CommanUtils.getSquareBitmap(BitmapFactory
                            .decodeFile(strEncodedDp)));
                    cursor.close();
                    Log.d(TAG, "onActivityResult: GALLERY "+strEncodedDp);

                }
            } catch (Exception e) {
                Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    /**
     * Function to save profile picture
     * @param bitmap
     */
    private void saveProfilePicture(Bitmap bitmap) {

        Log.d(TAG, "XXsaveProfilePicture: "+PREF.getLoginType());

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        CommanUtils.saveProfilePic(this, PREF.getLoginType(), temp);

    }


    private void getFragmentTransaction(Fragment fragment){

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.platform, fragment)
                .addToBackStack(null).commit();
    }



    private void populatingSideDrawerList() {

        if (PREF.getLoginType().equals(Social.IND_ENTITY)) {

            String emptyArray[] = new String[0];

            String[] listDataHeader = getResources().getStringArray(R.array.ind_drawer_array);
            String[] loginItems = getResources().getStringArray(R.array.login_array);

            HashMap<String, String[]> listDataChild = new HashMap<String, String[]>();
            listDataChild.clear();

            Log.d("IND_ENTITY","listDataHeader size : "+listDataHeader.length);

            listDataChild.put(listDataHeader[0], emptyArray);
            listDataChild.put(listDataHeader[1], emptyArray); // Header, Child data
            listDataChild.put(listDataHeader[2], emptyArray);

            listDataChild.put(listDataHeader[3], emptyArray);
            listDataChild.put(listDataHeader[4], emptyArray);
            listDataChild.put(listDataHeader[5], emptyArray);

            listDataChild.put(listDataHeader[6], loginItems);
            listDataChild.put(listDataHeader[7], emptyArray);
            listDataChild.put(listDataHeader[8], emptyArray);

            Log.d("IND_ENTITY","listDataChild size : "+listDataChild.size());

            ExpandableListAdapter listAdapter = new ExpandableListAdapter(ActivityFragmentPlatform.this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);

        } else {

            String emptyArray[] = new String[0];
            // Adding child data
            String[] listDataHeader = getResources().getStringArray(R.array.drawer_array);
            // Adding child data
            String[] achievementItems = getResources().getStringArray(R.array.achievements_array);
            // Adding child data
            String[] eventItems = getResources().getStringArray(R.array.events_array);

            String[] requestItems = getResources().getStringArray(R.array.request_array);

            HashMap<String, String[]> listDataChild = new HashMap<String, String[]>();
            listDataChild.clear();

            Log.d("ORG_ENTITY","listDataHeader size : "+listDataHeader.length);

            listDataChild.put(listDataHeader[0], emptyArray);
            listDataChild.put(listDataHeader[1], achievementItems); // Header, Child data
            listDataChild.put(listDataHeader[2], eventItems);
            listDataChild.put(listDataHeader[3], requestItems);
            listDataChild.put(listDataHeader[4], emptyArray);
            listDataChild.put(listDataHeader[5], emptyArray);
            listDataChild.put(listDataHeader[6], emptyArray);

            Log.d("ORG_ENTITY","listDataChild size : "+listDataChild.size());

            ExpandableListAdapter listAdapter = new ExpandableListAdapter(ActivityFragmentPlatform.this, listDataHeader, listDataChild);
            expListView.setAdapter(listAdapter);
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
        getProfileUpdate();
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

    /**
     * Function to get profile image from sp
     */
    public void getProfileUpdate(){
        Log.d("", "XXgetProfileUpdate: "+PREF.getLoginType());
        Bitmap profilePic = CommanUtils.getUserProfile(this, PREF.getLoginType());
        if (profilePic != null)
            IMG_PROFILE.setImageBitmap(profilePic);

        if (PREF.getUserName() != null) {
            TXT_USER_NAME.setText(PREF.getUserName());
        }
    }

    public static void getFinished(FragmentActivity context){
        context.finish();
    }

}

