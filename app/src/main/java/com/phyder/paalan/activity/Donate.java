package com.phyder.paalan.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.R;
import com.phyder.paalan.fragments.AddressInformation;
import com.phyder.paalan.fragments.DonateView;
import com.phyder.paalan.fragments.OrganisationInformation;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.utils.CommanUtils;

/**
 * Created by yashika on 6/2/17.
 */

public class Donate extends AppIntro {

    private static final String TAG = Donate.class.getSimpleName();
    DonateView donateView = new DonateView();
    AddressInformation addressInformation;
    OrganisationInformation organisationInformation;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Bundle savedInstanceState) {


        Bundle bundle = new Bundle();
        bundle.putBoolean("isForDonate",true);

        organisationInformation = new OrganisationInformation();
        organisationInformation.setArguments(bundle);

        addressInformation = new AddressInformation();
        addressInformation.setArguments(bundle);


        addSlide(new DonateView());
        addSlide(organisationInformation);
        addSlide(addressInformation);

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
            Log.d(TAG, "onDonePressed: valid");
        }else {
            Log.d(TAG, "onDonePressed: not valid");
        }

    }

    private boolean validateData() {
        String donateCategory = donateView.getSelectedCategory();
        String donateImage = donateView.getDonateCategoryImage();

        IndivitualReqRegistration.Data  data = organisationInformation.setIndividualInfo().getData();
        String address = addressInformation.getAddress();

        if (TextUtils.isEmpty(donateCategory)){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.select_category));
            return false;
        }else if (TextUtils.isEmpty(data.getName())) {
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.error_empty_name));
            return false;
        }else if(TextUtils.isEmpty(data.getEmailid()) || !organisationInformation.isValidEmailId(data.getEmailid())){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.email_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(data.getMobileno()) || data.getMobileno().length() < 10 ){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.mobile_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(address)){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.addess_cannot_be_empty));
            return false;
        }

        return true;
    }
}
