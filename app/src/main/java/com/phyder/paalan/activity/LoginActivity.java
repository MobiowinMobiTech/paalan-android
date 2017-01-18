package com.phyder.paalan.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.organization.OrgRegistrationActivity;
import com.phyder.paalan.payload.request.RequestLogin;
import com.phyder.paalan.payload.response.ResponseLogin;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;
import com.phyder.paalan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getCanonicalName();

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView txtSignUp;
    private Button btnSignIn;
    private String loginType = "org";
    private static final int PERMISSION_READ_STATE = 1;
    private String deviceID = "", email = "", password = "";
    private PreferenceUtils pref;

    private TextView txtLoginAsOrg, txtLoginAsInd;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolBar();
        initializations();
        clickEventFire();
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.login);
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons));
        setSupportActionBar(toolbar);
    }


    /**
     * components initializing here
     */
    private void initializations() {

        pref = new PreferenceUtils(LoginActivity.this);
        txtSignUp = (TextView) findViewById(R.id.txt_sign_up_organization);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

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

        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        txtLoginAsInd = (TextView) findViewById(R.id.btnLoginAsIND);
        txtLoginAsOrg = (TextView) findViewById(R.id.btnLoginAsORG);

        txtLoginAsOrg.setBackgroundResource(R.drawable.shape_rounded_border_white_bg);
        txtLoginAsOrg.setTextColor(getResources().getColor(R.color.icons));

        txtLoginAsInd.setBackgroundResource(R.drawable.shape_rounded_borderwhite_ind);
        txtLoginAsInd.setTextColor(getResources().getColor(R.color.primary));

    }

    /**
     * clicking action event fire here
     */
    private void clickEventFire() {

        txtSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OrgRegistrationActivity.class);
                startActivity(intent);
            }
        });

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

                if (requestPermission()) {
                    deviceID = CommanUtils.getImeiNo(LoginActivity.this);
                    getRetrofitCall();
                }

//                Intent intent = new Intent(LoginActivity.this, ActivityFragmentPlatform.class);
//                intent.putExtra("LOGIN", loginType);
//                startActivity(intent);
//                }
            }
//            }
        });


        txtLoginAsInd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtLoginAsOrg.setBackgroundResource(R.drawable.shape_rounded_borderwhite_org);
                txtLoginAsOrg.setTextColor(getResources().getColor(R.color.primary));

                txtLoginAsInd.setBackgroundResource(R.drawable.shape_rounded_border_white_bg);
                txtLoginAsInd.setTextColor(getResources().getColor(R.color.icons));

                loginType = "individual";
            }
        });

        txtLoginAsOrg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                txtLoginAsInd.setBackgroundResource(R.drawable.shape_rounded_borderwhite_ind);
                txtLoginAsInd.setTextColor(getResources().getColor(R.color.primary));

                txtLoginAsOrg.setBackgroundResource(R.drawable.shape_rounded_border_white_bg);
                txtLoginAsOrg.setTextColor(getResources().getColor(R.color.icons));

                loginType = "org";
            }
        });

    }


    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_READ_STATE);
        }
        return false;
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ_STATE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    deviceID = CommanUtils.getImeiNo(LoginActivity.this);
                    getRetrofitCall();
                } else {
                    //permission is not granted
                    Toast.makeText(LoginActivity.this, "not granted : " + grantResults[0], Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    public void getRetrofitCall() {
        CommanUtils.showDialog(LoginActivity.this);
        Device.newInstance(LoginActivity.this);
        RequestLogin reqLogin = RequestLogin.get(deviceID, email, password, loginType);

        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        final Call<ResponseLogin> resLogin = mPaalanServices.paalanLogin(reqLogin);

        resLogin.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                Log.e(TAG, "onResponse: " + response.body());
                CommanUtils.hideDialog();
                if (response.isSuccessful()) {
                    if(response.body().getStatus().equals("success")){

                        pref.setOrgID(response.body().getData()[0].getOrgregdata()[0].getOrgId().toString());
                        pref.setUserName(response.body().getData()[0].getOrgregdata()[0].getName().toString());
                        Intent intent = new Intent(LoginActivity.this, ActivityFragmentPlatform.class);
                        intent.putExtra("LOGIN", loginType);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.logggedIn), Toast.LENGTH_SHORT).show();
                    }else  {
                            Toast.makeText(LoginActivity.this,getResources().getString(R.string.error_went_wrong), Toast.LENGTH_LONG)
                                    .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                CommanUtils.hideDialog();
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this,getResources().getString(R.string.error_timeout), Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
