package com.phyder.paalan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.fragments.WhatsNewSlide;
import com.phyder.paalan.model.WhatsNewScreenModel;
import com.phyder.paalan.utils.CommanUtils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pramod Waghmare on 31/1/17.
 */

public class WhatsNew extends AppIntro {

    private static final String TAG = WhatsNew.class.getSimpleName();
    ArrayList<WhatsNewScreenModel> screensToRender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CommanUtils.setNewUserStatus(this,true);

        getScreensList();

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
