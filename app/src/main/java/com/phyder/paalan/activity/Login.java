package com.phyder.paalan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.db.DBAdapter;
import com.phyder.paalan.payload.request.RequestLogin;
import com.phyder.paalan.payload.response.ResponseLogin;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.social.Social;
import com.phyder.paalan.utils.AutoCompleteTextViewOpenSansRegular;
import com.phyder.paalan.utils.ButtonOpenSansSemiBold;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.EditTextOpenSansRegular;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getCanonicalName();

    private AutoCompleteTextViewOpenSansRegular mEmailView;
    private EditTextOpenSansRegular mPasswordView;
    private ButtonOpenSansSemiBold btnSignIn;
    private static final int PERMISSION_READ_STATE = 1;
    private String deviceID = "", email = "", password = "";
    private PreferenceUtils pref;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializations();
        clickEventFire();
    }


    /**
     * components initializing here
     */
    private void initializations() {

        pref = new PreferenceUtils(this);
        dbAdapter = new DBAdapter(this);
        mEmailView = (AutoCompleteTextViewOpenSansRegular) findViewById(R.id.email);
        mPasswordView = (EditTextOpenSansRegular) findViewById(R.id.password);

        mEmailView.setText("7709642004");
        mPasswordView.setText("cmss");

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        btnSignIn = (ButtonOpenSansSemiBold) findViewById(R.id.btnSignIn);
    }

    /**
     * clicking action event fire here
     */
    private void clickEventFire() {

        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                email = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();

//                if(TextUtils.isEmpty(email)){
//                    mEmailView.setError(getString(R.string.error_field_required));
//                } else if(!CommanUtils.isEmailValid(email)){
//                    mEmailView.setError(getString(R.string.error_invalid_email));
//                } else if (TextUtils.isEmpty(password)) {
//                    mPasswordView.setError(getString(R.string.error_empty_password));
//                } else if(!CommanUtils.isPasswordValid(password)){
//                    mPasswordView.setError(getString(R.string.error_invalid_password));
//                } else {

                    getRetrofitCall();

//                Intent intent = new Intent(LoginActivity.this, ActivityFragmentPlatform.class);
//                intent.putExtra("LOGIN", loginType);
//                startActivity(intent);
//                }
            }
//            }
        });

    }






    public void getRetrofitCall() {

        if(NetworkUtil.isInternetConnected(Login.this)) {

            CommanUtils.showDialog(Login.this);
            Device.newInstance(Login.this);
            RequestLogin reqLogin = RequestLogin.get( email, password);

            Retrofit mRetrofit = NetworkUtil.getRetrofit();
            PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

            final Call<ResponseLogin> resLogin = mPaalanServices.paalanLogin(reqLogin);

            resLogin.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    Log.e(TAG, "onResponse: " + response.body());
                    CommanUtils.hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("success")) {
                            Toast.makeText(Login.this, getResources().getString(R.string.logggedIn),
                                    Toast.LENGTH_SHORT).show();
                            pref.setOrgID(response.body().getData()[0].getOrgregdata()[0].getOrgId().toString());
                            pref.setUserName(response.body().getData()[0].getOrgregdata()[0].getName().toString());
                            dbAdapter.open();
                            dbAdapter.insertProfile(
                                    response.body().getData()[0].getOrgprofiledata()[0].getDpImgLink(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getRole(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getRegistrationNo(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getFbLink(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getLinkedinLink(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getWebsiteLink(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getTwitterLink(),
                                    response.body().getData()[0].getOrgprofiledata()[0].getPresenceArea());
                            dbAdapter.close();
                            pref.setLogin(true);
                            pref.setLoginType(Social.ORG_ENTITY);
                            Intent intent = new Intent(Login.this, ActivityFragmentPlatform.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Login.this.finish();

                        } else {
                            Toast.makeText(Login.this, getResources().getString(R.string.error_went_wrong),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }else if(response.body()==null){
                        Toast.makeText(Login.this, getResources().getString(R.string.error_server), Toast.LENGTH_LONG)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    CommanUtils.hideDialog();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(Login.this, getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                            .show();
                }
            });
        }else{
            CommanUtils.showAlertDialog(Login.this,getResources().getString(R.string.error_internet));
        }
    }

}




