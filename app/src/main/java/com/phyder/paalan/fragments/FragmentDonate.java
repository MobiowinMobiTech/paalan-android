package com.phyder.paalan.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by yashika on 6/2/17.
 */

public class FragmentDonate extends AppIntro {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Bundle savedInstanceState) {


        addSlide(new DonateView());
        addSlide(new OrganisationInformation());
        addSlide(new AddressInformation());

        // Override bar/separator color.
        setBarColor(Color.parseColor("#00BCD4"));
        setSeparatorColor(Color.parseColor("#FFFFFF"));

        setFadeAnimation();

        showSkipButton(false);

        //set text to done button
        setDoneText("Donate");

    }
}
