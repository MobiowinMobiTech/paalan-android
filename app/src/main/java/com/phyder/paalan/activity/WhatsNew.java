package com.phyder.paalan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.fragments.WhatsNewSlide;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.WhatsNewScreenModel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by yashika on 31/1/17.
 */

public class WhatsNew extends AppIntro {

    private static final String TAG = WhatsNew.class.getSimpleName();
    ArrayList<WhatsNewScreenModel> screensToRender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getScreensList();

    }

    /**
     * Function to get screen list from shared preference
     */
    private void getScreensList() {
        screensToRender = new ArrayList<WhatsNewScreenModel>();
        screensToRender = CommanUtils.getScreens(this);
        Collections.sort(screensToRender);
        for (int screenIndex = 0 ; screenIndex < screensToRender.size() ; screenIndex ++){
            Log.d(TAG, "getScreensList:  "+screenIndex+" seq "+screensToRender.get(screenIndex).getSequence());
            Log.d(TAG, "getScreensList:  "+screenIndex+" title "+screensToRender.get(screenIndex).getTitle());
            Log.d(TAG, "getScreensList:  "+screenIndex+" message "+screensToRender.get(screenIndex).getMessage());
            Log.d(TAG, "getScreensList:  "+screenIndex+" imagelink "+screensToRender.get(screenIndex).getImageLink());
        }

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

        setDoneText("Ok got it");

    }

    private void launchDashboard(){
        //// TODO: 31/1/17
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed() {
        launchDashboard();
        Toast.makeText(getApplicationContext(), "Skip", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed() {
        launchDashboard();
    }

    public void getStarted(View v){
        launchDashboard();
    }

}
