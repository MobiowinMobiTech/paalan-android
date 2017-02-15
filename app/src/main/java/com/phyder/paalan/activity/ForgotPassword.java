package com.phyder.paalan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;

import com.phyder.paalan.R;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;

/**
 * Created by yashika on 14/2/17.
 */
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    CardView cardVerifyUsername, cardConfirmPassword;
    AutoCompleteTextViewOpenSansRegular edtUsername, edtPassword, edtConfirmPassword;
    ButtonOpenSansSemiBold btnVerifyUser, btnConfirmPassword;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);

        cardVerifyUsername = (CardView)findViewById(R.id.card_view_verify_username);
        cardConfirmPassword = (CardView)findViewById(R.id.card_view_update_password);

        cardVerifyUsername.setVisibility(View.VISIBLE);

        edtUsername = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtUsername);
        edtPassword = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtPassword);
        edtConfirmPassword = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.edtConfirmPassword);


        btnVerifyUser = (ButtonOpenSansSemiBold)findViewById(R.id.btnVerify);
        btnConfirmPassword = (ButtonOpenSansSemiBold)findViewById(R.id.btnUpdatePassword);

        btnVerifyUser.setOnClickListener(this);
        btnConfirmPassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVerify:
                if (edtUsername.getText().length() > 0 && !TextUtils.isEmpty(edtUsername.getText())){
                    verifyUser();
                }
                break;
            case R.id.btnUpdatePassword:
                if (isPasswordValid())
                    updatePassword();
                break;
        }
    }

    /**
     * Function used to update password
     */
    private void updatePassword() {

    }

    /**
     * Function used to validate password
     * @return : validation result
     */
    private boolean isPasswordValid() {
        if (TextUtils.isEmpty(edtPassword.getText())) {
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_empty_message));
            return false;
        }else if (TextUtils.isEmpty(edtConfirmPassword.getText())) {
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_empty_message));
            return false;
        }else if (!CommanUtils.isPasswordValid(edtPassword.getText().toString())){
            CommanUtils.showAlert(this, getString(R.string.forgot_password), getString(R.string.password_length_message));
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

    }
}
