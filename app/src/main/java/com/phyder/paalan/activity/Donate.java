package com.phyder.paalan.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.fragments.AddressInformation;
import com.phyder.paalan.fragments.DonateView;
import com.phyder.paalan.fragments.OrganisationInformation;
import com.phyder.paalan.model.OrgAddressInfo;

/**
 * Created by yashika on 6/2/17.
 */

public class Donate extends AppIntro {

    DonateView donateView = new DonateView();
    AddressInformation addressInformation;
    OrgAddressInfo orgAddressInfo;


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

    @Override
    public void onDonePressed() {
        super.onDonePressed();

        if (validateData()){

        }

    }

    private boolean validateData() {
        String donateCategory = donateView.getSelectedCategory();
        String donateImage = donateView.getDonateCategoryImage();
        return false;
    }
}
