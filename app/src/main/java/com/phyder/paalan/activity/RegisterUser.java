package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.fragments.AddressRegistrationInfo;
import com.phyder.paalan.fragments.BasicRegistrationInfo;

/**
 * Created by yashika on 31/1/17.
 */

public class RegisterUser extends AppIntro {

    BasicRegistrationInfo basicRegistrationInfo;
    AddressRegistrationInfo addressRegistrationInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basicRegistrationInfo = new BasicRegistrationInfo();
        addressRegistrationInfo = new AddressRegistrationInfo();
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);


        addSlide(basicRegistrationInfo);
        addSlide(addressRegistrationInfo);



    }
}
