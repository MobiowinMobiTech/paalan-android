package com.phyder.paalan.activity.organization;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.payload.request.organization.OrganizationReqLogin;
import com.phyder.paalan.payload.response.organization.OrganizationResLogin;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ORGLoginActivity extends AppCompatActivity {

    Button ORGbtnSignIn, btnIndLogin, btnORGLogin;
    private String TAG = ORGLoginActivity.class.getSimpleName();

    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_log);

        ORGbtnSignIn = (Button) findViewById(R.id.email_sign_in_button);
        btnORGLogin = (Button) findViewById(R.id.btn_log_in_org);
        btnIndLogin = (Button) findViewById(R.id.btn_log_in_ind);
        btnORGLogin.setBackgroundColor(Color.parseColor("#c2c2c2"));
        edtEmail = (EditText) findViewById(R.id.org_email);
        edtPassword = (EditText) findViewById(R.id.org_password);
        btnIndLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ORGLoginActivity.this, LoginActivity.class);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(intent);
                finish();
            }
        });
        ORGbtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imeiNo = "123456789";
                Log.d(TAG, "attemptLogin: " + imeiNo);
                String userID = "1234567890";
                String orgPassword = "123456789";
                Device.newInstance(ORGLoginActivity.this);

//                if (TextUtils.isEmpty(edtEmail.getText()) || TextUtils.isEmpty(edtPassword.getText())) {
//                    Toast.makeText(ORGLoginActivity.this, "Login Area can not be Empty.", Toast.LENGTH_SHORT).show();
//                } else if (edtEmail.getText() == null || edtPassword.getText() == null) {
//                    Toast.makeText(ORGLoginActivity.this, "Login Area can not be Empty.", Toast.LENGTH_SHORT).show();
//                } else if (orgPassword.length() > 7) {
//                    Toast.makeText(ORGLoginActivity.this, "Password length can not be less than six", Toast.LENGTH_SHORT).show();
//                } else {
                OrganizationReqLogin reqLogin = OrganizationReqLogin.get(imeiNo, userID, orgPassword);
                Retrofit mRetrofit = NetworkUtil.getRetrofit();
                PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                Call<OrganizationResLogin> loginCall = mPaalanServices.paalanORGLogin(reqLogin);
                loginCall.enqueue(new Callback<OrganizationResLogin>() {
                    @Override
                    public void onResponse(Call<OrganizationResLogin> call, Response<OrganizationResLogin> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("success")) {
                                Log.d(TAG, "onResponse: " + response.body().getData());
                            } else if (response.body().getStatus().equals("error")) {

                            }
                        } else {
                            Log.d(TAG, "onResponse: " + response.body().getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<OrganizationResLogin> call, Throwable t) {
                        Log.d(TAG, "onResponse: " + t.getMessage());
                    }
                });
//                }
            }
        });
    }
}
