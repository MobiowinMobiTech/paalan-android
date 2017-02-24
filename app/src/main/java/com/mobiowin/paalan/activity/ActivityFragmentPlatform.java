package com.mobiowin.paalan.activity;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobiowin.paalan.db.DBAdapter;
import com.mobiowin.paalan.fragments.FragmentAboutUs;
import com.mobiowin.paalan.fragments.FragmentCreateAchievement;
import com.mobiowin.paalan.fragments.FragmentDashBorad;
import com.mobiowin.paalan.fragments.FragmentIndDashboard;
import com.mobiowin.paalan.fragments.FragmentMyProfile;
import com.mobiowin.paalan.fragments.FragmentNotifications;
import com.mobiowin.paalan.fragments.FragmentViewAchievement;
import com.mobiowin.paalan.fragments.FragmentViewDetailsAchievement;
import com.mobiowin.paalan.fragments.FragmentViewDetailsRequest;
import com.mobiowin.paalan.fragments.FragmentViewEvent;
import com.mobiowin.paalan.fragments.FragmentViewGroups;
import com.mobiowin.paalan.fragments.FragmentViewRequest;
import com.mobiowin.paalan.helper.DialogPopupListener;
import com.mobiowin.paalan.helper.PaalanGetterSetter;
import com.mobiowin.paalan.payload.request.RequestBroadcastNotification;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsAchievement;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsEvent;
import com.mobiowin.paalan.payload.response.OrgResNotificationDetailsRequest;
import com.mobiowin.paalan.services.Device;
import com.mobiowin.paalan.utils.CommanUtils;
import com.mobiowin.paalan.utils.Config;
import com.mobiowin.paalan.utils.NetworkUtil;
import com.mobiowin.paalan.utils.PreferenceUtils;
import com.mobiowin.paalan.utils.TextViewOpenSansRegular;
import com.mobiowin.paalan.utils.TextViewOpenSansSemiBold;
import com.phyder.paalan.R;
import com.mobiowin.paalan.fragments.FragmentConnectWithUs;
import com.mobiowin.paalan.fragments.FragmentCreateEvent;
import com.mobiowin.paalan.fragments.FragmentCreateRequest;
import com.mobiowin.paalan.fragments.FragmentViewDetailsEvent;
import com.mobiowin.paalan.services.PaalanServices;
import com.mobiowin.paalan.helper.Social;
import com.mobiowin.paalan.utils.RoundedImageView;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by cmss on 13/1/17.
 */
public class ActivityFragmentPlatform extends AppCompatActivity implements View.OnClickListener,
        DialogPopupListener {

    private static final String TAG = ActivityFragmentPlatform.class.getSimpleName();

    private static DrawerLayout mDrawerLayout;
    private static LinearLayout mDrawerList;

    private Fragment fragment;
    private static RoundedImageView IMG_PROFILE;
    private static TextView TXT_USER_NAME;
    private static PreferenceUtils PREF;
    private static DBAdapter DB_ADAPTER;
    final static int IMG_RESULT = 1;
    final int CAMERA_REQUEST = 1888;

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

    private static Toolbar TOOLBAR;
    private static boolean IS_LAST = true;
    private static AppCompatActivity _CONTEXT;

//    private AdView mAdView;
//    private ViewGroup adContainer;

    private static ImageView IMG_DONATE,IMG_NOTIFICATION;
    private static TextViewOpenSansSemiBold TOOLBAR_TITLE;
    private static TextViewOpenSansSemiBold NOTIFICATION_COUNT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_platform);
        initComponents();
