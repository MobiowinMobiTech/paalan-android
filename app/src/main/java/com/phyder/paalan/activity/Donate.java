package com.phyder.paalan.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.phyder.paalan.R;
import com.phyder.paalan.fragments.DonateView;
import com.phyder.paalan.fragments.Donorinformation;
import com.phyder.paalan.payload.request.SubmitDonateForm;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.payload.response.SubmitDonateResponse;
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
    Donorinformation donorinformation;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void init(Bundle savedInstanceState) {


        Bundle bundle = new Bundle();
        bundle.putBoolean("isForDonate",true);


        donorinformation = new Donorinformation();
        donateView = new DonateView();

        addSlide(donorinformation);
        addSlide(donateView);


        // Override bar/separator color.
        setBarColor(Color.parseColor("#00BCD4"));
        setSeparatorColor(Color.parseColor("#FFFFFF"));

        setFadeAnimation();

        showSkipButton(false);

        //set text to done button
        setDoneText("Donate");



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submitDonateForm() {
        CommanUtils.showDialog(this);

        String donateCategory = donateView.getSelectedCategory();
        String donateImage = donateView.getDonateCategoryImage();

        IndivitualReqRegistration.Data  indData = donorinformation.setIndividualInfo().getData();
        String address = donorinformation.getAddress();
        int pickUpOption = donateView.getPickUpOption();

        SubmitDonateForm submitDonateForm = new SubmitDonateForm();
        submitDonateForm.setAction(Social.SUBMIT_ACTION);
        submitDonateForm.setType(Social.DONATE);
        submitDonateForm.setEntity(Social.IND_ENTITY);

        SubmitDonateForm.Data data = new SubmitDonateForm.Data();
        data.setMobileno(indData.getMobileno());
        data.setEmailid(indData.getEmailid());
        data.setCategory(donateCategory);
        data.setAddress(address);
        data.setCollectionMode(pickUpOption == 0 ? "Home Pickup" : "Other");
        data.setImg(donateImage);
        data.setDaterequest(donateView.getSelectedDate());
        data.setFreetext1("");
        data.setFreetext2("");
        data.setFreetext3("");
        data.setFreetext4("");

        submitDonateForm.setData(data);


        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);
        Call<SubmitDonateResponse> call = mPaalanServices.submitDonateForm(submitDonateForm);
        call.enqueue(new Callback<SubmitDonateResponse>() {
            @Override
            public void onResponse(Call<SubmitDonateResponse> call, Response<SubmitDonateResponse> response) {
                CommanUtils.hideDialog();
                Log.d(TAG, "onResponse: success :: "+call.toString());
                CommanUtils.showToast(Donate.this,getString(R.string.request_submitted_success));
                finish();
            }

            @Override
            public void onFailure(Call<SubmitDonateResponse> call, Throwable t) {
                CommanUtils.hideDialog();
                Log.d(TAG, "onResponse: Failure :: "+call.toString());
                CommanUtils.showToast(Donate.this,getString(R.string.technical_issue));
            }
        });

    }

    /**
     * Function to validate data
     * @return : validation result
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean validateData() {
        String donateCategory = donateView.getSelectedCategory();

        Log.d(TAG, "validateData: CAT "+donateCategory);

        IndivitualReqRegistration.Data  data = donorinformation.setIndividualInfo().getData();
        String address = donorinformation.getAddress();

        if (TextUtils.isEmpty(donateCategory)){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.select_category));
            return false;
        }else if (TextUtils.isEmpty(donateView.getSelectedDate())) {
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.date_should_not_be_empty));
            return false;
        }else if (TextUtils.isEmpty(data.getName())) {
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.error_empty_name));
            return false;
        }else if(TextUtils.isEmpty(data.getEmailid()) || !donorinformation.isValidEmailId(data.getEmailid())){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.email_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(data.getMobileno()) || data.getMobileno().length() < 10 ){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.mobile_validation_mesasage));
            return false;
        }else if (TextUtils.isEmpty(address.replace(" ","").replace(",",""))){
            CommanUtils.showAlert(this,getString(R.string.donate),getString(R.string.addess_cannot_be_empty));
            return false;
        }

        return true;
    }
}
