package com.phyder.paalan.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
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
import com.phyder.paalan.helper.DialogPopupListener;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.PreferenceUtils;
import com.phyder.paalan.utils.RoundedImageView;
import com.phyder.paalan.utils.TextViewOpenSansRegular;

import java.io.ByteArrayOutputStream;

/**
 * Created by cmss on 13/1/17.
 */
public class ActivityFragmentPlatform extends AppCompatActivity implements View.OnClickListener,DialogPopupListener{

    private static final String TAG = ActivityFragmentPlatform.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;

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

    private View viewIndividual, viewOrganization;

    private LinearLayout llDrawerHolder;

    private TextViewOpenSansRegular txtProfile, txtOrgAchievement, txtOrgCreateAchievement, txtOrgViewAchievement,
            txtOrgEvent, txtOrgCreateEvent, txtOrgViewEvent, txtOrgRequest,
            txtOrgCreateRequest, txtOrgViewRequest,
            txtOrgAboutUs, txtOrgContactUs, txtSignOut;

    private TextViewOpenSansRegular txtIndEvent, txtIndGroup, txtIndRequest, txtIndAchievement,
            txtConnectPaalan, txtLogin, txtRegister, txtDatePaalan, txtDonate, txtIndAboutUs, txtIndContactUs;


    private LinearLayout llOrgAchiements, llOrgEvents, llOrgRequest;
    private LinearLayout llIndConnectPaalan, llIndDonate;
    private ValueAnimator mAnimatorOrgAchievements,mAnimatorOrgEvents,mAnimatorOrgRequests;
    private ValueAnimator mAnimatorIndConnectPaalan,mAnimatorIndDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
        setUpDrawer();
    }



    public static void getChangeToolbarTitle(String title) {

        if (actionBar != null) {
            actionBar.setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + title + "</font>"));
        }
    }


    private void setUpDrawer() {

        PREF = new PreferenceUtils(ActivityFragmentPlatform.this);
        DB_ADAPTER = new DBAdapter(ActivityFragmentPlatform.this);

        llDrawerHolder = (LinearLayout) findViewById(R.id.llDrawerHolder);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


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
            initializingIndDrawerComponants();
            transaction.replace(R.id.platform, new FragmentIndDashboard());
        } else {
            initializingOrgDrawerComponants();
            transaction.replace(R.id.platform, new FragmentDashBorad());
        }

        transaction.addToBackStack(null);
        transaction.commit();


    }


    private void expand(LinearLayout linearLayout,ValueAnimator valueAnimator) {
        // set Visible
        linearLayout.setVisibility(View.VISIBLE);
        valueAnimator.start();
    }

    private void collapse(final LinearLayout linearLayout) {
        int finalHeight = linearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(linearLayout,finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                // Height=0, but it set visibility to GONE
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(final LinearLayout linearLayout,int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new     ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = linearLayout
                        .getLayoutParams();
                layoutParams.height = value;
                linearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
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

        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawers();
        }
    }



    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
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


    /**
     * Method to exit from app if user do not give location permission to fetching the location
     * @param context
     */
    public static void getFinished(FragmentActivity context){
        context.finish();
    }


    /**
     * Method to defining the side drawer for individual login
     */
    private void initializingIndDrawerComponants() {

        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewIndividual  = inflater.inflate(R.layout.individual_drawer, null);
        llDrawerHolder.removeView(viewIndividual);
        llDrawerHolder.addView(viewIndividual);

        llIndConnectPaalan = (LinearLayout) viewIndividual.findViewById(R.id.llIndConnect);
        llIndDonate = (LinearLayout) viewIndividual.findViewById(R.id.llIndDonate);

        txtIndEvent = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndEvent);
        txtIndGroup = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndGroup);
        txtIndRequest = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndRequest);
        txtIndAchievement = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndAchievement);

        txtConnectPaalan = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndConnect);
        txtLogin = (TextViewOpenSansRegular) llIndConnectPaalan.findViewById(R.id.txtIndLogin);
        txtRegister = (TextViewOpenSansRegular) llIndConnectPaalan.findViewById(R.id.txtIndRegister);

        txtDatePaalan = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndDonatePaalan);
        txtDonate = (TextViewOpenSansRegular) llIndDonate.findViewById(R.id.txtIndDonate);