//        initAdsMob();
        initSideDrawer();
    }


    private void initComponents() {

        _CONTEXT = this;
        PREF = new PreferenceUtils(ActivityFragmentPlatform.this);
        DB_ADAPTER = new DBAdapter(ActivityFragmentPlatform.this);
        llDrawerHolder = (LinearLayout) findViewById(R.id.llDrawerHolder);

        launchDashboard();

            try {
                String entity = getIntent().getExtras().getString(Config.ENTITY);
                if (PREF.getLoginType().equals(Social.IND_ENTITY)) {
                    if(entity.equals("multiple_notifications")){
                         getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentNotifications());
                    }else {
                        setRetrofitRequest(ActivityFragmentPlatform.this, PaalanGetterSetter.getNotificationOrgId(),
                                PaalanGetterSetter.getNotificationRecordId(), entity);
                    }
                } else {
                    CommanUtils.showToast(ActivityFragmentPlatform.this, "Please sign out organization");
                }
                PaalanGetterSetter.clearNotifications();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    private void launchDashboard(){

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

//    private void initAdsMob() {
//
//        //render admob
//        adContainer = (ViewGroup) findViewById(R.id.adMobView);
//        mAdView = new AdView(this);
//        mAdView.setLayoutParams(new RelativeLayout.LayoutParams
//                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//        //mAdView.setPadding(7, 7, 7, 7);
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));
//        adContainer.addView(mAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        showAdsMob();
//    }


    private void initSideDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);

        IMG_DONATE = (ImageView) findViewById(R.id.action_donate);
        IMG_NOTIFICATION = (ImageView) findViewById(R.id.action_notification);
        NOTIFICATION_COUNT = (TextViewOpenSansSemiBold) findViewById(R.id.action_count);
        TOOLBAR_TITLE = (TextViewOpenSansSemiBold) findViewById(R.id.txtDashTitle);

        IMG_DONATE.setOnClickListener(this);
        IMG_NOTIFICATION.setOnClickListener(this);

        TOOLBAR = (Toolbar) findViewById(R.id.toolbar);
        TOOLBAR.setTitle("");
        setSupportActionBar(TOOLBAR);

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

    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
        super.onPause();
    }


    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mAdView != null) {
//            mAdView.destroy();
//        }

    }



    public static void changeToolbarTitleIcon(String title,int icon) {

        if (TOOLBAR != null) {
            TOOLBAR_TITLE.setText(title);
            TOOLBAR.setNavigationIcon(icon);

            if(_CONTEXT!=null){
                IS_LAST = title.equals(_CONTEXT.getString(R.string.dash_borad)) ? true : false;
            }


            int notification_donate_IconVisibility =
                (title.equals(_CONTEXT.getString(R.string.dash_borad)) && PREF.getLoginType().equals(Social.IND_ENTITY))
                 ? View.VISIBLE : View.GONE;
            IMG_DONATE.setVisibility(notification_donate_IconVisibility);
            IMG_NOTIFICATION.setVisibility(notification_donate_IconVisibility);

            DB_ADAPTER.open();
            int count = DB_ADAPTER.getUnreadNotificationCounts();
            DB_ADAPTER.close();
            NOTIFICATION_COUNT.setText(""+count);

            int notificationCountVisibility = (count > 0 && notification_donate_IconVisibility==View.VISIBLE)
                    ? View.VISIBLE : View.GONE;
            NOTIFICATION_COUNT.setVisibility(notificationCountVisibility);

        }
    }




//    private void showAdsMob(){
//
//        Log.e(TAG,"showAdsMob_adContainer :"+adContainer);
//        if(adContainer!=null) {
//            if (NetworkUtil.isInternetConnected(this)) {
//                adContainer.setVisibility(View.VISIBLE);
//            } else {
//                adContainer.setVisibility(View.GONE);
//            }
//        }
//
//    }

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
                getFragmentTransaction(ActivityFragmentPlatform.this,fragment);
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                CommanUtils.showToast(this, "Please try again");
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


    private static void getFragmentTransaction(final FragmentActivity context,Fragment fragment){

        context.getSupportFragmentManager().beginTransaction()
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
            showExitAlert();
        } else if (getSupportFragmentManager().findFragmentById(R.id.platform) instanceof FragmentDashBorad) {
            showExitAlert();
        } else{
            super.onBackPressed();
        }
    }

    /**
     * Function to display exit app dialogue with options
     */
    private void showExitAlert(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.app_name));
        alertBuilder.setMessage(getString(R.string.exit_app_message));
        alertBuilder.setIcon(R.mipmap.ic_launcher);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PaalanGetterSetter.setAppInitCall(true);
//                unregisterReceiver(broadcastReceiver);
                finish();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        _CONTEXT = this;
