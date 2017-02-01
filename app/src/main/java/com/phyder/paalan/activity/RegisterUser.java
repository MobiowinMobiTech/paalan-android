package com.phyder.paalan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.R;
import com.phyder.paalan.fragments.AddressRegistrationInfo;
import com.phyder.paalan.fragments.OrganisationInformation;
import com.phyder.paalan.model.OrgAddressInfo;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Pramod Waghmare on 31/1/17.
 */

public class RegisterUser extends AppIntro {

    private static final String TAG = RegisterUser.class.getSimpleName();
    OrganisationInformation organisationInformation;
    AddressRegistrationInfo addressRegistrationInfo;
    private IndivitualReqRegistration indivitualResRegistration;
    boolean isIndividualFieldsEmpty = true;
    private OrgAddressInfo orgAddressInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organisationInformation = new OrganisationInformation();
        addressRegistrationInfo = new AddressRegistrationInfo();
    }

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);

        addSlide(organisationInformation);
        addSlide(addressRegistrationInfo);

        showSkipButton(false);
        setDoneText("Register");
        // Override bar/separator color.
        setBarColor(Color.parseColor("#00BCD4"));
        setSeparatorColor(Color.parseColor("#FFFFFF"));
        setFadeAnimation();

    }

    @Override
    public void onNextPressed() {
        super.onNextPressed();
        indivitualResRegistration = organisationInformation.setIndividualInfo();
    }

    @Override
    public void onDonePressed() {
        super.onDonePressed();
        orgAddressInfo = addressRegistrationInfo.setOrganisationInfo();
        registerUser();
    }

    /**
     * Function used to register user
     */
    public void registerUser() {
        if (valiateUserData()){
            if (NetworkUtil.isInternetConnected(RegisterUser.this)) {
                registerOrganisationUserWithServer();
            }else {
                CommanUtils.showAlert(this,getString(R.string.app_name),getString(R.string.network_connectivity));
            }

        }else {
            Log.d(TAG, "registerUser: "+isIndividualFieldsEmpty);
            if (!isIndividualFieldsEmpty)
                onPageSelected(0);
            else {
                onNextPressed();
            }
        }
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    /**
     * Function used to nvalidate user data
     * @return
     */
    private boolean valiateUserData() {
        Log.d(TAG, "isBasicInfoFilled valiateUserData: ");
        isIndividualFieldsEmpty = isBasicInfoFilled();
        Log.d(TAG, "isBasicInfoFilled valiateUserData: ind "+isIndividualFieldsEmpty);
        if (isIndividualFieldsEmpty){
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

            boolean isValidEmailId = organisationInformation.isValidEmailId(emailId);

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

            boolean isPasswordMatched = organisationInformation.isValidPassword();

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

    /**
     * Function to register user as an organisation with server
     */
    private void registerOrganisationUserWithServer() {
        Device.newInstance(RegisterUser.this);
        String notificationToken = Device.getInstance().getNotificationId(this);

        Log.d(TAG, "onTokenRefresh registerOrganisationUserWithServer: "+notificationToken);

        IndivitualReqRegistration.Data data = indivitualResRegistration.getData();
        // check for network connectivity
        //show dialogue
        CommanUtils.showDialog(this);
        OrganizationReqResistration reqResistration =
                OrganizationReqResistration.get(data.getName(),
                        data.getMobileno(), data.getEmailid(), data.getPassword(),
                        notificationToken, Device.getDeviceId(this) , Device.getImeiNo(this),
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
                        CommanUtils.showAlert(RegisterUser.this,getString(R.string.register_validation_title),
                                getString(R.string.register_success));
                        launchDashboard();

                    } else if (response.body().getStatus().equals("error")) {
                        String reserrorMsg = response.body().getData()[0].getErrmsg();
                        Log.d(TAG, "onResponse: " + reserrorMsg);
                        CommanUtils.showAlert(RegisterUser.this,getString(R.string.register_validation_title),
                                reserrorMsg);
                    }
                } else {
                    String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                    CommanUtils.showAlert(RegisterUser.this,getString(R.string.register_validation_title),resFailedMsg);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResRegistration> call, Throwable t) {
                CommanUtils.hideDialog();
                String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                CommanUtils.showAlert(RegisterUser.this,getString(R.string.register_validation_title),resFailedMsg);
            }
        });
    }

    /**
     * Function used to call dashboard
     */
    private void launchDashboard() {
        Intent intent = new Intent(this,ActivityFragmentPlatform.class);
        startActivity(intent);
        finish();
    }

}
