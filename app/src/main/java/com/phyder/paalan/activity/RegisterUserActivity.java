package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phyder.paalan.R;
import com.phyder.paalan.fragments.AddressRegistrationInfo;
import com.phyder.paalan.fragments.BasicRegistrationInfo;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.services.RegisterUserInterface;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.OrgAddressInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Pramod Waghmare on 18/1/17.
 */
public class RegisterUserActivity extends AppCompatActivity implements RegisterUserInterface {

    private static final String TAG = RegisterUserActivity.class.getSimpleName();
    private ViewPager registerUserWizard;
    private IndivitualReqRegistration indivitualResRegistration;
    private OrgAddressInfo orgAddressInfo;
    TextView txtWizardName;
    View stepViewCredentials, stepViewAddress;
    BasicRegistrationInfo basicRegistrationInfo;
    AddressRegistrationInfo addressRegistrationInfo;
    LinearLayout stepViewRegistration;
    boolean isIndividualFieldsEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        basicRegistrationInfo = new BasicRegistrationInfo();
        addressRegistrationInfo = new AddressRegistrationInfo();

        initializeViews();

    }

    /**
     * Function used to initialize views
     */
    private void initializeViews() {
        txtWizardName = (TextView)findViewById(R.id.txtWizardName);
        txtWizardName.setText(getString(R.string.user_credential));

        stepViewRegistration = (LinearLayout)findViewById(R.id.stepViewRegistration);

        stepViewAddress = (View) findViewById(R.id.stepViewAddress);
        stepViewCredentials = (View)findViewById(R.id.stepViewCredentials);

        registerUserWizard = (ViewPager) findViewById(R.id.registerUserWizard);
        registerUserWizard.setAdapter(new CustomWizards(getSupportFragmentManager()));
        registerUserWizard.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    navigatePrevious();
                else
                    navigateNext();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void navigatePrevious() {
        registerUserWizard.setCurrentItem(0);
        txtWizardName.setText(getString(R.string.user_credential));
        toggleStepViewCredentials(false);

    }

    @Override
    public void navigateNext() {
        registerUserWizard.setCurrentItem(1);
        txtWizardName.setText(getString(R.string.user_address));
        toggleStepViewCredentials(true);
    }

    /**
     * Function to toggle steps images
     * Navigation page indicator
     * @param visible : visibility to set
     */
    private void toggleStepViewCredentials(boolean visible) {
        if (stepViewRegistration.getVisibility() == View.VISIBLE)
            if (!visible){
                stepViewCredentials.setBackgroundDrawable( getResources().getDrawable(R.drawable.wizard_selected) );
                stepViewAddress.setBackgroundDrawable( getResources().getDrawable(R.drawable.wizard_un_selected) );
            }else {
                stepViewCredentials.setBackgroundDrawable( getResources().getDrawable(R.drawable.wizard_un_selected) );
                stepViewAddress.setBackgroundDrawable( getResources().getDrawable(R.drawable.wizard_selected) );
            }
    }

    @Override
    public void registerUser() {
        if (valiateUserData()){
            if (NetworkUtil.isInternetConnected(RegisterUserActivity.this)) {
                registerOrganisationUserWithServer();
            }else {
                CommanUtils.showAlert(this,getString(R.string.app_name),getString(R.string.network_connectivity));
            }

        }else {
            if (!isIndividualFieldsEmpty)
                navigatePrevious();
            else {
                navigateNext();
            }
        }
    }

    /**
     * Function to register user as an organisation with server
     */
    private void registerOrganisationUserWithServer() {
        Device.newInstance(RegisterUserActivity.this);
        String notificationToken = Device.getInstance().getNotificationId();

        Log.d(TAG, "onTokenRefresh registerOrganisationUserWithServer: "+notificationToken);

        IndivitualReqRegistration.Data data = indivitualResRegistration.getData();
        // check for network connectivity
        //show dialogue
        CommanUtils.showDialog(this);
        OrganizationReqResistration reqResistration =
                OrganizationReqResistration.get(data.getName(),
                        data.getMobileno(), data.getEmailid(), data.getPassword(),
                        notificationToken,Device.getDeviceId(this) , Device.getImeiNo(this),
                        orgAddressInfo.getAddress(), orgAddressInfo.getCity(),
                        orgAddressInfo.getState(), orgAddressInfo.getPincode(), orgAddressInfo.getPincode());


        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Call<OrganizationResRegistration> resRegistrationCall = mPaalanServices.orgRegistration(reqResistration);
        resRegistrationCall.enqueue(new Callback<OrganizationResRegistration>() {
            @Override
            public void onResponse(Call<OrganizationResRegistration> call, Response<OrganizationResRegistration> response) {
                Log.d(TAG, "onResponse: res " + response);
                Log.d(TAG, "onResponse: status " + response.body().getStatus());
                Log.d(TAG, "onResponse: isSuccess " + response.isSuccessful());
                CommanUtils.hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("success")) {
                        Log.d(TAG, "onResponse: status success");
                        CommanUtils.showAlert(RegisterUserActivity.this,getString(R.string.register_validation_title),
                                getString(R.string.register_success));
                        //// TODO: 24/1/17 jump to dashboard

                    } else if (response.body().getStatus().equals("error")) {
                        String reserrorMsg = response.body().getData()[0].getErrmsg();
                        Log.d(TAG, "onResponse: " + reserrorMsg);
                        CommanUtils.showAlert(RegisterUserActivity.this,getString(R.string.register_validation_title),
                                reserrorMsg);
                    }
                } else {
                    String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                    CommanUtils.showAlert(RegisterUserActivity.this,getString(R.string.register_validation_title),resFailedMsg);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResRegistration> call, Throwable t) {
                CommanUtils.hideDialog();
                String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                CommanUtils.showAlert(RegisterUserActivity.this,getString(R.string.register_validation_title),resFailedMsg);
            }
        });
    }

    /**
     * Function used to nvalidate user data
     * @return
     */
    private boolean valiateUserData() {
        Log.d(TAG, "isBasicInfoFilled valiateUserData: ");
        isIndividualFieldsEmpty = isBasicInfoFilled();
        Log.d(TAG, "isBasicInfoFilled valiateUserData: ind "+isIndividualFieldsEmpty);
        if (true){
            return isAddressInfoFilled();
        }else {
            Log.d(TAG, "isBasicInfoFilled valiateUserData: else"+isIndividualFieldsEmpty);
            if (isIndividualFieldsEmpty)
                return true;
            else
                return false;

        }

    }

    /**
     * Function to validate if address page filled properly
     * @return : validation status
     */
    private boolean isAddressInfoFilled() {
        if (TextUtils.isEmpty(orgAddressInfo.getAddress())){
            CommanUtils.showAlert(this,getString(R.string.register),
                    getString(R.string.addess_cannot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(orgAddressInfo.getCity())){
            CommanUtils.showAlert(this,getString(R.string.register),
                    getString(R.string.city_cannot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(orgAddressInfo.getState())){
            CommanUtils.showAlert(this,getString(R.string.register),
                    getString(R.string.state_cannot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(orgAddressInfo.getCountry())){
            CommanUtils.showAlert(this,getString(R.string.register),
                    getString(R.string.country_cannot_be_empty));
            return false;
        }
        if (TextUtils.isEmpty(orgAddressInfo.getPincode())){
            CommanUtils.showAlert(this,getString(R.string.register),
                    getString(R.string.pincode_cannot_be_empty));
            return false;
        }
        return true;
    }

    /**
     * Function to validate users basic information
     * @return : basic info validation result
     */
    private boolean isBasicInfoFilled() {
        if (indivitualResRegistration != null){
            IndivitualReqRegistration.Data data = indivitualResRegistration.getData();
            String name = data.getName();
            String mobileNo = data.getMobileno();
            String emailId = data.getEmailid();

            Log.d(TAG, "isBasicInfoFilled: valid fields "+isIndividualFieldsEmpty);

            if (TextUtils.isEmpty(name)){
                CommanUtils.showAlert(this,getString(R.string.register),
                        getString(R.string.fields_cannot_be_empty));
                return false;
            }

            boolean isValidEmailId = basicRegistrationInfo.isValidEmailId(emailId);

            Log.d(TAG, "isBasicInfoFilled: valid email "+isValidEmailId);

            if (!isValidEmailId){
                CommanUtils.showAlert(this,getString(R.string.email_validation_id),
                        getString(R.string.email_validation_mesasage));
                return  false;
            }

            if (mobileNo.length() < 10){
                CommanUtils.showAlert(this,getString(R.string.register_validation_title),
                        getString(R.string.mobile_validation_mesasage));
                return  false;
            }

            boolean isPasswordMatched = basicRegistrationInfo.isValidPassword();

            Log.d(TAG, "isBasicInfoFilled: valid password "+isPasswordMatched);

            if (!isPasswordMatched){
                CommanUtils.showAlert(this,getString(R.string.password_validation_id),
                        getString(R.string.password_validation_mesasage));
                return  false;
            }

            return true;
        }else
            return false;


    }


    @Override
    public void setIndividualRegInfo(IndivitualReqRegistration individualInfo) {
        indivitualResRegistration = individualInfo;
    }

    @Override
    public void setAddressRegInfo(OrgAddressInfo orgAddressInfo) {
        this.orgAddressInfo = orgAddressInfo;
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
                    Bundle bundle = new Bundle();
                    basicRegistrationInfo.setArguments(bundle);
                    return basicRegistrationInfo;
                case 1:
                    return addressRegistrationInfo;
            }
         return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