//        if (mAdView != null) {
//            mAdView.resume();
//        }

        getProfileUpdate();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        registerReceiver(broadcastReceiver, filter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if(IS_LAST){

                    if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
                        mDrawerLayout.closeDrawers();  // CLOSE DRAWER
                    else
                        mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER

                }else{
                    onBackPressed();
                }

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
                DB_ADAPTER.open();
                if (DB_ADAPTER.getAllEvent("F",PREF.getLoginType()).getCount() > 0)
                    if(!(fragment instanceof FragmentViewEvent)) {
                        getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewEvent());
                    }else{
                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                else
                    CommanUtils.showToast(getApplicationContext(),getString(R.string.event_not_found));

                DB_ADAPTER.close();
                break;

            case R.id.txtIndGroup:
                DB_ADAPTER.open();
                if (DB_ADAPTER.getAllGroups("F").getCount() > 0)
                    if(!(fragment instanceof FragmentViewGroups)) {
                        getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewGroups());
                    }else{
                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                else
                    CommanUtils.showToast(getApplicationContext(),getString(R.string.group_not_found));
                DB_ADAPTER.close();
                break;
            case R.id.txtIndRequest:
                DB_ADAPTER.open();
                if (DB_ADAPTER.getAllRequests("F",PREF.getLoginType()).getCount() > 0)
                    if(!(fragment instanceof FragmentViewRequest)) {
                        getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewRequest());
                    }else{
                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                else
                    CommanUtils.showToast(getApplicationContext(),getString(R.string.social_request_not_found));
                DB_ADAPTER.close();
                break;

            case R.id.txtIndAchievement:
                DB_ADAPTER.open();
                if (DB_ADAPTER.getAllAchievements("F",PREF.getLoginType()).getCount() > 0)
                    if(!(fragment instanceof FragmentViewAchievement)) {
                        getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewAchievement());
                    }else{
                        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }
                else
                    CommanUtils.showToast(getApplicationContext(),getString(R.string.achievement_not_found));
                DB_ADAPTER.close();
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
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawers();
                    collapse(llIndDonate);
                }

                break;

            case R.id.txtIndAbout:
                if(!(fragment instanceof FragmentAboutUs)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentAboutUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            case R.id.txtIndContact:
                if(!(fragment instanceof FragmentConnectWithUs)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentConnectWithUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

            // for Organization clicking event fire

            case R.id.txtOrgProfile:
                if(!(fragment instanceof FragmentMyProfile)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentMyProfile());
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
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentCreateAchievement());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgAchiements);
                }
                break;

            case R.id.txtOrgViewAchievement:
                if(!(fragment instanceof FragmentViewAchievement)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewAchievement());
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
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentCreateEvent());
                }
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    collapse(llOrgEvents);
                }
               break;

            case R.id.txtOrgViewEvent:
                if(!(fragment instanceof FragmentViewEvent)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewEvent());
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
                   getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentCreateRequest());
               }
               if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                   mDrawerLayout.closeDrawer(GravityCompat.START);
                   collapse(llOrgRequest);
               }

               break;

           case R.id.txtOrgViewRequest:
               if(!(fragment instanceof FragmentViewRequest)) {
                   getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentViewRequest());
               }
               if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                   mDrawerLayout.closeDrawer(GravityCompat.START);
                   collapse(llOrgRequest);
               }

               break;

            case R.id.txtOrgAbout:
                if(!(fragment instanceof FragmentAboutUs)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentAboutUs());
                }else{
                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }

                }
                break;

            case R.id.txtOrgContact:
                if(!(fragment instanceof FragmentConnectWithUs)) {
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentConnectWithUs());
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
                    getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentIndDashboard());

                    if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    }
                }
                break;

           case R.id.action_notification:
               getFragmentTransaction(ActivityFragmentPlatform.this,new FragmentNotifications());
               break;

           case R.id.action_donate:
              startActivity(new Intent(ActivityFragmentPlatform.this,Donate.class));
              break;
        }

    }


    @Override
    public void onCancelClicked(String label) {
        onBackPressed();
    }

