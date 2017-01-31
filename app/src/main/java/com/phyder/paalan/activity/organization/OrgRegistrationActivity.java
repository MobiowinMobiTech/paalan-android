package com.phyder.paalan.activity.organization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.phyder.paalan.R;
import com.phyder.paalan.activity.LoginActivity;
import com.phyder.paalan.payload.request.organization.OrganizationReqResistration;
import com.phyder.paalan.payload.response.organization.OrganizationResRegistration;
import com.phyder.paalan.services.Device;
import com.phyder.paalan.services.PaalanServices;
import com.phyder.paalan.utils.CommanUtils;
import com.phyder.paalan.utils.Config;
import com.phyder.paalan.utils.NetworkUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Class used to register user
 * @author Pramod Waghmare
 * CMSS Phyder
 */
public class OrgRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = OrgRegistrationActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    String name, mobileNo, emailId, password, notificationId, deviceId, imeiNo, address, city, state, pincode, country;
    EditText edtName, edtMobileNo, edtEmailId, edtPassword, edtAdd, edtCity, edtState, edtPincode, edtCountry;
    private boolean isAOrganisation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_registration);
        initializeView();
        initilizeBroadCastReceiver();

        Device.newInstance(this);

        imeiNo = Device.getImeiNo(this);
        deviceId = Device.getDeviceId(this);

        notificationId = CommanUtils.getFBRegId(this);


        Log.d(TAG, "onCreate: REG ID:"+ CommanUtils.getFBRegId(this));

    }

    private void initilizeBroadCastReceiver() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
            // checking for type intent filter
            if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                // gcm successfully registered
                // now subscribe to `global` topic to receive app wide notifications
                FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                notificationId = CommanUtils.getFBRegId(OrgRegistrationActivity.this);
                Log.d(TAG, "onReceive: Notification id: "+notificationId);
            }else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)){
                // new push notification is received
                String message = intent.getStringExtra("message");
                Log.d(TAG, "onReceive: FROM SERVER "+message);
                Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
            }
            }
        };
    }

    /**
     * Function to initialize view
     */
    private void initializeView() {
//        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.addressLayout);
//        if (isAOrganisation)
//            linearLayout.setVisibility(View.VISIBLE);
//        else
//            linearLayout.setVisibility(View.INVISIBLE);
//
//        Button btnRegister = (Button) findViewById(R.id.btn_register);
//        edtName = (EditText) findViewById(R.id.edit_ngo_name);
//        edtMobileNo = (EditText) findViewById(R.id.edtContactNumber);
//        edtEmailId = (EditText) findViewById(R.id.edit_ngo_email);
//        edtPassword = (EditText) findViewById(R.id.edtPassword);
//        edtAdd = (EditText) findViewById(R.id.edtPostalAddress);
//        edtCity = (EditText) findViewById(R.id.edtCity);
//        edtState = (EditText) findViewById(R.id.edtState);
//        edtPincode = (EditText) findViewById(R.id.edtPinCode);
//        edtCountry = (EditText) findViewById(R.id.edtCountry);

//        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
//                if (NetworkUtil.checkInternet(OrgRegistrationActivity.this))
                    if (isValidData()){
                        if (isAOrganisation){
                            registerUserAsOrganization();
                        }else {

                        }
                    }
                    else
                        NetworkUtil.alert(OrgRegistrationActivity.this,"All fields are mandatory.");
                break;
        }
    }

    private void registerUserAsIndividuals() {

    }

    /**
     * Function used to register user As organisation with server
     */
    private void registerUserAsOrganization() {
        Retrofit mRetrofit = NetworkUtil.getRetrofit();
        PaalanServices mPaalanServices = mRetrofit.create(PaalanServices.class);

        Log.d(TAG, "onReceive registerUserAsOrganization: "+notificationId);

        OrganizationReqResistration reqResistration =
                OrganizationReqResistration.get(name, mobileNo, emailId, password, notificationId,
                        deviceId, imeiNo, address, city, state, pincode, country);

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
                        Log.e(TAG, "onResponse: " + reserrorMsg);
                        NetworkUtil.alert(OrgRegistrationActivity.this,reserrorMsg);
                    }
                } else {
                    String resFailedMsg = "Erver Error Due to some thechnical issue. Please try after sometime.";
                    NetworkUtil.alert(OrgRegistrationActivity.this,resFailedMsg);
                }
            }

            @Override
            public void onFailure(Call<OrganizationResRegistration> call, Throwable t) {
                String errMsg = t.getMessage();
                Log.d(TAG, "onFailure: " + errMsg);
            }
        });


    }

    /**
     * Function to apply validations
     * @return  true : if data is valid
     *           false : if data not valid
     */
    private boolean isValidData() {
        name = edtName.getText().toString();
        mobileNo = edtMobileNo.getText().toString();
        emailId = edtEmailId.getText().toString();
        password = edtPassword.getText().toString();
        address = edtAdd.getText().toString();
        city = edtCity.getText().toString();
        state = edtState.getText().toString();
        pincode = edtPincode.getText().toString();
        country = edtCountry.getText().toString();


        Log.d(TAG, "isValidData: NAME "+ TextUtils.isEmpty(name));

        boolean isIndividualFieldsEmpty = TextUtils.isEmpty(name) || TextUtils.isEmpty(mobileNo) ||
                TextUtils.isEmpty(emailId) || TextUtils.isEmpty(password);

        Log.d(TAG, "isValidData: IND VALID "+ isIndividualFieldsEmpty);


        if (isAOrganisation){
            boolean isOrgFieldsEmpty = TextUtils.isEmpty(address) || TextUtils.isEmpty(city)
                    || TextUtils.isEmpty(state) || TextUtils.isEmpty(pincode) || TextUtils.isEmpty(country);
            if (!isOrgFieldsEmpty && !isIndividualFieldsEmpty)
                return true;
            else
                return false;
        }else {
            if (!isIndividualFieldsEmpty)
                return true;
            else
                return false;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}
