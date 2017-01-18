package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.phyder.paalan.R;
import com.phyder.paalan.fragments.AddressRegistrationInfo;
import com.phyder.paalan.fragments.BasicRegistrationInfo;
import com.phyder.paalan.services.RegisterUserInterface;
import com.phyder.paalan.utils.NonSwipeableViewPager;

/**
 * Created by Pramod Waghmare on 18/1/17.
 */

public class RegisterUserActivity extends AppCompatActivity implements RegisterUserInterface {

    private NonSwipeableViewPager registerUserWizard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        initializeViews();

    }

    /**
     * Function used to initialize views
     */
    private void initializeViews() {
        registerUserWizard = (NonSwipeableViewPager) findViewById(R.id.registerUserWizard);
        registerUserWizard.setAdapter(new CustomWizards(getSupportFragmentManager()));
    }

    @Override
    public void navigatePrevious() {
        registerUserWizard.setCurrentItem(0);
    }

    @Override
    public void navigateNext() {
        registerUserWizard.setCurrentItem(1);
    }

    @Override
    public void registerUser() {

    }

    /**
     *
     */
    private class CustomWizards extends FragmentPagerAdapter {
        public CustomWizards(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new BasicRegistrationInfo();
                case 1:
                    return new AddressRegistrationInfo();
            }
         return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