//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            try {
//                showAdsMob();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    };


    /**
     * putting request to call
     * @param recordId notification type id
     * @param notificationType identify which type of notification such as events,achievements,social requests
     */
    public static void setRetrofitRequest(final FragmentActivity context,String orgId,String recordId,final String notificationType){

        if(NetworkUtil.isInternetConnected(context)) {

            Device.newInstance(context);

            RequestBroadcastNotification requestBroadcastNotification = RequestBroadcastNotification.get(orgId,
                    recordId, notificationType);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            CommanUtils.showDialog(context);

            if (notificationType.contains(context.getString(R.string.click_event_event))) {
                getEventsCallBack(context,requestBroadcastNotification, mPaalanServices);
            } else if (notificationType.contains(context.getString(R.string.click_event_achievement))) {
                getAchiementsCallBack(context,requestBroadcastNotification, mPaalanServices);
            } else if (notificationType.contains(context.getString(R.string.click_event_social_request))) {
                getRequestsCallBack(context,requestBroadcastNotification, mPaalanServices);
            }

            CommanUtils.hideDialog();
            PaalanGetterSetter.setAppInitCall(true);
        }else {
            CommanUtils.showAlertDialog(context,context.getResources().getString(R.string.error_internet));
        }

    }

    public static void getEventsCallBack(final FragmentActivity context,final RequestBroadcastNotification broadcastNotification,PaalanServices mPaalanServices){

        Call<OrgResNotificationDetailsEvent> resBroadcast = mPaalanServices.eventBroadcastNotification(broadcastNotification);

        resBroadcast.enqueue(new Callback<OrgResNotificationDetailsEvent>() {
            @Override
            public void onResponse(Call<OrgResNotificationDetailsEvent> call, Response<OrgResNotificationDetailsEvent> response) {
                Log.e(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals("success")) {

                        if(response.body().getData()[0].getOrgreclist().length>0) {
                            DB_ADAPTER.open();

                            for (int i = 0; i < response.body().getData()[0].getOrgreclist().length; i++) {

                                DB_ADAPTER.populatingEventsIntoDB(response.body().getData()[0].getOrgreclist()[i].getOrgId(), null,
                                        response.body().getData()[0].getOrgreclist()[i].getEventId(),
                                        response.body().getData()[0].getOrgreclist()[i].getName(),
                                        response.body().getData()[0].getOrgreclist()[i].getTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getSubTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getDiscription(),
                                        response.body().getData()[0].getOrgreclist()[i].getOthers(),
                                        response.body().getData()[0].getOrgreclist()[i].getStartDt(),
                                        response.body().getData()[0].getOrgreclist()[i].getEndDt(),
                                        response.body().getData()[0].getOrgreclist()[i].getDeleteFlag(), null, null, null,
                                        PREF.getLoginType());

                            }

                            DB_ADAPTER.updateNotification(broadcastNotification.getData().getRecordid());
                            DB_ADAPTER.close();

                            PaalanGetterSetter.setEventID(broadcastNotification.getData().getRecordid());
                            getFragmentTransaction(context, new FragmentViewDetailsEvent());
                        }else{
                            CommanUtils.showToast(context,context.getString(R.string.technical_issue));
                        }
                    } else if(response.body().getStatus().equals("error")){
                        CommanUtils.showToast(context,response.body().getMessage());
                    }
                } else if (response.body() == null) {
                    CommanUtils.showToast(context,context.getResources().getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<OrgResNotificationDetailsEvent> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommanUtils.showToast(context,context.getResources().getString(R.string.error_timeout));
            }
        });

    }

    public static void getRequestsCallBack(final FragmentActivity context,final RequestBroadcastNotification broadcastNotification,PaalanServices mPaalanServices){

        Call<OrgResNotificationDetailsRequest> resBroadcast = mPaalanServices.requestBroadcastNotification(broadcastNotification);

        resBroadcast.enqueue(new Callback<OrgResNotificationDetailsRequest>() {
            @Override
            public void onResponse(Call<OrgResNotificationDetailsRequest> call, Response<OrgResNotificationDetailsRequest> response) {
                Log.e(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals("success")) {

                        if(response.body().getData()[0].getOrgreclist().length>0) {
                            DB_ADAPTER.open();

                            for (int i = 0; i < response.body().getData()[0].getOrgreclist().length; i++) {

                                DB_ADAPTER.populatingRequestIntoDB(response.body().getData()[0].getOrgreclist()[i].getOrgId(), null,
                                        response.body().getData()[0].getOrgreclist()[i].getRequestId(),
                                        response.body().getData()[0].getOrgreclist()[i].getOrgName(),
                                        response.body().getData()[0].getOrgreclist()[i].getTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getSubTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getDiscription(),
                                        response.body().getData()[0].getOrgreclist()[i].getOthers(),
                                        response.body().getData()[0].getOrgreclist()[i].getLocation(),
                                        response.body().getData()[0].getOrgreclist()[i].getDeleteFlag(),
                                        PREF.getLoginType());
                            }

                            DB_ADAPTER.updateNotification(broadcastNotification.getData().getRecordid());
                            DB_ADAPTER.close();

                            PaalanGetterSetter.setRequestID(broadcastNotification.getData().getRecordid());
                            getFragmentTransaction(context, new FragmentViewDetailsRequest());
                        }else{
                            CommanUtils.showToast(context,context.getString(R.string.technical_issue));
                        }

                    } else if(response.body().getStatus().equals("error")){
                        CommanUtils.showToast(context,response.body().getMessage());
                    }
                }else if(response.body()==null){
                    CommanUtils.showToast(context,context.getResources().getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<OrgResNotificationDetailsRequest> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommanUtils.showToast(context,context.getResources().getString(R.string.error_timeout));
            }
        });
    }

    public static void getAchiementsCallBack(final FragmentActivity context,final RequestBroadcastNotification broadcastNotification,PaalanServices mPaalanServices){

        Call<OrgResNotificationDetailsAchievement> resBroadcast = mPaalanServices.achievementBroadcastNotification(broadcastNotification);

        resBroadcast.enqueue(new Callback<OrgResNotificationDetailsAchievement>() {
            @Override
            public void onResponse(Call<OrgResNotificationDetailsAchievement> call, Response<OrgResNotificationDetailsAchievement> response) {
                Log.e(TAG, "onResponse: " + response.body());

                if (response.isSuccessful()) {

                    if (response.body().getStatus().equals("success")) {

                        if(response.body().getData()[0].getOrgreclist().length>0) {

                            DB_ADAPTER.open();

                            for (int i = 0; i < response.body().getData()[0].getOrgreclist().length; i++) {

                                DB_ADAPTER.populatingAchievementsIntoDB(response.body().getData()[0].getOrgreclist()[i].getOrgId(), null,
                                        response.body().getData()[0].getOrgreclist()[i].getAchievementId(),
                                        response.body().getData()[0].getOrgreclist()[i].getOrgName(),
                                        response.body().getData()[0].getOrgreclist()[i].getTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getSubTitle(),
                                        response.body().getData()[0].getOrgreclist()[i].getDiscription(),
                                        response.body().getData()[0].getOrgreclist()[i].getOthers(),
                                        response.body().getData()[0].getOrgreclist()[i].getImage1(),
                                        response.body().getData()[0].getOrgreclist()[i].getImage2(),
                                        response.body().getData()[0].getOrgreclist()[i].getImage3(),
                                        response.body().getData()[0].getOrgreclist()[i].getImage4(),
                                        response.body().getData()[0].getOrgreclist()[i].getDeleteFlag(),
                                        PREF.getLoginType());
                            }
                            DB_ADAPTER.updateNotification(broadcastNotification.getData().getRecordid());
                            DB_ADAPTER.close();

                            PaalanGetterSetter.setAchivementID(broadcastNotification.getData().getRecordid());
                            getFragmentTransaction(context, new FragmentViewDetailsAchievement());
                        }else{
                            CommanUtils.showToast(context,context.getString(R.string.technical_issue));
                        }

                    } else if(response.body().getStatus().equals("error")){
                        CommanUtils.showToast(context,response.body().getMessage());
                    }
                }else if(response.body()==null){
                    CommanUtils.showToast(context,context.getResources().getString(R.string.error_server));
                }
            }

            @Override
            public void onFailure(Call<OrgResNotificationDetailsAchievement> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                CommanUtils.showToast(context,context.getResources().getString(R.string.error_timeout));
            }
        });
    }
}