//        txtDate = (TextViewOpenSansRegular) llIndDonate.findViewById(R.id.txtIndDatePaalan);

        txtIndAboutUs = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndAbout);
        txtIndContactUs = (TextViewOpenSansRegular) viewIndividual.findViewById(R.id.txtIndContact);

        txtIndEvent.setOnClickListener(this);
        txtIndGroup.setOnClickListener(this);
        txtIndRequest.setOnClickListener(this);
        txtIndAchievement.setOnClickListener(this);
        txtConnectPaalan.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtDatePaalan.setOnClickListener(this);
        txtDonate.setOnClickListener(this);
//        txtDate.setOnClickListener(this);
        txtIndAboutUs.setOnClickListener(this);
        txtIndContactUs.setOnClickListener(this);

        llIndConnectPaalan.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        llIndConnectPaalan.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        llIndConnectPaalan.setVisibility(View.GONE);

                        final int widthSpec =     View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec
                                .makeMeasureSpec(0,
                                        View.MeasureSpec.UNSPECIFIED);
                        llIndConnectPaalan.measure(widthSpec, heightSpec);

                        mAnimatorIndConnectPaalan = slideAnimator(llIndConnectPaalan,0,
                                llIndConnectPaalan.getMeasuredHeight());
                        return true;
                    }
                });

        llIndDonate.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        llIndDonate.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        llIndDonate.setVisibility(View.GONE);

                        final int widthSpec =     View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec
                                .makeMeasureSpec(0,
                                        View.MeasureSpec.UNSPECIFIED);
                        llIndDonate.measure(widthSpec, heightSpec);

                        mAnimatorIndDonate = slideAnimator(llIndDonate,0,
                                llIndDonate.getMeasuredHeight());
                        return true;
                    }
                });


    }



    /**
     * Method to defining the side drawer for organization login
     */
    private void initializingOrgDrawerComponants() {

        LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewOrganization  = inflater.inflate(R.layout.org_drawer, null);
        viewIndividual  = inflater.inflate(R.layout.individual_drawer, null);
        llDrawerHolder.removeView(viewOrganization);
        llDrawerHolder.removeView(viewIndividual);

        llDrawerHolder.addView(viewOrganization);

        llOrgAchiements = (LinearLayout) viewOrganization.findViewById(R.id.llOrgAchiements);
        llOrgEvents = (LinearLayout) viewOrganization.findViewById(R.id.llOrgEvent);
        llOrgRequest = (LinearLayout) viewOrganization.findViewById(R.id.llOrgRequest);

        txtProfile = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgProfile);
        txtOrgAchievement = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgAchievement);
        txtOrgCreateAchievement = (TextViewOpenSansRegular) llOrgAchiements.findViewById(R.id.txtOrgCreateAchievement);
        txtOrgViewAchievement = (TextViewOpenSansRegular) llOrgAchiements.findViewById(R.id.txtOrgViewAchievement);

        txtOrgEvent = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgEvent);
        txtOrgCreateEvent = (TextViewOpenSansRegular) llOrgEvents.findViewById(R.id.txtOrgCreateEvent);
        txtOrgViewEvent = (TextViewOpenSansRegular) llOrgEvents.findViewById(R.id.txtOrgViewEvent);

        txtOrgRequest = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgRequest);
        txtOrgCreateRequest = (TextViewOpenSansRegular) llOrgRequest.findViewById(R.id.txtOrgCreateRequest);
        txtOrgViewRequest = (TextViewOpenSansRegular) llOrgRequest.findViewById(R.id.txtOrgViewRequest);

        txtOrgAboutUs = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgAbout);
        txtOrgContactUs = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtOrgContact);
        txtSignOut = (TextViewOpenSansRegular) viewOrganization.findViewById(R.id.txtSignOut);


        txtProfile.setOnClickListener(this);
        txtOrgAchievement.setOnClickListener(this);
        txtOrgCreateAchievement.setOnClickListener(this);
        txtOrgViewAchievement.setOnClickListener(this);
        txtOrgEvent.setOnClickListener(this);
        txtOrgCreateEvent.setOnClickListener(this);
        txtOrgViewEvent.setOnClickListener(this);
        txtOrgRequest.setOnClickListener(this);
        txtOrgCreateRequest.setOnClickListener(this);
        txtOrgViewRequest.setOnClickListener(this);
        txtOrgAboutUs.setOnClickListener(this);
        txtOrgContactUs.setOnClickListener(this);
        txtSignOut.setOnClickListener(this);


        llOrgAchiements.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        llOrgAchiements.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        llOrgAchiements.setVisibility(View.GONE);

                        final int widthSpec =     View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec
                                .makeMeasureSpec(0,
                                        View.MeasureSpec.UNSPECIFIED);
                        llOrgAchiements.measure(widthSpec, heightSpec);

                        mAnimatorOrgAchievements = slideAnimator(llOrgAchiements,0,
                                llOrgAchiements.getMeasuredHeight());
                        return true;
                    }
                });

        llOrgEvents.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        llOrgEvents.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        llOrgEvents.setVisibility(View.GONE);

                        final int widthSpec =     View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec
                                .makeMeasureSpec(0,
                                        View.MeasureSpec.UNSPECIFIED);
                        llOrgEvents.measure(widthSpec, heightSpec);

                        mAnimatorOrgEvents = slideAnimator(llOrgEvents,0,
                                llOrgEvents.getMeasuredHeight());
                        return true;
                    }
                });

        llOrgRequest.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        llOrgRequest.getViewTreeObserver()
                                .removeOnPreDrawListener(this);
                        llOrgRequest.setVisibility(View.GONE);

                        final int widthSpec =     View.MeasureSpec.makeMeasureSpec(
                                0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec
                                .makeMeasureSpec(0,
                                        View.MeasureSpec.UNSPECIFIED);
                        llOrgRequest.measure(widthSpec, heightSpec);

                        mAnimatorOrgRequests = slideAnimator(llOrgRequest,0,
                                llOrgRequest.getMeasuredHeight());
                        return true;
                    }
                });

    }


    @Override
    public void onClick(View v) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.platform);

       switch (v.getId()){

            // for individual clicking event fire

            case R.id.txtIndEvent:
                if(!(fragment instanceof FragmentViewEvent)) {
                    getFragmentTransaction(new FragmentViewEvent());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtIndGroup:
                if(!(fragment instanceof FragmentViewGroups)) {
                    getFragmentTransaction(new FragmentViewGroups());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;
            case R.id.txtIndRequest:
                if(!(fragment instanceof FragmentViewRequest)) {
                    getFragmentTransaction(new FragmentViewRequest());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtIndAchievement:
                if(!(fragment instanceof FragmentViewAchievement)) {
                    getFragmentTransaction(new FragmentViewAchievement());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtIndConnect:

                if (llIndConnectPaalan.getVisibility() == View.GONE) {
                    expand(llIndConnectPaalan,mAnimatorIndConnectPaalan);
                } else {
                    collapse(llIndConnectPaalan);
                }
                break;

            case R.id.txtIndLogin:
                startActivity(new Intent(ActivityFragmentPlatform.this,Login.class));
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llIndConnectPaalan);
                }

                break;

            case R.id.txtIndRegister:
                startActivity(new Intent(ActivityFragmentPlatform.this,RegisterUser.class));
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llIndConnectPaalan);
                }
                break;

            case R.id.txtIndDonatePaalan:
                startActivity(new Intent(this, Donate.class));
//                if (llIndDonate.getVisibility() == View.GONE) {
//                    expand(llIndDonate,mAnimatorIndDonate);
//                } else {
//                    collapse(llIndDonate);
//                }

                break;

//            case R.id.txtIndDonate:
//                startActivity(new Intent(this, Donate.class));
//                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
//                    mDrawerLayout.closeDrawers();
//                    collapse(llIndDonate);
//                }
//                break;

//            case R.id.txtIndDatePaalan:
//
//                break;
            case R.id.txtIndAbout:
                if(!(fragment instanceof FragmentAboutUs)) {
                    getFragmentTransaction(new FragmentAboutUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtIndContact:
                if(!(fragment instanceof FragmentConnectWithUs)) {
                    getFragmentTransaction(new FragmentConnectWithUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            // for Organization clicking event fire

            case R.id.txtOrgProfile:
                if(!(fragment instanceof FragmentMyProfile)) {
                    getFragmentTransaction(new FragmentMyProfile());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtOrgAchievement:

                if (llOrgAchiements.getVisibility() == View.GONE) {
                    expand(llOrgAchiements,mAnimatorOrgAchievements);
                } else {
                    collapse(llOrgAchiements);
                }

                break;

            case R.id.txtOrgCreateAchievement:
                if(!(fragment instanceof FragmentCreateAchievement)) {
                    getFragmentTransaction(new FragmentCreateAchievement());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgAchiements);
                }
                break;

            case R.id.txtOrgViewAchievement:
                if(!(fragment instanceof FragmentViewAchievement)) {
                    getFragmentTransaction(new FragmentViewAchievement());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgAchiements);
                }

                break;

            case R.id.txtOrgEvent:
                if (llOrgEvents.getVisibility() == View.GONE) {
                    expand(llOrgEvents,mAnimatorOrgEvents);
                } else {
                    collapse(llOrgEvents);
                }
                break;

            case R.id.txtOrgCreateEvent:
                if(!(fragment instanceof FragmentCreateEvent)) {
                    getFragmentTransaction(new FragmentCreateEvent());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgEvents);
                }
               break;

            case R.id.txtOrgViewEvent:
                if(!(fragment instanceof FragmentViewEvent)) {
                    getFragmentTransaction(new FragmentViewEvent());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgEvents);
                }

                break;

           case R.id.txtOrgRequest:
               if (llOrgRequest.getVisibility() == View.GONE) {
                   expand(llOrgRequest,mAnimatorOrgRequests);
               } else {
                   collapse(llOrgRequest);
               }
               break;

           case R.id.txtOrgCreateRequest:
               if(!(fragment instanceof FragmentCreateRequest)) {
                   getFragmentTransaction(new FragmentCreateRequest());
               }
               if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                   mDrawerLayout.closeDrawer(GravityCompat.START);
                   collapse(llOrgRequest);
               }

               break;

           case R.id.txtOrgViewRequest:
               if(!(fragment instanceof FragmentViewRequest)) {
                   getFragmentTransaction(new FragmentViewRequest());
               }
               if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                   mDrawerLayout.closeDrawer(GravityCompat.START);
                   collapse(llOrgRequest);
               }

               break;

            case R.id.txtOrgAbout:
                if(!(fragment instanceof FragmentAboutUs)) {
                    getFragmentTransaction(new FragmentAboutUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }

                }
                break;

            case R.id.txtOrgContact:
                if(!(fragment instanceof FragmentConnectWithUs)) {
                    getFragmentTransaction(new FragmentConnectWithUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtSignOut:
                if(PREF.getLoginType().equals(Social.ORG_ENTITY)) {
                    PREF.setLogin(false);
                    DB_ADAPTER.open();
                    DB_ADAPTER.deleteProfile();
                    DB_ADAPTER.close();
                    PREF.setLoginType(Social.IND_ENTITY);
                    getProfileUpdate();
                    try {
                        llDrawerHolder.removeView(viewOrganization);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    initializingIndDrawerComponants();
                    getFragmentTransaction(new FragmentIndDashboard());

                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;
        }

    }


    @Override
    public void onCancelClicked(String label) {
        onBackPressed();
    }
}
