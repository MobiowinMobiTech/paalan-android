package com.phyder.paalan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.phyder.paalan.R;
import com.phyder.paalan.payload.request.individual.IndivitualReqRegistration;
import com.phyder.paalan.services.RegisterUserInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yashika on 18/1/17.
 */
public class BasicRegistrationInfo extends Fragment {

    private  RegisterUserInterface registerUserInterface;
    private EditText edtName, edtEmail, edtContactNo, edtPassword, edtConfirmPassword;
  git

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_basic_info,null);

        edtName = (EditText)view.findViewById(R.id.edtName);
        edtEmail = (EditText)view.findViewById(R.id.edtEmail);
        edtContactNo = (EditText)view.findViewById(R.id.edtContactNumber);
        edtPassword = (EditText)view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)view.findViewById(R.id.edtConfirmPassword);

        btnNext = (Button)view.findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager telephonyManager = (TelephonyManager) getActivity()
                        .getSystemService(Context.TELEPHONY_SERVICE);

                IndivitualReqRegistration indivitualReqRegistration = IndivitualReqRegistration.get(
                        edtName.getText().toString(),
                                edtEmail.getText().toString(),
                                edtContactNo.getText().toString(),
                                edtPassword.getText().toString(),
                        telephonyManager.getDeviceId(),"","");
                registerUserInterface.setIndividualRegInfo(indivitualReqRegistration);
                registerUserInterface.navigateNext();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerUserInterface = (RegisterUserInterface)context;
    }

    public boolean isValidPassword() {
        return  edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())
                && edtPassword.getText().length() > 0;
    }

    public boolean isValidEmailId(String email){
        Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

        Matcher matcher1 = pattern1.matcher(email);
        Log.d("", "isBasicInfoFilled: valid email i/p:"+email);
        if (!matcher1.matches()) {
            //show your message if not matches with email pattern
            return false;
        }else
            return true;
    }


    public void isIndividualUser(boolean isIndividualRegistration) {
        if (isIndividualRegistration)
            btnNext.setText(getString(R.string.register));
    }
}
