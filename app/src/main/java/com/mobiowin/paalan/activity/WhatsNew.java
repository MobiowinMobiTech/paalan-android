package com.mobiowin.paalan.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.mobiowin.paalan.services.CronJobService;
import com.mobiowin.paalan.fragments.WhatsNewSlide;
import com.mobiowin.paalan.model.WhatsNewScreenModel;
import com.mobiowin.paalan.utils.CommanUtils;
import com.mobiowin.paalan.utils.Config;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pramod Waghmare on 31/1/17.
 */

public class WhatsNew extends AppIntro {

    private static final String TAG = WhatsNew.class.getSimpleName();
    ArrayList<WhatsNewScreenModel> screensToRender;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: scheduleAlarm");
        if (CommanUtils.isNewUser(this))
            scheduleAlarm();
        getScreensList();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void scheduleAlarm() {

        Log.d("", "scheduleAlarm: new user "+ CommanUtils.isNewUser(this));
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, CronJobService.class);
        PendingIntent alarmIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(),
                Config.TRIGGER_TIME, alarmIntent);

        CommanUtils.setNewUserStatus(this,false);

    }




    /**
     * Function to get screen list from shared preference
     */
    private void getScreensList() {
        screensToRender = new ArrayList<WhatsNewScreenModel>();
        screensToRender = CommanUtils.getScreens(this);
        Collections.sort(screensToRender);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Bundle savedInstanceState) {


        for(int screenIndex = 0 ; screenIndex < screensToRender.size(); screenIndex ++){
            // fragment to show
            WhatsNewSlide whatsNewSlide = new WhatsNewSlide();
            Bundle bundle = new Bundle();
            bundle.putString("title",screensToRender.get(screenIndex).getTitle());
            bundle.putString("message",screensToRender.get(screenIndex).getMessage());
            bundle.putString("imageLink",screensToRender.get(screenIndex).getImageLink());

            whatsNewSlide.setArguments(bundle);

            addSlide(whatsNewSlide);
        }

        // Override bar/separator color.
        setBarColor(Color.parseColor("#00BCD4"));
        setSeparatorColor(Color.parseColor("#FFFFFF"));

        setFadeAnimation();
        //set text to done button

        setDoneText("Ok got it");

    }

    /**
     * To launch dashboard
     */
    private void launchDashboard(){
        Intent intent = new Intent(this, ActivityFragmentPlatform.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed() {
        launchDashboard();
    }

    @Override
    public void onDonePressed() {
        launchDashboard();
    }

    public void getStarted(View v){
        launchDashboard();
    }

}
