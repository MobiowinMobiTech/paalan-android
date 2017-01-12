package com.phyder.paalan.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.organization.OrgRegistrationActivity;
import com.phyder.paalan.fragments.FragmentDashBoard;
import com.phyder.paalan.payload.request.RequestLogin;
import com.phyder.paalan.payload.response.ResponseLogin;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.Manifest.permission.READ_PHONE_STATE;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getCanonicalName();

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView txtSignUp;
    private RadioGroup radioGroupLoginAS;
    private Button btnSignIn;
    private String loginType = "org";
    private static final int PERMISSION_READ_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializations();
        clickEventFire();
        requestPermission();

    }


    /**
     * components initializing here
     */
    private void initializations() {

        txtSignUp = (TextView) findViewById(R.id.txt_sign_up_organization);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mEmailView.setText("962383731");
        mPasswordView.setText("123456789");

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        radioGroupLoginAS = (RadioGroup) findViewById(R.id.radioGroupLoginAs);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

    }

    /**
     * clicking action event fire here
     */
    private void clickEventFire() {

        radioGroupLoginAS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                loginType = (checkedId == R.id.rb_org_login ? "org" : "individual");
            }
        });


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

                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

//                if(TextUtils.isEmpty(email)){
//                    mEmailView.setError(getString(R.string.error_field_required));
//                } else if(!CommanUtils.isEmailValid(email)){
//                    mEmailView.setError(getString(R.string.error_invalid_email));
//                } else if (TextUtils.isEmpty(password)) {
//                    mPasswordView.setError(getString(R.string.error_empty_password));
//                } else if(!CommanUtils.isPasswordValid(password)){
//                    mPasswordView.setError(getString(R.string.error_invalid_password));
//                } else {


                Device.newInstance(LoginActivity.this);
                RequestLogin reqLogin = RequestLogin.get(CommanUtils.getImeiNo(LoginActivity.this), email, password, loginType);

                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<ResponseLogin> resLogin = mPaalanServices.paalanLogin(reqLogin);

                resLogin.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        Log.e(TAG, "onResponse: ");
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "You LoggedIn Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, FragmentDashBoard.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
//            }
        });


    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_READ_STATE);

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
                    // permission granted!
                    // you may now do the action that requires this permission
                } else {
                    // permission denied
                }
                return;
            }

        }
    }

}