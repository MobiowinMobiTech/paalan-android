package com.phyder.paalan.activity.organization;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phyder.paalan.R;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrgRegistrationActivity extends AppCompatActivity {

    private static final String TAG = OrgRegistrationActivity.class.getSimpleName();

    Button btnRegister;

    String name, mobileNo, emailId, password, notificationId, deviceId, imeiNo, address, city, state, pincode, country;
    EditText edtName, edtMobileNo, edtEmailId, edtPassword, edtAdd, edtCity, edtState, edtPincode, edtCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_registration);

        btnRegister = (Button) findViewById(R.id.btn_register);

        // TODO: 7/1/17 Get request payload values from edittext and set it to reqPayload

        edtName = (EditText) findViewById(R.id.edit_ngo_name);
        edtMobileNo = (EditText) findViewById(R.id.edit_ngo_contact_number);
        edtEmailId = (EditText) findViewById(R.id.edit_ngo_email);
        edtPassword = (EditText) findViewById(R.id.edit_ngo_password);
        edtAdd = (EditText) findViewById(R.id.edit_ngo_postal_address);
        edtCity = (EditText) findViewById(R.id.edit_ngo_city);
        edtState = (EditText) findViewById(R.id.edit_ngo_state);
        edtPincode = (EditText) findViewById(R.id.edit_ngo_pin_code);
        edtCountry = (EditText) findViewById(R.id.edit_ngo_country);

        edtName.setText("Raman");
        edtMobileNo.setText("8879536984");
        edtEmailId.setText("dharmendr5.gupta@cmss.in");
        edtPassword.setText("1234567");
        edtAdd.setText("Mumbai");
        edtCity.setText("Mumbai");
        edtState.setText("Maharashtra");
        edtPincode.setText("400064");
        edtCountry.setText("India");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                mobileNo = edtMobileNo.getText().toString();
                emailId = edtEmailId.getText().toString();
                password = edtPassword.getText().toString();
                address = edtAdd.getText().toString();
                city = edtCity.getText().toString();
                state = edtState.getText().toString();
                pincode = edtPincode.getText().toString();
                country = edtCountry.getText().toString();

                Log.d(TAG, "onClick: " + name + "\n" + mobileNo + "\n" + emailId + "\n" + password + "\n" + address + "\n" + city + "\n" + state + "\n" + pincode + "\n" + country);
                if (NetworkUtil.isInternetConnected(OrgRegistrationActivity.this)) {
                    OrganizationReqResistration reqResistration = OrganizationReqResistration.get(name, mobileNo, emailId, password, notificationId, deviceId, imeiNo, address, city, state, pincode, country);

                    Device.newInstance(OrgRegistrationActivity.this);
                    Retrofit mRetrofit = NetworkUtil.getRetrofit();
                    PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

                    Call<OrganizationResRegistration> resRegistrationCall = mPaalanServices.orgRegistration(reqResistration);
                    resRegistrationCall.enqueue(new Callback<OrganizationResRegistration>() {
                        @Override
                        public void onResponse(Call<OrganizationResRegistration> call, Response<OrganizationResRegistration> response) {

                            Log.d(TAG, "onResponse: " + response);

                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equals("success")) {
                                    String successmsg = response.body().getMessage();
                                    Intent intent = new Intent(OrgRegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);

                                } else if (response.body().getStatus().equals("error")) {
                                    String reserrorMsg = response.body().getMessage();
                                    Log.d(TAG, "onResponse: " + reserrorMsg);
                                    ErrorDialog(reserrorMsg);
                                }
                            } else {
                                String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                                ErrorDialog(resFailedMsg);
                            }
                        }

                        @Override
                        public void onFailure(Call<OrganizationResRegistration> call, Throwable t) {
                            String errMsg = t.getMessage();
                            Log.d(TAG, "onFailure: " + errMsg);
                        }
                    });
                }
            }
        });
    }

    public void ErrorDialog(String reserrorMsg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrgRegistrationActivity.this);

        alertDialog.setTitle("Server Error");
        alertDialog.setMessage(reserrorMsg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i("Server Error Found", "");
            }
        });
        alertDialog.show();
    }
}
