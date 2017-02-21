package com.phyder.paalan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.ForgotPasswordRequest;
import com.phyder.paalan.payload.response.ForgotPasswordResponse;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Pramod Waghmare on 14/2/17.
 */
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = ForgotPassword.class.getSimpleName();
    CardView cardVerifyUsername, cardConfirmPassword, cardVerifyOTP;
    AutoCompleteTextViewOpenSansRegular edtUsername, edtPassword, edtConfirmPassword, edtOTP;
    ButtonOpenSansSemiBold btnVerifyUser, btnConfirmPassword, btnVerifyOTP;
    int otp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        cardVerifyUsername = (CardView)findViewById(R.id.card_view_verify_username);
        cardConfirmPassword = (CardView)findViewById(R.id.card_view_update_password);
        cardVerifyOTP = (CardView)findViewById(R.id.card_view_verify_otp);

        cardVerifyUsername.setVisibility(View.VISIBLE);

        edtUsername = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtUsername);
        edtPassword = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtPassword);
        edtConfirmPassword = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtConfirmPassword);
        edtOTP = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtOTP);


        btnVerifyUser = (ButtonOpenSansSemiBold)findViewById(R.id.btnVerify);
        btnConfirmPassword = (ButtonOpenSansSemiBold)findViewById(R.id.btnUpdatePassword);
        btnVerifyOTP = (ButtonOpenSansSemiBold)findViewById(R.id.btnVerifyOTP);

        btnVerifyUser.setOnClickListener(this);
        btnConfirmPassword.setOnClickListener(this);
        btnVerifyOTP.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVerify:
                if (edtUsername.getText().length() > 0 && !TextUtils.isEmpty(edtUsername.getText())){
                    verifyUser();
                }else
                    CommanUtils.showAlert(ForgotPassword.this,getString(R.string.forgot_password),getString(R.string.error_empty_name));
                break;
            case R.id.btnUpdatePassword:
                if (isPasswordValid())
                    updatePassword();
                break;
            case R.id.btnVerifyOTP:
                if (!TextUtils.isEmpty(edtOTP.getText())){
                    if (otp == Integer.parseInt(edtOTP.getText().toString()))
                        toggleView(false,false,true);
                    else
                        CommanUtils.showAlert(ForgotPassword.this,getString(R.string.forgot_password),getString(R.string.wrong_otp));
                }else
                    CommanUtils.showAlert(ForgotPassword.this,getString(R.string.forgot_password),getString(R.string.error_empty_otp));

                break;
        }
    }

    /**
     * Function used to update password
     */
    private void updatePassword() {
        Device.newInstance(this);
        CommanUtils.showDialog(this);

        ForgotPasswordRequest forgotPassword = new ForgotPasswordRequest();
        forgotPassword.setAction(Social.UPDATE_ACTION);
        forgotPassword.setType(Social.LOGIN_TYPE);
        forgotPassword.setEntity(Social.ORG_ENTITY);

        ForgotPasswordRequest.Data data = new ForgotPasswordRequest.Data();
        data.setPassword(edtPassword.getText().toString());
        data.setUserid(edtUsername.getText().toString());

        forgotPassword.setData(data);

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Call<ForgotPasswordResponse> reqForgotPasswordCall = mPaalanServices.forgotPassword(forgotPassword);
        reqForgotPasswordCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                CommanUtils.hideDialog();
                if (response.body().getStatus().equalsIgnoreCase("success")){
                    CommanUtils.showAlert(ForgotPassword.this, getString(R.string.forgot_password),
                            getString(R.string.password_update_success));
                    Intent loginIntent = new Intent(ForgotPassword.this,Login.class);
                    startActivity(loginIntent);
                    finish();
                }else {
                    CommanUtils.showAlert(ForgotPassword.this, getString(R.string.forgot_password), getString(R.string.technical_issue));
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                CommanUtils.hideDialog();
                CommanUtils.showAlert(ForgotPassword.this, getString(R.string.forgot_password), getString(R.string.technical_issue));
            }
        });
    }

    /**
     * Function used to validate password
     * @return : validation result
     */
    private boolean isPasswordValid() {
        if (TextUtils.isEmpty(edtPassword.getText())) {
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_empty_message));
            return false;
        }else if (!CommanUtils.isPasswordValid(edtPassword.getText().toString())){
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_length_message));
            return false;
        }else if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.confirm_password_empty_message));
            return false;
        }else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_validation_mesasage));
            return false;
        }

        return true;
    }

    /**
     * Function used to verify user
     */
    private void verifyUser() {

        Device.newInstance(this);
        CommanUtils.showDialog(this);

        ForgotPasswordRequest forgotPassword = new ForgotPasswordRequest();
        forgotPassword.setAction(Social.FORGOT);
        forgotPassword.setType(Social.LOGIN_TYPE);
        forgotPassword.setEntity(Social.ORG_ENTITY);

        ForgotPasswordRequest.Data data = new ForgotPasswordRequest.Data();
        data.setUserid(edtUsername.getText().toString());

        forgotPassword.setData(data);

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Call<ForgotPasswordResponse> reqForgotPasswordCall = mPaalanServices.forgotPassword(forgotPassword);
        reqForgotPasswordCall.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {
                CommanUtils.hideDialog();
                if (response.body().getData()[0].getOtp() != null){
                    otp = Integer.parseInt(response.body().getData()[0].getOtp());
                    toggleView(false,true,false);
                }else
                    CommanUtils.showAlert(ForgotPassword.this, getString(R.string.forgot_password),
                            response.body().getData()[0].getErrmsg());
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                CommanUtils.hideDialog();
                CommanUtils.showAlert(ForgotPassword.this, getString(R.string.forgot_password), getString(R.string.technical_issue));
            }
        });


    }

    /**
     * Function to toggle ui of forgot password
     * @param verifyUser : status to display verify username
     * @param verifyOtp : status to display verify otp
     * @param verifyPassword : status to display verify password
     */
    private void toggleView(boolean verifyUser, boolean verifyOtp, boolean verifyPassword) {
        if (verifyUser)
            cardVerifyUsername.setVisibility(View.VISIBLE);
        else
            cardVerifyUsername.setVisibility(View.INVISIBLE);
        if (verifyOtp)
            cardVerifyOTP.setVisibility(View.VISIBLE);
        else
            cardVerifyOTP.setVisibility(View.INVISIBLE);
        if (verifyPassword)
            cardConfirmPassword.setVisibility(View.VISIBLE);
        else
            cardConfirmPassword.setVisibility(View.INVISIBLE);
    }
}
