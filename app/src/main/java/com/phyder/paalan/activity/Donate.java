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
import com.phyder.paalan.payload.request.SubmitDonateForm;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by yashika on 6/2/17.
 */

public class Donate extends AppIntro {

    private static final String TAG = Donate.class.getSimpleName();
    DonateView donateView;
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

        donateView = new DonateView();

        addSlide(donateView);
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
            submitDonateForm();
        }else {
            Log.d(TAG, "onDonePressed: not valid");
        }

    }

    /**
     * Function to submit donate request
     */
    private void submitDonateForm() {
        CommanUtils.showDialog(this);

        String donateCategory = donateView.getSelectedCategory();
        String donateImage = donateView.getDonateCategoryImage();

        IndivitualReqRegistration.Data  indData = organisationInformation.setIndividualInfo().getData();
        String address = addressInformation.getAddress();
        int pickUpOption = addressInformation.getPickUpOption();

        SubmitDonateForm submitDonateForm = new SubmitDonateForm();
        submitDonateForm.setAction(Social.SUBMIT_ACTION);
        submitDonateForm.setStatus(Social.DONATE);
        submitDonateForm.setEntity(Social.IND_ENTITY);

        SubmitDonateForm.Data data = new SubmitDonateForm.Data();
        data.setMobileno(indData.getMobileno());
        data.setEmailid(indData.getEmailid());
        data.setCategory(donateCategory);
        data.setAddress(address);
        data.setDeliverymode(pickUpOption == 0 ? "Pickup from Home" : "Pick up from Address");
        data.setImg(donateImage);
        data.setDate(donateView.getSelectedDate());
        submitDonateForm.setData(data);

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
        Call<SubmitDonateForm> call = mPaalanServices.submitDonateForm(submitDonateForm);
        call.enqueue(new Callback<SubmitDonateForm>() {
            @Override
            public void onResponse(Call<SubmitDonateForm> call, Response<SubmitDonateForm> response) {
                CommanUtils.hideDialog();
                Log.d(TAG, "onResponse: Succes :: "+response.body().getStatus());
            }

            @Override
            public void onFailure(Call<SubmitDonateForm> call, Throwable t) {
                CommanUtils.hideDialog();
                Log.d(TAG, "onResponse: Failure :: "+call.toString());
            }
        });

    }

    /**
     * Function to validate data
     * @return : validation result
     */
    private boolean validateData() {
        String donateCategory = donateView.getSelectedCategory();

        IndivitualReqRegistration.Data  data = organisationInformation.setIndividualInfo().getData();
        String address = addressInformation.getAddress();

        if (TextUtils.isEmpty(donateCategory)){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.select_category));
            return false;
        }else if (TextUtils.isEmpty(donateView.getSelectedDate())) {
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.date_should_not_be_empty));
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
